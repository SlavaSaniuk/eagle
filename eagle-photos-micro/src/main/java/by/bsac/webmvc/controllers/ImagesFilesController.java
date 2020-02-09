package by.bsac.webmvc.controllers;

import by.bsac.domain.dto.ContextWithImageDto;
import by.bsac.domain.models.UserImagesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import static by.bsac.core.logging.SpringCommonLogging.*;

public class ImagesFilesController implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesFilesController.class);
    //Spring beans

    //Constructor
    public ImagesFilesController() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(ImagesFilesController.class)));
    }

    /**
     *  Http POST controller method used to upload {@link by.bsac.domain.models.Image} user image file.
     * Method save {@link ContextWithImageDto#getImageData()} of user image in specified storage,
     * get name for new image, and save path to new image in database.
     * Required properties:
     *  - You need to specify {@link ContextWithImageDto#getContextId()} context_id parameter
     * in given DTO object to maps user image with {@link UserImagesContext}.
     *  - You need to specify {@link ContextWithImageDto#getImageData()} byte array parameter
     * in given DTO object to write this bytes in selected storage.
     *  - You need to specify {@link ContextWithImageDto#getImageExtension()} image_extension parameter
     * in given DTO object to known how to display uploaded image.
     * @param a_dto - {@link ContextWithImageDto} dto object with defined required properties.
     * @return - {@link ContextWithImageDto} with defined image_name property.
     */
    public ContextWithImageDto uploadImage(ContextWithImageDto a_dto) {

        return null;

    }


    //Dependency management
    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(ImagesFilesController.class)));
    }
}
