package edu.netcracker.order.app.order.entity;

import edu.netcracker.common.currency.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Customer {
    private String email;
    private String address;
    private Currency currency;
}
