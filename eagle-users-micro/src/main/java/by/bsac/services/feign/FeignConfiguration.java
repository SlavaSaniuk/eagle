package by.bsac.services.feign;

import by.bsac.configuration.properties.FeignServersProperties;
import by.bsac.models.xml.FeignServersModel;
import by.bsac.services.xml.XmlConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBException;

import static by.bsac.core.logging.SpringCommonLogging.*;

@SuppressWarnings("AccessStaticViaInstance")
@Configuration
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class)
public class FeignConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignConfiguration.class);
    //Spring beans
    private ObjectMapper jacksonObjectMapper; //Autowired from WebMvcConfiguration class
    private XmlConverter<FeignServersModel> feign_servers_xml_converter; //Autowired from ServicesConfiguration class

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



    @Bean(name = "FeignServersProperties")
    public FeignServersProperties getFeignServerProperties() {

        LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("FeignServerProperties").ofClass(FeignServersProperties.class)));
        LOGGER.info(DependencyManagement.setViaConstructor(BeanDefinition.of("FeignServersModel").ofClass(FeignServersModel.class), FeignServersProperties.class));
        FeignServersProperties properties = new FeignServersProperties(this.getFeignServersModel());

        LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("FeignServerProperties").ofClass(FeignServersProperties.class)));
        return properties;

    }

    @Bean(name = "FeignServerModel")
    public FeignServersModel getFeignServersModel() {

        //Parse feign-servers xml file
        try {
            LOGGER.info(CREATION.startCreateBean(BeanDefinition.of("FeignServersModel").ofClass(FeignServersModel.class)));
            FeignServersModel model = this.feign_servers_xml_converter.convertToObject();

            LOGGER.info(CREATION.endCreateBean(BeanDefinition.of("FeignServersModel").ofClass(FeignServersModel.class)));
            return model;
        } catch (JAXBException e) {
            e.printStackTrace();
            LOGGER.info(CREATION.creationThrowExceptionWithMessage(
                    BeanDefinition.of("FeignServersModel").ofClass(FeignServersModel.class), e));
            throw new BeanCreationException(e.getMessage());
        }

    }

    //Spring autowiring
    @Autowired
    public void setObjectMapper(ObjectMapper jacksonObjectMapper) {
        LOGGER.info(DependencyManagement.autowireViaSetter(
                BeanDefinition.of("JacksonObjectMapper").ofClass(ObjectMapper.class), FeignConfiguration.class));
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    @Autowired @Qualifier("FeignServersModelXmlConverter")
    public void setFeignServersXmlConverter(XmlConverter<FeignServersModel> a_converter) {
        LOGGER.info(DependencyManagement.autowireViaSetter(
                BeanDefinition.of("FeignServersModelXmlConverter").ofClass(XmlConverter.class).forGenericType(FeignServersModel.class), FeignConfiguration.class));
        this.feign_servers_xml_converter = a_converter;
    }


}
