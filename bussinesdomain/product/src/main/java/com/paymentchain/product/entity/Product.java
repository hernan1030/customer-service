/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.product.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 *
 * @author herna
 */
@Entity
@Data
public class Product {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @Schema(description="codigo del producto " ,example = "1")
    @NotNull(message =  "El code del producto no puede ser null")
    private Long code;
    
    
    @Schema(description="Nombre del producto " ,example = "Debito o Credito")
    @NotNull(message =  "El nombre del producto no puede ser null")
    private String name;
    
}
