package by.bsac.aspects;

import by.bsac.aspects.debug.MethodCallAspect;
import by.bsac.aspects.debug.MethodExecutionTimeAspect;
import by.bsac.aspects.validation.ParameterValidationAspect;
import by.bsac.aspects.validators.UserWithDetailsDtoParameterValidator;
import by.bsac.core.debugging.LoggerLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static by.bsac.core.logging.SpringCommonLogging.*;

@SuppressWarnings("AccessStaticViaInstance")
@Configuration
public class AspectsBeans {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AspectsBeans.class);

    public AspectsBeans() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(AspectsBeans.class));
    }

    //Configure aspects from by.bsac.ltori library
    //Debug aspects
    @Bean(name = "MethodCallAspect")
    @Profile("ASPECTS_DEBUG")
    public MethodCallAspect getMethodCallAspect() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("MethodCallAspect").ofClass(MethodCallAspect.class).forProfile("ASPECT_DEBUG")));
        MethodCallAspect aspect = MethodCallAspect.aspectOf();
        aspect.setLoggerLevel(LoggerLevel.DEBUG);
        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("MethodCallAspect").ofClass(MethodCallAspect.class).forProfile("ASPECT_DEBUG")));
        return aspect;
    }

    @Bean(name = "MethodExecutionTimeAspect")
    @Profile("ASPECTS_DEBUG")
    public MethodExecutionTimeAspect getMethodExecutionTimeAspect() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("MethodExecutionTimeAspect").ofClass(MethodExecutionTimeAspect.class).forProfile("ASPECT_DEBUG")));
        MethodExecutionTimeAspect aspect = MethodExecutionTimeAspect.aspectOf();
        aspect.setLoggerLevel(LoggerLevel.DEBUG);
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("MethodExecutionTimeAspect").ofClass(MethodExecutionTimeAspect.class).forProfile("ASPECT_DEBUG")));
        return aspect;
    }

    @Bean(name = "ParameterValidationAspect")
    public ParameterValidationAspect getParameterValidationAspect() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("ParameterValidationAspect").ofClass(ParameterValidationAspect.class)));
        ParameterValidationAspect aspect = ParameterValidationAspect.aspectOf();
        aspect.addValidator(new UserWithDetailsDtoParameterValidator());
        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("ParameterValidationAspect").ofClass(ParameterValidationAspect.class)));
        return aspect;
    }


}
