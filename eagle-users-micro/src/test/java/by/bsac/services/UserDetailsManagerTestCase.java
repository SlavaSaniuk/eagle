package by.bsac.services;

import by.bsac.exceptions.NoCreatedDetailsException;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.repositories.DetailsRepository;
import by.bsac.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserDetailsManagerTestCase {

    @Mock
    private DetailsRepository details_repository;

    @Mock
    private UserRepository user_repository;

    @InjectMocks
    private UserDetailsManager details_manager = new UserDetailsManager();

    @BeforeEach
    void setUpBeforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createDetails_parameterIsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, ()-> details_manager.createDetails(null, null));
    }

    @Test
    void createDetails_nonPersistedUser_shouldThrowIAE() {
        User owner = new User();
        UserDetails details = new UserDetails();
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.details_manager.createDetails(owner, details));
    }

    @Test
    void createDetails_persistedUser_shouldReturnPersistedUserDetails() {
        User owner = new User();
        owner.setUserId(3);

        UserDetails details = new UserDetails();
        details.setDetailsUser(owner);

        UserDetails details_returned = new UserDetails();
        details_returned.setDetailsUser(owner);
        details_returned.setDetailId(owner.getUserId());


        BDDMockito.given(user_repository.findById(owner.getUserId())).willReturn(Optional.of(owner));
        BDDMockito.given(details_repository.save(details)).willReturn(details_returned);

        UserDetails test = this.details_manager.createDetails(owner, details);
        Assertions.assertNotNull(test);
        Assertions.assertEquals(owner.getUserId(), test.getDetailId());
    }

    @Test
    void getDetails_registerUser_shouldReturnUserDetails() throws NoCreatedDetailsException {
        User user = new User();
        UserDetails details = new UserDetails();

        user.setUserId(23);
        details.setDetailId(23);

        user.setUserDetails(details);

        BDDMockito.given(user_repository.findById(user.getUserId())).willReturn(Optional.of(user));

        UserDetails test = this.details_manager.getDetails(user);

        Assertions.assertNotNull(test);
        Assertions.assertEquals(details.getDetailId(), test.getDetailId());

    }

    @Test
    void getDetails_nullUser_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, () -> this.details_manager.getDetails(null));
    }

    @Test
    void getDetails_userWithNullId_shouldThrowIAE(){
        User user = new User();
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.details_manager.getDetails(user));
    }

    @Test
    void getDetails_userWithInvalidId_shouldThrowIAE(){
        User user = new User();
        user.setUserId(24);
        BDDMockito.given(user_repository.findById(user.getUserId())).willReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.details_manager.getDetails(user));
    }

    @Test
    void getDetails_noCreatedDetailsForUser_shouldThrowNoCreatedDetailsException(){
        User user = new User();
        user.setUserId(23);

        BDDMockito.given(user_repository.findById(user.getUserId())).willReturn(Optional.of(user));

        Assertions.assertThrows(NoCreatedDetailsException.class, () -> this.details_manager.getDetails(user));

    }


}
