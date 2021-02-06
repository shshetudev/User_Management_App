package com.shetu.user_management_app.exception;


import com.shetu.user_management_app.enums.ErrorCodes;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidActionException extends RuntimeException{
    private ErrorCodes errorCode;
    public InvalidActionException(ErrorCodes errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public InvalidActionException(ErrorCodes code, Object... args) {
    super(String.format(code.getErrorMessage(),args));
    this.errorCode = code;
    }
}
