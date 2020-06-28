package hr.drigler.lisea.juno.services;

public class DuplicateUserException extends Exception {

    private static final long serialVersionUID = 3548706830607841716L;

    public DuplicateUserException() {

        super();
        // TODO Auto-generated constructor stub
    }

    public DuplicateUserException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public DuplicateUserException(String message, Throwable cause) {

        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public DuplicateUserException(String message) {

        super(message);
        // TODO Auto-generated constructor stub
    }

    public DuplicateUserException(Throwable cause) {

        super(cause);
        // TODO Auto-generated constructor stub
    }

}
