package edu.netcracker.order.app.order_product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table("products_orders")
public class OrdersProductsRelationModel {
    @Id
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private Integer amount = 1;
}
