package by.bsac.exceptions;

public class AccountAlreadyRegisteredException extends RuntimeException {

    public AccountAlreadyRegisteredException(String message) {
        super(message);
    }

    public AccountAlreadyRegisteredException() {
        super("Account with same email already register.");
    }
}
