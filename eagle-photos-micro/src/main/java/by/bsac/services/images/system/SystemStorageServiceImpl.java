package by.bsac.services.images.system;

import by.bsac.domain.models.Image;
import by.bsac.domain.models.ImageFile;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.services.images.ImagesFilesCrudService;
import by.bsac.services.images.UserImagesContextCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Component("SystemStorageServiceImpl")
public class SystemStorageServiceImpl implements SystemStorageService, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemStorageServiceImpl.class);
    //Spring dependencies
    //Set via setter
    private UserImagesContextCrudService context_crud_service; // From ServicesConfiguration
    private ImagesFilesCrudService files_crud_service; //From ServicesConfiguration

    public SystemStorageServiceImpl() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(SystemStorageServiceImpl.class)));
    }


    @Override
    @Transactional
    public ImageFile saveImage(UserImagesContext a_context, ImageFile a_image_file, Image a_image) {

        //Get user images context service by ID
        UserImagesContext images_context = this.context_crud_service.get(a_context.getContextId());

        //Create new image file entity
        a_image_file.setImagesContext(images_context);
        images_context.addImageFile(a_image_file);

        // a_image_file.setImagePath("");

        return null;
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

    @Override
    public void afterPropertiesSet() throws Exception {

        //Check dependencies
        if (this.context_crud_service == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(UserImagesContextCrudService.class)));

        if (this.files_crud_service == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(ImagesFilesCrudService.class)));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(SystemStorageServiceImpl.class)));

    }
}
