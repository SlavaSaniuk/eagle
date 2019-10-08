package repositories;

import by.bsac.conf.DatasourcesConfiguration;
import by.bsac.conf.PersistenceConfiguration;
import by.bsac.models.Account;
import by.bsac.models.User;
import by.bsac.repositories.AccountRepository;
import by.bsac.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("TEST")
@SpringJUnitConfig({DatasourcesConfiguration.class, PersistenceConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountRepositoryIntegrationTest {

    @Autowired
    private AccountRepository account_repository;

    @Autowired
    private UserRepository user_repository;

    //Shared between tests
    private String account_email = "test@test.com";
    private final Integer generated_id = 1;

    @Test
    @Transactional
    @Commit
    @Order(1)
    void save_accountWithUser_shouldReturnAccount() {

        //Create entities
        User user = new User();
        Account account = new Account();

        account.setAccountEmail(account_email);
        account.setAccountPasswordHash("account-password-hash");
        account.setAccountPasswordSalt("account-password-salt");

        //Persist user entity
        user.setUserAccount(account);
        account.setAccountUser(user);
        user = this.user_repository.save(user);

        Assertions.assertEquals(this.generated_id, user.getUserId());

        //Save account
        account = this.account_repository.save(account);

        //Check maps ID
        Assertions.assertEquals(this.generated_id, account.getAccountId());

    }

    @Test
    void findByAccountEmail_createdAccount_shouldReturnAccount() {

        Account account = this.account_repository.foundByAccountEmail(this.account_email);

        Assertions.assertNotNull(account);
        Assertions.assertEquals(this.generated_id, account.getAccountId());
        Assertions.assertEquals(this.account_email, account.getAccountEmail());
    }

    @Test
    void findByAccountEmail_notCreatedAccount_shouldReturnNull() {

        Account account = this.account_repository.foundByAccountEmail("incorrect");
        Assertions.assertNull(account);
    }



}
