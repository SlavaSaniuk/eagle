package by.bsac.webmvc.controllers;

import by.bsac.webmvc.forms.UserDetailsForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserAboutController {


    @ModelAttribute("details-form")
    public UserDetailsForm getDetailsForm() {
        return new UserDetailsForm();
    }

    @GetMapping("/about-user")
    public ModelAndView getUserAboutPage() {
        //Model and view
        final ModelAndView mav = new ModelAndView();

        mav.setViewName("about-user");
        return mav;
    }


}
