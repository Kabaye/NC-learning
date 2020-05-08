package edu.netcracker.order.app.order.util;


import edu.netcracker.common.currency.model.Currency;
import edu.netcracker.common.currency.utils.MoneyUtils;
import edu.netcracker.order.app.order.entity.Order;

import java.util.Map;
import java.util.function.Function;

public class OrderUtils {
    private OrderUtils() {
    }

    public static Order postProcessTotalPriceValue(Order order, Function<Double, Double> postProcessPrice, boolean calculateTotalPrice) {
        if (calculateTotalPrice) {
            order.setTotalPrice(0D);
            order.getProducts().forEach(product -> order.setTotalPrice(product.getFirst().getPrice() * product.getSecond() + order.getTotalPrice()));
        }
        order.setTotalPrice(postProcessPrice.apply(order.getTotalPrice()));
        return order;
    }

    public static Order postProcessTotalPriceCurrency(Order order, Map<Currency, Double> rates) {
        order.setTotalPrice(MoneyUtils.convertMoney(order.getTotalPrice(), rates.get(order.getCurrency())));
        return order;
    }
}
