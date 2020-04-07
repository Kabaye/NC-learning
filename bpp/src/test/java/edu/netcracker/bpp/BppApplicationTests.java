package edu.netcracker.bpp;

import edu.netcracker.bpp.bean.Bean1;
import edu.netcracker.bpp.bean.Bean2;
import edu.netcracker.bpp.bpp.annotation.Inject;
import edu.netcracker.bpp.test.test1.TestClass1;
import edu.netcracker.bpp.test.test1.TestClass2;
import edu.netcracker.bpp.test.test2.pkg1.TestClass3;
import edu.netcracker.bpp.test.test2.pkg2.TestClass4;
import edu.netcracker.bpp.test.test3.TestClass5;
import edu.netcracker.bpp.test.test4.TestClass6;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

@SpringBootTest
class BppApplicationTests {
    @Inject
    private Bean1 bean1;
    @Inject
    private Bean2 bean2;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void contextLoads() {
    }

    @Test
    void checkInjected() {
        Assert.notNull(bean1, "bean 1 is null");
        Assert.notNull(bean2, "bean 2 is null");
    }

    @Test
    void testSamePackage() {
        Assert.notNull(((TestClass1) applicationContext.getBean("testClass1")).getBean1(), "");
        Assert.isNull(((TestClass2) applicationContext.getBean("testClass2")).getBean1(), "");
    }

    @Test
    void testDiffPackage() {
        Assert.notNull(((TestClass3) applicationContext.getBean("testClass3")).getBean1(), "");
        Assert.notNull(((TestClass4) applicationContext.getBean("testClass4")).getBean1(), "");
    }

    @Test
    void testInterfaceInjecting() {
        Assert.notNull(((TestClass5) applicationContext.getBean("testClass5")).getTestInterface1(), "");
    }

    @Test
    void testInjectingSpringBean() {
        Assert.notNull(applicationContext.getBean("testClass6", TestClass6.class).getInjectAnnotationBeanPostProcessor(), "");
    }
}
