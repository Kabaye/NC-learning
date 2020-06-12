package shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor(staticName = "of")
public class Customer {
    private long customerID;
    private String surnameName;
    private long phone;
    private PaymentData paymentData;

    public Customer(String surnameName, long phone, PaymentData paymentData) {
        customerID = 0l;

        this.surnameName = surnameName;
        this.phone = phone;
        this.paymentData = paymentData;
    }

    public static Customer getBasicInstance() {
        return new Customer("name_surname", 0, PaymentData.getBasicInstance());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID = " + customerID +
                ", surnameName = '" + surnameName + '\'' +
                ", phone = " + phone +
                ", paymentData = " + paymentData +
                '}';
    }
}
