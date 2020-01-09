package by.bsac.webmvc.controllers;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import by.bsac.annotations.validation.ParameterValidation;
import by.bsac.aspects.validators.UserWithDetailsDtoParameterValidator;
import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.core.logging.SpringCommonLogging;
import by.bsac.exceptions.NoCreatedDetailsException;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import by.bsac.services.DetailsManager;
import by.bsac.services.feign.clients.AccountsStatusesManager;
import by.bsac.webmvc.dto.AccountWithStatusDto;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static by.bsac.configuration.LoggerDefaultLogs.*;
import static by.bsac.core.logging.SpringCommonLogging.*;

/**
 * Main controller od eagle-users-micro microservice.
 * Controller has a GRUD methods for controlling {@link UserDetails} entities.
 */
@SuppressWarnings("AccessStaticViaInstance")
@RestController
public class DetailsController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DetailsController.class);
    //Spring beans
    private DetailsManager details_manager;
    private EmbeddedDeConverter<UserWithDetailsDto> converter;
    private AccountsStatusesManager asm; //Autowired via setter from FeignConfiguration.class
    //HTTP exception status codes
    private static final int NO_CREATED_DETAILS_EXCEPTION_STATUS_CODE = 441;

    /**
     * Spring automatically construct new {@link DetailsController} controller bean.
     */
    //Default constructor
    public DetailsController() {
        LOGGER.info(SpringCommonLogging.CREATION.startCreateBean(BeanDefinition.of("DetailsController").ofClass(this.getClass())));
    }

    /**
     * Create new {@link UserDetails} entity and persist it in database.
     * Associate it with {@link User} entity.
     * @param dto - {@link UserWithDetailsDto} DTO object contains info about user and it's own details.
     * @return - created {@link UserWithDetailsDto} DTO object.
     */
    @PostMapping(path = "/details_create", headers = {"content-type=application/json"}, produces = {"application/json"})
    @ResponseBody
    @ParameterValidation(value = UserWithDetailsDtoParameterValidator.class,
            parametersClasses = UserWithDetailsDto.class, errorMessage = "Parameter [user_id] is in invalid value.")
    @MethodCall(withArgs = true, withReturnType = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true, inMillis = true)
    public UserWithDetailsDto createDetails(@RequestBody UserWithDetailsDto dto) {

        //Get entities from DTO
        User user = this.converter.toEntity(dto, new User());
        UserDetails details = this.converter.toEntity(dto, new UserDetails(), new UserName());

        //Try to create user details
        details = this.details_manager.createDetails(user, details);

        //Confirm account status
        AccountWithStatusDto status_dto = new AccountWithStatusDto();
        status_dto.setAccountId(details.getDetailId());
        LOGGER.debug("Account to confirm: " +status_dto.toString());
        status_dto = this.asm.confirmAccount(status_dto);
        LOGGER.debug("Confirmed account: " +status_dto.toString());

        //Create response dto
        UserWithDetailsDto response = this.converter.toDto(user, new UserWithDetailsDto());
        this.converter.toDto(details, response);

        return response;
    }

    /**
     * Controller retrieve {@link UserDetails} entity object
     * by {@link User} and convert it to DTO object.
     * @param dto - {@link UserWithDetailsDto} DTO object contains info about user and it's own details.
     * @return - Retriever {@link UserWithDetailsDto} dto object.
     * @throws NoCreatedDetailsException - Exception throws if user already registered
     * in database but hasn't it own {@link UserDetails}.
     */
    @PostMapping(path = "/details_get", headers = {"content-type=application/json"}, produces = {"application/json"})
    @ResponseBody
    @ParameterValidation(value = UserWithDetailsDtoParameterValidator.class,
            parametersClasses = UserWithDetailsDto.class, errorMessage = "Parameter [user_id] is in invalid value.")
    @MethodCall(withArgs = true, withReturnType = true, withStartTime = true)
    @MethodExecutionTime(inMicros = true, inMillis = true)
    public UserWithDetailsDto getDetails(@RequestBody @NonNull UserWithDetailsDto dto) throws NoCreatedDetailsException {

        User user = this.converter.toEntity(dto, new User());

        //Try to get details
        UserDetails details = this.details_manager.getDetails(user);

        return this.converter.toDto(details, dto);
    }

    /**
     * Controller method handle {@link NoCreatedDetailsException} exception and return response
     * entity with error HTTP status 441.
     * @param exc - {@link NoCreatedDetailsException} exception.
     * @return - {@link ResponseEntity} with 441 error HTTP status code.
     */
    @ExceptionHandler(NoCreatedDetailsException.class)
    public ResponseEntity<NoCreatedDetailsException> handleNoCreatedDetailsException(NoCreatedDetailsException exc) {
        return ResponseEntity.status(NO_CREATED_DETAILS_EXCEPTION_STATUS_CODE).contentType(MediaType.APPLICATION_JSON).body(exc);
    }

    //AUTOWIRING
    @Autowired
    public void setDetailsManager(DetailsManager details_manager) {
        LOGGER.info(AUTOWIRING.viaSetter(details_manager.getClass(), this.getClass()));
        this.details_manager = details_manager;
    }

    @Autowired
    public void setDeConverter(EmbeddedDeConverter<UserWithDetailsDto> a_converter) {
        LOGGER.debug(AUTOWIRING.viaSetter(a_converter.getClass(), this.getClass()));
        this.converter = a_converter;
    }

    @Autowired
    public void setAccountsStatusesManager(AccountsStatusesManager a_asm) {
        LOGGER.debug(DependencyManagement.autowireViaSetter(BeanDefinition.of("AccountStatusesManager").ofClass(AccountsStatusesManager.class), this.getClass()));
        this.asm = a_asm;
    }
}
