package by.bsac.services;

import by.bsac.models.User;
import by.bsac.models.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface DetailsManager {

    @Transactional
    UserDetails createDetails(User a_user, UserDetails a_details);
}
