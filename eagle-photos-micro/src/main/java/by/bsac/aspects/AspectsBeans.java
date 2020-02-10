package by.bsac.aspects;

import by.bsac.aspects.debug.MethodCallAspect;
import by.bsac.aspects.debug.MethodExecutionTimeAspect;
import by.bsac.aspects.logging.BeforeLogAspect;
import by.bsac.aspects.validation.ParameterValidationAspect;
import by.bsac.aspects.validators.*;
import by.bsac.core.debugging.LoggerLevel;
import by.bsac.core.validation.ParameterValidator;
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
        aspect.enable();

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(MethodCallAspect.class)));
        return aspect;
    }

    @Bean(name = "MethodExecutionTimeAspect")
    @Profile("ASPECT_DEBUG")
    public MethodExecutionTimeAspect getMethodExecutionTimeAspect() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(MethodExecutionTimeAspect.class)));
        MethodExecutionTimeAspect aspect = MethodExecutionTimeAspect.aspectOf();

        aspect.setLoggerLevel(LoggerLevel.DEBUG);
        aspect.enable();

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(MethodExecutionTimeAspect.class)));
        return aspect;
    }

    @Bean(name = "BeforeLogAspect")
    public BeforeLogAspect getBeforeLogAspect() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(BeforeLogAspect.class)));
        BeforeLogAspect aspect = BeforeLogAspect.aspectOf();

        aspect.setLoggerLevel(LoggerLevel.INFO);
        aspect.enable();

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(BeforeLogAspect.class)));
        return aspect;
    }

    @Bean(name = "ParameterValidationAspect")
    public ParameterValidationAspect getParameterValidationAspect() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(ParameterValidationAspect.class)));
        ParameterValidationAspect aspect = ParameterValidationAspect.aspectOf();

        //Add validators
        aspect.addValidator(this.getIntegerIdParameterValidator());
        aspect.addValidator(this.getLongIdParameterValidator());
        aspect.addValidator(this.getUserIdParameterValidator());
        aspect.addValidator(this.getContextIdParameterValidator());
        aspect.addValidator(this.getImageFileIdParameterValidator());

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(ParameterValidationAspect.class)));
        return aspect;
    }


    //Parameters validators
    @Bean("IntegerIdParameterValidator")
    public IntegerIdParameterValidator getIntegerIdParameterValidator() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(IntegerIdParameterValidator.class)));
        IntegerIdParameterValidator validator = new IntegerIdParameterValidator();

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(IntegerIdParameterValidator.class)));
        return validator;
    }

    @Bean("LongIdParameterValidator")
    public LongIdParameterValidator getLongIdParameterValidator() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(LongIdParameterValidator.class)));
        LongIdParameterValidator validator = new LongIdParameterValidator();

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(LongIdParameterValidator.class)));
        return validator;
    }

    @Bean("UserIdParameterValidator")
    public UserIdParameterValidator getUserIdParameterValidator() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(UserIdParameterValidator.class)));
        UserIdParameterValidator validator = new UserIdParameterValidator();

        validator.setIntegerIdParameterValidator(this.getIntegerIdParameterValidator());

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(UserIdParameterValidator.class)));
        return validator;
    }

    @Bean("ContextIdParameterValidator")
    public ContextIdParameterValidator getContextIdParameterValidator() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(ContextIdParameterValidator.class)));
        ContextIdParameterValidator validator = new ContextIdParameterValidator();

        validator.setIntegerIdParameterValidator(this.getIntegerIdParameterValidator());

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(ContextIdParameterValidator.class)));
        return validator;
    }

    @Bean("ImageFileIdParameterValidator")
    public ParameterValidator getImageFileIdParameterValidator() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(ImageFileIdParameterValidator.class)));
        ImageFileIdParameterValidator validator = new ImageFileIdParameterValidator();

        validator.setLongIdParameterValidator(this.getLongIdParameterValidator());

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(ImageFileIdParameterValidator.class)));
        return validator;
    }


    @Override
    public void afterPropertiesSet() {
        LOGGER.info(INITIALIZATION.endInitializeConfiguration(AspectsBeans.class));
    }
}
