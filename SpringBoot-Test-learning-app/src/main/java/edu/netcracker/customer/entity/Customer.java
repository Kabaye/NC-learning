package edu.netcracker.customer.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Accessors(chain = true)
@Entity
public class Customer {
    @Id
    private Integer id;
    private String firstName;
    private String email;
    private String address;
    private Double money;

    public static Customer of(Customer customer) {
        return new Customer()
                .setMoney(customer.getMoney())
                .setFirstName(customer.getFirstName())
                .setAddress(customer.getAddress())
                .setEmail(customer.getEmail())
                .setId(customer.getId());
    }
}
