package by.bsac.services.context;

import by.bsac.domain.models.User;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.repositories.UserCrudRepository;
import by.bsac.services.images.context.ImagesContextManager;
import by.bsac.services.images.context.UserImagesContextCrudService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ImagesContextManagerTestCase {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesContextManagerTestCase.class);

    //Mocks
    @Mock
    private UserImagesContextCrudService context_service;

    @Mock
    private UserCrudRepository user_repository;

    @InjectMocks
    private ImagesContextManager context_manager;

    @BeforeEach
    void setUpBeforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createUserImagesContext_newUser_shouldReturnCreatedUserImagesContext() {

        Integer ID = 24;
        User a_owner = new User();
        a_owner.setUserId(ID);

        UserImagesContext ctx = new UserImagesContext();

        User founded = new User();
        founded.setUserId(24);
        BDDMockito.given(this.user_repository.findById(a_owner.getUserId())).willReturn(Optional.of(founded));

        UserImagesContext created = new UserImagesContext();
        created.setContextId(ID);
        created.setImagesOwner(founded);
        BDDMockito.given(this.context_service.create(ctx)).willReturn(created);

        UserImagesContext TEST = this.context_manager.createUserImagesContext(a_owner, ctx);

        Assertions.assertNotNull(TEST);
        Assertions.assertNotNull(TEST.getContextId());
        Assertions.assertNotNull(TEST.getImagesOwner());
        Assertions.assertNotNull(TEST.getUserImages());

        LOGGER.debug("Created entity: " +TEST);

    }
}
