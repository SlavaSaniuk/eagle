package by.bsac.webmvc;

import by.bsac.core.beans.BasicDtoEntityConverter;
import by.bsac.core.beans.DtoEntityConverter;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import by.bsac.domain.dto.UserWithContextDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration("DtoConversionConfiguration")
public class DtoConversionConfiguration implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DtoConversionConfiguration.class);

    //Constructor
    public DtoConversionConfiguration() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(DtoConversionConfiguration.class)));
    }

    /**
     * {@link DtoEntityConverter} for {@link UserWithContextDto} dto object.
     * Can convert to {@link by.bsac.domain.models.User} and {@link by.bsac.domain.models.UserImagesContext} entities.
     * @return - {@link DtoEntityConverter} convert bean.
     */
    @Bean(name = "UserWithContextDtoConverter")
    public DtoEntityConverter<UserWithContextDto> getUserWithContextDtoConverter() {

        BasicDtoEntityConverter<UserWithContextDto> converter;

        try {
            LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(DtoEntityConverter.class).withName("UserWithContextDtoConverter").forGenericType(UserWithContextDto.class)));
            converter = new BasicDtoEntityConverter<>(UserWithContextDto.class);
        } catch (NoSupportedEntitiesException e) {
            throw new BeanCreationException(CREATION.creationThrowExceptionWithMessage(
                    BeanDefinition.of(DtoEntityConverter.class).withName("UserWithContextDtoConverter").forGenericType(UserWithContextDto.class), e));
        }

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(DtoEntityConverter.class).withName("UserWithContextDtoConverter").forGenericType(UserWithContextDto.class)));
        return converter;
    }

    //Dependency management
    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(DtoConversionConfiguration.class)));
    }
}
