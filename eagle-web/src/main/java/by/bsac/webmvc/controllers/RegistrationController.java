package by.bsac.webmvc.controllers;

import by.bsac.conf.LoggerDefaultLogs;
import by.bsac.exceptions.AccountAlreadyRegisteredException;
import by.bsac.feign.clients.AccountManagementService;
import by.bsac.models.Account;
import by.bsac.webmvc.forms.AccountForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    //Spring beans
    private AccountManagementService ams; //Autowired via setter

    //Model attributes
    @ModelAttribute("account-form")
    public AccountForm getAccountForm() {
        return new AccountForm();
    }

    @GetMapping
    public ModelAndView getRegistrationPage() {
        ModelAndView mav = new ModelAndView();

        //View name
        mav.setViewName("registration");
        return mav;
    }

    @PostMapping
    public ModelAndView registerNewAccount(@ModelAttribute("account-form") AccountForm form) {

        LOGGER.debug("Post registration request on '/register' with account email " +form.getAccountEmail());

        //Create mav
        ModelAndView mav = new ModelAndView();

        //Convert to account entity
        Account account = form.toAccountEntity();

        //Try to register new user account
        try {
            this.ams.registerAccount(account);
        }catch (AccountAlreadyRegisteredException exc) {
            LOGGER.debug(exc.getMessage());
            mav.setViewName("registration");
            return mav;
        }

        mav.setViewName("user_page");
        return mav;
    }

    @Autowired
    public void setAccountManagementService(AccountManagementService a_ams) {
        LOGGER.debug(LoggerDefaultLogs.AUTOWIRING.viaSetter(a_ams.getClass(), this.getClass()));
        this.ams = a_ams;
    }
}
