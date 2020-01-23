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
 * All datasource properties defined in application.properties file
 * under "src/main/resources" directory with prefix "eagle.datasource.*".
 */
@Component("DatasourcesProperties")
@ConfigurationProperties(prefix = "eagle.datasource")
@Getter
public class DatasourcesProperties implements InitializingBean {

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
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(DatasourcesProperties.class)));
    }

    @Override
    public void afterPropertiesSet() {
        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(DatasourcesProperties.class)));
    }

    /**
     * Datasource configuration properties for "DATASOURCE_DEVELOPMENT" profile
     * and {@link DatasourcesConfiguration#getDevDataSource()} bean.
     */
    @Getter @Setter
    public static class Development {

        private String database_url;
        private String driver_class_name;
        private String user_name;
        private String password;

        @Override
        public String toString() {
            return String.format("Datasource {profile [DATASOURCE_DEVELOPMENT], url [%s], driver_class [%s], user_name [%s]};",
                    this.database_url, this.driver_class_name, this.user_name);
        }
    }


    @Getter @Setter
    public static class Production {

        private  String jndi_name;

        @Override
        public String toString() {
            return String.format("Datasource {profile [DATASOURCE_PRODUCTION], jndi_name [%s]};",
                    this.jndi_name);
        }
    }

    /**
     * Datasource configuration properties for "DATASOURCE_TESTS" profile
     * and {@link DatasourcesConfiguration#getTestDataSource()} bean.
     */
    @Getter @Setter
    public static class Test {

        private String database_url;
        private String driver_class_name;
        private String user_name;
        private String password;

        @Override
        public String toString() {
            return String.format("Datasource {profile [DATASOURCE_TESTS], url [%s], driver_class [%s], user_name [%s]};",
                    this.database_url, this.driver_class_name, this.user_name);
        }
    }

}
