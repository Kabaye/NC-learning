package edu.netcracker.order.app.product.controller;

import edu.netcracker.common.metric.annotation.Metric;
import edu.netcracker.order.app.product.entity.Product;
import edu.netcracker.order.app.product.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static edu.netcracker.common.metric.model.MetricType.INTERACTING;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Mono<Product> saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/{id}")
    public Mono<Product> updateProd(@PathVariable Integer id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProd(@PathVariable Integer id) {
        return productService.deleteProduct(id);
    }

    @GetMapping
    @Metric(type = INTERACTING)
    public Flux<Product> findAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    @Metric(type = INTERACTING)
    public Mono<Product> findProduct(@PathVariable Integer id) {
        return productService.findProductById(id);
    }
}