package by.bsac.services.images.storage;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import by.bsac.annotations.logging.BeforeLog;
import by.bsac.configuration.properties.SystemStorageProperties;
import by.bsac.domain.models.Image;
import by.bsac.domain.models.ImageFile;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.services.images.ImagesFilesCrudService;
import by.bsac.services.images.context.UserImagesContextCrudService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Component("SystemStorageServiceImpl")
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

    public SystemStorageServiceImpl() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(SystemStorageServiceImpl.class)));
    }


    @Override
    @MethodCall(withArgs = true, withStartTime = true, withReturnType = true)
    @MethodExecutionTime(inMicros = true, inMillis = true)
    @BeforeLog(value = "Save image file[%s] with UserImagesContext[%s] to filesystem;",
            argsClasses = {Image.class, ImageFile.class, UserImagesContext.class})
    @Transactional
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


    public Path writeImage(String a_image_name, byte[] a_image_data) throws IOException {

        Path image_path = Paths.get(this.USED_STORAGE_PATH, a_image_name);
        return Files.write(image_path, a_image_data);

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
