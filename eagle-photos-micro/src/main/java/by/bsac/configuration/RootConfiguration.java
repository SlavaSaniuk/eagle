package by.bsac.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration("RootContextConfiguration")
@Import({DatasourcesConfiguration.class})
public class RootConfiguration implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(RootConfiguration.class);

    public RootConfiguration() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(RootConfiguration.class));
    }


    @Override
    public void afterPropertiesSet() {
        LOGGER.info(INITIALIZATION.endInitializeConfiguration(RootConfiguration.class));
    }
}
