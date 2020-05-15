package edu.netcracker.order.app.order.util;


import edu.netcracker.common.currency.model.Currency;
import edu.netcracker.common.currency.utils.MoneyUtils;
import edu.netcracker.order.app.order.entity.Order;
import edu.netcracker.order.app.product.entity.Product;
import org.springframework.data.util.Pair;

import java.util.Map;
import java.util.function.Function;

import static edu.netcracker.common.currency.model.Currency.USD;

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

    public static Order postProcessPriceCurrency(Order order, Map<Currency, Double> rates) {
        order.setTotalPrice(MoneyUtils.convertMoney(order.getTotalPrice(), rates.get(order.getCurrency())));
        for (Pair<Product, Integer> product : order.getProducts()) {
            product.getFirst().setPrice(MoneyUtils.convertMoney(product.getFirst().getPrice(), rates.get(USD)));
        }
        return order;
    }
}
