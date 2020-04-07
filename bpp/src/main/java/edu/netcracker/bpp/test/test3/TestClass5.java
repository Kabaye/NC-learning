package edu.netcracker.bpp.test.test3;

import edu.netcracker.bpp.bean.Bean1;
import edu.netcracker.bpp.bpp.annotation.Inject;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class TestClass5 {
    @Inject
    @Getter
    private TestInterface1 testInterface1;
}
