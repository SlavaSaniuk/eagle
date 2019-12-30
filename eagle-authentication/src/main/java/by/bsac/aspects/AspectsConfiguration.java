package by.bsac.aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import static by.bsac.conf.LoggerDefaultLogs.*;

@Configuration
public class AspectsConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectsConfiguration.class);

    public AspectsConfiguration() {
        LOGGER.info(INITIALIZATION.initConfig(AspectsConfiguration.class));
    }



    
}
