package edu.netcracker.bpp.test.test4;

import edu.netcracker.bpp.bpp.annotation.Inject;
import lombok.Getter;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TestClass6 {
    @Inject
    BeanPostProcessor injectAnnotationBeanPostProcessor;
}
