package by.bsac.feign;

import by.bsac.conf.LoggerDefaultLogs;
import by.bsac.feign.clients.AccountManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
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

        AccountManagementService ams = Feign.builder()
                .encoder(this.jacksonEncoder())
                .decoder(this.jacksonDecoder())
                .target(AccountManagementService.class, "http://10.8.8.20:36547/eagle-auth/");

        LOGGER.debug(String.format(LoggerDefaultLogs.CREATE_BEAN_FINISH, ams.getClass().getSimpleName()));
        return ams;
    }

    @Bean("JacksonEncoder")
    public Encoder jacksonEncoder() {
        LOGGER.debug(String.format(LoggerDefaultLogs.CREATE_BEAN_START, JacksonEncoder.class.getSimpleName()));
        final JacksonEncoder encoder = new JacksonEncoder(this.jacksonObjectMapper());

        LOGGER.debug(String.format(LoggerDefaultLogs.CREATE_BEAN_FINISH, encoder.getClass().getSimpleName()));
        return encoder;
    }

    @Bean("jacksonDecoder")
    public Decoder jacksonDecoder() {
        LOGGER.debug(String.format(LoggerDefaultLogs.CREATE_BEAN_START, JacksonDecoder.class.getSimpleName()));
        JacksonDecoder decoder = new JacksonDecoder(this.jacksonObjectMapper());

        LOGGER.debug(String.format(LoggerDefaultLogs.CREATE_BEAN_FINISH, decoder.getClass().getSimpleName()));
        return decoder;
    }

    @Bean("ObjectMapper")
    public ObjectMapper jacksonObjectMapper() {

        LOGGER.debug(String.format(LoggerDefaultLogs.CREATE_BEAN_START, ObjectMapper.class.getSimpleName()));
        ObjectMapper mapper = new ObjectMapper();

        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        LOGGER.debug(String.format(LoggerDefaultLogs.CREATE_BEAN_FINISH, mapper.getClass().getSimpleName()));
        return mapper;
    }


}
