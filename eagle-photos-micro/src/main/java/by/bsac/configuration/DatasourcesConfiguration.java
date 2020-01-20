package by.bsac.configuration;

import by.bsac.configuration.properties.DatasourcesProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static by.bsac.core.logging.SpringCommonLogging.*;

/**
 * Configuration class for JDBC/Spring Data configuration. Class define {@link DataSource} beans definitions
 * and {@link DatasourcesProperties} configuration properties.
 */
@Configuration("DatasourcesConfiguration")
public class DatasourcesConfiguration implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DatasourcesConfiguration.class);

    /**
     * Construct new {@link DatasourcesConfiguration} configuration class object.
     */
    public DatasourcesConfiguration() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(DatasourcesConfiguration.class));
    }

    //Datasources for profiles

    /**
     * {@link DataSource} bean for "DATASOURCE_DEVELOPMENT" profile. Use this {@link DataSource}
     * only for development purposes.
     * @return - Configured {@link DataSource} bean.
     */
    @Bean(name = "Datasource")
    @Profile("DATASOURCE_DEVELOPMENT")
    public DataSource getDevDataSource() {

        LOGGER.info(
                CREATION.startCreateBean(BeanDefinition.of("Datasource").ofClass(DataSource.class).forProfile("DATASOURCE_DEVELOPMENT")));
        DriverManagerDataSource ds = new DriverManagerDataSource();

        ds.setUrl(this.getDatasourcesProperties().forDevelopmentProfile().getDatabaseUrl());
        ds.setDriverClassName(this.getDatasourcesProperties().forDevelopmentProfile().getDriverClassName());
        ds.setUsername(this.getDatasourcesProperties().forDevelopmentProfile().getUserName());
        ds.setPassword(this.getDatasourcesProperties().forDevelopmentProfile().getPassword());

        LOGGER.info(
                CREATION.endCreateBean(BeanDefinition.of("Datasource").ofClass(DataSource.class).forProfile("DATASOURCE_DEVELOPMENT")));
        return ds;
    }

    // Docs in class definition
    @Bean("DatasourcesProperties")
    public DatasourcesProperties getDatasourcesProperties() {

        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of(DatasourcesProperties.class)));
        DatasourcesProperties props = new DatasourcesProperties();

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of(DatasourcesProperties.class)));
        return props;
    }

    @Override
    public void afterPropertiesSet() {
        LOGGER.info(INITIALIZATION.endInitializeConfiguration(DatasourcesConfiguration.class));
    }
}
