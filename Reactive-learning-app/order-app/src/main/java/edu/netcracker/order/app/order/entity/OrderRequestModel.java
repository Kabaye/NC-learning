package edu.netcracker.order.app.order.entity;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestModel {
    private String customAddress;
    private String customerEmail;
    private List<ProductAmountRequest> products;

    public static class ProductAmountRequest {
        private Integer productId;
        private Integer amount;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }
    }
}
