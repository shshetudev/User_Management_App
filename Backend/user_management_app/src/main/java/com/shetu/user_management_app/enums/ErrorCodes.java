package com.shetu.user_management_app.enums;

import com.shetu.user_management_app.utils.Constants;

public enum ErrorCodes {
    U0000(Constants.USER_NOT_FOUND),
    U0001(Constants.INVALID_USER_REQUEST),
    U0002(Constants.USER_NOT_UPDATABLE),
    U0003(Constants.INVALID_USER_ID),
    U0004(Constants.INVALID_USER_TYPE),
    U0005(Constants.PARENT_USER_NOT_FOUND),
    U0006(Constants.USERS_NOT_SAVED),

    // Constraint Violation Exception
    CVOOOO(Constants.CONSTRAINT_VIOLATION),
    // Unexpected Type Exception
    UTOOOO(Constants.UNEXPECTED_TYPE),
    // Server Errors
    SER0000(Constants.ERROR_FOUND_IN_SERVER_SIDE)
    ;
    private final String errorMessage;
    ErrorCodes(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage(){
        return this.errorMessage;
    }
}
