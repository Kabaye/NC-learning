package edu.netcracker.order.app.order.repository;

import edu.netcracker.order.app.order.entity.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface OrderRepository extends ReactiveCrudRepository<Order, Integer> {
    Mono<Void> deleteById(Integer orderId);
}