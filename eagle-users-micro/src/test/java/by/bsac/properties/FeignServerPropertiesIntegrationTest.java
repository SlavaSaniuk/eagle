package by.bsac.properties;

import by.bsac.configuration.DatasourcesConfig;
import by.bsac.configuration.properties.FeignServersProperties;
import by.bsac.models.xml.FeignServer;
import by.bsac.services.ServicesConfiguration;
import by.bsac.services.feign.FeignConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("DEVELOPMENT")
@SpringBootTest(classes = {ServicesConfiguration.class, DatasourcesConfig.class, FeignConfiguration.class})
@EnableAutoConfiguration
@EntityScan("by.bsac.models")
public class FeignServerPropertiesIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignServerPropertiesIntegrationTest.class);
    //Spring beans
    @Autowired
    private FeignServersProperties properties;

    @Test
    void getServerById_idIs1_shouldReturnFeignServerWithIdEquals1() {

        FeignServer server = this.properties.getServerById(1);

        Assertions.assertNotNull(server);
        Assertions.assertEquals(1, server.getServerId());

        LOGGER.debug("Feign server with 1 id: " +server.toString());

    }

    @Test
    void getServerByName_definedName_shouldReturnFeignServerWithSameName() {

        final String SERVER_NAME = "eagle-authentication-microservice";
        FeignServer server = this.properties.getServerByName(SERVER_NAME);

        Assertions.assertNotNull(server);
        Assertions.assertEquals(SERVER_NAME, server.getServerName());

        LOGGER.debug("Feign server with name [eagle-authentication-microservice]: " +server.toString());

    }
}
