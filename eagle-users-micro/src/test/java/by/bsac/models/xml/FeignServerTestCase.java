package by.bsac.models.xml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeignServerTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeignServerTestCase.class);

    @Test
    void of_feignServerModel_shouldReturnNewFeignServerObject() {

        final int SERVER_ID = 24;
        final String SERVER_NAME = "TEST-SERVER";
        final String SERVER_IP = "192.168.0.1";
        final int SERVER_PORT = 69;
        final String SERVER_CONTEXT = "/Hello_world";

        FeignServerModel model = new FeignServerModel();
        model.setServerId(SERVER_ID);
        model.setServerName(SERVER_NAME);
        model.setServerIp(SERVER_IP);
        model.setServerPort(SERVER_PORT);
        model.setServerContext(SERVER_CONTEXT);

        FeignServer test = FeignServer.of(model);

        Assertions.assertNotNull(test);
        Assertions.assertEquals(SERVER_ID, test.getServerId());
        Assertions.assertEquals(SERVER_NAME, test.getServerName());
        Assertions.assertEquals(SERVER_IP, test.getServerIp());
        Assertions.assertEquals(SERVER_PORT, test.getServerPort());
        Assertions.assertEquals(SERVER_CONTEXT, test.getServerContextPath());

        LOGGER.debug("FeignServerModel: " +model.toString());
        LOGGER.debug("FeignServer: " +test.toString());
    }

    @Test
    void getFullServerPath_newFeignServer_shouldReturnFullServerPath() {
        final String EXPECTED_FULL_PATH = "http://192.168.0.1:69/Hello_world";

        final int SERVER_ID = 24;
        final String SERVER_NAME = "TEST-SERVER";
        final String SERVER_IP = "192.168.0.1";
        final int SERVER_PORT = 69;
        final String SERVER_CONTEXT = "/Hello_world";

        FeignServerModel model = new FeignServerModel();
        model.setServerId(SERVER_ID);
        model.setServerName(SERVER_NAME);
        model.setServerIp(SERVER_IP);
        model.setServerPort(SERVER_PORT);
        model.setServerContext(SERVER_CONTEXT);

        FeignServer test = FeignServer.of(model);

        Assertions.assertEquals(EXPECTED_FULL_PATH, test.getFullServerPath());
        LOGGER.debug("Full server path: " +test.getFullServerPath());

    }
}
