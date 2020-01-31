package by.bsac.configuration;

import by.bsac.configuration.properties.SystemStorageProperties;
import by.bsac.services.ServicesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static by.bsac.core.logging.SpringCommonLogging.*;

/**
 * Main Root Spring Application Context configuration class.
 * Class import other configuration classes for root application context
 * via {@link Import} annotation.
 */
@Configuration("RootContextConfiguration")
@Import({DatasourcesConfiguration.class, ServicesConfiguration.class})
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

    /**
     * External configuration properties bean. Properties start with "eagle.storage" prefix.
     * @return - Configuration properties storage bean.
     */
    @Bean("SystemStorageProperties")
    public SystemStorageProperties getSystemStorageProperties() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(SystemStorageProperties.class)));
        SystemStorageProperties props = new SystemStorageProperties();

        LOGGER.info("Configuration properties for Images section: " +props.getImages());

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(SystemStorageProperties.class)));
        return props;
    }



}
