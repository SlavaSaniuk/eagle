package by.bsac.services;

import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.repositories.DetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserDetailsManagerTestCase {

    @Mock
    private DetailsRepository details_repository;

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

        BDDMockito.given(details_repository.save(details)).willReturn(details_returned);

        UserDetails test = this.details_manager.createDetails(owner, details);
        Assertions.assertNotNull(test);
        Assertions.assertEquals(owner.getUserId(), test.getDetailId());
    }
}
