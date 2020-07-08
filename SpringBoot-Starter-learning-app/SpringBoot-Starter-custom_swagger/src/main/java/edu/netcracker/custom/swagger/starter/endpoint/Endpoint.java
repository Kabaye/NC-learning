package edu.netcracker.custom.swagger.starter.endpoint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Endpoint {
    private String method;
    private String href;
    private String body;
    private String requestParam;
    private String returnType;
}
