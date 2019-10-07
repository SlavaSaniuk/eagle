package by.bsac.webmvc.controllers;

import by.bsac.exceptions.AccountNotRegisteredException;
import by.bsac.exceptions.EmailAlreadyRegisteredException;
import by.bsac.models.Account;
import by.bsac.models.User;
import by.bsac.services.accounts.AccountManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("AuthenticationController")
public class AuthenticationController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    //Spring beans
    private AccountManagementService ams; //Autowired via setter

    @PostMapping(value = "/register", produces = {"application/json"})
    public @ResponseBody User register(@RequestBody Account account) {

        try{
            return this.ams.register(account);
        }catch (EmailAlreadyRegisteredException exc) {
            LOGGER.debug(exc.getMessage());
            return null;
        }
    }

    @PostMapping(value = "/login", headers = {"content-type=application/json"}, produces = {"application/json"})
    public User login(@RequestBody Account account) {
        try{
            return  this.ams.login(account);
        }catch (AccountNotRegisteredException exc) {
            LOGGER.debug(exc.getMessage());
            return null;
        }
    }

    @Autowired
    public void setAccountManagementService(AccountManagementService a_ams) {
        LOGGER.debug("[AUTOWIRE] " +a_ams.getClass().getSimpleName() +" to " +getClass().getSimpleName() +" controller bean.");
        this.ams = a_ams;
    }
}
