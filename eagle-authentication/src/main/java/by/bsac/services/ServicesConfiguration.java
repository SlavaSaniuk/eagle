package by.bsac.services;

import by.bsac.repositories.AccountRepository;
import by.bsac.repositories.UserRepository;
import by.bsac.services.accounts.AccountManagementService;
import by.bsac.services.accounts.AccountManager;
import by.bsac.services.security.hashing.HashAlgorithm;
import by.bsac.services.security.hashing.PasswordHash;
import by.bsac.services.security.hashing.PasswordHasher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);
    //Spring beans
    private AccountRepository account_repository;
    private UserRepository user_repository;

    public ServicesConfiguration() {
        LOGGER.info("Start to initialize " +getClass().getSimpleName() +" configuration class.");
    }

    //Beans
    @Bean("PasswordHasher")
    public PasswordHash passwordHasher() {

        PasswordHasher hasher = new PasswordHasher();

        //Set "SHA-512" hash function
        hasher.setHashAlgorithm(HashAlgorithm.SHA_512);
        LOGGER.debug(hasher.getClass().getSimpleName() +": Use hash function [" +hasher.getHashAlgorithm()+"]");

        return hasher;
    }

    @Bean("AccountManagementService")
    public AccountManagementService accountManager() {

        AccountManager manager = new AccountManager();

        //Set properties
        manager.setPasswordHasher(this.passwordHasher());
        manager.setAccountRepository(this.account_repository);
        manager.setUserRepository(this.user_repository);

        return manager;
    }

    //Spring autowiring
    @Autowired
    public void setAccountRepository(AccountRepository account_repository) {
        LOGGER.debug("[AUTOWIRE] " +account_repository.getClass().getSimpleName() +" to " +getClass().getSimpleName() +" configuration.");
        this.account_repository = account_repository;
    }

    @Autowired
    public void setUserRepository(UserRepository user_repository) {
        LOGGER.debug("[AUTOWIRE] " +user_repository.getClass().getSimpleName() +" to " +getClass().getSimpleName() +" configuration.");
        this.user_repository = user_repository;
    }
}
