package by.bsac.webmvc.controllers;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import by.bsac.annotations.logging.BeforeLog;
import by.bsac.core.beans.DtoEntityConverter;
import by.bsac.domain.dto.UserWithContextDto;
import by.bsac.domain.models.User;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.services.images.context.ImagesContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static by.bsac.core.logging.SpringCommonLogging.*;

@SuppressWarnings("AccessStaticViaInstance")
@RestController("ImagesContextsController")
public class ImagesContextsController implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesContextsController.class);
    //Spring beans
    //Autowired via setter
    private ImagesContextService context_service; //From services configuration
    private DtoEntityConverter<UserWithContextDto> context_dto_converter; //From DtoConversion configuration

    //Constructor
    public ImagesContextsController() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(ImagesContextsController.class)));
    }

    /**
     * Http GET controller method user to create new {@link UserImagesContext} entity in database and application.
     * You need to specify {@link UserWithContextDto#getUserId()} user_id parameter in given DTO object.
     * @param a_dto - {@link UserWithContextDto} dto object with defined "user_id" property.
     * @return - {@link UserWithContextDto} with 'user_id' and 'context_id' properties.
     */
    @MethodCall(withStartTime = true)
    @MethodExecutionTime(inMicros = true, inMillis = true)
    @BeforeLog(value = "Create new UserImagesContext entity from DTO[%s]", argsClasses = {UserWithContextDto.class})
    @PostMapping(value = "/context_create", headers = {"content-type=application/json"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserWithContextDto createUserImagesContext(@RequestBody UserWithContextDto a_dto) {
        //Get entities from dto
        User user = this.context_dto_converter.toEntity(a_dto, new User());

        //Create UserImagesContext
        UserImagesContext context = this.context_service.createUserImagesContext(user, new UserImagesContext());

        //Convert to dto
        return this.context_dto_converter.toDto(context, a_dto);
    }

    @MethodCall(withStartTime = true)
    @MethodExecutionTime(inMicros = true, inMillis = true)
    @BeforeLog(value = "Get UserImagesContext entity from DTO[%s]", argsClasses = {UserWithContextDto.class})
    @PostMapping(value = "/context_get", headers = {"content-type=application/json"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserWithContextDto getUserImagesContext(@RequestBody UserWithContextDto a_dto) {

        //Get entities from dto
        UserImagesContext context = this.context_dto_converter.toEntity(a_dto, new UserImagesContext());

        //Get context by id
        context = this.context_service.getUserImagesContext(context);

        //Convert to entity
        UserWithContextDto result = this.context_dto_converter.toDto(context, a_dto);
        result = this.context_dto_converter.toDto(context.getImagesOwner(), result);

        return result;
    }

    @MethodCall(withStartTime = true)
    @MethodExecutionTime(inMicros = true, inMillis = true)
    @BeforeLog(value = "Get UserImagesContext entity by Id[%s]", argsClasses = {Integer.class})
    @GetMapping(value = "/context_getById", headers = {"content-type=application/json"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserWithContextDto getUserImagesContextById(@RequestParam("context_id") Integer context_id) {


        //Get context by id
        UserImagesContext context = this.context_service.getUserImagesContextById(context_id);

        //Convert to entity
        UserWithContextDto result = this.context_dto_converter.toDto(context, new UserWithContextDto());
        result = this.context_dto_converter.toDto(context.getImagesOwner(), result);

        return result;
    }

    //Dependency management
    @Autowired
    public void setImagesContextService(ImagesContextService a_service) {
        LOGGER.debug(DependencyManagement.autowireViaSetter(BeanDefinition.of(ImagesContextService.class), ImagesContextsController.class));
        this.context_service = a_service;
    }

    @Autowired @Qualifier("UserWithContextDtoConverter")
    public void setUserWithContextDtoConverter(DtoEntityConverter<UserWithContextDto> a_converter) {
        LOGGER.debug(DependencyManagement.autowireViaSetter(
                BeanDefinition.of(DtoEntityConverter.class).withName("UserWithContextDtoConverter").forGenericType(UserWithContextDto.class), ImagesContextsController.class));
        this.context_dto_converter = a_converter;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //Check dependencies
        if (this.context_service == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(ImagesContextService.class)));

        if (this.context_dto_converter == null)
            throw new Exception(new BeanCreationException(DependencyManagement.Exceptions.nullProperty(DtoEntityConverter.class)));

        LOGGER.debug(CREATION.endCreateBean(BeanDefinition.of(ImagesContextsController.class)));
    }
}
