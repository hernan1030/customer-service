/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.product.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
/**
 *
 Este sera el Manipulador global de excecciones, desde
 * aqui se manejaran los errores comunes como 504 el 404 y otros
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    
    
    // Maneja errores generales del sistema y devuelve un mensaje de error interno del s
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> ExceptionHandler(Exception e){
        log.error("Error interno del servidor ", e);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor " + e.getMessage());
    }
    
    
    
    //Se activa cuando no se encuentra una entidad en la base de datos y devuelve un mensaje de error 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String>  handlerEntityNotFound(Exception e){
        log.error("Entidad no encontrada {} ",e);
        
        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Entidad no encontrada " + e.getMessage());
    }
    
    
    
    //Maneja errores al comunicarse con servicios internos, como problemas de conexión o respuestas inválidas.
    @ExceptionHandler({HttpClientErrorException.class,ResourceAccessException.class})
    public ResponseEntity<String> handlerRestClientExceptional(Exception e){
        log.error("Error al comunicarse con el servicio interno ", e);
        
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Error al comunicarser con el servidor interno " + e.getMessage());
    }
    
    
}
