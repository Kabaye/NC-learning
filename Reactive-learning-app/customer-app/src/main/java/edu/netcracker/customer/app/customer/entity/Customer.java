package edu.netcracker.customer.app.customer.entity;

import edu.netcracker.common.currency.model.Currency;
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
@Table("customers")
public class Customer {
    @Id
    private Integer id;
    private String email;
    private String name;
    private String address;
    private Currency currency;
}
