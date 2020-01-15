package by.bsac.aspects;

import by.bsac.aspects.validators.AccountIdParameterValidator;
import by.bsac.aspects.validators.IdParameterValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration("ParametersValidatorsConfiguration")
public class ParametersValidatorsConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ParametersValidatorsConfiguration.class);

    //Constructor
    public ParametersValidatorsConfiguration() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(ParametersValidatorsConfiguration.class));
    }

    @Bean(name = "IdParameterValidator")
    public IdParameterValidator getIdParameterValidator() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("IdParameterValidator").ofClass(IdParameterValidator.class)));
        IdParameterValidator validator = new IdParameterValidator();

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("IdParameterValidator").ofClass(IdParameterValidator.class)));
        return validator;
    }

    @Bean(name = "AccountIdParameterValidator")
    public AccountIdParameterValidator getAccountIdParameterValidator() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("AccountIdParameterValidator").ofClass(AccountIdParameterValidator.class)));
        AccountIdParameterValidator validator = new AccountIdParameterValidator();

        validator.setIdParameterValidator(this.getIdParameterValidator());

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("AccountIdParameterValidator").ofClass(AccountIdParameterValidator.class)));
        return validator;
    }

}
