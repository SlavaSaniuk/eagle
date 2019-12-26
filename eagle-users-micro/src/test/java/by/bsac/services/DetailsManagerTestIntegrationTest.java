package by.bsac.services;

import by.bsac.configuration.DatasourcesConfig;
import by.bsac.exceptions.NoCreatedDetailsException;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import by.bsac.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("TEST")
@SpringBootTest(classes = {ServicesConfiguration.class, DatasourcesConfig.class})
@EntityScan("by.bsac.models")
@EnableAutoConfiguration
public class DetailsManagerTestIntegrationTest {

    //Spring beans
    @Autowired
    private DetailsManager details_manager;
    @Autowired
    private UserRepository user_repository;

    @Test
    public void getDetails_persistedUserWithDetails_shouldReturnDetails() throws NoCreatedDetailsException {

        //Persist details before
        User user = new User();
        user = this.user_repository.save(user);

        UserDetails details = new UserDetails();
        details.setUserName(new UserName("TEST", "TEST2"));

        UserDetails persisted = this.details_manager.createDetails(user, details);

        //Get user details
        UserDetails test = this.details_manager.getDetails(user);

        Assertions.assertNotNull(test);
        Assertions.assertEquals(persisted.getDetailId(), test.getDetailId());

    }

    @Test
    public void getDetails_persistedUserWithoutDetails_shouldThrowNoCreatedDetailsException() {

        //Persist details before
        User user = new User();
        user = this.user_repository.save(user);

        User finalUser = user;
        Assertions.assertThrows(NoCreatedDetailsException.class, () -> this.details_manager.getDetails(finalUser));

    }

}
