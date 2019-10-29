package by.bsac.services;

import by.bsac.configuration.DatasourcesConfig;
import by.bsac.models.User;
import by.bsac.repositories.UserRepository;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("TEST")
@SpringBootTest(classes = {DatasourcesConfig.class, ServicesConfiguration.class})
@EnableAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DetailsControllerIntegrationTest {

    //Spring beans
    private MockMvc mock_mvc;

    @Autowired
    private static UserRepository user_repo;

    @Test
    @Order(1)
    @Transactional
    @Commit
    public void setUpBeforeAll() {
        User user = new User();
        user.setUserIdAlias("details-controller-test");

        user_repo.save(user);
    }

    @Test
    @Order(2)
    public void createDetails_registeredUser_shouldCreateNewDetails() {

        //Create input dto
        UserWithDetailsDto input = new UserWithDetailsDto();


    }

}
