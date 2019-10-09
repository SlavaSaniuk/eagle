package feign;

import by.bsac.conf.RootContextConfiguration;
import by.bsac.feign.FeignConfiguration;
import by.bsac.feign.clients.AccountManagementService;
import by.bsac.models.Account;
import by.bsac.models.User;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig({RootContextConfiguration.class, FeignConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthServiceIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceIntegrationTest.class);

    @Autowired
    private AccountManagementService ams;

    @Test
    @Order(1)
    void register_newAccount_shouldReturnNewAccountUser() {
        Account account = new Account();
        account.setAccountEmail("test1@eagle-web.com");
        account.setAccountPassword("12345678");

        User user = this.ams.registerAccount(account);

        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getUserId());

        LOGGER.debug(user.toString());
    }

    @Test
    @Order(2)
    void login_registeredAccount_shouldReturnUserAccount() {
        Account account = new Account();
        account.setAccountEmail("test1@eagle-web.com");
        account.setAccountPassword("12345678");

        User user = this.ams.loginAccount(account);

        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getUserId());
        Assertions.assertNotEquals(0, user.getUserId());

        LOGGER.debug(user.toString());
        LOGGER.debug("User ID: " +user.getUserId().toString());
    }

    @Test
    @Order(3)
    void login_incorrectPassword_shouldReturnInvalidUserEntity() {
        Account account = new Account();
        account.setAccountEmail("test1@eagle-web.com");
        account.setAccountPassword("invalid-password");

        User user = this.ams.loginAccount(account);

        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getUserId());
        Assertions.assertEquals(-1, user.getUserId());

        LOGGER.debug(user.toString());
        LOGGER.debug("User ID: " +user.getUserId().toString());
    }

}
