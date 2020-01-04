package by.bsac.webmvc;

import by.bsac.core.beans.BasicDtoEntityConverter;
import by.bsac.core.beans.DtoEntityConverter;
import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.core.beans.EmbeddedDtoEntityConverter;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import by.bsac.core.logging.SpringCommonLogging;
import by.bsac.webmvc.dto.AccountWithStatusDto;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration
public class DtoConvertersConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DtoConvertersConfiguration.class);

    public DtoConvertersConfiguration() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(DtoConvertersConfiguration.class));
    }

    @Bean(name = "UserWithDetailsDtoConverter")
    public EmbeddedDeConverter<UserWithDetailsDto> getUserWithDetailsConverter() {

        EmbeddedDeConverter<UserWithDetailsDto> converter = null;

        try {
            LOGGER.info(SpringCommonLogging.CREATION.startCreateBean(BeanDefinition.of("UserWithDetailsDtoConverter").ofClass(EmbeddedDeConverter.class).forGenericType(UserWithDetailsDto.class)));
            converter = new EmbeddedDtoEntityConverter<>(UserWithDetailsDto.class);
        } catch (NoSupportedEntitiesException e) {
            LOGGER.warn(CREATION.creationThrowExceptionWithMessage(BeanDefinition.of("UserWithDetailsDtoConverter").ofClass(EmbeddedDeConverter.class).forGenericType(UserWithDetailsDto.class), e));
            throw new BeanCreationException(e.getMessage());
        }

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("UserWithDetailsDtoConverter").ofClass(EmbeddedDeConverter.class).forGenericType(UserWithDetailsDto.class)));
        return converter;
    }

    @Bean(name = "AccountWithStatusDtoConverter")
    public DtoEntityConverter<AccountWithStatusDto> getAccountWithStatusDtoConverter() {


        BasicDtoEntityConverter<AccountWithStatusDto> converter;

        try {

            LOGGER.info(SpringCommonLogging.CREATION.startCreateBean(BeanDefinition.of("AccountWithStatusDtoConverter").ofClass(DtoEntityConverter.class).forGenericType(AccountWithStatusDto.class)));
            converter = new BasicDtoEntityConverter<>(AccountWithStatusDto.class);

        } catch (NoSupportedEntitiesException e) {
            LOGGER.warn(SpringCommonLogging.CREATION.creationThrowExceptionWithMessage(BeanDefinition.of("AccountWithStatusDtoConverter").ofClass(DtoEntityConverter.class).forGenericType(AccountWithStatusDto.class), e));
            throw new BeanCreationException(e.getMessage());
        }

        LOGGER.info(SpringCommonLogging.CREATION.endCreateBean(BeanDefinition.of("AccountWithStatusDtoConverter").ofClass(DtoEntityConverter.class).forGenericType(AccountWithStatusDto.class)));
        return converter;

    }
}
