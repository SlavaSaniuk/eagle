package by.bsac.feign;

import by.bsac.conf.LoggerDefaultLogs;
import by.bsac.feign.clients.AccountManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class)
@Import(FeignClientsConfiguration.class) //Import feign clients beans
public class FeignConfiguration {

    //logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignConfiguration.class);

    public FeignConfiguration() {
        LOGGER.info(String.format(LoggerDefaultLogs.INITIALIZE_CONFIGURATION, this.getClass().getSimpleName()));
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

    @Bean("FeignExceptionHandler")
    public ErrorDecoder feignExceptionHandler() {

        LOGGER.debug(String.format(LoggerDefaultLogs.CREATE_BEAN_START, ErrorDecoder.class.getSimpleName()));
        FeignExceptionHandler handler = new FeignExceptionHandler();


        handler.setObjectMapper(this.jacksonObjectMapper());

        return handler;
    }

}
