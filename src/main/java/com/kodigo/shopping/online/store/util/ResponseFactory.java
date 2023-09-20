package com.kodigo.shopping.online.store.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseFactory {

    public static ResponseEntity<?> responseCreated() {
        Map<String, Object> res = new HashMap<>();
        res.put("HTTP", HttpStatus.CREATED);
        res.put("Message", "Resource created successfully");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    public static ResponseEntity<?> responseCreated(Object data) {
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("HTTP", HttpStatus.CREATED);
        res.put("Message", "Resource created successfully");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    public static ResponseEntity<?> responseCreated(Object data, String mensaje) {
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("HTTP", HttpStatus.CREATED);
        res.put("Mensaje", mensaje);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}
