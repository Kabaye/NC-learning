package edu.netcracker.order.app.order_product.repository;

import edu.netcracker.order.app.order_product.entity.OrdersProductsRelationModel;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Repository
public class DefaultOrdersProductsRelationRepository {
    private final OrdersProductsRelationRepository ordersProductsRelationRepository;

    public DefaultOrdersProductsRelationRepository(OrdersProductsRelationRepository ordersProductsRelationRepository) {
        this.ordersProductsRelationRepository = ordersProductsRelationRepository;
    }

    public Flux<OrdersProductsRelationModel> saveOrderProductRelation(List<OrdersProductsRelationModel> ordersProductsRelationModels) {
        return ordersProductsRelationRepository.saveAll(ordersProductsRelationModels);
    }

    public Flux<OrdersProductsRelationModel> getAllOrderProducts(Integer orderId) {
        return ordersProductsRelationRepository.findAllByOrderId(orderId);
    }

    public Mono<Void> deleteOrderProductsRelation(Integer orderId) {
        return ordersProductsRelationRepository.deleteAllByOrderId(orderId);
    }

    public Mono<Void> deleteProductFromOrder(Integer id) {
        return ordersProductsRelationRepository.deleteById(id);
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
}
