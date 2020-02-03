package by.bsac.webmvc.controllers;

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


    //Dependency management
    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(ImagesFilesController.class)));
    }
}
