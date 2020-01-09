package by.bsac.aspects.validators;

import by.bsac.core.validation.ParameterValidator;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserWithDetailsDtoParameterValidator implements ParameterValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserWithDetailsDtoParameterValidator.class);

    @Override
    public boolean validate(List<Object> list) {

        UserWithDetailsDto for_validation = (UserWithDetailsDto) list.get(0);
        LOGGER.debug("Validate UserWithDetailsDto object: " +for_validation.toString());

        LOGGER.debug("Validate user_id parameter");
        return for_validation.getUserId() != null;
    }
}
