package by.bsac.webmvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@ComponentScan("by.bsac.webmvc.controllers")
@EnableWebMvc
public class WebmvcConfiguration implements WebMvcConfigurer {

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        converter.setObjectMapper(mapper);
        converters.add(converter);
    }

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(WebmvcConfiguration.class);

    public WebmvcConfiguration() {
        LOGGER.info("Start to initialize " +this.getClass().getName() +" configuration class.");
    }

}
