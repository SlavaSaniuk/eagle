package by.bsac.configuration;

import by.bsac.configuration.properties.DatasourcesProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.sql.DataSource;


import static by.bsac.configuration.LoggerDefaultLogs.*;

/**
 * Spring {@link Configuration} configuration class.
 * This class contains beans definition of {@link DataSource} beans
 * for different profiles.
 */
@Configuration
@EnableJpaRepositories("by.bsac.repositories")
@EnableTransactionManagement
@EntityScan("by.bsac.models")
@EnableConfigurationProperties(DatasourcesProperties.class)
public class DatasourcesConfig {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DatasourcesConfig.class);
    //Spring beans
    private DatasourcesProperties datasources_properties; //Autowired via constructor

    /**
     * Initialize new {@link DatasourcesConfig} configuration class.
     * @param a_properties - {@link DatasourcesProperties} autowired by Spring.
     */
    @Autowired
    public DatasourcesConfig(DatasourcesProperties a_properties) {
        LOGGER.info(INITIALIZATION.initConfig(DatasourcesConfig.class));

        LOGGER.info(AUTOWIRING.viaConstructor(DatasourcesProperties.class, DatasourcesConfig.class));
        this.datasources_properties = a_properties;
    }

    /**
     * {@link DataSource} bean for "DEVELOPMENT" profile.
     * Implementation of this bean is {@link DriverManagerDataSource} class.
     * @return - configured {@link DataSource}.
     */
    @Bean
    @Profile("DEVELOPMENT")
    public DataSource developmentDataSource() {

        final String PROFILE = "DEVELOPMENT";
        LOGGER.info(CREATION.beanCreationStartForProfile(DataSource.class, PROFILE));
        DriverManagerDataSource ds = new DriverManagerDataSource();

        //Set database server URL
        ds.setUrl(this.datasources_properties.getDevelopment().getDatabaseUrl());
        ds.setDriverClassName(this.datasources_properties.getDevelopment().getDriverClassName());
        ds.setUsername(this.datasources_properties.getDevelopment().getUserName());
        ds.setPassword(this.datasources_properties.getDevelopment().getPassword());

        LOGGER.info(CREATION.beanCreationFinishForProfile(DataSource.class, PROFILE));
        return ds;

    }

    /**
     * {@link DataSource} bean for "PRODUCTION" profile.
     * Implementation of this bean is external tomcat connection pooling datasource.
     * Note: This beans creates by tomcat server. Application gets link via "JNDI" context.
     * @return - configured {@link DataSource}.
     */
    @Bean
    @Profile("PRODUCTION")
    public DataSource productionDataSource() throws NamingException {

        final String PROFILE = "PRODUCTION";
        LOGGER.info(CREATION.beanCreationStartForProfile(DataSource.class, PROFILE));
        JndiObjectFactoryBean jndi = new JndiObjectFactoryBean();

        final String JNDI_CONTEXT = "java:comp/env/";
        jndi.setJndiName(JNDI_CONTEXT +this.datasources_properties.getProduction().getJndiName());
        jndi.setProxyInterface(DataSource.class);
        jndi.setLookupOnStartup(false);
        jndi.afterPropertiesSet();

        LOGGER.info(CREATION.beanCreationFinishForProfile(DataSource.class, PROFILE));
        return (DataSource) jndi.getObject();
    }

}
