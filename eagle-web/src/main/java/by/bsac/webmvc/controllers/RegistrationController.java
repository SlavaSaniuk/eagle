package by.bsac.webmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @GetMapping
    public ModelAndView getRegistrationPage() {
        ModelAndView mav = new ModelAndView();

        //View name
        mav.setViewName("registration_page");
        return mav;
    }

}
