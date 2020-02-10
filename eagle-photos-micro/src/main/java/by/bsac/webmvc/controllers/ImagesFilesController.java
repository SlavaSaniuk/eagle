package by.bsac.webmvc.controllers;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import by.bsac.annotations.logging.BeforeLog;
import by.bsac.core.beans.DtoEntityConverter;
import by.bsac.domain.dto.ContextWithImageDto;
import by.bsac.domain.models.Image;
import by.bsac.domain.models.ImageFile;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.services.images.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static by.bsac.core.logging.SpringCommonLogging.*;

@RestController("ImagesFilesController")
public class ImagesFilesController implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesFilesController.class);
    //Spring beans
    private StorageService storage_service; //From ServicesConfiguration
    private DtoEntityConverter<ContextWithImageDto> CWI_converter; //From DtoConversionConfiguration

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
    @MethodCall(withStartTime = true)
    @MethodExecutionTime(inMicros = true, inMillis = true)
    @BeforeLog(value = "Upload ImageFile entity from DTO[%s]", argsClasses = {ContextWithImageDto.class})
    @PostMapping(value = "/image_file_upload", headers = {"content-type=application/json"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ContextWithImageDto uploadImage(@RequestBody ContextWithImageDto a_dto) throws IOException {

        //Get entities from DTO
        UserImagesContext context = this.CWI_converter.toEntity(a_dto, new UserImagesContext());
        Image image = this.CWI_converter.toEntity(a_dto, new Image());

        //Save image to storage
        ImageFile SAVED = this.storage_service.saveImage(context, new ImageFile(), image);

        //Convert to DTO
        ContextWithImageDto result = this.CWI_converter.toDto(SAVED.getImagesContext(), new ContextWithImageDto());
        result = this.CWI_converter.toDto(SAVED, result);

        result.setImageName(SAVED.getImage().getImageName());
        result.setImageExtension(SAVED.getImage().getImageExtension());

        return result;

    }


    //Dependency management
    @Autowired @Qualifier("StorageService")
    public void setStorageService(StorageService a_service) {
        LOGGER.debug(DependencyManagement.autowireViaSetter(BeanDefinition.of(StorageService.class), ImagesFilesController.class));
        this.storage_service = a_service;
    }

    @Autowired @Qualifier("ContextWithImageDtoConverter")
    public void setContextWithImageDtoConverter(DtoEntityConverter<ContextWithImageDto> a_converter) {
        LOGGER.debug(DependencyManagement.autowireViaSetter(
                BeanDefinition.of(DtoEntityConverter.class).withName("ContextWithImageDtoConverter").forGenericType(ContextWithImageDto.class), ImagesFilesController.class));
        this.CWI_converter = a_converter;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //Check dependencies
        if (this.storage_service == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(StorageService.class)));

        if (this.CWI_converter == null)
            throw new Exception((new BeanCreationException("Spring bean dependency of [ContextWithImageDtoConverter] bean is null;")));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(ImagesFilesController.class)));
    }
}
