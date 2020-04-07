package edu.netcracker.bpp.test.test2.pkg1;

import edu.netcracker.bpp.bean.Bean1;
import edu.netcracker.bpp.bean.Bean3;
import edu.netcracker.bpp.bpp.annotation.Inject;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TestClass3 {
    @Inject
    private Bean1 bean1;
    @Inject
    private Bean3 bean3;
}
