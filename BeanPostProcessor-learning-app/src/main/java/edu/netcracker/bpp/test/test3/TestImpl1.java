package edu.netcracker.bpp.test.test3;

import edu.netcracker.bpp.bean.Bean1;
import edu.netcracker.bpp.bpp.annotation.Inject;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TestImpl1 implements TestInterface1 {
    @Inject
    Bean1 bean1;

    @Override
    public void doSmth() {

    }
}
