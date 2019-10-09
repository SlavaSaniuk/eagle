package by.bsac.feign;

import by.bsac.conf.LoggerDefaultLogs;
import by.bsac.feign.clients.AccountManagementService;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientsConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignClientsConfiguration.class);

    //Spring beans
    //Autowired via constructor
    private Encoder encoder;
    private Decoder decoder;

    //Constructor
    @Autowired
    public FeignClientsConfiguration(Encoder a_encoder, Decoder a_decoder) {

        LOGGER.info(LoggerDefaultLogs.INITIALIZATION.initConfig(this.getClass()));

        LOGGER.debug(LoggerDefaultLogs.AUTOWIRING.viaConstructor(a_encoder.getClass(), this.getClass()));
        this.encoder = a_encoder;

        LOGGER.debug(LoggerDefaultLogs.AUTOWIRING.viaConstructor(a_decoder.getClass(), this.getClass()));
        this.decoder = a_decoder;

    }

    @Bean(name = "AccountManagementService")
    public AccountManagementService accountManagementService() {
        LOGGER.debug(String.format(LoggerDefaultLogs.CREATE_BEAN_START, AccountManagementService.class.getSimpleName()));

        AccountManagementService ams = Feign.builder()
                .encoder(this.encoder)
                .decoder(this.decoder)
                .target(AccountManagementService.class, "http://10.8.8.20:36547/eagle-auth/");

        LOGGER.debug(String.format(LoggerDefaultLogs.CREATE_BEAN_FINISH, ams.getClass().getSimpleName()));
        return ams;
    }
}
