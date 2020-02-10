package by.bsac.services.storage;

import by.bsac.Main;
import by.bsac.Tests;
import by.bsac.configuration.RootConfiguration;
import by.bsac.domain.ImageExtension;
import by.bsac.domain.dto.ContextWithImageDto;
import by.bsac.domain.models.Image;
import by.bsac.domain.models.ImageFile;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.repositories.UserCrudRepository;
import by.bsac.services.images.context.UserImagesContextCrudService;
import by.bsac.services.images.storage.StorageService;
import by.bsac.streams.InputStreamUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import testconfiguration.TestsAspectsConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ActiveProfiles({"DATASOURCE_TESTS", "ASPECT_DEBUG"})
@SpringBootTest(classes = {RootConfiguration.class, TestsAspectsConfiguration.class})
public class SystemStorageIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemStorageIntegrationTest.class);

    //Tests properties
    private final Tests tests = new Tests();

    //Spring beans
    @Autowired @Qualifier("StorageService")
    private StorageService TEST;

    @Autowired @Qualifier("UserCrudRepository")
    private UserCrudRepository user_repository;

    @Autowired
    private UserImagesContextCrudService context_service;

    @Test
    @Transactional
    void saveImage_newImage_shouldReturnCreatedImageFileEntity() throws IOException {

        UserImagesContext context = this.tests.createUserContext(this.user_repository, this.context_service);
        LOGGER.debug("Created context: " +context);

        InputStream in = Main.class.getClassLoader().getResourceAsStream("files/mountains.jpg");
        byte[] image_data = InputStreamUtilities.toByteArray(in);
        Image image = new Image();
        image.setImageExtension(ImageExtension.JPG);
        image.setImageData(image_data);

        ImageFile CREATED = this.TEST.saveImage(context, new ImageFile(), image);

        Assertions.assertNotNull(CREATED);
        Assertions.assertNotNull(CREATED.getImageId());

        LOGGER.debug("Created image" +CREATED);
    }

    @Test
    void loadImage_savedImage_shouldReturnCImageFileEntity() throws IOException {

        ImageFile image_file = new ImageFile();
        image_file.setImageId(15L);

        ImageFile LOADED = this.TEST.loadImage(image_file);

        Assertions.assertNotNull(LOADED);
        Assertions.assertEquals(15L, LOADED.getImageId());
        LOGGER.debug("Loaded image file: " +LOADED);

        Assertions.assertNotNull(LOADED.getImage());
        LOGGER.debug("Loaded image: " +LOADED.getImage());

        Assertions.assertNotNull(LOADED.getImage().getImageData());
        LOGGER.debug("Loaded image data: " + Arrays.toString(LOADED.getImage().getImageData()));
    }


}
