package by.bsac.webmvc.controllers;

import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.exceptions.AccountAlreadyRegisteredException;
import by.bsac.exceptions.AccountNotRegisteredException;
import by.bsac.exceptions.NoCreatedDetailsException;
import by.bsac.feign.clients.AccountManagementService;
import by.bsac.feign.clients.UserDetailsService;
import by.bsac.models.Account;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import by.bsac.webmvc.dto.UserWithDetailsDto;
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

import static by.bsac.conf.LoggerDefaultLogs.*;

@Controller
@SessionAttributes({"common_user"})
public class SignController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    //Spring beans
    private AccountManagementService ams; //Autowired via setter
    private UserDetailsService uds; //Autowired via setter
    private EmbeddedDeConverter<UserWithDetailsDto> details_converter; //Autowired via setter

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
    public ModelAndView loginAccount(@ModelAttribute("AccountForm") AccountForm account_form) throws NoCreatedDetailsException {
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

        //Get user details
        UserWithDetailsDto dto = new UserWithDetailsDto();
        dto.setUserId(account_user.getUserId());
        dto = this.uds.getDetails(dto);
        UserDetails details = this.details_converter.toEntity(dto, new UserDetails(), new UserName());
        account_user.setUserDetails(details);
        details.setDetailsUser(account_user);

        //Put user attribute to session
        mav.getModel().put("common_user", account_user);

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

        //Put user attribute to session
        mav.getModel().put("common_user", account_user);

        mav.setViewName("redirect:/about-user");
        return mav;
    }

    //Autowiring
    @Autowired
    public void setAccountManagementService(AccountManagementService a_ams) {
        LOGGER.debug(AUTOWIRING.viaSetter(a_ams.getClass(), this.getClass()));
        this.ams = a_ams;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService a_uds) {
        LOGGER.debug(AUTOWIRING.viaSetter(a_uds.getClass(), this.getClass()));
        this.uds = a_uds;
    }

    @Autowired
    public void setDetailsConverter(EmbeddedDeConverter<UserWithDetailsDto> a_converter) {
        LOGGER.debug(AUTOWIRING.viaSetter(a_converter.getClass(), this.getClass()));
        this.details_converter = a_converter;
    }

}
