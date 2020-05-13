package edu.netcracker.order.app.product.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import edu.netcracker.order.app.product.entity.Product;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Currency;

import static edu.netcracker.common.currency.model.Currency.USD;

@Component
public class ProductSerializer extends JsonSerializer<Product> {
    @Override
    public void serialize(Product value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("name", value.getName());
        gen.writeStringField("description", value.getDescription());
        gen.writeStringField("price", String.format("%.2f" + Currency.getInstance(USD.name()).getSymbol(), value.getPrice()));
        gen.writeEndObject();
    }
}
