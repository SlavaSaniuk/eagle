package by.bsac.configuration.properties;

import by.bsac.configuration.DatasourcesConfig;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static by.bsac.configuration.LoggerDefaultLogs.*;

/**
 * Configuration properties for {@link by.bsac.configuration.DatasourcesConfig} configuration class.
 * All datasource properties defined in application.properties file
 * under "src/main/resources" directory with prefix "eagle.datasource.*".
 */
@Component("DatasourcesProperties")
@ConfigurationProperties(prefix = "eagle.datasource")
@Getter
public class DatasourcesProperties {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DatasourcesProperties.class);
    private Development development = new Development();
    private Production production = new Production();
    private Test test = new Test();

    /**
     * Construct new {@link DatasourcesProperties} object
     * with defined datasources properties.
     */
    public DatasourcesProperties() {
        LOGGER.debug(CREATION.beanCreationStart(this.getClass()));
        LOGGER.debug(CREATION.beanCreationFinish(this.getClass()));
    }

    /**
     * Datasources configuration properties for {@link DatasourcesConfig#developmentDataSource()}
     * development datasource bean. This properties starts with "eagle.datasource.development.*" prefix.
     */
    @Getter @Setter
    public static class Development {

        public Development() {
            LOGGER.debug(CREATION.beanCreationStart(this.getClass()));
            LOGGER.debug(CREATION.beanCreationFinish(this.getClass()));
        }

        private String database_url;
        private String driver_class_name;
        private String user_name;
        private String password;
    }

    /**
     * Datasources configuration properties for {@link DatasourcesConfig#productionDataSource()}
     * production datasource bean. This properties starts with "eagle.datasource.production.*" prefix.
     */
    @Getter @Setter
    public static class Production {

        public Production() {
            LOGGER.debug(CREATION.beanCreationStart(this.getClass()));
            LOGGER.debug(CREATION.beanCreationFinish(this.getClass()));
        }

        private  String jndi_name;

    }

    /**
     * Datasources configuration properties for {@link DatasourcesConfig#testDataSource()}
     * test datasource bean. This properties starts with "eagle.datasource.test.*" prefix.
     */
    @Getter @Setter
    public static class Test {

        public Test() {
            LOGGER.debug(CREATION.beanCreationStart(this.getClass()));
            LOGGER.debug(CREATION.beanCreationFinish(this.getClass()));
        }

        private String database_url;
        private String driver_class_name;
        private String user_name;
        private String password;
    }

}
