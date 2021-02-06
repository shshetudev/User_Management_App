package com.shetu.user_management_app.exception;

import com.shetu.user_management_app.dto.response.ErrorResponse;
import com.shetu.user_management_app.enums.ErrorCodes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
//check this: https://devwithus.com/exception-handling-for-rest-api-with-spring-boot/

// *********** Errors handled without Custom Model Starts ****************

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCodes.SER0000, ErrorCodes.SER0000.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCodes.CVOOOO, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedTypeException(UnexpectedTypeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCodes.UTOOOO, ErrorCodes.UTOOOO.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder errorCollector = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(
                error -> {
                    String errorMessage = error.getDefaultMessage();
                    errorCollector.append(errorMessage).append(";");
                }
        );
        ErrorResponse errorResponse = new ErrorResponse(ErrorCodes.U0001, errorCollector.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

// ************ Errors handled without Custom Model Ends ******************


// ************ Errors handled with Custom Model Starts ******************

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidActionException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidActionException(InvalidActionException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
// ************ Errors handled with Custom Model Ends ******************


/*@ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
//        List<String> errorList = new ArrayList<>();
        StringBuilder errorCollector = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(
                error -> {
                    String errorMessage = error.getDefaultMessage();
                    errorCollector.append(errorMessage).append(";");
                }
        );
        ErrorResponse errorResponse = new ErrorResponse(ErrorCodes.U0001,errorCollector.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }*/

}
