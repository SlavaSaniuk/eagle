package by.bsac.services.context;

import by.bsac.configuration.RootConfiguration;
import by.bsac.domain.models.User;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.services.images.context.ImagesContextService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import testconfiguration.TestsAspectsConfiguration;

@ActiveProfiles({"DATASOURCE_TESTS", "ASPECT_DEBUG"})
@SpringBootTest(classes = {RootConfiguration.class, TestsAspectsConfiguration.class})
public class ImagesContextServiceIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesContextServiceIntegrationTest.class);
    //Spring beans
    @Autowired
    private ImagesContextService TEST;

    @Test
    @Transactional
    void createUserImagesContext_createdUser_shouldReturnCreatedUserImagesContextEntity() {

        final Integer ID = 1;

        User a_user = new User();
        a_user.setUserId(ID);

        UserImagesContext test = this.TEST.createUserImagesContext(a_user, new UserImagesContext());

        Assertions.assertNotNull(test);
        Assertions.assertEquals(ID, test.getContextId());
        Assertions.assertNotNull(test.getImagesOwner());

        LOGGER.debug("Created context: " +test);
    }
}
