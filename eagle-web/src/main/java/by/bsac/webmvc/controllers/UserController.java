package by.bsac.webmvc.controllers;

import by.bsac.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import static by.bsac.conf.LoggerDefaultLogs.*;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public UserController() {
        LOGGER.debug(CREATION.beanCreationStart(this.getClass()));
        LOGGER.debug(CREATION.beanCreationFinish(this.getClass()));
    }


    @GetMapping(path = "/user_{given_id}")
    public ModelAndView getGivenUserPage(@SessionAttribute("common_user") User common_user, @PathVariable Integer given_id) {
        final ModelAndView mav = new ModelAndView();

        //Check whether user access your own page
        if (common_user.getUserId().equals(given_id)) {
            mav.setViewName("forward:/common_user_page");
            return mav;
        }




        mav.setViewName("given_user_page");
        return mav;
    }

    @GetMapping(path = "/common_user_page")
    public ModelAndView getCommonUserPage() {
        final ModelAndView mav = new ModelAndView();

        mav.setViewName("common_user_page");
        return mav;
    }


















}
