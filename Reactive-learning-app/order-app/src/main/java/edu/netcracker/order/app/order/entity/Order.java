package edu.netcracker.order.app.order.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.netcracker.common.currency.model.Currency;
import edu.netcracker.order.app.order.utils.OrderSerializer;
import edu.netcracker.order.app.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table("orders")
@JsonSerialize(using = OrderSerializer.class)
public class Order {
    @Id
    private Integer id;
    @Transient
    private List<Pair<Product, Integer>> products = new ArrayList<>();
    private String customAddress;
    private String customerEmail;
    private Double totalPrice = 0D;
    @Transient
    private Currency currency;

    public static Order of(OrderRequestModel orderRequestModel) {
        return new Order(null, orderRequestModel.getProducts().stream()
                .map(productAmountRequest -> Pair.of(new Product(productAmountRequest.getProductId(), null, null, null),
                        productAmountRequest.getAmount())).collect(Collectors.toList()), orderRequestModel.getCustomAddress(), orderRequestModel.getCustomerEmail(),
                null, null);
    }
}
