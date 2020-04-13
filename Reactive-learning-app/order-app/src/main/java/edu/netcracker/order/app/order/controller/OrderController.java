package edu.netcracker.order.app.order.controller;

import edu.netcracker.order.app.order.entity.Order;
import edu.netcracker.order.app.order.service.OrderService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public Mono<Order> findOrder(@PathVariable Integer orderId) {
        return orderService.findOrder(orderId);
    }

    @GetMapping
    public Flux<Order> findAllOrders() {
        return orderService.findAll();
    }

    @PostMapping
    public Mono<Order> saveOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @DeleteMapping("/{orderId}")
    public Mono<Void> deleteOrder(@PathVariable Integer orderId) {
        return orderService.deleteOrder(orderId);
    }
}
