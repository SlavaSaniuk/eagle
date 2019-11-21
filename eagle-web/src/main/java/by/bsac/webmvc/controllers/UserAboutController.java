package by.bsac.webmvc.controllers;

import by.bsac.conf.LoggerDefaultLogs;
import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import by.bsac.webmvc.forms.UserDetailsForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserAboutController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAboutController.class);
    //Spring beans
    private EmbeddedDeConverter<UserDetailsForm> details_converter;

    public UserAboutController() {
        LOGGER.debug(LoggerDefaultLogs.CREATION.beanCreationStart(UserAboutController.class));

        LOGGER.debug(LoggerDefaultLogs.CREATION.beanCreationFinish(UserAboutController.class));
    }


    @ModelAttribute("details_form")
    public UserDetailsForm getDetailsForm() {
        return new UserDetailsForm();
    }

    @GetMapping("/about-user")
    public ModelAndView getUserAboutPage() {
        //Model and view
        final ModelAndView mav = new ModelAndView();

        mav.setViewName("user_about");
        return mav;
    }

    @PostMapping("/about-user-create")
    public ModelAndView registerUserDetails(@ModelAttribute("details_form") UserDetailsForm details_dto) {
        //Model and view
        final ModelAndView mav = new ModelAndView();
        LOGGER.info("DETAILS-FORM: " +details_dto.toString());

        UserDetails details = this.details_converter.toEntity(details_dto, new UserDetails(), new UserName());
        LOGGER.info("DETAILS-ENTITY: " +details.toString());


        mav.setViewName("user_page");
        return mav;
    }

    @Autowired
    public void setDetailsConverter(EmbeddedDeConverter<UserDetailsForm> details_converter) {
        LOGGER.debug(LoggerDefaultLogs.AUTOWIRING.viaSetter(details_converter.getClass(), this.getClass()));
        this.details_converter = details_converter;
    }
}
