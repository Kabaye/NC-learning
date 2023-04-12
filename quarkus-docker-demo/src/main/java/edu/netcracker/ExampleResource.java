package edu.netcracker;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Path("/api/v1")
public class ExampleResource {

    @GET
    @Path("uuid")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello(@QueryParam("amount") Integer amount) {
        return Response.ok().entity(Map.of("ids", Stream.generate(UUID::randomUUID)
                        .limit(amount)
                        .toList(), "amount", amount))
                .build();
    }
}
