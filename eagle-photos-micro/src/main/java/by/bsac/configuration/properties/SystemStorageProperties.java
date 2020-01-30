package by.bsac.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import static by.bsac.core.logging.SpringCommonLogging.*;

@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "eagle.storage")
@Getter
public class SystemStorageProperties {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemStorageProperties.class);
    //Properties
    private Images images = new Images();

    public SystemStorageProperties() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(SystemStorageProperties.class)));
    }


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
