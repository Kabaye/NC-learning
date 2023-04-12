package edu.netcracker;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;


@Slf4j
@Path("/api/v1")
public class ExampleResource {

    private final ApplicationConfiguration applicationConfiguration;

    public ExampleResource(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @GET
    @Path("uuid")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello(@QueryParam("amount") Integer amount) {
        amount = Objects.requireNonNullElse(amount, applicationConfiguration.defaultAmount());
        log.info("Generating UUID. Amount = " + amount);
        return Response.ok().entity(Map.of("ids", Stream.generate(UUID::randomUUID)
                        .limit(amount)
                        .toList(), "amount", amount))
                .build();
    }
}
