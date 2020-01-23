package by.bsac.repositories;

import by.bsac.configuration.RootConfiguration;
import by.bsac.domain.models.ImageFile;
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
public class ImagesFilesJpaRepositoryIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesFilesJpaRepositoryIntegrationTest.class);
    //Spring beans
    @Autowired @Qualifier("ImagesFilesRepository")
    private ImagesFilesJpaRepository repository;

    @Autowired @Qualifier("UserImagesContextRepository")
    private UserImagesContextCrudRepository context_repository;

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    @Transactional
    @Commit
    void save_newImageEntity_shouldReturnImageId() {

        ImageFile file = new ImageFile();
        file.setImagePath("IMAGE_PATH");

        UserImagesContext ctx = this.context_repository.findById(2).get();

        file.setImagesContext(ctx);

        ImageFile CREATED = this.repository.save(file);

        Assertions.assertNotNull(CREATED);
        Assertions.assertEquals(ctx.getContextId(), CREATED.getImagesContext().getContextId());

        LOGGER.debug("CREATED image file: " +CREATED);

    }
}
