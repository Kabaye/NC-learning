package edu.netcracker.order.app.client;

import edu.netcracker.order.app.order.entity.Customer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class CustomerWebClient {
    private final WebClient customerWebClient;

    public CustomerWebClient(WebClient.Builder webClientBuilder) {
        this.customerWebClient = webClientBuilder.baseUrl("http://localhost:8663/api/v1/customers").build();
    }

    public Mono<Customer> getCustomerByEmail(String email) {
        return customerWebClient.get()
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
}
