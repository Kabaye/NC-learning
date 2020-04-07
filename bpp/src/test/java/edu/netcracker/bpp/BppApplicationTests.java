package edu.netcracker.bpp;

import edu.netcracker.bpp.bean.Bean1;
import edu.netcracker.bpp.bean.Bean2;
import edu.netcracker.bpp.bean.Bean3;
import edu.netcracker.bpp.bpp.annotation.Inject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class BppApplicationTests {
    @Inject
    private Bean1 bean1;
    @Inject
    private Bean2 bean2;
    @Inject
    private Bean3 bean3;

    @Test
    void contextLoads() {
    }

    @Test
    void checkInjected() {
        Assert.notNull(bean1, "bean 1 is null");
        Assert.notNull(bean2, "bean 2 is null");
    }


    @Test
    void checkCreatedAndInjected(){
        Assert.notNull(bean3,"bean 3 is null");
    }

}
