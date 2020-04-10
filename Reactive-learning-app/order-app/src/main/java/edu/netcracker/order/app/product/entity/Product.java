package edu.netcracker.order.app.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import utils.models.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product {
    @Id
    private Integer id;
    private String name;
    private String description;
    private Long price;
    private Currency currency;
}
