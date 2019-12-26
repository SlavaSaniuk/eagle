package by.bsac.conf;


import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatasourcesConfiguration {

    @Bean("DevelopmentDataSource")
    @Profile("DEVELOPMENT")
    public DataSource devDataSource() {

        //Create spring driver manager datasource
        DriverManagerDataSource ds = new DriverManagerDataSource();

        //Set database server URL
        final String DATABASE_URL = "jdbc:mysql://10.8.8.100:3306/eagle_users";
        ds.setUrl(DATABASE_URL);

        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //Credentials
        ds.setUsername("eagle-admin");
        ds.setPassword("12345678");

        return ds;
    }

    @Bean("TestDataSource")
    @Profile("TEST")
    public DataSource testDataSource() {

        BasicDataSource ds = new BasicDataSource();

        final String DATABASE_URL = "jdbc:mysql://10.8.8.110:3306/eagle_users";
        ds.setUrl(DATABASE_URL);
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("eagle-admin");
        ds.setPassword("12345678");

        return ds;
    }

}
