package by.bsac.services.validators;

import by.bsac.core.validation.ParameterValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AccountWithStatusesDtoParameterValidator")
public class AccountWithStatusesDtoParameterValidator implements ParameterValidator {

    @Override
    public boolean validate(List<Object> list) {

        return false;
    }

}
