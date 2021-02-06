package com.shetu.user_management_app.dto.response;

import lombok.Data;

@Data
public class ApiResponse {
    private Integer statusCode;
    private String message;
    private Object result;

    public ApiResponse(Integer statusCode, String message, Object result) {
        this.statusCode = statusCode;
        this.message = message;
        this.result = result;
    }
}
