package edu.netcracker.order.app.client;

import edu.netcracker.order.app.order.entity.Customer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
public class CustomerWebClient {
    private final WebClient customerWebClient;

    public CustomerWebClient() {
        this.customerWebClient = WebClient.create("http://localhost:8663/api/v1/customer");
    }

    public Mono<Customer> getCustomerByEmail(String email) {
        return customerWebClient.get()
                .uri(UriComponentsBuilder.newInstance()
                        .path("/customer")
                        .queryParam("email", email)
                        .toUriString())
                .retrieve()
                .bodyToMono(Customer.class);
    }
}
