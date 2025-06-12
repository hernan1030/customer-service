/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author herna
 */
@Data
@Schema(name="ProductResponse", description = "Modelo que representa a product en la base de datos")
public class ProductDto {

    @Schema(description="codigo del producto " ,example = "1")
    @NotNull(message =  "El code del producto no puede ser null")
    private Long Productcode;
    
    
    @Schema(description="Nombre del producto " ,example = "Debito o Credito")
    @NotNull(message =  "El nombre del producto no puede ser null")
    private String name;
}
