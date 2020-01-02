package by.bsac.services.feign;

import by.bsac.services.feign.services.AccountStatusesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import static by.bsac.configuration.LoggerDefaultLogs.*;

@Configuration
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class, clients = {AccountStatusesManager.class})
public class FeignConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignConfiguration.class);

    public FeignConfiguration() {
        LOGGER.info(INITIALIZATION.initConfig(FeignConfiguration.class));
    }





}
