package edu.netcracker.springboot.starter.inclusion.conf;

import edu.netcracker.custom.swagger.starter.endpoint.Endpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SwController {
    private final List<Endpoint> allEndpoints;

    public SwController(List<Endpoint> allEndpoints, ApplicationContext applicationContext) {
        this.allEndpoints = allEndpoints;
    }

    @GetMapping("/swagger-ui2.html")
    public List<Endpoint> getAllEndpoints() {
        return allEndpoints;
    }
}
