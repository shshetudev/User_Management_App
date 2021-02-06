package com.shetu.user_management_app.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Utils {
    public Map<String,Object> getResponseMap(String statusCode,String message){
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("statusCode", statusCode);
        responseMap.put("message", message);
        return  responseMap;
    }
    public Map<String,Object> getResponseMap(String statusCode,String message,Object result){
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("statusCode", statusCode);
        responseMap.put("message", message);
        responseMap.put("result", result);
        return  responseMap;
    }
}
