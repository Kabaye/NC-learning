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
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final DefaultOrdersProductsRelationRepository ordersProductsRelationRepository;
    private final ProductRepository productRepository;

    private final Function<Float, Float> convertTotalPriceToDB;
    private final Function<Float, Float> convertTotalPriceFromDB;

    public OrderService(OrderRepository orderRepository, DefaultOrdersProductsRelationRepository ordersProductsRelationRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.ordersProductsRelationRepository = ordersProductsRelationRepository;
        this.productRepository = productRepository;

        convertTotalPriceFromDB = (fl) -> fl / 1000;

        convertTotalPriceToDB = (fl) -> {
            long l = (long) (fl * 1000);
            return (float) l;
        };

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
                .map(order1 -> OrderUtils.postProcessOrderSum(order1, convertTotalPriceToDB))
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

    public Mono<Order> findOrder(Integer id) {
        final Mono<Order> foundOrder = orderRepository.findById(id);
        return foundOrder.flatMap(order -> ordersProductsRelationRepository.getAllOrderProducts(order.getId())
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
                                })).then(Mono.just(order)).map(order1 -> OrderUtils.postProcessOrderSum(order1, convertTotalPriceFromDB)));
    }
}
