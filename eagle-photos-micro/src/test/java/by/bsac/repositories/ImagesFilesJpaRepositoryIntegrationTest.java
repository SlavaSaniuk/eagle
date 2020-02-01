package by.bsac.repositories;

import by.bsac.configuration.RootConfiguration;
import by.bsac.domain.models.Image;
import by.bsac.domain.models.ImageFile;
import by.bsac.domain.models.User;
import by.bsac.domain.models.UserImagesContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("DATASOURCE_TESTS")
@SpringBootTest(classes = RootConfiguration.class)
public class ImagesFilesJpaRepositoryIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesFilesJpaRepositoryIntegrationTest.class);
    //Spring beans
    @Autowired @Qualifier("ImagesFilesRepository")
    private ImagesFilesJpaRepository repository;

    @Autowired @Qualifier("UserImagesContextRepository")
    private UserImagesContextCrudRepository context_repository;

    @Autowired @Qualifier("UserCrudRepository")
    private UserCrudRepository user_repository;

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    @Transactional
    void save_newImageEntity_shouldReturnImageId() {

        User created_user = this.user_repository.save(new User());

        UserImagesContext ctx = new UserImagesContext();
        ctx.setImagesOwner(created_user);
        created_user.setImagesContext(ctx);

        UserImagesContext created_ctx = this.context_repository.save(ctx);


        ImageFile file = new ImageFile();
        file.setImagePath("IMAGE_PATH");

        file.setImagesContext(created_ctx);
        created_ctx.addImageFile(file);

        ImageFile CREATED = this.repository.save(file);

        Assertions.assertNotNull(CREATED);
        Assertions.assertEquals(ctx.getContextId(), CREATED.getImagesContext().getContextId());

        LOGGER.debug("CREATED image file: " +CREATED);

    }

    @Test
    @Transactional
    void findByImagePath_entityWithImagePath_shouldReturnEntity() {

        this.save_newImageEntity_shouldReturnImageId();

        final String IMAGE_PATH = "IMAGE_PATH";
        ImageFile FOUNDED = this.repository.findByImagePath(IMAGE_PATH);

        Assertions.assertNotNull(FOUNDED);
        Assertions.assertEquals(IMAGE_PATH, FOUNDED.getImagePath());

        LOGGER.debug("Founded entity: " +FOUNDED);
    }

    @Test
    @Transactional
    void updateImagePath_createdEntity_shouldUpdateImagePathColumn() {

        this.save_newImageEntity_shouldReturnImageId();

        final String IMAGE_PATH = "IMAGE_PATH";
        ImageFile FOUNDED = this.repository.findByImagePath(IMAGE_PATH);

        final String UPDATED_PATH = "UPDATED_PATH";

        this.repository.updateImagePath(FOUNDED.getImageId(), UPDATED_PATH);

        ImageFile updated = this.repository.getOne(FOUNDED.getImageId());

        Assertions.assertNotNull(updated);
        Assertions.assertEquals(UPDATED_PATH, updated.getImagePath());

        LOGGER.debug("Updated path: " +updated.getImagePath());

    }


}
