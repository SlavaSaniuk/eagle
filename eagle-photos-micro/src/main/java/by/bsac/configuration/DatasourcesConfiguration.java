package by.bsac.configuration;

import by.bsac.configuration.properties.DatasourcesProperties;
import by.bsac.repositories.UserCrudRepository;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static by.bsac.core.logging.SpringCommonLogging.*;

/**
 * Configuration class for JDBC/Spring Data configuration. Class define {@link DataSource} beans definitions
 * and {@link DatasourcesProperties} configuration properties.
 */
@Configuration("DatasourcesConfiguration")
@EnableConfigurationProperties(DatasourcesProperties.class)
@EntityScan("by.bsac.domain.models")
@EnableJpaRepositories(basePackageClasses = UserCrudRepository.class)
@EnableTransactionManagement
public class DatasourcesConfiguration implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DatasourcesConfiguration.class);

    //Spring beans
    private DatasourcesProperties properties; //Autowired via setter from DatasourcesProperties class

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

        ds.setUrl(this.properties.getDevelopment().getDatabaseUrl());
        ds.setDriverClassName(this.properties.getDevelopment().getDriverClassName());
        ds.setUsername(this.properties.getDevelopment().getUserName());
        ds.setPassword(this.properties.getDevelopment().getPassword());

        LOGGER.info(
                CREATION.endCreateBean(BeanDefinition.of("Datasource").ofClass(DataSource.class).forProfile("DATASOURCE_DEVELOPMENT")));
        return ds;
    }

    /**
     * {@link DataSource} bean for "DATASOURCE_TESTS" profile. Use this {@link DataSource}
     * only for tests purposes.
     * @return - Configured {@link DataSource} bean.
     */
    @Bean(name = "Datasource")
    @Profile("DATASOURCE_TESTS")
    public DataSource getTestDataSource() {

        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("Datasource").ofClass(DataSource.class).forProfile("DATASOURCE_TESTS")));
        BasicDataSource ds = new BasicDataSource();

        //Datasource properties
        ds.setUrl(this.properties.getTest().getDatabaseUrl());
        ds.setDriverClassName(this.properties.getTest().getDriverClassName());
        ds.setUsername(this.properties.getTest().getUserName());
        ds.setPassword(this.properties.getTest().getPassword());

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("Datasource").ofClass(DataSource.class).forProfile("DATASOURCE_TESTS")));
        return ds;

    }

    @Override
    public void afterPropertiesSet() {
        LOGGER.info(INITIALIZATION.endInitializeConfiguration(DatasourcesConfiguration.class));
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public void setDatasourceProperties(DatasourcesProperties properties) {
        LOGGER.info(DependencyManagement.autowireViaSetter(BeanDefinition.of(DatasourcesProperties.class), DatasourcesConfiguration.class));
        this.properties = properties;
    }
}
