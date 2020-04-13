package edu.netcracker.order.app.order.utils;


import edu.netcracker.order.app.order.entity.Order;

import java.util.function.Function;

public class OrderUtils {
    private OrderUtils() {
    }

    public static Order postProcessOrderSum(Order order, Function<Float, Float> postProcessPrice) {
        order.getProducts().forEach(product -> order.setTotalPrice(product.getFirst().getPrice() * product.getSecond() + order.getTotalPrice()));
        order.setTotalPrice(postProcessPrice.apply(order.getTotalPrice()));
        return order;
    }
}
