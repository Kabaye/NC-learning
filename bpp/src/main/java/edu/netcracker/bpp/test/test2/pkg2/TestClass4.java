package edu.netcracker.bpp.test.test2.pkg2;

import edu.netcracker.bpp.bean.Bean1;
import edu.netcracker.bpp.bean.Bean3;
import edu.netcracker.bpp.bpp.annotation.Inject;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TestClass4 {
    @Inject
    private Bean1 bean1;
    @Inject
    private Bean3 bean3;
}
