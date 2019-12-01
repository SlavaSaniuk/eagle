package by.bsac.services;

import by.bsac.configuration.DatasourcesConfig;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import by.bsac.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@ActiveProfiles("DEVELOPMENT")
@SpringBootTest(classes = {DatasourcesConfig.class, ServicesConfiguration.class})
@EnableAutoConfiguration
@EntityScan("by.bsac.models")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DetailsManagerIntegrationTest {

    @Autowired
    private DetailsManager manager;

    @Autowired
    private UserRepository user_repository;


    @Test
    @Order(1)
    @Transactional
    @Commit
    public void setUp() {
        User user = new User();
        user.setUserIdAlias("test-alias-2");
        user = this.user_repository.save(user);
        System.out.println("AAA: " +user.getUserId().toString());
    }

    @Test
    @Commit
    @Order(2)
    @Transactional
    public void createDetails_persistedUser_shouldReturnCreatedDetails() {

        User user = this.user_repository.findByUserIdAlias("test-alias-2");

        UserDetails details = new UserDetails();
        details.setUserName(new UserName("Slava", "Saniuk"));
        details = this.manager.createDetails(user, details);

        Assertions.assertNotNull(details);
        Assertions.assertEquals(user.getUserId(), details.getDetailId());
    }

    @Test
    public void createDetails_notInPersistenceContext_shouldReturnCreatedDetails() {

        User user = new User();
        user.setUserId(3);

        UserDetails details = new UserDetails();
        details.setUserName(new UserName("Slava", "Saniuk"));
        details = this.manager.createDetails(user, details);

        Assertions.assertNotNull(details);
        Assertions.assertEquals(user.getUserId(), details.getDetailId());
    }

}
