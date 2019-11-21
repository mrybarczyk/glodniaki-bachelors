package jestesmy.glodni.cateringi.exception;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException() {
        super();
    }

    public ClientNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ClientNotFoundException(final String message) {
        super(message);
    }

    public ClientNotFoundException(final Throwable cause) {
        super(cause);
    }
}
