package by.bsac.aspects;

import by.bsac.annotations.debug.MethodExecutionTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tests.BeansConfiguration;
import tests.beans.Lou;

@SpringBootTest(classes = {TestAspectsConfiguration.class, BeansConfiguration.class})
public class LouIntegrationTest {


    //Spring beans
    @Autowired
    private Lou lou;

    @Test
    @MethodExecutionTime(inMicros = true)
    void sayLou_annotatedMethods_shouldEnableAspects() {
        this.lou.sayLou("Hello world!");
    }

}
