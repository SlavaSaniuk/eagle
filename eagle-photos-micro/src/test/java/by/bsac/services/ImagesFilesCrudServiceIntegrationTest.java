package by.bsac.services;

import by.bsac.configuration.DatasourcesConfiguration;
import by.bsac.domain.models.ImageFile;
import by.bsac.domain.models.User;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.repositories.UserCrudRepository;
import by.bsac.services.images.ImagesFilesCrudService;
import by.bsac.services.images.UserImagesContextCrudService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import testconfiguration.TestsAspectsConfiguration;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ActiveProfiles({"DATASOURCE_TESTS", "ASPECT_DEBUG"})
@SpringBootTest(classes = {DatasourcesConfiguration.class, ServicesConfiguration.class, TestsAspectsConfiguration.class})
public class ImagesFilesCrudServiceIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesFilesCrudServiceIntegrationTest.class);
    //Spring beans
    @Autowired
    private ImagesFilesCrudService service;
    @Autowired
    private UserImagesContextCrudService context_service;
    @Autowired
    private UserCrudRepository user_repository;

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    @Transactional
    void create_newEntity_shouldCreateNewEntity() {

        User user = this.user_repository.findById(1).get();

        UserImagesContext ctx = new UserImagesContext();
        ctx.setImagesOwner(user);

        ctx = this.context_service.create(ctx);


        ImageFile file = new ImageFile();
        file.setImagePath("image-path");
        file.setImagesContext(ctx);

        ImageFile CREATED = this.service.create(file);

        Assertions.assertNotNull(CREATED);
        Assertions.assertNotNull(CREATED.getImageId());

        LOGGER.debug("Created entity: " +CREATED);


    }
}
