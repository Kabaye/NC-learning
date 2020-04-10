package edu.netcracker.order.app.order.service;

import edu.netcracker.order.app.order.entity.Order;
import edu.netcracker.order.app.order.repository.OrderRepository;
import edu.netcracker.order.app.order_product.entity.OrdersProductsRelationModel;
import edu.netcracker.order.app.order_product.repository.DefaultOrdersProductsRelationRepository;
import edu.netcracker.order.app.product.entity.Product;
import edu.netcracker.order.app.product.repository.ProductRepository;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
        final Mono<Order> orderMono = orderRepository.save(order);
        return orderMono.map(ord -> ord.getProducts().stream().parallel()
                .map(productIntegerPair -> new OrdersProductsRelationModel(null, ord.getId(),
                        productIntegerPair.getFirst().getId(), productIntegerPair.getSecond()))
                .collect(Collectors.toList()))
                .map(ordersProductsRelationRepository::saveOrderProductRelation)
                .then(orderMono);
    }

    public Mono<Order> findOrder(Integer id) {
        final Mono<Order> foundOrder = orderRepository.findById(id);
        return foundOrder
                .flatMapMany(order -> ordersProductsRelationRepository.getAllOrderProducts(order.getId()))
                .flatMap(ordersProductsRelationModel -> foundOrder.map(order -> {
                    Product product = new Product();
                    product.setId(ordersProductsRelationModel.getProductId());
                    order.getProducts().add(Pair.of(product, ordersProductsRelationModel.getAmount()));
                    return order;
                })).flatMap(order -> productRepository.findAllById(order.getProducts().stream()
                        .parallel()
                        .map(Pair::getFirst)
                        .map(Product::getId)
                        .collect(Collectors.toList())
                ).map(product -> {
                    Product tempProduct = order.getProducts().stream()
                            .map(Pair::getFirst)
                            .filter(order1 -> order1.getId().equals(order.getId()))
                            .findFirst()
                            .orElseThrow(RuntimeException::new);
                    tempProduct.setPrice(product.getPrice());
                    tempProduct.setCurrency(product.getCurrency());
                    tempProduct.setDescription(product.getDescription());
                    tempProduct.setName(product.getName());
                    return order;
                })).next();
    }
}
