package com.ziponia.iamport.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class IamportExceptionHandler {

    @ExceptionHandler(IamportException.class)
    public HashMap<String, Object> iamportException(Exception e) {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("Message", "PaymentError");
        hm.put("trace", e.getMessage());
        return hm;
    }
}
