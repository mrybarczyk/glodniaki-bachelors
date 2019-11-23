package jestesmy.glodni.cateringi.exception;

public class IdMismatchException extends RuntimeException {

    public IdMismatchException(String message, Throwable cause){
        super(message, cause);
    }

    public IdMismatchException(){
        super();
    }
}
