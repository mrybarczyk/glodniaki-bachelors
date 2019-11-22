package jestesmy.glodni.cateringi.controller;

import jestesmy.glodni.cateringi.exception.CompanyIdMissmatchException;
import jestesmy.glodni.cateringi.exception.CompanyNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CompanyNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception,"Company not found", new HttpHeaders(),
                HttpStatus.NOT_FOUND,request);
    }

    @ExceptionHandler({CompanyIdMissmatchException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleBadRequest(Exception exception,WebRequest request) {
        return handleExceptionInternal(exception,exception.getLocalizedMessage(),new HttpHeaders(),
                HttpStatus.BAD_REQUEST,request);
    }
}
