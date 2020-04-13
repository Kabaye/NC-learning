package edu.netcracker.order.app.order.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.netcracker.order.app.product.entity.Product;
import org.springframework.data.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PairDeserializer extends JsonDeserializer<List<Pair<?, ?>>> {
    @Override
    public List<Pair<?, ?>> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ArrayNode arrayNode = jsonParser.getCodec().readTree(jsonParser);
        List<Pair<?, ?>> list = new ArrayList<>();
        for (int i = 0; i < arrayNode.size(); i++) {
            JsonNode pair = arrayNode.get(i);
            Integer amount = pair.get("second").asInt();
            Product product = new Product();
            product.setId(pair.get("first").get("id").asInt());
            list.add(Pair.of(product, amount));
        }
        return list;
    }
}
