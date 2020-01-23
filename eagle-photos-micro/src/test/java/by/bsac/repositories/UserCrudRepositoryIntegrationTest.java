package by.bsac.repositories;

import by.bsac.configuration.RootConfiguration;
import by.bsac.domain.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("DATASOURCE_TESTS")
@SpringBootTest(classes = {RootConfiguration.class})
public class UserCrudRepositoryIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCrudRepositoryIntegrationTest.class);
    //Spring beans
    @Autowired @Qualifier("UserRepository")
    private UserCrudRepository repository;

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void getById_persistedUser_shouldReturnUserEntity() {

        User user  = this.repository.findById(1).get();

        Assertions.assertNotNull(user);
        Assertions.assertEquals(1, user.getUserId());

        LOGGER.debug("User: " +user);

    }

}
