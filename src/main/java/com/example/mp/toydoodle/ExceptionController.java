package com.example.mp.toydoodle;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                      HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointer(NullPointerException ex, WebRequest request){
        String error = "Malformed JSON";
        return buildResponseEntity(new ApiError(HttpStatus.NOT_ACCEPTABLE,error,ex));
    }

    @ExceptionHandler(DoodleNotValidException.class)
    protected ResponseEntity<Object> handleDoodleNotValid(DoodleNotValidException ex, WebRequest request){
        String error = "Doodle JSON Malformed";
        return buildResponseEntity(new ApiError(HttpStatus.NOT_ACCEPTABLE,error,ex));
    }

    @ExceptionHandler(IdNotValidException.class)
    protected ResponseEntity<Object> handleIdNotValid(IdNotValidException ex, WebRequest request){
        String error = "Doodle not present";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,error,ex));
    }
    @ExceptionHandler(VoteAlreadyPresentException.class)
    protected ResponseEntity<Object> handleVoteAltreadyPresent(VoteAlreadyPresentException ex,
                                                               WebRequest request){
        String error = "Multiple preferences not allowed";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,error,ex));
    }

    @ExceptionHandler(VoteNotPresentException.class)
    protected ResponseEntity<Object> handleVoteNotPresent(VoteNotPresentException ex, WebRequest request){
        String error = "Vote not present";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,error,ex));
    }
    @ExceptionHandler(VoteNotValidException.class)
    protected ResponseEntity<Object> handleVoteNotPresent(VoteNotValidException ex, WebRequest request){
        String error = "User parameters mismatch";
        return buildResponseEntity(new ApiError(HttpStatus.METHOD_NOT_ALLOWED,error,ex));
    }
}
