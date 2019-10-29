package by.bsac.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


import static by.bsac.configuration.LoggerDefaultLogs.*;

@Configuration
@EnableJpaRepositories("by.bsac.repositories")
@EnableTransactionManagement
@EntityScan("by.bsac.models")
public class DatasourcesConfig {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DatasourcesConfig.class);

    public DatasourcesConfig() {
        LOGGER.info(INITIALIZATION.initConfig(this.getClass()));
    }

    @Bean
    @Profile("DEVELOPMENT")
    public DataSource devDataSource() {

        LOGGER.debug(CREATION.beanCreationStart(DataSource.class));
        DriverManagerDataSource ds = new DriverManagerDataSource();

        //Set database server URL
        final String DATABASE_URL = "jdbc:mysql://10.8.8.100:3306/eagle_users";
        ds.setUrl(DATABASE_URL);

        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //Credentials
        ds.setUsername("eagle-admin");
        ds.setPassword("12345678");

        LOGGER.debug(CREATION.beanCreationFinish(ds.getClass()));
        return ds;

    }

}
