package by.bsac.services;

import by.bsac.models.User;
import by.bsac.repositories.DetailsRepository;
import by.bsac.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static by.bsac.configuration.LoggerDefaultLogs.*;

@Configuration
public class ServicesConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);
    //Spring beans
    private DetailsRepository detail_repository;
    private UserRepository user_repository;

    public ServicesConfiguration() {
        LOGGER.info(INITIALIZATION.initConfig(this.getClass()));
    }

    @Bean("DetailsManager")
    public DetailsManager createDetailsManager() {

        final UserDetailsManager dm = new UserDetailsManager();
        LOGGER.info(CREATION.beanCreationStart(dm.getClass()));

        LOGGER.info(DEPENDENCY.viaSetter(this.detail_repository.getClass(), dm.getClass()));
        dm.setDetailsRepository(this.detail_repository);

        LOGGER.info(DEPENDENCY.viaSetter(UserRepository.class, dm.getClass()));
        dm.setUserRepository(this.user_repository);

        LOGGER.info(CREATION.beanCreationFinish(dm.getClass()));
        return dm;
    }

    @Autowired
    public void setDetailRepository(DetailsRepository detail_repository) {
        LOGGER.info(AUTOWIRING.viaSetter(detail_repository.getClass(), this.getClass()));
        this.detail_repository = detail_repository;
    }

    @Autowired
    public void setUserRepository(UserRepository user_repository) {
        LOGGER.info(AUTOWIRING.viaSetter(user_repository.getClass(), this.getClass()));
        this.user_repository = user_repository;
    }
}