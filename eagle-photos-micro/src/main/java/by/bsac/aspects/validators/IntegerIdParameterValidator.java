package by.bsac.aspects.validators;

import by.bsac.core.validation.ParameterValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static by.bsac.core.logging.SpringCommonLogging.*;

public class IntegerIdParameterValidator implements ParameterValidator {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(IntegerIdParameterValidator.class);

    //Constructor
    public IntegerIdParameterValidator() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(IntegerIdParameterValidator.class)));
    }

    @Override
    public boolean validate(List<Object> list) {

        //Cast to Integer
        Integer id = (Integer) list.get(0);
        LOGGER.trace(String.format("Validate [%s] integer ID.", id));

        return this.validateId(id);
    }

    public boolean validateId(Integer id) {
        //Check whether id is not null
        if (id == null) return false;

        //Check whether id is positive value
        return id > 0;
    }


}
