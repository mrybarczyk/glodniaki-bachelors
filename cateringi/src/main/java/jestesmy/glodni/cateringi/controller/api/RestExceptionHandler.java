package jestesmy.glodni.cateringi.controller.api;

import jestesmy.glodni.cateringi.exception.IdMismatchException;
import jestesmy.glodni.cateringi.exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception,"Not found", new HttpHeaders(),
                HttpStatus.NOT_FOUND,request);
    }

    @ExceptionHandler({IdMismatchException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleBadRequest(Exception exception,WebRequest request) {
        return handleExceptionInternal(exception, exception.getLocalizedMessage(), new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }
}
