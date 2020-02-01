package by.bsac.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import static by.bsac.core.logging.SpringCommonLogging.*;

/**
 * External configuration properties bean. Properties defined in "application.properties"
 * file and starts with "eagle.storage" prefix.
 */
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "eagle.storage")
@Getter @Setter
public class SystemStorageProperties {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemStorageProperties.class);
    //Properties
    private Images images = new Images();

    //Constructor
    public SystemStorageProperties() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(SystemStorageProperties.class)));
    }

    /**
     * Inner class define external configuration properties for images section.
     * Properties starts with "eagle.storage.images" prefix.
     */
    @Getter @Setter
    public static class Images {

        private String storage_path;

        @Override
        public String toString() {
            return "Images{" +
                    "storage_path='" + storage_path + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SystemStorageProperties{" +
                "images_properties=" + images +
                '}';
    }
}
