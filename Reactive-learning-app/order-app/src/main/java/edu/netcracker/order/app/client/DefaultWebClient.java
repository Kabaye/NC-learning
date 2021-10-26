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

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;

@Component
public class DefaultWebClient {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final AtomicInteger counter;

    public DefaultWebClient(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8663/api/v1/customers").build();
        this.objectMapper = objectMapper;
        counter = new AtomicInteger();
    }

    public Mono<Customer> getCustomerByEmail(String email) {
        return webClient.get()
                .uri(UriComponentsBuilder.newInstance()
                        .path("/customer")
                        .queryParam("email", email)
                        .toUriString())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(clientResponse -> clientResponse.statusCode().is2xxSuccessful() ? clientResponse.bodyToMono(Customer.class)
                        : Mono.error(new RuntimeException(String.format("No such customer with email: %s!", email))));
    }

    public Mono<Pair<Map<Currency, Double>, Currency>> getCurrentExchangeRates() {
        return getCurrentExchangeRates(Currency.EUR, Arrays.asList(Currency.values()));
    }

    public Mono<Pair<Map<Currency, Double>, Currency>> getCurrentExchangeRates(Currency baseCurrency, List<Currency> neededCurrencies) {
        Pair<Map<Currency, Double>, Currency> pair = Pair.of(new HashMap<>(), baseCurrency);
        String uri = UriComponentsBuilder.newInstance()
                .queryParam("symbols", neededCurrencies.stream().reduce(new StringJoiner(""), (stringJoiner, currency) -> stringJoiner.add(",").add(currency.name()), StringJoiner::merge))
                .queryParam("access_key", "cc7de3d2db06c213960df4e8c18a9d5e")
                .toUriString();
        return WebClient.create("http://api.exchangeratesapi.io/v1/latest")
                .get()
                .uri(uri)
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
