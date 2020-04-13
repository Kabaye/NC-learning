package edu.netcracker.order.app.controller;

import edu.netcracker.order.app.order.entity.Order;
import edu.netcracker.order.app.order.service.OrderService;
import edu.netcracker.order.app.product.entity.Product;
import edu.netcracker.order.app.product.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController("/api/v1")
public class OrderProductController {
    private final OrderService orderService;
    private final ProductService productService;

    public OrderProductController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/orders/{orderId}")
    public Mono<Order> findOrder(@PathVariable Integer orderId) {
        return orderService.findOrder(orderId);
    }

    @PostMapping("/orders")
    public Mono<Order> saveOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }


    @PostMapping("/products")
    public Mono<Product> saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/products/{id}")
    public Mono<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/products/{id}")
    public Mono<Void> deleteProduct(@PathVariable Integer id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/products")
    public Flux<Product> findAll() {
        return productService.findAllProducts();
    }

    @GetMapping("/products/{id}")
    public Mono<Product> findProduct(@PathVariable Integer id) {
        return productService.findProductById(id);
    }
}