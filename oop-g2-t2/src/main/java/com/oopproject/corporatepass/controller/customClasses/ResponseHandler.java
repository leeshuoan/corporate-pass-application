package com.oopproject.corporatepass.controller.customClasses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ResponseHandler {
    public static ResponseEntity<Object> generateObjectResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", message);
            map.put("status", status.value());
            map.put("data", responseObj);

            return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> generateListResponse(String message, HttpStatus status, List<Object> responseList) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", message);
            map.put("status", status.value());
            map.put("data", responseList);

            return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> generateListStringResponse(String message, HttpStatus status, List<String> responseListString) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", message);
            map.put("status", status.value());
            map.put("data", responseListString);

            return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> generateIntResponse(String message, HttpStatus status, int responseInt) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", message);
            map.put("status", status.value());
            map.put("data", responseInt);

            return new ResponseEntity<Object>(map,status);
    }
}