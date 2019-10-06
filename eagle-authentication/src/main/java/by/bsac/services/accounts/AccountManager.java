package by.bsac.services.accounts;

import by.bsac.exceptions.EmailAlreadyRegisteredException;
import by.bsac.models.Account;
import by.bsac.models.User;
import by.bsac.repositories.AccountRepository;
import by.bsac.repositories.UserRepository;
import by.bsac.services.security.hashing.PasswordHash;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;

@Service
@Setter
public class AccountManager implements AccountManagementService, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountManager.class);

    //Spring beans
    private AccountRepository account_repository;
    private UserRepository user_repository;
    private PasswordHash password_hasher;

    public AccountManager() {
        LOGGER.info("Create " +getClass().getSimpleName() +" service bean.");
    }

    @Override
    @Transactional
    public User register(Account account) {

        if (account == null || account.getAccountEmail() == null) throw new NullPointerException("Account or account email object is null");
        if (account.getAccountEmail().isEmpty()) throw new IllegalArgumentException("Account email string is empty.");

        //Check if account with same email already registered
        if (account_repository.foundByAccountEmail(account.getAccountEmail()) != null)
            throw new EmailAlreadyRegisteredException("Email address [" +account.getAccountEmail() +"] already registered");

        //Generate password hash and salt
        byte[] password_salt = this.password_hasher.salt();
        account.setAccountPasswordSalt(DatatypeConverter.printHexBinary(password_salt));
        byte[] password_hash = this.password_hasher.hashPassword(account.getAccountPassword(), password_salt);
        account.setAccountPasswordHash(DatatypeConverter.printHexBinary(password_hash));

        //Reset clear account password
        account.setAccountPassword(null);

        //Create new user entity
        User user = new User();
        user.setUserAccount(account);
        user = this.user_repository.save(user);

        //Persist account entity
        account.setAccountUser(user);
        this.account_repository.save(account);

        return user;
    }

    @Override
    public User login(Account account) {
        return null;
    }


    @Override
    public void afterPropertiesSet() throws Exception {

        if (this.account_repository == null) {
            LOGGER.error(AccountRepository.class.getSimpleName() +" parameter is null");
            throw new Exception(new BeanCreationException(AccountRepository.class.getSimpleName() +" parameter is null"));
        }

        if (this.user_repository == null) {
            LOGGER.error(UserRepository.class.getSimpleName() +" parameter is null");
            throw new Exception(new BeanCreationException(UserRepository.class.getSimpleName() +" parameter is null"));
        }

        if (this.password_hasher == null) {
            LOGGER.error(PasswordHash.class.getSimpleName() +" parameter is null");
            throw new Exception(new BeanCreationException(PasswordHash.class.getSimpleName() +" parameter is null"));
        }
    }
}
