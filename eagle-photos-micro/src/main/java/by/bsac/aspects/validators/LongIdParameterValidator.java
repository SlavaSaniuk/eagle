package by.bsac.aspects.validators;

import by.bsac.core.validation.ParameterValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static by.bsac.core.logging.SpringCommonLogging.*;

public class LongIdParameterValidator implements ParameterValidator {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(LongIdParameterValidator.class);

    //Constructor
    public LongIdParameterValidator() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(LongIdParameterValidator.class)));
    }

    @Override
    public boolean validate(List<Object> list) {
        LOGGER.trace("Validate Long ID property.");
        return validateLongId( (Long) list.get(0));
    }

    public boolean validateLongId(Long a_id) {
        LOGGER.trace(String.format("Long ID property for validate [%d];", a_id));
        return a_id != null && a_id > 0;
    }
}
