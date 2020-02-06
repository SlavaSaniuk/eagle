package by.bsac.aspects.validators;

import by.bsac.core.validation.ParameterValidator;
import by.bsac.domain.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

import static by.bsac.core.logging.SpringCommonLogging.*;

public class UserIdParameterValidator implements ParameterValidator, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserIdParameterValidator.class);
    //Spring beans
    private IntegerIdParameterValidator integer_id_validator; //From AspectBeans

    //Constructor
    public UserIdParameterValidator() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(UserIdParameterValidator.class)));
    }

    @Override
    public boolean validate(List<Object> list) {

        //Cast to UserWithContextDto
        User user = (User) list.get(0);

        //Validate user_id
        LOGGER.trace(String.format("Validate user_id[%d] parameter;", user.getUserId()));
        return this.integer_id_validator.validateId(user.getUserId());

    }

    //Dependency management
    public void setIntegerIdParameterValidator(IntegerIdParameterValidator a_validator) {
        LOGGER.debug(DependencyManagement.setViaSetter(BeanDefinition.of(IntegerIdParameterValidator.class), UserIdParameterValidator.class));
        this.integer_id_validator = a_validator;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (this.integer_id_validator == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(IntegerIdParameterValidator.class)));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(UserIdParameterValidator.class)));
    }
}
