package by.bsac.services;

import by.bsac.configuration.DatasourcesConfiguration;
import by.bsac.configuration.RootConfiguration;
import by.bsac.domain.models.User;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.repositories.UserCrudRepository;
import by.bsac.services.images.context.UserImagesContextCrudService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import testconfiguration.TestsAspectsConfiguration;


@SuppressWarnings("OptionalGetWithoutIsPresent")
@ActiveProfiles({"DATASOURCE_TESTS"})
@SpringBootTest(classes = {ServicesConfiguration.class, DatasourcesConfiguration.class, TestsAspectsConfiguration.class, RootConfiguration.class})
public class UserImagesContextCrudServiceIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserImagesContextCrudServiceIntegrationTest.class);
    //Spring beans
    @Autowired
    private UserImagesContextCrudService service;
    @Autowired
    private UserCrudRepository user_repository;

    @Test
    @Transactional
    void create_userImagesContextEntity_shouldCreateUserImagesContextEntity() {

        User user = this.user_repository.findById(2).get();

        UserImagesContext context = new UserImagesContext();

        context.setImagesOwner(user);
        user.setImagesContext(context);

        UserImagesContext CREATED = this.service.create(context);

        Assertions.assertNotNull(CREATED);
        Assertions.assertEquals(user.getUserId(), CREATED.getContextId()); //1:1

        LOGGER.debug("Created entity: " +CREATED);

    }

    @Test
    @Transactional
    void get_userImagesContextEntityWithId2_shouldReturnImagesContextEntity() {

        this.create_userImagesContextEntity_shouldCreateUserImagesContextEntity();

        UserImagesContext FOUNDED = this.service.get(2);

        Assertions.assertNotNull(FOUNDED);
        Assertions.assertEquals(2, FOUNDED.getContextId());

        LOGGER.debug("Founded entity: " +FOUNDED);
    }

    @Test
    @Transactional
    void delete_userImagesContextEntityWithId2_shouldDeleteImagesContextEntity() {

        this.create_userImagesContextEntity_shouldCreateUserImagesContextEntity();

        UserImagesContext FOUNDED = this.service.get(2);
        Assertions.assertNotNull(FOUNDED);

        this.service.delete(FOUNDED);
        FOUNDED = this.service.get(2);

        Assertions.assertNull(FOUNDED);

    }
}
