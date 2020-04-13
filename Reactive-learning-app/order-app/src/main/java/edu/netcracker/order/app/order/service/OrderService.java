package edu.netcracker.order.app.order.service;

import edu.netcracker.order.app.order.entity.Order;
import edu.netcracker.order.app.order.repository.OrderRepository;
import edu.netcracker.order.app.order.utils.OrderUtils;
import edu.netcracker.order.app.order_product.entity.OrdersProductsRelationModel;
import edu.netcracker.order.app.order_product.repository.DefaultOrdersProductsRelationRepository;
import edu.netcracker.order.app.product.entity.Product;
import edu.netcracker.order.app.product.repository.ProductRepository;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import utils.utils.MoneyUtils;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final DefaultOrdersProductsRelationRepository ordersProductsRelationRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, DefaultOrdersProductsRelationRepository ordersProductsRelationRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.ordersProductsRelationRepository = ordersProductsRelationRepository;
        this.productRepository = productRepository;
    }

    public Mono<Order> saveOrder(Order order) {
        return Mono.just(order).flatMap(ord -> productRepository.findAllById(ord.getProducts().stream()
                .map(productIntegerPair -> productIntegerPair.getFirst().getId())
                .collect(Collectors.toList()))
                .map(product -> {
                    ord.getProducts().stream()
                            .filter(productIntegerPair -> productIntegerPair.getFirst().getId().equals(product.getId()))
                            .forEach(productIntegerPair -> {
                                productIntegerPair.getFirst().setPrice(product.getPrice());
                                productIntegerPair.getFirst().setName(product.getName());
                                productIntegerPair.getFirst().setDescription(product.getDescription());
                            });
                    return ord;
                })
                .then(Mono.just(order)))
                .map(order1 -> OrderUtils.postProcessOrderSum(order1, MoneyUtils::convertToDBPrecision))
                .flatMap(orderRepository::save)
                .flatMap(ord -> ordersProductsRelationRepository.saveOrderProductRelation(ord.getProducts().
                        stream().
                        parallel()
                        .map(productIntegerPair -> new
                                OrdersProductsRelationModel(null, ord.getId(),
                                productIntegerPair.getFirst().getId(), productIntegerPair.getSecond()))
                        .collect(Collectors.toList())).
                        then(Mono.just(ord)));
    }

    public Mono<Order> updateOrder(Integer id, Order order) {
        return orderRepository.findById(id).flatMap(order1 -> {
            if (Objects.isNull(order1)) {
                return Mono.error(new RuntimeException("No such product for provided id."));
            }
            order.setId(id);
            return orderRepository.save(order);
        });
    }

    public Mono<Order> findOrder(Integer id) {
        final Mono<Order> foundOrder = orderRepository.findById(id);
        return foundOrder.flatMap(this::findWithAllDetails);
    }

    public Flux<Order> findAll() {
        return orderRepository.findAll()
                .flatMap(this::findWithAllDetails);
    }

    public Mono<Void> deleteOrder(Integer id) {
        return ordersProductsRelationRepository.deleteOrderProductsRelation(id).then(orderRepository.deleteById(id));
    }

    public Mono<Order> addProduct(Integer orderId, Integer productId, Integer amount) {
        return this.findOrder(orderId)
                .flatMap(order -> ordersProductsRelationRepository.addProductToOrder(new OrdersProductsRelationModel(null, orderId, productId, amount))
                        .map(ordersProductsRelationModel -> {
                            order.getProducts().add(Pair.of(new Product(productId, null, null, null), amount));
                            return ordersProductsRelationModel;
                        }).flatMap(ordersProductsRelationModel -> productRepository.findById(productId)
                                .map(product -> {
                                    order.getProducts().stream()
                                            .filter(productIntegerPair -> product.getId().equals(productIntegerPair.getFirst().getId()))
                                            .forEach(productIntegerPair -> {
                                                productIntegerPair.getFirst().setName(product.getName());
                                                productIntegerPair.getFirst().setDescription(product.getDescription());
                                                productIntegerPair.getFirst().setPrice(product.getPrice());
                                            });
                                    return OrderUtils.postProcessOrderSum(order, MoneyUtils::convertToDBPrecision);
                                })
                        )).flatMap(order -> updateOrder(orderId, order));
    }

    public Mono<Order> deleteProduct(Integer orderId, Integer productId) {
        return this.findOrder(orderId)
                .flatMap(order -> ordersProductsRelationRepository.deleteProductFromOrder(orderId, productId).then(Mono.just(order)))
                .flatMap(order -> productRepository.findById(productId)
                        .map(product -> {
                            order.getProducts().stream()
                                    .filter(productIntegerPair -> product.getId().equals(productIntegerPair.getFirst().getId()))
                                    .findAny()
                                    .ifPresent(productIntegerPair -> order.getProducts().remove(productIntegerPair));
                            return OrderUtils.postProcessOrderSum(order, MoneyUtils::convertToDBPrecision);
                        })
                ).flatMap(order -> updateOrder(orderId, order));
    }

    private Mono<? extends Order> findWithAllDetails(Order order) {
        return ordersProductsRelationRepository.getAllOrderProducts(order.getId())
                .collectList()
                .map(ordersProductsRelationModels -> {
                    ordersProductsRelationModels.forEach(ordersProductsRelationModel -> {
                        Product product = new Product();
                        product.setId(ordersProductsRelationModel.getProductId());
                        order.getProducts().add(Pair.of(product, ordersProductsRelationModel.getAmount()));
                    });
                    return ordersProductsRelationModels;
                }).flatMap(ordersProductsRelationModels ->
                        productRepository.findAllById(ordersProductsRelationModels.stream()
                                .parallel()
                                .map(OrdersProductsRelationModel::getProductId)
                                .collect(Collectors.toList()))
                                .collectList()
                                .map(products -> {
                                    order.setProducts(products.stream()
                                            .map(product -> Pair.of(product, ordersProductsRelationModels.stream()
                                                    .filter(ordersProductsRelationModel -> ordersProductsRelationModel.getProductId().equals(product.getId()))
                                                    .findFirst()
                                                    .orElseThrow(RuntimeException::new)
                                                    .getAmount()))
                                            .collect(Collectors.toList()));
                                    return products;
                                })).then(Mono.just(order))
                .map(order1 -> OrderUtils.postProcessOrderSum(order1, MoneyUtils::convertFromDbPrecision));
    }
}
