package jestesmy.glodni.cateringi.exception;

public class IdMissmatchException extends RuntimeException {

    public IdMissmatchException(String message, Throwable cause){
        super(message, cause);
    }

    public IdMissmatchException(){
        super();
    }
}
