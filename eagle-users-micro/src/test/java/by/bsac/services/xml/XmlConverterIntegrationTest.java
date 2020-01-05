package by.bsac.services.xml;

import by.bsac.configuration.DatasourcesConfig;
import by.bsac.models.xml.FeignServersModel;
import by.bsac.services.ServicesConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.xml.bind.JAXBException;

@ActiveProfiles("DEVELOPMENT")
@SpringBootTest(classes = {ServicesConfiguration.class, DatasourcesConfig.class})
@EnableAutoConfiguration
@EntityScan("by.bsac.models")
public class XmlConverterIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlConverterIntegrationTest.class);

    @Autowired
    private XmlConverter<FeignServersModel> test_converter;

    @Test
    public void convertToObject_feignServersXmlFile_shouldReturnFeignServersModelObject() throws JAXBException {

        FeignServersModel model = this.test_converter.convertToObject();

        Assertions.assertNotNull(model);
        Assertions.assertEquals(3, model.getFeignServers().size());

        model.getFeignServers().stream().forEach(s -> LOGGER.debug(
                String.format("Server: id: %d, name: %s, ip: %s, port: %d, context: %s;", s.getServerId(), s.getServerName(), s.getServerIp(), s.getServerPort(), s.getServerContext())));
    }

}
