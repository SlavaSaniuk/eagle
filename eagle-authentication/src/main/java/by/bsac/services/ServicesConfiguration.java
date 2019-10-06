package by.bsac.services;

import by.bsac.services.security.hashing.HashAlgorithm;
import by.bsac.services.security.hashing.PasswordHash;
import by.bsac.services.security.hashing.PasswordHasher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);

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

}
