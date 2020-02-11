package by.bsac.services.images.storage;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import by.bsac.annotations.logging.BeforeLog;
import by.bsac.annotations.validation.ParameterValidation;
import by.bsac.aspects.validators.ContextIdParameterValidator;
import by.bsac.aspects.validators.ImageFileIdParameterValidator;
import by.bsac.aspects.validators.LongIdParameterValidator;
import by.bsac.configuration.properties.SystemStorageProperties;
import by.bsac.domain.ImageExtension;
import by.bsac.domain.models.Image;
import by.bsac.domain.models.ImageFile;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.files.FileNameUtilities;
import by.bsac.services.images.ImagesFilesCrudService;
import by.bsac.services.images.context.UserImagesContextCrudService;
import by.bsac.streams.InputStreamUtilities;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static by.bsac.core.logging.SpringCommonLogging.*;

public class SystemStorageServiceImpl implements StorageService, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemStorageServiceImpl.class);
    //Spring dependencies
    //Set via setter
    private UserImagesContextCrudService context_crud_service; // From ServicesConfiguration
    private ImagesFilesCrudService files_crud_service; //From ServicesConfiguration
    private SystemStorageProperties storage_properties; //From RootConfiguration
    //Bean properties
    @Setter
    private String images_storage_path; //For tests purposes
    private String USED_STORAGE_PATH; //Used system storage path

    //Constructor
    public SystemStorageServiceImpl() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(SystemStorageServiceImpl.class)));
    }

    @Override
    @MethodCall(withArgs = true, withStartTime = true, withReturnType = true)
    @MethodExecutionTime(inMicros = true, inMillis = true)
    @BeforeLog(value = "Save image file[%s] with UserImagesContext[%s] to filesystem;",
            argsClasses = {Image.class, ImageFile.class, UserImagesContext.class})
    @Transactional
    @ParameterValidation(value = ContextIdParameterValidator.class, parametersClasses = UserImagesContext.class, errorMessage = "Integer ID of given user_image_context is in invalid value.")
    public ImageFile saveImage(UserImagesContext a_context, ImageFile a_image_file, Image a_image) throws IOException {

        //Get user images context service by ID
        UserImagesContext images_context = this.context_crud_service.get(a_context.getContextId());

        //Create new image file entity
        a_image_file.setImagesContext(images_context);
        images_context.addImageFile(a_image_file);

        a_image_file.setImagePath(this.USED_STORAGE_PATH);

        //Save image file entity to db
        ImageFile created = this.files_crud_service.create(a_image_file);

        //Image name: image_{image_id}id.{image_extension}
        final String image_name = "image_" +created.getImageId();
        a_image.setImageName(image_name);

        //Try to write image to fs
        Path image_path = this.writeImage(a_image.getFullName() , a_image.getImageData());

        //Update image path
        this.files_crud_service.updateImagePath(created, image_path.toString());
        created.setImagePath(image_path.toString());
        created.setImage(a_image);

        return created;
    }

    @Override
    @MethodCall(withArgs = true, withStartTime = true, withReturnType = true)
    @MethodExecutionTime(inMicros = true, inMillis = true)
    @BeforeLog(value = "Load image file[%s] with Image property from filesystem;",
            argsClasses = ImageFile.class)
    @Transactional
    @ParameterValidation(value = ImageFileIdParameterValidator.class,
            parametersClasses = ImageFile.class, errorMessage = "Long ID of given image file is in invalid value.")
    public ImageFile loadImage(ImageFile a_image_file) throws IOException {
        return this.loadImage(a_image_file.getImageId());
    }

    @Override
    @MethodCall(withArgs = true, withStartTime = true, withReturnType = true)
    @MethodExecutionTime(inMicros = true, inMillis = true)
    @BeforeLog(value = "Load image file with Image property from filesystem by ID[%s];",
            argsClasses = Long.class)
    @Transactional
    @ParameterValidation(value = LongIdParameterValidator.class,
            parametersClasses = Long.class, errorMessage = "Long ID of given image file is in invalid value.")
    public ImageFile loadImage(Long a_image_id) throws IOException {

        //Load image by ID
        ImageFile image_file = this.files_crud_service.get(a_image_id);

        //Read image file from path property
        Image image = this.loadImage(image_file.getImagePath());

        image_file.setImage(image);

        return image_file;
    }


    public Path writeImage(String a_image_name, byte[] a_image_data) throws IOException {

        Path image_path = Paths.get(this.USED_STORAGE_PATH, a_image_name);
        return Files.write(image_path, a_image_data);

    }

    /**
     * Method load {@link Image} object from file system.
     * @param path - file path.
     * @return - {@link Image} file.
     * @throws IOException - Throws if read error occurs.
     */
    public Image loadImage(String path) throws IOException {

        Image image = new Image();

        File file = new File(path);

        //Set property to image file
        image.setImageName(FileNameUtilities.getFileNameWithoutExtension(file.getName()));
        image.setImageExtension(ImageExtension.valueOf(FileNameUtilities.getFileExtension(file.getName()).toUpperCase()));

        //Set image data
        FileInputStream in = new FileInputStream(file);
        image.setImageData(InputStreamUtilities.toByteArray(in));

        return image;
    }

    public String getImagesStoragePath() {
        return this.USED_STORAGE_PATH;
    }

    public void initializePath() {
        //Set used storage path
        if (this.storage_properties.getImages() != null &&
                this.storage_properties.getImages().getStoragePath() != null &&
                !this.storage_properties.getImages().getStoragePath().isEmpty())
            this.USED_STORAGE_PATH = this.storage_properties.getImages().getStoragePath();
        else this.USED_STORAGE_PATH = this.images_storage_path;
    }

    //Dependency management
    public void setUserImagesContextCrudService(UserImagesContextCrudService a_service) {
        LOGGER.debug(DependencyManagement.setViaSetter(BeanDefinition.of(UserImagesContextCrudService.class), SystemStorageServiceImpl.class));
        this.context_crud_service = a_service;
    }

    public void setImagesFilesCrudService(ImagesFilesCrudService a_service) {
        LOGGER.debug(DependencyManagement.setViaSetter(BeanDefinition.of(ImagesFilesCrudService.class), SystemStorageServiceImpl.class));
        this.files_crud_service = a_service;
    }

    public void setSystemStorageProperties(SystemStorageProperties a_props) {
        LOGGER.debug(DependencyManagement.setViaSetter(BeanDefinition.of(SystemStorageProperties.class), SystemStorageServiceImpl.class));
        this.storage_properties = a_props;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        //Check dependencies
        if (this.context_crud_service == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(UserImagesContextCrudService.class)));

        if (this.files_crud_service == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(ImagesFilesCrudService.class)));

        this.initializePath();

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(SystemStorageServiceImpl.class)));

    }
}
