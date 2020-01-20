package by.bsac.configuration.properties;

import by.bsac.configuration.DatasourcesConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static by.bsac.core.logging.SpringCommonLogging.*;

/**
 * Configuration properties bean for {@link javax.sql.DataSource} configuration for various profiles.
 * Bean has three section for various datasources
 * beans (DATASOURCE_DEVELOPMENT, DATASOURCE_TEST, DATASOURCE_PRODUCTION).
 */
@Component("DatasourcesProperties")
@ConfigurationProperties("eagle.datasource")
public class DatasourcesProperties implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DatasourcesProperties.class);

    /**
     * Construct new {@link DatasourcesProperties} bean.
     */
    public DatasourcesProperties() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(this.getClass())));
    }

    //Spring properties for profiles
    private Development development = new Development(); //For DATASOURCE_DEVELOPMENT profile

    /**
     * Configuration properties {@link DatasourcesConfiguration#getDevDataSource()}
     * DATASOURCE_DEVELOPMENT bean.
     */
    @Getter @Setter
    public static class Development {

        private String database_url;
        private String driver_class_name;
        private String user_name;
        private String password;

    }

    /**
     * Method return {@link Development} configuration properties.
     * @return - {@link Development} configuration properties.
     */
    public Development forDevelopmentProfile() {
        return this.development;
    }

    @Override
    public void afterPropertiesSet() {
        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(this.getClass())));
    }
}
