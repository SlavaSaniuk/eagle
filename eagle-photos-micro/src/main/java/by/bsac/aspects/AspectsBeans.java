package by.bsac.aspects;

import by.bsac.aspects.debug.MethodCallAspect;
import by.bsac.aspects.debug.MethodExecutionTimeAspect;
import by.bsac.core.debugging.LoggerLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration("AspectsBeans")
public class AspectsBeans implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AspectsBeans.class);

    public AspectsBeans() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(AspectsBeans.class));
    }

    //Aspects
    //From ltori project
    @Bean(name = "MethodCallAspect")
    @Profile("ASPECT_DEBUG")
    public MethodCallAspect getMethodCallAspect() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(MethodCallAspect.class)));
        MethodCallAspect aspect = MethodCallAspect.aspectOf();

        aspect.setLoggerLevel(LoggerLevel.DEBUG);

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(MethodCallAspect.class)));
        return aspect;
    }

    @Bean(name = "MethodExecutionTimeAspect")
    @Profile("ASPECT_DEBUG")
    public MethodExecutionTimeAspect getMethodExecutionTimeAspect() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(MethodExecutionTimeAspect.class)));
        MethodExecutionTimeAspect aspect = MethodExecutionTimeAspect.aspectOf();

        aspect.setLoggerLevel(LoggerLevel.DEBUG);

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(MethodExecutionTimeAspect.class)));
        return aspect;
    }

    @Override
    public void afterPropertiesSet() {
        LOGGER.info(INITIALIZATION.endInitializeConfiguration(AspectsBeans.class));
    }
}
