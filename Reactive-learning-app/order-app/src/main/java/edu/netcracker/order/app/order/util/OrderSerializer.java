package edu.netcracker.order.app.order.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import edu.netcracker.order.app.order.entity.Order;
import edu.netcracker.order.app.product.entity.Product;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Currency;

@Component
public class OrderSerializer extends JsonSerializer<Order> {
    @Override
    public void serialize(Order value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", value.getId().toString());
        gen.writeStringField("customAddress", value.getCustomAddress());
        gen.writeStringField("customerEmail", value.getCustomerEmail());
        gen.writeArrayFieldStart("products");
        for (Pair<Product, Integer> productIntegerPair : value.getProducts()) {
            gen.writeStartObject();
            gen.writeNumberField("id", value.getId());
            gen.writeStringField("name", productIntegerPair.getFirst().getName());
            gen.writeStringField("description", productIntegerPair.getFirst().getDescription());
            gen.writeStringField("price", String.format("%.2f" + Currency.getInstance(value.getCurrency().name()).getSymbol(), productIntegerPair.getFirst().getPrice()));
            gen.writeNumberField("amount", productIntegerPair.getSecond());
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeStringField("totalPrice", String.format("%.2f" + Currency.getInstance(value.getCurrency().name()).getSymbol(), value.getTotalPrice()));
        gen.writeEndObject();
    }
}
