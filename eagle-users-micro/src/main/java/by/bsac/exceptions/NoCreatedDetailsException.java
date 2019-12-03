package by.bsac.exceptions;

import by.bsac.models.User;

/**
 * Exception throws in cases when {@link by.bsac.models.User} entity persisted in database,
 * but not persist {@link by.bsac.models.UserDetails} entity (Unfinished registration process).
 */
public class NoCreatedDetailsException extends Exception {

    /**
     * Construct new {@link NoCreatedDetailsException} exception object
     * with exception message: "User [user_id] not create details before.";
     * @param user_id - {@link User#getUserId()} parameter.
     */
    public NoCreatedDetailsException(Integer user_id) {
        super("User ["+user_id +"] not create details before.");
    }
}
