package com.tsi.vehicle.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> notFoundHandler(ResourceNotFoundException ex) {
        Map map = new HashMap();
        map.put("message", ex.getMessage());
        map.put("Success", false);
        map.put("Status", HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }
@ExceptionHandler
    public ResponseEntity<Map<String, Object>> vinAlreadyExits(DataIntegrityViolationException ex){
        Map map= new HashMap();
        map.put("message","VIN already exist");
        map.put("success",false);
        map.put("status", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> duplicateVin(ConstraintViolationException ex){
        Map map= new HashMap();
        map.put("message", "VIN must be alphanumric");
        map.put("success",false);
        map.put("status", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }
}
