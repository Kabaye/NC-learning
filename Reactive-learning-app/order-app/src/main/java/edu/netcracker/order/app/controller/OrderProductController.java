package edu.netcracker.order.app.controller;

import edu.netcracker.order.app.order.entity.Order;
import edu.netcracker.order.app.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController("/api/v1")
public class OrderProductController {
    private final OrderService orderService;

    public OrderProductController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/{orderId}")
    public Mono<Order> findOrder(@PathVariable Integer orderId) {
        return orderService.findOrder(orderId);
    }
}
