package service.accounts;

import by.bsac.conf.DatasourcesConfiguration;
import by.bsac.conf.PersistenceConfiguration;
import by.bsac.exceptions.EmailAlreadyRegisteredException;
import by.bsac.models.Account;
import by.bsac.models.User;
import by.bsac.services.ServicesConfiguration;
import by.bsac.services.accounts.AccountManagementService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ActiveProfiles("TEST")
@SpringJUnitConfig({DatasourcesConfiguration.class, PersistenceConfiguration.class, ServicesConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountManagementServiceIntegrationTest {

    @Autowired
    private AccountManagementService ams;

    @Test
    @Order(1)
    @Commit
    void registerAccount_newAccount_shouldReturnCreatedUser() {

        Account account = new Account();
        account.setAccountEmail("test@test.com");
        account.setAccountPassword("account-password");

        User user = this.ams.register(account);

        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getUserId());

        Account persisted = user.getUserAccount();

        Assertions.assertNotNull(persisted);
        Assertions.assertNotNull(persisted.getAccountId());
        Assertions.assertEquals(persisted.getAccountId(), user.getUserId());
        Assertions.assertNull(persisted.getAccountPassword());
        Assertions.assertNotNull(persisted.getAccountPasswordHash());
        Assertions.assertNotNull(persisted.getAccountPasswordSalt());
    }

    @Test
    @Order(2)
    void registerAccount_accountAlreadyRegister_shouldThrowEmailAlreadyRegisteredException() {

        Account account = new Account();
        account.setAccountEmail("test@test.com");

        Assertions.assertThrows(EmailAlreadyRegisteredException.class, ()-> this.ams.register(account));
    }
}
