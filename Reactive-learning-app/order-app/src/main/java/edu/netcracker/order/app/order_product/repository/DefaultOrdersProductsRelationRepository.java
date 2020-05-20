package edu.netcracker.order.app.order_product.repository;

import edu.netcracker.order.app.order_product.entity.OrdersProductsRelationModel;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class DefaultOrdersProductsRelationRepository {
    private final OrdersProductsRelationRepository ordersProductsRelationRepository;

    public DefaultOrdersProductsRelationRepository(OrdersProductsRelationRepository ordersProductsRelationRepository) {
        this.ordersProductsRelationRepository = ordersProductsRelationRepository;
    }

    @SafeVarargs
    private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t -> {
            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }

    public Flux<OrdersProductsRelationModel> getAllOrderProducts(Integer orderId) {
        return ordersProductsRelationRepository.findAllByOrderId(orderId);
    }

    public Mono<Void> deleteAll(Iterable<OrdersProductsRelationModel> iterable) {
        return ordersProductsRelationRepository.deleteAll(iterable);
    }

    public Flux<OrdersProductsRelationModel> saveOrderProductRelation(List<OrdersProductsRelationModel> ordersProductsRelationModels) {
        return ordersProductsRelationRepository.saveAll(ordersProductsRelationModels.stream()
                .filter(distinctByKeys(OrdersProductsRelationModel::getOrderId, OrdersProductsRelationModel::getProductId))
                .collect(Collectors.toList()));
    }

    public Mono<OrdersProductsRelationModel> addProductToOrder(OrdersProductsRelationModel ordersProductsRelationModel) {
        return ordersProductsRelationRepository.findByOrderIdAndProductId(ordersProductsRelationModel.getOrderId(), ordersProductsRelationModel.getProductId())
                .flatMap(oprm -> {
                    if (Objects.isNull(oprm)) {
                        return ordersProductsRelationRepository.save(ordersProductsRelationModel);
                    } else {
                        oprm.setAmount(oprm.getAmount() + ordersProductsRelationModel.getAmount());
                        return this.updateAmount(oprm);
                    }
                });
    }

    public Mono<OrdersProductsRelationModel> updateAmount(OrdersProductsRelationModel ordersProductsRelationModel) {
        return ordersProductsRelationRepository.findById(ordersProductsRelationModel.getId()).flatMap(oprm -> {
            if (Objects.isNull(oprm)) {
                return Mono.error(new RuntimeException("You are trying to update not existing order."));
            } else {
                return ordersProductsRelationRepository.save(ordersProductsRelationModel);
            }
        });
    }

    public Mono<?> deleteProductFromOrder(OrdersProductsRelationModel ordersProductsRelationModel) {
        return ordersProductsRelationRepository.findByOrderIdAndProductId(ordersProductsRelationModel.getOrderId(),
                ordersProductsRelationModel.getProductId())
                .flatMap(oprm -> {
                    if (oprm.getAmount() <= ordersProductsRelationModel.getAmount()) {
                        return ordersProductsRelationRepository.findByOrderIdAndProductId(ordersProductsRelationModel.getOrderId(), ordersProductsRelationModel.getProductId())
                                .flatMap(ordersProductsRelationRepository::delete);
                    } else {
                        oprm.setAmount(oprm.getAmount() - ordersProductsRelationModel.getAmount());
                        return this.updateAmount(oprm);
                    }
                });
    }
}
