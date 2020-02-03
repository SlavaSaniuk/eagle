package by.bsac.webmvc;

import by.bsac.webmvc.controllers.ImagesContextsController;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration("WebmvcConfiguration")
@EnableWebMvc
@Import({DtoConversionConfiguration.class})
@ComponentScan(basePackageClasses = ImagesContextsController.class)
public class WebmvcConfiguration implements WebMvcConfigurer, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(WebmvcConfiguration.class);

    public WebmvcConfiguration() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(WebmvcConfiguration.class));
    }

    @Bean(name = "jacksonObjectMapper")
    @Primary
    public ObjectMapper jacksonObjectMapper() {

        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(ObjectMapper.class)));
        ObjectMapper mapper = new ObjectMapper();

        //Customize jackson object mapper
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(ObjectMapper.class)));
        return mapper;
    }

    @Override
    public void afterPropertiesSet() {
        LOGGER.info(INITIALIZATION.endInitializeConfiguration(WebmvcConfiguration.class));
    }
}
