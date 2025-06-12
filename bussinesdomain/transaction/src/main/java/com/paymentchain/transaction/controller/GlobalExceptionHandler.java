/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.transaction.controller;

import com.paymentchain.transaction.exception.exceptionNotFoundEntity;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author herna
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handlerInvalidException(MethodArgumentNotValidException ex){
        
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(";"));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    
    
    
    @ExceptionHandler(exceptionNotFoundEntity.class)
    public ResponseEntity<String> exceptionNotFoundEntity(exceptionNotFoundEntity ex){
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entidad no encontrada en la base de datos..");
    }
    
}
