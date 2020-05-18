package edu.netcracker.order.app.order.repository;

import edu.netcracker.order.app.order.entity.Order;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveCrudRepository<Order, Integer> {
    @Query("SELECT * FROM (SELECT * FROM orders ORDER BY id DESC LIMIT 500) AS \"inner_orders\" ORDER BY id")
    Flux<Order> findAll();
}