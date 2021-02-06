package com.shetu.user_management_app.dto.response;

import com.shetu.user_management_app.enums.ErrorCodes;
import lombok.Data;

@Data
public class ErrorResponse {
    private ErrorCodes errorCode;
    private String message;

    public ErrorResponse(ErrorCodes errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
