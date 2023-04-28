package edu.netcracker;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.MurmurHash3;

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
    public Response getUUIDS(@QueryParam("amount") Integer amount) {
        amount = Objects.requireNonNullElse(amount, applicationConfiguration.defaultAmount());
        log.info("Generating UUID. Amount = " + amount);
        return Response.ok().entity(Map.of("ids", Stream.generate(UUID::randomUUID)
                        .limit(amount)
                        .toList(), "amount", amount))
                .build();
    }

    @GET
    @Path("hash")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateHash(@QueryParam("valueToHash") String valueToHash) {
        return Response.ok().entity(Map.of("hash", getHash(valueToHash).toUpperCase()))
                .build();
    }

    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public static String getHash(String value) {
        return bytesToHex(getHash(MurmurHash3.hash128x64(value.getBytes())[0]));
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static byte[] getHash(long v) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) v;
        bytes[1] = (byte) (v >>> 8);
        bytes[2] = (byte) (v >>> 16);
        bytes[3] = (byte) (v >>> 24);
        bytes[4] = (byte) (v >>> 32);
        bytes[5] = (byte) (v >>> 40);
        bytes[6] = (byte) (v >>> 48);
        bytes[7] = (byte) (v >>> 56);
        return bytes;
    }

}
