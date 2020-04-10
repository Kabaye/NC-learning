package edu.netcracker.order.app.product.repository;

import edu.netcracker.order.app.product.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
}
