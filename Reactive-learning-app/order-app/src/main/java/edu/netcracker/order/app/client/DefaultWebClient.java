package edu.netcracker.order.app.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.netcracker.common.currency.model.Currency;
import edu.netcracker.order.app.order.entity.Customer;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class DefaultWebClient {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public DefaultWebClient(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8663/api/v1/customers").build();
        this.objectMapper = objectMapper;
    }

    public Mono<Customer> getCustomerByEmail(String email) {
        return webClient.get()
                .uri(UriComponentsBuilder.newInstance()
                        .path("/customer")
                        .queryParam("email", email)
                        .toUriString())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(clientResponse -> clientResponse.toEntity(Customer.class))
                .map(customerResponseEntity -> {
                    if (Objects.isNull(customerResponseEntity.getBody())) {
                        throw new RuntimeException(String.format("No such customer with email: %s!", email));
                    }
                    return customerResponseEntity.getBody();
                });
    }

    public Mono<Pair<Map<Currency, Double>, Currency>> getCurrentExchangeRates() {
        return getCurrentExchangeRates(Currency.USD, Arrays.asList(Currency.values()));
    }

    private Mono<Pair<Map<Currency, Double>, Currency>> getCurrentExchangeRates(Currency baseCurrency, List<Currency> neededCurrencies) {
        Pair<Map<Currency, Double>, Currency> pair = Pair.of(new HashMap<>(), baseCurrency);
        return WebClient.create("https://api.exchangeratesapi.io/latest")
                .get()
                .uri(UriComponentsBuilder.newInstance()
                        .queryParam("base", baseCurrency.name())
                        .queryParam("symbols", neededCurrencies)
                        .toUriString())
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class))
                .map(s -> {
                    JsonNode jsonNode = null;
                    try {
                        jsonNode = objectMapper.readTree(s);
                    } catch (JsonProcessingException e) {
                        System.err.println(e.getMessage());
                    }
                    final JsonNode rates = jsonNode.get("rates");
                    final Iterator<String> ratesIt = rates.fieldNames();
                    while (ratesIt.hasNext()) {
                        String cur = ratesIt.next();
                        pair.getFirst().put(Currency.valueOf(cur), rates.get(cur).asDouble());
                    }
                    return pair;
                });
    }
}
