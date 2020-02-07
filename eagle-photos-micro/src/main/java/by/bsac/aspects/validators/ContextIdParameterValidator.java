package by.bsac.aspects.validators;

import by.bsac.core.validation.ParameterValidator;
import by.bsac.domain.models.User;
import by.bsac.domain.models.UserImagesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

import static by.bsac.core.logging.SpringCommonLogging.*;

public class ContextIdParameterValidator implements ParameterValidator, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ContextIdParameterValidator.class);
    //Spring beans
    private IntegerIdParameterValidator integer_id_validator; //From AspectBeans

    //Constructor
    public ContextIdParameterValidator() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(ContextIdParameterValidator.class)));
    }

    @Override
    public boolean validate(List<Object> list) {

        //Cast to UserImagesContext entity
        UserImagesContext context = (UserImagesContext) list.get(0);

        //Validate user_id
        LOGGER.trace(String.format("Validate context_id[%d] parameter;", context.getContextId()));
        return this.integer_id_validator.validateId(context.getContextId());

    }

    //Dependency management
    public void setIntegerIdParameterValidator(IntegerIdParameterValidator a_validator) {
        LOGGER.debug(DependencyManagement.setViaSetter(BeanDefinition.of(IntegerIdParameterValidator.class), ContextIdParameterValidator.class));
        this.integer_id_validator = a_validator;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (this.integer_id_validator == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(IntegerIdParameterValidator.class)));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(ContextIdParameterValidator.class)));
    }
}
