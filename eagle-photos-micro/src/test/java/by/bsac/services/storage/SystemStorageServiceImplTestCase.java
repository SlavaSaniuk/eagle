package by.bsac.services.storage;

import by.bsac.Main;
import by.bsac.configuration.properties.SystemStorageProperties;
import by.bsac.domain.ImageExtension;
import by.bsac.domain.dto.ContextWithImageDto;
import by.bsac.domain.models.Image;
import by.bsac.domain.models.ImageFile;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.services.images.ImagesFilesCrudService;
import by.bsac.services.images.context.UserImagesContextCrudService;
import by.bsac.services.images.storage.SystemStorageServiceImpl;
import by.bsac.streams.InputStreamUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;

public class SystemStorageServiceImplTestCase {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemStorageServiceImplTestCase.class);

    @Mock
    private SystemStorageProperties properties;

    @Mock
    private UserImagesContextCrudService context_crud_service;

    @Mock
    private ImagesFilesCrudService files_crud_service; //From ServicesConfiguration

    @InjectMocks
    private SystemStorageServiceImpl sss;

    @BeforeEach
    void setUpBeforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getImagesStoragePath_systemStoragePropertiesImagesPathIsNull_shouldUsePathSpecifiedInProperties() {

        final String PATH = "/home/user/Pictures";
        this.sss.setImagesStoragePath(PATH);

        this.sss.initializePath();

        Assertions.assertNotNull(this.sss.getImagesStoragePath());
        Assertions.assertEquals(PATH, this.sss.getImagesStoragePath());

        LOGGER.debug("Path: " +this.sss.getImagesStoragePath());

    }

    @Test
    void writeImage_newImage_shouldReturnPath() throws IOException {

        //Get resource
        InputStream in = Main.class.getClassLoader().getResourceAsStream("files/mountains.jpg");
        byte[] image_data = InputStreamUtilities.toByteArray(in);

        final String image_name = "test.jpg";

        final String PATH = "/home/slava/Pictures";
        this.sss.setImagesStoragePath(PATH);

        this.sss.initializePath();

        Path created = this.sss.writeImage(image_name, image_data);

        Assertions.assertNotNull(created);
        LOGGER.debug("Created path: " +created);

    }

    @Test
    void saveImage_newImage_shouldReturnImageFileEntity() throws IOException {

        //Get resource
        InputStream in = Main.class.getClassLoader().getResourceAsStream("files/mountains.jpg");
        byte[] image_data = InputStreamUtilities.toByteArray(in);

        Image image = new Image();
        image.setImageExtension(ImageExtension.JPG);
        image.setImageData(image_data);

        final String PATH = "/home/slava/Pictures";
        this.sss.setImagesStoragePath(PATH);
        this.sss.initializePath();

        UserImagesContext ctx = new UserImagesContext();
        UserImagesContext founded_ctx = new UserImagesContext();

        final Integer CTX_ID = 2;
        ctx.setContextId(CTX_ID);
        founded_ctx.setContextId(CTX_ID);

        BDDMockito.given(this.context_crud_service.get(ctx.getContextId())).willReturn(founded_ctx);

        ImageFile file = new ImageFile();
        ImageFile image_file = new ImageFile();
        image_file.setImagesContext(founded_ctx);
        image_file.setImageId(23L);

        BDDMockito.given(this.files_crud_service.create(file)).willReturn(image_file);

        ImageFile TEST = this.sss.saveImage(ctx, file, image);
        Assertions.assertNotNull(TEST);

        LOGGER.debug("Saved image: " +image);
        LOGGER.debug("Saved image_file: " +TEST);

    }

    @Test
    void loadImage_imageFromFS_shouldReturnImageObject() throws IOException {


        Image image = this.sss.loadImage("/home/slava/Pictures/image_14.jpg");

        Assertions.assertNotNull(image);
        Assertions.assertEquals("image_14", image.getImageName());
        Assertions.assertEquals(ImageExtension.JPG, image.getImageExtension());
        Assertions.assertNotNull(image.getImageData());

        LOGGER.debug("Loaded image: " +image);
        LOGGER.debug("Loaded image data: " + Arrays.toString(image.getImageData()));

    }

}
