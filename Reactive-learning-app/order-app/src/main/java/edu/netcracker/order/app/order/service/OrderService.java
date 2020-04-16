package edu.netcracker.order.app.order.service;

import edu.netcracker.order.app.client.DefaultWebClient;
import edu.netcracker.order.app.order.entity.Order;
import edu.netcracker.order.app.order.repository.OrderRepository;
import edu.netcracker.order.app.order.utils.OrderUtils;
import edu.netcracker.order.app.order_product.entity.OrdersProductsRelationModel;
import edu.netcracker.order.app.order_product.repository.DefaultOrdersProductsRelationRepository;
import edu.netcracker.order.app.product.entity.Product;
import edu.netcracker.order.app.product.service.ProductService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import utils.models.Currency;
import utils.utils.MoneyUtils;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final DefaultOrdersProductsRelationRepository ordersProductsRelationRepository;
    private final ProductService productService;
    private final DefaultWebClient defaultWebClient;
    private final AtomicReference<Pair<Map<Currency, Double>, Currency>> rates = new AtomicReference<>();

    private final Function<Order, Order> postProcessOrderFromDB = order -> OrderUtils.postProcessTotalPriceValue(order, MoneyUtils::convertFromDbPrecision, false);
    private final Function<Order, Order> postProcessTotalPriceToCustomerCurrency = order -> OrderUtils.postProcessTotalPriceCurrency(order, rates.get().getFirst());

    public OrderService(OrderRepository orderRepository, DefaultOrdersProductsRelationRepository ordersProductsRelationRepository, ProductService productService, DefaultWebClient defaultWebClient) {
        this.orderRepository = orderRepository;
        this.ordersProductsRelationRepository = ordersProductsRelationRepository;
        this.productService = productService;
        this.defaultWebClient = defaultWebClient;

        Flux.interval(Duration.ofSeconds(0), Duration.ofSeconds(15), Schedulers.single())
                .flatMap(obj -> defaultWebClient.getCurrentExchangeRates())
                .doOnNext(rates::set)
                .subscribe();
    }

    public Mono<Order> saveOrder(Order order) {
        return Mono.just(order)
                .flatMap(ord -> defaultWebClient.getCustomerByEmail(ord.getCustomerEmail())
                        .map(customer -> {
                            ord.setCurrency(customer.getCurrency());
                            return ord;
                        })).flatMap(ord -> productService.findAllById(ord.getProducts().stream()
                        .map(productIntegerPair -> productIntegerPair.getFirst().getId())
                        .collect(Collectors.toList()))
                        .map(product -> {
                            ord.getProducts().stream()
                                    .filter(productIntegerPair -> productIntegerPair.getFirst().getId().equals(product.getId()))
                                    .forEach(productIntegerPair -> {
                                        productIntegerPair.getFirst().setPrice(product.getPrice());
                                        productIntegerPair.getFirst().setName(product.getName());
                                        productIntegerPair.getFirst().setDescription(product.getDescription());
                                    });
                            return ord;
                        })
                        .then(Mono.just(ord)))
                .map(ord -> OrderUtils.postProcessTotalPriceValue(ord, MoneyUtils::convertToDBPrecision, true))
                .flatMap(orderRepository::save)
                .flatMap(ord -> ordersProductsRelationRepository.saveOrderProductRelation(ord.getProducts().
                        stream().
                        parallel()
                        .map(productIntegerPair -> new
                                OrdersProductsRelationModel(null, ord.getId(),
                                productIntegerPair.getFirst().getId(), productIntegerPair.getSecond()))
                        .collect(Collectors.toList()))
                        .then(Mono.just(ord)))
                .map(postProcessOrderFromDB)
                .map(postProcessTotalPriceToCustomerCurrency);
    }

    public Mono<Order> updateOrder(Integer id, Order order) {
        return updateOrder(id, order, false);
    }

    public Mono<Order> findOrder(Integer id) {
        return orderRepository.findById(id)
                .flatMap(ord -> defaultWebClient.getCustomerByEmail(ord.getCustomerEmail())
                        .map(customer -> {
                            ord.setCurrency(customer.getCurrency());
                            return ord;
                        }))
                .flatMap(this::findWithAllDetails);
    }

    public Flux<Order> findAll() {
        return orderRepository.findAll()
                .flatMap(ord -> defaultWebClient.getCustomerByEmail(ord.getCustomerEmail())
                        .map(customer -> {
                            ord.setCurrency(customer.getCurrency());
                            return ord;
                        }))
                .flatMap(this::findWithAllDetails);
    }

    public Mono<Void> deleteOrder(Integer id) {
        return ordersProductsRelationRepository.getAllOrderProducts(id)
                .collectList()
                .flatMap(ordersProductsRelationRepository::deleteAll)
                .then(orderRepository.deleteById(id));
    }

    public Mono<Order> addProduct(Integer orderId, Integer productId, Integer amount) {
        return this.findOrder(orderId)
                .flatMap(order -> ordersProductsRelationRepository.addProductToOrder(new OrdersProductsRelationModel(null, orderId, productId, amount))
                        .map(ordersProductsRelationModel -> {
                            AtomicBoolean isPresent = new AtomicBoolean(false);
                            order.getProducts().stream()
                                    .filter(productIntegerPair -> productIntegerPair.getFirst().getId().equals(productId))
                                    .findFirst()
                                    .ifPresent(productIntegerPair -> {
                                        order.getProducts().remove(productIntegerPair);
                                        order.getProducts().add(Pair.of(productIntegerPair.getFirst(), productIntegerPair.getSecond() + amount));
                                        isPresent.set(true);
                                    });
                            if (!isPresent.get()) {
                                order.getProducts().add(Pair.of(new Product(productId, null, null, null), amount));
                            }
                            return ordersProductsRelationModel;
                        }).flatMap(ordersProductsRelationModel -> productService.findProductById(productId)
                                .map(product -> {
                                    order.getProducts().stream()
                                            .filter(productIntegerPair -> product.getId().equals(productIntegerPair.getFirst().getId()))
                                            .forEach(productIntegerPair -> {
                                                productIntegerPair.getFirst().setName(product.getName());
                                                productIntegerPair.getFirst().setDescription(product.getDescription());
                                                productIntegerPair.getFirst().setPrice(product.getPrice());
                                            });
                                    return OrderUtils.postProcessTotalPriceValue(order, MoneyUtils::convertToDBPrecision, true);
                                })
                        ))
                .flatMap(order -> updateOrder(orderId, order, true));
    }

    public Mono<Order> deleteProduct(Integer orderId, Integer productId, Integer amount) {
        return this.findOrder(orderId)
                .flatMap(ord -> defaultWebClient.getCustomerByEmail(ord.getCustomerEmail())
                        .map(customer -> {
                            ord.setCurrency(customer.getCurrency());
                            return ord;
                        }))
                .flatMap(order -> ordersProductsRelationRepository.deleteProductFromOrder(
                        new OrdersProductsRelationModel(null, orderId, productId, amount))
                        .then(Mono.just(order)))
                .flatMap(order -> productService.findProductById(productId)
                        .map(product -> {
                            order.getProducts().stream()
                                    .filter(productIntegerPair -> product.getId().equals(productIntegerPair.getFirst().getId()))
                                    .findAny()
                                    .ifPresent(productIntegerPair -> {
                                        order.getProducts().remove(productIntegerPair);
                                        if (productIntegerPair.getSecond() > amount) {
                                            order.getProducts().add(Pair.of(productIntegerPair.getFirst(), productIntegerPair.getSecond() - amount));
                                        }
                                    });
                            return OrderUtils.postProcessTotalPriceValue(order, MoneyUtils::convertToDBPrecision, true);
                        })
                ).flatMap(order -> updateOrder(orderId, order, true));
    }

    private Mono<? extends Order> findWithAllDetails(Order order) {
        return ordersProductsRelationRepository.getAllOrderProducts(order.getId())
                .collectList()
                .map(ordersProductsRelationModels -> {
                    ordersProductsRelationModels.forEach(ordersProductsRelationModel -> {
                        Product product = new Product();
                        product.setId(ordersProductsRelationModel.getProductId());
                        order.getProducts().add(Pair.of(product, ordersProductsRelationModel.getAmount()));
                    });
                    return ordersProductsRelationModels;
                }).flatMap(ordersProductsRelationModels ->
                        productService.findAllById(ordersProductsRelationModels.stream()
                                .parallel()
                                .map(OrdersProductsRelationModel::getProductId)
                                .collect(Collectors.toList()))
                                .collectList()
                                .map(products -> {
                                    order.setProducts(products.stream()
                                            .map(product -> Pair.of(product, ordersProductsRelationModels.stream()
                                                    .filter(ordersProductsRelationModel -> ordersProductsRelationModel.getProductId().equals(product.getId()))
                                                    .findFirst()
                                                    .orElseThrow(RuntimeException::new)
                                                    .getAmount()))
                                            .collect(Collectors.toList()));
                                    return products;
                                })).then(Mono.just(order))
                .map(postProcessOrderFromDB)
                .map(postProcessTotalPriceToCustomerCurrency);
    }

    private Mono<Order> updateOrder(Integer id, Order order, boolean updateTotalPrice) {
        return orderRepository.findById(id)
                .flatMap(ord -> defaultWebClient.getCustomerByEmail(ord.getCustomerEmail())
                        .map(customer -> {
                            ord.setCurrency(customer.getCurrency());
                            return ord;
                        }))
                .flatMap(ord -> {
                    if (Objects.isNull(ord)) {
                        return Mono.error(new RuntimeException("No such product for provided id."));
                    }
                    order.setId(id);
                    if (!updateTotalPrice) {
                        order.setTotalPrice(ord.getTotalPrice());
                    }
                    return orderRepository.save(order)
                            .map(postProcessOrderFromDB)
                            .map(postProcessTotalPriceToCustomerCurrency);
                });
    }
}
