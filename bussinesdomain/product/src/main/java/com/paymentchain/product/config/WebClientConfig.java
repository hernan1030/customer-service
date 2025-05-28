/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.product.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 * @author herna
 */

@Configuration
public class WebClientConfig {
    
  
    
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        
        return WebClient.builder();
    }
    
    @Bean("LoadClientPrueba")
    public WebClient webClientPrueba(WebClient.Builder build){
        return build.baseUrl("http://customer").build();
    }
    
    
    
    
}
