package by.bsac.feign;

import by.bsac.conf.LoggerDefaultLogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class)
public class FeignConfiguration {

    //logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignConfiguration.class);

    public FeignConfiguration() {
        LOGGER.info(String.format(LoggerDefaultLogs.INITIALIZE_CONFIGURATION, this.getClass().getSimpleName()));
    }

}
