package by.bsac.webmvc.controllers;

import by.bsac.core.DEConverter;
import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import by.bsac.services.DetailsManager;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static by.bsac.configuration.LoggerDefaultLogs.*;

@RestController
public class DetailsController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DetailsController.class);
    //Spring beans
    private DetailsManager details_manager;
    private EmbeddedDeConverter<UserWithDetailsDto> converter;

    //Default constructor
    public DetailsController() {
        LOGGER.info(CREATION.beanCreationStart(this.getClass()));
        LOGGER.info(CREATION.beanCreationFinish(this.getClass()));
    }

    @PostMapping(path = "/details_create", headers = {"content-type=application/json"}, produces = {"application/json"})
    @ResponseBody
    public UserWithDetailsDto createDetails(@RequestBody UserWithDetailsDto dto) {

        //Get entities from DTO
        User user = this.converter.toEntity(dto, new User());
        UserDetails details = this.converter.toEntity(dto, new UserDetails(), new UserName());

        //Try to create user details
        details = this.details_manager.createDetails(user, details);

        //Create response dto
        UserWithDetailsDto response = this.converter.toDto(user, new UserWithDetailsDto());
        this.converter.toDto(details, response);

        return response;
    }

    @PostMapping(path = "/details_get", headers = {"content-type=application/json"}, produces = {"application/json"})
    @ResponseBody
    public UserWithDetailsDto getDetails(@RequestBody @NonNull UserWithDetailsDto dto) {


        return null;
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
}
