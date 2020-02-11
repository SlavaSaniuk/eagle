package by.bsac.webmvc.controllers;

import by.bsac.domain.ImageExtension;
import by.bsac.domain.models.ImageFile;
import by.bsac.services.images.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static by.bsac.core.logging.SpringCommonLogging.*;

@RestController("ImagesController")
public class ImagesController implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesController.class);
    //Spring dependencies
    private StorageService storage_service; //From ServicesConfiguration

    //Constructor
    public ImagesController() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(ImagesController.class)));
    }

    @GetMapping(value = "/images")
    public ResponseEntity<byte[]> getImageByIdInRequestParameters(@RequestParam("image_id") Long image_id) throws IOException {

        //Response headers
        HttpHeaders resp_headers = new HttpHeaders();

        ImageFile image_file = this.storage_service.loadImage(image_id);

        assert image_file.getImage() != null;

        //Set properly response content-type
        if (image_file.getImage().getImageExtension() == ImageExtension.JPG)
            resp_headers.setContentType(MediaType.IMAGE_JPEG);
        if (image_file.getImage().getImageExtension() == ImageExtension.PNG)
            resp_headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(image_file.getImage().getImageData(), resp_headers, HttpStatus.OK);
    }

    //Dependency management
    @Autowired @Qualifier("StorageService")
    public void setStorageService(StorageService a_service) {
        LOGGER.debug(DependencyManagement.autowireViaSetter(BeanDefinition.of(StorageService.class), ImagesController.class));
        this.storage_service = a_service;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        //Check dependencies
        if (this.storage_service == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(StorageService.class)));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(ImagesController.class)));
    }
}
