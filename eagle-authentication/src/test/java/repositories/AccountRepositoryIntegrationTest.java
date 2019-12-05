package repositories;

import by.bsac.conf.DatasourcesConfiguration;
import by.bsac.conf.PersistenceConfiguration;
import by.bsac.models.Account;
import by.bsac.models.AccountStatus;
import by.bsac.models.Status;
import by.bsac.repositories.AccountRepository;
import by.bsac.repositories.AccountStatusRepository;
import by.bsac.services.ServicesConfiguration;
import by.bsac.services.accounts.AccountManagementService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ActiveProfiles("TEST")
@SpringJUnitConfig({DatasourcesConfiguration.class, PersistenceConfiguration.class, ServicesConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountRepositoryIntegrationTest {

    @Autowired
    private AccountRepository account_repository;

    @Autowired
    private AccountManagementService ams;

    @Autowired
    private AccountStatusRepository status_repository;

    @Test
    @Transactional
    @Commit
    @Order(1)
    public void foundAllByAccountStatus_createdStatus_shouldReturnAccounts() {

        Account account1 = new Account();
        account1.setAccountEmail("test1@mail");
        account1.setAccountPassword("12345678");


        Account account2 = new Account();
        account2.setAccountEmail("test2@mail");
        account2.setAccountPassword("12345678");

        Account account3 = new Account();
        account3.setAccountEmail("test3@mail");
        account3.setAccountPassword("12345678");

        this.ams.register(account1);
        this.ams.register(account2);
        this.ams.register(account3);

        List<Account> founded = this.account_repository.foundAllByAccountStatus(Status.CREATED);

        Assertions.assertNotNull(founded);
        Assertions.assertEquals(3, founded.size());

    }

    @Test
    @Transactional
    @Commit
    @Order(2)
    public void updateAccountStatusById_oneStatusForUpdate_shouldUpdateStatus() {

        Account account = this.account_repository.foundByAccountEmail("test2@mail");
        assert account != null;

        this.status_repository.updateAccountStatusById(Status.CONFIRMED, account.getAccountId());

        List<AccountStatus> updated_statuses = this.status_repository.findAccountStatusByStatus(Status.CONFIRMED);

        Assertions.assertNotNull(updated_statuses);
        Assertions.assertEquals(1, updated_statuses.size());
        Assertions.assertEquals(account.getAccountId(), updated_statuses.get(0).getStatusId());

    }

    @Test
    @Order(3)
    public void findAccountStatusByStatus_twoCreatedStatuses_shouldReturnThis() {

        List<AccountStatus> statuses = this.status_repository.findAccountStatusByStatus(Status.CREATED);

        Assertions.assertNotNull(statuses);
        Assertions.assertEquals(2, statuses.size());
    }





}
