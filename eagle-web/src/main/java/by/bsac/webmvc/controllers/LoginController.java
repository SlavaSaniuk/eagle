package by.bsac.webmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public ModelAndView getLoginPage() {
        //Create mav
        ModelAndView mav = new ModelAndView();


        //View name
        mav.setViewName("login_page");
        return mav;
    }

}
