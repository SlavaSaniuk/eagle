package by.bsac.exceptions;

/**
 * Exception throws in cases when user wan't login with no confirmed account.
 */
public class NoConfirmedAccountException extends Exception {

    public NoConfirmedAccountException(String msg) {
        super(msg);
    }

}
