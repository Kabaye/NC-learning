package edu.netcracker.order.app.order.repository;

import edu.netcracker.order.app.order.entity.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveCrudRepository<Order, Integer> {
}