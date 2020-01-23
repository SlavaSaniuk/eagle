package by.bsac.repositories;

import by.bsac.configuration.RootConfiguration;
import by.bsac.domain.models.User;
import by.bsac.domain.models.UserImagesContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("DATASOURCE_TESTS")
@SpringBootTest(classes = RootConfiguration.class)
public class UserImagesContextCrudRepositoryIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserImagesContextCrudRepositoryIntegrationTest.class);
    //Spring beans
    @Autowired @Qualifier("UserImagesContextRepository")
    private UserImagesContextCrudRepository repository;

    @Autowired @Qualifier("UserRepository")
    private UserCrudRepository user_repository;

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    @Transactional
    @Commit
    void save_newUserImagesContextEntity_shouldSetContextId() {

        User user = this.user_repository.findById(2).get();

        UserImagesContext context = new UserImagesContext();
        context.setImagesOwner(user);
        user.setImagesContext(context);

        UserImagesContext CREATED = this.repository.save(context);

        Assertions.assertNotNull(CREATED);
        Assertions.assertEquals(user.getUserId(), CREATED.getContextId());

        LOGGER.debug("Created context: " +CREATED);
    }
}
