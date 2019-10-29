package by.bsac.webmvc.controllers;

import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.services.DetailsManager;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static by.bsac.configuration.LoggerDefaultLogs.*;

@RestController
public class DetailsController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DetailsController.class);
    //Spring beans
    private DetailsManager details_manager;

    //Default constructor
    public DetailsController() {
        LOGGER.info(CREATION.beanCreationStart(this.getClass()));
        LOGGER.info(CREATION.beanCreationFinish(this.getClass()));
    }

    @PostMapping(path = "details_create", headers = {"content-type=application/json"}, produces = {"application/json"})
    public UserDetails createDetails(UserWithDetailsDto dto) {
        //Try to create user details
        //Get user and details entity
        User user = dto.getUserEntity();
        UserDetails details = dto.getDetailsEntity();

        details = this.details_manager.createDetails(user, details);

        return details;
    }

    //AUTOWIRING
    public void setDetailsManager(DetailsManager details_manager) {
        LOGGER.info(AUTOWIRING.viaSetter(details_manager.getClass(), this.getClass()));
        this.details_manager = details_manager;
    }
}
