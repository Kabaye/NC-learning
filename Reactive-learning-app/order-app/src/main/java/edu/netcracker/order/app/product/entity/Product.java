package edu.netcracker.order.app.product.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.netcracker.order.app.product.serializer.ProductSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonSerialize(using = ProductSerializer.class)
@Table("products")
public class Product {
    @Id
    private Integer id;
    private String name;
    private String description;
    private Double price;
}
