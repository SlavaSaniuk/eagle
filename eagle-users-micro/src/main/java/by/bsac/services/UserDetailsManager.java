package by.bsac.services;

import by.bsac.configuration.LoggerDefaultLogs;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.repositories.DetailsRepository;
import by.bsac.repositories.UserRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import static by.bsac.configuration.LoggerDefaultLogs.*;

@Service
@Getter
public class UserDetailsManager implements DetailsManager, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsManager.class);
    //Spring beans
    private DetailsRepository details_repository;
    private UserRepository users_repository;

    public UserDetailsManager() {
        LOGGER.debug(CREATION.beanCreationStart(this.getClass()));
    }

    @Override
    public UserDetails createDetails(User a_user, UserDetails a_details) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        
        LOGGER.debug(CREATION.beanCreationFinish(this.getClass()));
    }

    public void setDetailsRepository(DetailsRepository details_repository) {
        LOGGER.debug(DEPENDENCY.viaSetter(details_repository.getClass(), this.getClass()));
        this.details_repository = details_repository;
    }

    public void setUsersRepository(UserRepository users_repository) {
        LOGGER.debug(DEPENDENCY.viaSetter(users_repository.getClass(), this.getClass()));
        this.users_repository = users_repository;
    }
}
