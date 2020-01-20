package by.bsac.webmvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration("WebmvcConfiguration")
@EnableWebMvc
public class WebmvcConfiguration implements WebMvcConfigurer, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(WebmvcConfiguration.class);

    public WebmvcConfiguration() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(WebmvcConfiguration.class));
    }

    @Override
    public void afterPropertiesSet() {
        LOGGER.info(INITIALIZATION.endInitializeConfiguration(WebmvcConfiguration.class));
    }
}
