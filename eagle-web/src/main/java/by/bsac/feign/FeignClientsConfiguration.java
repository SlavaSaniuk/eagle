package by.bsac.feign;

import by.bsac.feign.clients.AccountManagementService;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.bsac.conf.LoggerDefaultLogs.*;

@Configuration
public class FeignClientsConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignClientsConfiguration.class);

    //Spring beans
    //Autowired via constructor
    private Encoder encoder;
    private Decoder decoder;
    private ErrorDecoder error_decoder;

    //Constructor
    @Autowired
    public FeignClientsConfiguration(Encoder a_encoder, Decoder a_decoder, ErrorDecoder a_error_decoder) {

        LOGGER.info(INITIALIZATION.initConfig(this.getClass()));

        LOGGER.debug(AUTOWIRING.viaConstructor(a_encoder.getClass(), this.getClass()));
        this.encoder = a_encoder;

        LOGGER.debug(AUTOWIRING.viaConstructor(a_decoder.getClass(), this.getClass()));
        this.decoder = a_decoder;

        LOGGER.debug(AUTOWIRING.viaConstructor(a_error_decoder.getClass(), this.getClass()));
        this.error_decoder = a_error_decoder;

    }

    @Bean(name = "AccountManagementService")
    public AccountManagementService accountManagementService() {
        LOGGER.info(CREATION.beanCreationStart(AccountManagementService.class));
        AccountManagementService ams = Feign.builder()
                .encoder(this.encoder)
                .decoder(this.decoder)
                .errorDecoder(this.error_decoder)
                .target(AccountManagementService.class, "http://10.8.8.20:36547/eagle-auth/");
        LOGGER.info(CREATION.beanCreationFinish(ams.getClass()));
        return ams;
    }
}
