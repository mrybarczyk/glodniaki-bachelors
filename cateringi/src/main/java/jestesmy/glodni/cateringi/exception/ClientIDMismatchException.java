package jestesmy.glodni.cateringi.exception;

public class ClientIDMismatchException extends RuntimeException {

    public ClientIDMismatchException() {
        super();
    }

    public ClientIDMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ClientIDMismatchException(final String message) {
        super(message);
    }

    public ClientIDMismatchException(final Throwable cause) {
        super(cause);
    }
}
