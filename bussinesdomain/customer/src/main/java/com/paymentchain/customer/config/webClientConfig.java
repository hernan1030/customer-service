/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.customer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 * @author herna
 */
@Configuration
public class webClientConfig {
    
    
    @Bean()
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
    
    
    @Bean("webClientProduct")
    public WebClient productWebClient(WebClient.Builder builder){
        return builder
                .baseUrl("http://product/product")
                .defaultHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    
    
    @Bean("webClientTransaction")
    public WebClient transactionWebClient(WebClient.Builder builder){
        
        return builder
                .baseUrl("http://transaction/transaction/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    
    
    
    
    
}
