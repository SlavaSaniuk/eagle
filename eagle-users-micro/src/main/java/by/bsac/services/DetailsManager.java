package by.bsac.services;

import by.bsac.models.User;
import by.bsac.models.UserDetails;

public interface DetailsManager {

    UserDetails createDetails(User a_user, UserDetails a_details);
}
