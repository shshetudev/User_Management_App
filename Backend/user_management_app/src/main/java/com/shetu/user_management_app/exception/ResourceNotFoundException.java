package com.shetu.user_management_app.exception;

import com.shetu.user_management_app.enums.ErrorCodes;
import com.shetu.user_management_app.utils.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Slf4j
public class ResourceNotFoundException extends RuntimeException{
    private ErrorCodes errorCode;

    public ResourceNotFoundException(Integer id, ErrorCodes errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
        log.info("Resource Not found with id: {}",id);
    }
}
