/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.product.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 * @author herna
 */

@Service
public class LogicServices {
    
    
    private final WebClient LoadClientPrueba;

    public LogicServices(@Qualifier("LoadClientPrueba")WebClient LoadClientPrueba) {
        this.LoadClientPrueba = LoadClientPrueba;
    }
    
    
    public String getNameString(){
        return LoadClientPrueba.get()
                .uri("/customer/holaMundo")
                .retrieve()
                .bodyToMono(String.class).block();
    }
    
    
    
    
}
