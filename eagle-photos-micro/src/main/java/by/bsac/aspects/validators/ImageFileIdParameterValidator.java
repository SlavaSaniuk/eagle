package by.bsac.aspects.validators;

import by.bsac.core.logging.SpringCommonLogging;
import by.bsac.core.validation.ParameterValidator;
import by.bsac.domain.models.ImageFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

import static by.bsac.core.logging.SpringCommonLogging.*;

public class ImageFileIdParameterValidator implements ParameterValidator, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageFileIdParameterValidator.class);
    //Spring beans
    private LongIdParameterValidator long_validator;

    //Constructor
    public ImageFileIdParameterValidator() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(ImageFileIdParameterValidator.class)));
    }

    @Override
    public boolean validate(List<Object> list) {
        LOGGER.trace(String.format("Validate ImageFile[%s] ID property.", list.get(0)));
        return this.long_validator.validateLongId(((ImageFile) list.get(0)).getImageId());
    }

    //Dependency management
    public void setLongIdParameterValidator(LongIdParameterValidator a_validator) {
        LOGGER.debug(DependencyManagement.setViaSetter(BeanDefinition.of(LongIdParameterValidator.class), ImageFileIdParameterValidator.class));
        this.long_validator = a_validator;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.long_validator == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(LongIdParameterValidator.class)));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(LongIdParameterValidator.class)));
    }
}
