package edu.netcracker.order.app.product.service;

import edu.netcracker.common.currency.utils.MoneyUtils;
import edu.netcracker.order.app.product.entity.Product;
import edu.netcracker.order.app.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final Function<? super Product, ? extends Product> moneyToDB;
    private final Function<? super Product, ? extends Product> moneyFromDB;

    public ProductService(ProductRepository productRepository) {
        moneyToDB = product -> {
            if (Objects.isNull(product)) {
                throw new RuntimeException("The incoming product is null.");
            }
            product.setPrice(MoneyUtils.convertToDBPrecision(product.getPrice()));
            return product;
        };
        moneyFromDB = product -> {
            if (Objects.isNull(product)) {
                throw new RuntimeException("The outgoing product is null.");
            }
            product.setPrice(MoneyUtils.convertFromDbPrecision(product.getPrice()));
            return product;
        };
        this.productRepository = productRepository;
    }

    public Mono<Product> findProductById(Integer id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("No such product with id: " + id)))
                .map(moneyFromDB);
    }

    public Flux<Product> findAllProducts() {
        return productRepository.findAll()
                .map(moneyFromDB);
    }

    public Mono<Product> saveProduct(Product product) {
        return Mono.just(product)
                .map(moneyToDB)
                .flatMap(productRepository::save)
                .map(moneyFromDB);
    }

    public Mono<Product> updateProduct(Integer id, Product productForUpd) {
        return findProductById(id).map(product -> {
            productForUpd.setId(product.getId());
            return productForUpd;
        }).map(moneyToDB)
                .flatMap(this::saveProduct)
                .map(moneyFromDB);
    }

    public Mono<Void> deleteProduct(Integer id) {
        return productRepository.deleteById(id);
    }

    public Flux<Product> findAllById(Iterable<Integer> ids) {
        return productRepository.findAllById(ids).map(moneyFromDB);
    }
}
