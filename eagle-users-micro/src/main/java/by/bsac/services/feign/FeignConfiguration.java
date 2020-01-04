package by.bsac.services.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Configuration
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class)
public class FeignConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignConfiguration.class);
    //Spring beans
    private ObjectMapper jacksonObjectMapper; //Autowired from WebMvcConfiguration class

    public FeignConfiguration() {
        LOGGER.info(INITIALIZATION.startInitializeConfiguration(FeignConfiguration.class));
    }

    //Feign clients


    //Feign beans
    @Bean(name = "JacksonFeignEncoder")
    public Encoder getEncoder() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("JacksonFeignEncoder").ofClass(Encoder.class)));
        final JacksonEncoder encoder = new JacksonEncoder(this.jacksonObjectMapper);

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("JacksonFeignEncoder").ofClass(Encoder.class)));
        return encoder;
    }

    @Bean(name = "JacksonFeignDecoder")
    public Decoder gerDecoder() {
        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("JacksonFeignDecoder").ofClass(Decoder.class)));
        final JacksonDecoder decoder = new JacksonDecoder(this.jacksonObjectMapper);
        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of("JacksonFeignDecoder").ofClass(Decoder.class)));
        return decoder;
    }


    @Autowired
    public void setObjectMapper(ObjectMapper jacksonObjectMapper) {
        LOGGER.info(DependencyManagement.autowireViaSetter(BeanDefinition.of("JacksonObjectMapper").ofClass(ObjectMapper.class), FeignConfiguration.class));
        this.jacksonObjectMapper = jacksonObjectMapper;
    }


}
