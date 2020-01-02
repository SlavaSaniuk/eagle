package by.bsac.webmvc;

import by.bsac.core.beans.BasicDtoEntityConverter;
import by.bsac.core.beans.DtoEntityConverter;
import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.core.beans.EmbeddedDtoEntityConverter;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import by.bsac.webmvc.dto.AccountWithStatusDto;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.bsac.configuration.LoggerDefaultLogs.*;

@Configuration
public class DtoConvertersConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DtoConvertersConfiguration.class);

    public DtoConvertersConfiguration() {
        LOGGER.info(INITIALIZATION.initConfig(DtoConvertersConfiguration.class));
    }

    @Bean
    public EmbeddedDeConverter<UserWithDetailsDto> getUserWithDetailsConverter() {
        LOGGER.info(CREATION.beanCreationStart(EmbeddedDeConverter.class));
        EmbeddedDeConverter<UserWithDetailsDto> converter = null;
        try {
             converter = new EmbeddedDtoEntityConverter<>(UserWithDetailsDto.class);
            LOGGER.info(CREATION.beanCreationFinish(EmbeddedDeConverter.class));
        } catch (NoSupportedEntitiesException e) {
            e.printStackTrace();
        }

        return converter;
    }

    @Bean(name = "AccountWithStatusDtoConverter")
    public DtoEntityConverter<AccountWithStatusDto> getAccountWithStatusDtoConverter() {

        BasicDtoEntityConverter<AccountWithStatusDto> converter;

        try {
            LOGGER.info(CREATION.beanCreationStart(DtoEntityConverter.class));
            converter = new BasicDtoEntityConverter<>(AccountWithStatusDto.class);
        } catch (NoSupportedEntitiesException e) {
            e.printStackTrace();
            throw new BeanCreationException(e.getMessage());
        }

        LOGGER.info(CREATION.beanCreationFinish(DtoEntityConverter.class));
        return converter;

    }
}
