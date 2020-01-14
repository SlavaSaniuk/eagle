package by.bsac.aspects;

import by.bsac.aspects.debug.MethodCallAspect;
import by.bsac.aspects.debug.MethodExecutionTimeAspect;
import by.bsac.aspects.validation.ParameterValidationAspect;
import by.bsac.aspects.validators.IdParameterValidator;
import by.bsac.core.debugging.LoggerLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static by.bsac.core.logging.SpringCommonLogging.*;

@SuppressWarnings("AccessStaticViaInstance")
@Configuration("AspectsBeans")
public class AspectsBeans {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AspectsBeans.class);

    public AspectsBeans() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(AspectsBeans.class));
    }

    @Bean(name = "MethodCallAspect")
    @Profile("ASPECTS_DEBUG")
    public MethodCallAspect getMethodCallAspect() {

        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("MethodCallAspect").ofClass(MethodCallAspect.class).forProfile("ASPECTS_DEBUG")));
        MethodCallAspect aspect = MethodCallAspect.aspectOf();

        aspect.setLoggerLevel(LoggerLevel.INFO);

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("MethodCallAspect").ofClass(MethodCallAspect.class).forProfile("ASPECTS_DEBUG")));
        return aspect;
    }

    @Bean(name = "MethodExecutionTimeAspect")
    @Profile("ASPECTS_DEBUG")
    public MethodExecutionTimeAspect getMethodExecutionTimeAspect() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("MethodExecutionTimeAspect").ofClass(MethodExecutionTimeAspect.class).forProfile("ASPECTS_DEBUG")));
        MethodExecutionTimeAspect aspect = MethodExecutionTimeAspect.aspectOf();

        aspect.setLoggerLevel(LoggerLevel.INFO);

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("MethodExecutionTimeAspect").ofClass(MethodExecutionTimeAspect.class).forProfile("ASPECTS_DEBUG")));
        return aspect;
    }

    @Bean(name = "ParameterValidationAspect")
    public ParameterValidationAspect getParameterValidationAspect() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("ParameterValidationAspect").ofClass(ParameterValidationAspect.class)));
        ParameterValidationAspect aspect = new ParameterValidationAspect();

        aspect.addValidator(new IdParameterValidator()); //IdParameterValidator

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("ParameterValidationAspect").ofClass(ParameterValidationAspect.class)));
        return aspect;
    }

}