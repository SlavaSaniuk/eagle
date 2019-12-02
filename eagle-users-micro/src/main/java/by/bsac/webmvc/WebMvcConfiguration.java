package by.bsac.webmvc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static by.bsac.configuration.LoggerDefaultLogs.*;

@Configuration
@ComponentScan(basePackages = "by.bsac.webmvc.controllers")
public class WebMvcConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfiguration.class);

    public WebMvcConfiguration() {
        LOGGER.info(INITIALIZATION.initConfig(WebMvcConfiguration.class));
    }

    @Bean
    @Primary
    public ObjectMapper jacksonObjectMapper() {

        LOGGER.info(CREATION.beanCreationStart(ObjectMapper.class));
        ObjectMapper mapper = new ObjectMapper();
        LOGGER.debug(CREATION.beanCreationStart(mapper.getClass()));

        //Customize jackson object mapper
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        LOGGER.debug(CREATION.beanCreationFinish(mapper.getClass()));
        LOGGER.info(CREATION.beanCreationFinish(ObjectMapper.class));
        return mapper;
    }
}
