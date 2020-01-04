package by.bsac.services.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class)
public class FeignConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignConfiguration.class);

    public FeignConfiguration() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(FeignConfiguration.class));
    }


}
