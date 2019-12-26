package by.bsac.services;

import by.bsac.exceptions.NoCreatedDetailsException;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.repositories.DetailsRepository;
import by.bsac.repositories.UserRepository;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static by.bsac.configuration.LoggerDefaultLogs.*;

@Service
public class UserDetailsManager implements DetailsManager, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsManager.class);
    //Spring beans
    private DetailsRepository details_repository;
    private UserRepository user_repository;

    public UserDetailsManager() {
        LOGGER.debug(CREATION.beanCreationStart(this.getClass()));
    }

    @Override
    @Transactional
    public UserDetails createDetails(@NonNull User a_user, @NonNull UserDetails a_details) {

        //Check if user persisted before (has a defined ID)
        if (a_user.getUserId() == null) throw new IllegalArgumentException("[user_id] parameter cannot be null.");

        //Get user from database
        Optional<User> user_opt = this.user_repository.findById(a_user.getUserId());
        if (!user_opt.isPresent()) throw new IllegalArgumentException("[user_id] parameter is in invalid value.");
        User user = user_opt.get();

        //maps ID
        //persist details
        a_details.setDetailsUser(user);
        user.setUserDetails(a_details);

        return this.details_repository.save(a_details);
    }

    @Override
    public UserDetails getDetails(@NonNull User a_user) throws NoCreatedDetailsException {

        //Check if user have an it's ID
        if (a_user.getUserId() == null) throw new IllegalArgumentException("[user_id] parameter cannot be null.");

        //Get user entity from database
        Optional<User> user_optional = this.user_repository.findById(a_user.getUserId());
        if (!user_optional.isPresent()) throw new IllegalArgumentException("[user_id] parameter is in invalid value.");

        UserDetails details = user_optional.get().getUserDetails();
        if (details == null) throw new NoCreatedDetailsException(a_user.getUserId());

        return details;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (this.details_repository == null)
            throw new Exception(new BeanCreationException("[" +DetailsRepository.class.getCanonicalName() +"] parameter is null."));

        LOGGER.debug(CREATION.beanCreationFinish(this.getClass()));
    }

    //Dependency
    public void setDetailsRepository(DetailsRepository details_repository) {
        LOGGER.debug(DEPENDENCY.viaSetter(details_repository.getClass(), this.getClass()));
        this.details_repository = details_repository;
    }

    //Dependency
    public void setUserRepository(UserRepository user_repository) {
        LOGGER.debug(DEPENDENCY.viaSetter(user_repository.getClass(), this.getClass()));
        this.user_repository = user_repository;
    }


}
