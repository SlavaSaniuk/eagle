package by.bsac.feign;

import by.bsac.conf.LoggerDefaultLogs;
import by.bsac.feign.clients.AccountManagementService;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class)
public class FeignConfiguration {

    //logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignConfiguration.class);

    public FeignConfiguration() {
        LOGGER.info(String.format(LoggerDefaultLogs.INITIALIZE_CONFIGURATION, this.getClass().getSimpleName()));
    }

    @Bean(name = "AccountManagementService")
    public AccountManagementService accountManagementService() {
        LOGGER.debug(String.format(LoggerDefaultLogs.CREATE_BEAN_START, AccountManagementService.class.getSimpleName()));
        AccountManagementService ams = Feign.builder().encoder(new JacksonEncoder()).decoder(new JacksonDecoder()).target(AccountManagementService.class, "http://10.8.8.20:36547/eagle-auth/");

        LOGGER.debug(String.format(LoggerDefaultLogs.CREATE_BEAN_FINISH, ams.getClass().getSimpleName()));
        return ams;
    }


}
