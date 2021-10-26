package edu.netcracker.order.app.order.entity;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestModel {
    private String customAddress;
    private String customerEmail;
    private List<ProductAmountRequest> products;

    @Data
    public static class ProductAmountRequest {
        private Integer productId;
        private Integer amount;
    }
}
