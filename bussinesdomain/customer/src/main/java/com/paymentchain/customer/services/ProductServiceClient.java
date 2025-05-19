/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.customer.services;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author herna
 */
@Service
//@RequiredArgsConstructor esto si voy a utilizar solo un metodo para web cline me sirve por que genera un contructor atomatico pero si voy a usar mas de uno mejor hacer el contructor manual con quelifiter
public class ProductServiceClient {

    private final Logger logs = LoggerFactory.getLogger(ProductServiceClient.class);

    
    private final WebClient webClientProduct;
    private final WebClient webClientTransaction;
    
    public ProductServiceClient(@Qualifier("webClientProduct")WebClient webClientProduct, @Qualifier("webClientTransaction")WebClient webClientTransaction) {
        this.webClientProduct = webClientProduct;
        this.webClientTransaction = webClientTransaction;
    }
    
    
    

    public String getProductName(Long id) {

        try {
            JsonNode response = webClientProduct.get()
                    .uri("/" + id)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();
            logs.warn("Respuesta del servicio de productos: {}", response);
            return (response != null && response.has("name"))
                    ? response.get("name").asText()
                    : "name no esta presente en product";

        } catch (Exception e) {
            logs.error("Error en la petición al servicio de productos: {}", e.getMessage());
            return "Error obtenido en la respuesta de name";
        }
    }
    
    
    
    public List<Object> getTransactionIban(String accontIban){
        
        try {
            List<Object> transaction = webClientTransaction.get()
                    .uri(ruta -> ruta.path("customer/transaction")
                    .queryParam("accontIban", accontIban)
                    .build())
                    .retrieve()
                    .bodyToFlux(Object.class).collectList().block();
            return transaction;
                    
        } catch (Exception e) {
            logs.warn("No se encontro ningun cliente con el iban: {} " ,accontIban);
            return Collections.emptyList(); //retorna una lista vacia en caso de error
        }
        
    } 
    
    
    
    

}
