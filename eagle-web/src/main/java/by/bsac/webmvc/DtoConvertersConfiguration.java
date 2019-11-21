package by.bsac.webmvc;

import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.core.beans.EmbeddedDtoEntityConverter;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import by.bsac.webmvc.forms.UserDetailsForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.bsac.conf.LoggerDefaultLogs.*;

@Configuration
public class DtoConvertersConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DtoConvertersConfiguration.class);

    //Constructor
    public DtoConvertersConfiguration() {
        LOGGER.info(INITIALIZATION.initConfig(DtoConvertersConfiguration.class));
    }

    @Bean
    public EmbeddedDeConverter<UserDetailsForm> getUserDetailsConverter() {
        try {
            return new EmbeddedDtoEntityConverter<>(UserDetailsForm.class);
        } catch (NoSupportedEntitiesException e) {
            throw new BeanCreationException(e.getMessage());
        }
    }
}
