package by.bsac.conf;

import by.bsac.feign.FeignConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({FeignConfiguration.class})
public class RootContextConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(RootContextConfiguration.class);

    public RootContextConfiguration() {
        LOGGER.info(String.format(LoggerDefaultLogs.INITIALIZE_CONFIGURATION, getClass().getSimpleName()));
    }
}
