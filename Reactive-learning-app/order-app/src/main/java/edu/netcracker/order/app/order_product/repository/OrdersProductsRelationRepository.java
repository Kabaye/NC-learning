package edu.netcracker.order.app.order_product.repository;

import edu.netcracker.order.app.order_product.entity.OrdersProductsRelationModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

interface OrdersProductsRelationRepository extends ReactiveCrudRepository<OrdersProductsRelationModel, Integer> {
    Flux<OrdersProductsRelationModel> findAllByOrderId(Integer orderId);

    Mono<Void> deleteAllByOrderId(Integer orderId);

    Mono<OrdersProductsRelationModel> findByOrderIdAndProductId(Integer orderId, Integer productId);
}
