package by.bsac.services;

import by.bsac.exceptions.NoCreatedDetailsException;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Details manager service bean define CRUD methods.
 */
@Service
public interface DetailsManager {

    /**
     * Create new {@link UserDetails}, associate it with
     * {@link User} owner, and flush it to database.
     * Note: [user_id] and [detail_id] properties must be same.
     * @param a_user - {@link User} owner entity.
     * @param a_details - {@link UserDetails} details user entity.
     * @return - {@link UserDetails} with defined detail_id.
     */
    @Transactional
    UserDetails createDetails(User a_user, UserDetails a_details);

    /**
     * Get {@link UserDetails} by it's
     * {@link UserDetails#getDetailsUser()} user owner.
     * @param a_user - {@link User} owner with it's ID.
     * @return - {@link UserDetails} details.
     */
    UserDetails getDetails(User a_user) throws NoCreatedDetailsException;

}
