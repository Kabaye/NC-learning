package edu.netcracker.order.app.order.entity;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table("orders")
public class Order {
    @Id
    private Integer id;
    @Transient
    private List<Pair<Product, Integer>> products = new ArrayList<>();
    private String customAddress;
    private Float sumPrice;
}
