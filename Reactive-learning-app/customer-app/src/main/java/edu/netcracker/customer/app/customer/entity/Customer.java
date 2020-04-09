package edu.netcracker.customer.app.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private Integer id;
    private String email;
    private String name;
    private String address;
    private Currency currency;
}
