package hr.drigler.lisea.juno.services.exceptions;

public class WrongPasswordException extends Exception {

    private static final long serialVersionUID = -1490277917091843976L;

    public WrongPasswordException() {

        super();
    }

    public WrongPasswordException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    public WrongPasswordException(String message, Throwable cause) {

        super(message, cause);
    }

    public WrongPasswordException(String message) {

        super(message);
    }

    public WrongPasswordException(Throwable cause) {

        super(cause);
    }

}
