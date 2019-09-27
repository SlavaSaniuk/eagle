package by.bsac.webmvc.controllers;

import by.bsac.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    @ResponseBody
    public User login() {
        User usr = new User();
        usr.setUserId(2);
        return usr;
    }
}
