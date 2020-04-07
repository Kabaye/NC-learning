package edu.netcracker.bpp.test.test5;

import edu.netcracker.bpp.bpp.annotation.Inject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class TestClass7 {
    @Inject
    TestClass8 testClass8;
}
