package by.bsac.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static by.bsac.configuration.LoggerDefaultLogs.*;

@Component("DatasourcesProperties")
@ConfigurationProperties(prefix = "eagle.datasource")
@Getter
public class DatasourcesProperties {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DatasourcesProperties.class);
    private Development development = new Development();
    private Production production = new Production();


    public DatasourcesProperties() {
        LOGGER.debug(CREATION.beanCreationStart(this.getClass()));
        LOGGER.debug(CREATION.beanCreationFinish(this.getClass()));
    }



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

    @Getter @Setter
    public static class Production {

        public Production() {
            LOGGER.debug(CREATION.beanCreationStart(this.getClass()));
            LOGGER.debug(CREATION.beanCreationFinish(this.getClass()));
        }

        private  String jndi_name;

    }


}
