package by.bsac.exceptions;

public class AccountNotRegisteredException extends RuntimeException {


    public AccountNotRegisteredException(String detailed_message) {
        super(detailed_message);
    }

    public AccountNotRegisteredException() {
        super("Account with this email is not registered.");
    }
}
