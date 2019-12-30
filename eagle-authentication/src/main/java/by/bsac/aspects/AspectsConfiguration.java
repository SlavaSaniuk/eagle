package by.bsac.aspects;

import by.bsac.core.LoggerLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static by.bsac.conf.LoggerDefaultLogs.*;

@Configuration
public class AspectsConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectsConfiguration.class);

    public AspectsConfiguration() {
        LOGGER.info(INITIALIZATION.initConfig(AspectsConfiguration.class));
    }


    /*
        *** ltori - library ***
        * Methods executions debug aspects
     */

    private static final LoggerLevel DEFAULT_LOG_LEVEL = LoggerLevel.INFO; //Default log level

    @Bean(name = "MethodCallAspect")
    @Profile("DEBUG")
    public MethodCallAspect getMethodCallAspect() {
        LOGGER.info(CREATION.beanCreationStart(MethodCallAspect.class));
        MethodCallAspect aspect = MethodCallAspect.aspectOf();
        aspect.setLoggerLevel(DEFAULT_LOG_LEVEL);
        LOGGER.info(CREATION.beanCreationFinish(MethodCallAspect.class));
        return aspect;
    }
    
}
