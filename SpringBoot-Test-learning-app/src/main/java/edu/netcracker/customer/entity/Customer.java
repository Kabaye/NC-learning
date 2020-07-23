package edu.netcracker.customer.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

@Data
@Accessors(chain = true)
public class Customer {
    @Id
    private Integer id;
    private String firstName;
    private String email;
    private String address;
    private Double money;
}
