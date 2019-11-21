package by.bsac.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
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

import static by.bsac.conf.LoggerDefaultLogs.*;

@Configuration
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class)
@Import(FeignClientsConfiguration.class) //Import feign clients beans
public class FeignConfiguration {

    //logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignConfiguration.class);

    public FeignConfiguration() {
        LOGGER.info(INITIALIZATION.initConfig(this.getClass()));
    }

    @Bean("JacksonEncoder")
    public Encoder jacksonEncoder() {
        LOGGER.debug(CREATION.beanCreationStart(JacksonEncoder.class));
        final JacksonEncoder encoder = new JacksonEncoder(this.jacksonObjectMapper());

        LOGGER.debug(CREATION.beanCreationFinish(encoder.getClass()));
        return encoder;
    }

    @Bean("jacksonDecoder")
    public Decoder jacksonDecoder() {
        LOGGER.info(CREATION.beanCreationStart(Decoder.class));
        JacksonDecoder decoder = new JacksonDecoder(this.jacksonObjectMapper());

        LOGGER.info(CREATION.beanCreationFinish(decoder.getClass()));
        return decoder;
    }

    @Bean("ObjectMapper")
    public ObjectMapper jacksonObjectMapper() {
        LOGGER.info(CREATION.beanCreationFinish(ObjectMapper.class));
        ObjectMapper mapper = new ObjectMapper();

        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        LOGGER.info(CREATION.beanCreationFinish(mapper.getClass()));
        return mapper;
    }

    @Bean("FeignExceptionHandler")
    public ErrorDecoder feignExceptionHandler() {

        LOGGER.info(CREATION.beanCreationStart(ErrorDecoder.class));
        FeignExceptionHandler handler = new FeignExceptionHandler();

        handler.setObjectMapper(this.jacksonObjectMapper());

        LOGGER.info(CREATION.beanCreationFinish(handler.getClass()));
        return handler;
    }

}
