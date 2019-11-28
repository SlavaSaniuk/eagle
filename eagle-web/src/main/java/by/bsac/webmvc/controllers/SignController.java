package by.bsac.webmvc.controllers;

import by.bsac.conf.LoggerDefaultLogs;
import by.bsac.exceptions.AccountAlreadyRegisteredException;
import by.bsac.exceptions.AccountNotRegisteredException;
import by.bsac.feign.clients.AccountManagementService;
import by.bsac.models.Account;
import by.bsac.models.User;
import by.bsac.webmvc.forms.AccountForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({"common_user"})
public class SignController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    //Spring beans
    private AccountManagementService ams; //Autowired via setter

    //Model attributes
    @ModelAttribute("AccountForm")
    public AccountForm getAccountForm() {
        return new AccountForm();
    }

    @GetMapping("/sign")
    public ModelAndView getSignPage() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("sign");
        return mav;
    }

    @PostMapping("/signin")
    public ModelAndView loginAccount(@ModelAttribute("AccountForm") AccountForm account_form) {
        //Create mav
        ModelAndView mav = new ModelAndView();

        //Try to login account
        Account account = account_form.toAccountEntity();
        User account_user;
        try {
            account_user = this.ams.loginAccount(account);
        }catch (AccountNotRegisteredException exc) {
            LOGGER.debug(exc.getMessage());
            mav.setViewName("redirect://sign?form=signup");
            return mav;
        }
        mav.setViewName("redirect:/user_" +account_user.getUserId());
        return mav;
    }

    @PostMapping("/signup")
    public ModelAndView registerAccount(@ModelAttribute("AccountForm") AccountForm account_form) {
        //Create mav
        ModelAndView mav = new ModelAndView();

        //Try to register account
        Account account = account_form.toAccountEntity();
        User account_user;
        try {
            account_user = this.ams.registerAccount(account);
        }catch (AccountAlreadyRegisteredException exc) {
            LOGGER.debug(exc.getMessage());
            mav.setViewName("sign");
            return mav;
        }

        mav.getModel().put("common_user", account);
        mav.setViewName("redirect:/about-user");
        return mav;
    }

    @Autowired
    public void setAccountManagementService(AccountManagementService a_ams) {
        LOGGER.debug(LoggerDefaultLogs.AUTOWIRING.viaSetter(a_ams.getClass(), this.getClass()));
        this.ams = a_ams;
    }

}
