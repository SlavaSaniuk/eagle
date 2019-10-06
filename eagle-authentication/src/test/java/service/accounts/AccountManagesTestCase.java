package service.accounts;

import by.bsac.exceptions.EmailAlreadyRegisteredException;
import by.bsac.models.Account;
import by.bsac.models.User;
import by.bsac.repositories.AccountRepository;
import by.bsac.repositories.UserRepository;
import by.bsac.services.accounts.AccountManager;
import by.bsac.services.security.hashing.PasswordHash;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class AccountManagesTestCase {

    @Mock
    private AccountRepository account_repository;

    @Mock
    private UserRepository user_repository;

    @Mock
    private PasswordHash password_hasher;

    @InjectMocks
    private AccountManager manager;

    //Shared between tests
    private Account account = new Account();

    @BeforeEach
    void setUpBeforeEach() {
        this.manager = new AccountManager();
        MockitoAnnotations.initMocks(this);
        this.account.setAccountPassword("12345678");
    }

    @AfterEach
    void tierDownAfterEach() {
        this.manager = null;
        this.account = null;
    }

    @Test
    void register_accountIsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, ()-> this.manager.register(null));
    }

    @Test
    void register_accountEmailIsNull_shouldThrowNPE() {
        this.account.setAccountEmail(null);
        Assertions.assertThrows(NullPointerException.class, ()-> this.manager.register(account));
    }

    @Test
    void register_accountEmailIsEmpty_shouldThrowIAE() {
        this.account.setAccountEmail("");
        Assertions.assertThrows(IllegalArgumentException.class, ()-> this.manager.register(account));
    }

    @Test
    void register_newAccount_shouldReturnCreatedUser() {

        this.account.setAccountEmail("account-email");

        byte[] salt = {10, 20, 30};
        BDDMockito.given(this.password_hasher.salt()).willReturn(salt);
        BDDMockito.given(this.password_hasher.hashPassword(Mockito.anyString(), Mockito.any(byte[].class))).willReturn(new byte[] {40, 50, 60});

        Integer generated_user_id = 23;
        User created= new User();
        created.setUserId(23);
        created.setUserAccount(account);
        BDDMockito.given(this.user_repository.save(Mockito.any(User.class))).willReturn(created);

        this.account.setAccountId(generated_user_id);
        BDDMockito.given(this.account_repository.save(this.account)).willReturn(this.account);

        //Register account
        User user = this.manager.register(this.account);

        Assertions.assertEquals(generated_user_id, user.getUserId());

        Account persisted = user.getUserAccount();

        Assertions.assertNotNull(persisted);
        Assertions.assertEquals(generated_user_id, persisted.getAccountId());
        Assertions.assertNull(persisted.getAccountPassword());
        Assertions.assertNotNull(persisted.getAccountPasswordHash());
        Assertions.assertNotNull(persisted.getAccountPasswordSalt());

    }

    @Test
    void register_accountAlreadyRegistered_shouldThrowEmailAlreadyRegisteredException() {
        String account_email = "test@test.com";
        account.setAccountEmail(account_email);

        BDDMockito.given(this.account_repository.foundByAccountEmail(account.getAccountEmail())).willReturn(account);

        Assertions.assertThrows(EmailAlreadyRegisteredException.class, () -> this.manager.register(account));
    }
}
