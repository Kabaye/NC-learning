package edu.netcracker.customer.app.simulator;

import edu.netcracker.common.currency.model.Currency;
import edu.netcracker.customer.app.customer.entity.Customer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class LoadSimulator {
    private final WebClient webClient;

    private final AtomicInteger customerIdNew = new AtomicInteger(91);
    private final AtomicInteger customerIdOld = new AtomicInteger(91);
    private final AtomicReference<String> email = new AtomicReference<>("svyat@gmail.com");

    public LoadSimulator(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8663/api/v1/customers").build();

        //calling findAll every 7 seconds
        Flux.interval(Duration.ofSeconds(0), Duration.ofSeconds(7))
                .flatMap(l -> webClient.get()
                        .retrieve()
                        .toBodilessEntity()).subscribe();

        //calling findCustomer every 7 seconds
        Flux.interval(Duration.ofSeconds(1), Duration.ofSeconds(7))
                .flatMap(l -> webClient.get()
                        .uri(UriComponentsBuilder.fromPath("/customer")
                                .queryParam("email", email.get())
                                .toUriString())
                        .retrieve()
                        .toBodilessEntity())
                .subscribe();

        //calling saveCustomer every 10 seconds
        Flux.interval(Duration.ofSeconds(1), Duration.ofSeconds(10))
                .flatMap(l -> webClient.post()
                        .bodyValue(new Customer(null, getRandomEmail(), getRandomAlphanumericString(5), getRandomAlphanumericString(15), Currency.EUR))
                        .retrieve()
                        .bodyToMono(Customer.class)
                        .doOnNext(customer -> {
                            customerIdOld.set(customerIdNew.get());
                            customerIdNew.set(customer.getId());
                            email.set(customer.getEmail());
                        })
                ).subscribe();

        //calling updateCustomer every 10 seconds
        Flux.interval(Duration.ofSeconds(3), Duration.ofSeconds(10))
                .flatMap(l -> webClient.put()
                        .uri(UriComponentsBuilder.fromPath("/" + customerIdNew.get())
                                .toUriString())
                        .bodyValue(new Customer(customerIdNew.get(), getRandomEmail(), getRandomAlphabeticString(5), getRandomAlphanumericString(15), Currency.JPY))
                        .retrieve()
                        .bodyToMono(Customer.class)
                        .doOnNext(customer -> email.set(customer.getEmail()))
                ).subscribe();

        //calling deleteCustomer every 10 seconds
        Flux.interval(Duration.ofSeconds(8), Duration.ofSeconds(10))
                .flatMap(l -> webClient.delete()
                        .uri(UriComponentsBuilder.fromPath("/" + customerIdOld.get())
                                .toUriString())
                        .retrieve()
                        .toBodilessEntity()
                ).subscribe();


    }

    private String getRandomEmail() {
        return getRandomAlphanumericString(5) + "@" + getRandomAlphabeticString(2) + "." + getRandomAlphabeticString(3);
    }

    private String getRandomAlphanumericString(int targetStringLength) {
        int leftLimit = 48; // letter 'a'
        int rightLimit = 122; // letter 'z'

        return new Random().ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .mapToObj(Character::toString)
                .collect(Collectors.joining());

    }

    private String getRandomAlphabeticString(int targetStringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        return new Random().ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .mapToObj(Character::toString)
                .collect(Collectors.joining());
    }

}
