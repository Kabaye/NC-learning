package edu.netcracker.order.app.order.utils;


import edu.netcracker.order.app.order.entity.Order;

public class OrderUtils {
    private OrderUtils() {
    }

    public static Order postProcessOrderSum(Order order) {
        order.getProducts().forEach(product -> order.setSumPrice(product.getFirst().getPrice() + order.getSumPrice()));
        return order;
    }
}
