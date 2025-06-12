/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.product.mapper;

import com.paymentchain.product.dto.ProductDto;
import com.paymentchain.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;



/**
 *
 * @author herna
 */


@Mapper()
public interface ProductMapper {
    
    
    
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    
    
    @Mapping(source = "code" , target = "productcode")
    //convertir el la entidad Pruduct en ProductDto
    ProductDto productToproductdto(Product product);
    
    
    @Mapping(source = "productcode" , target = "code")
    //convertir el ProductDto a product
    Product productDtoToProduct(ProductDto productDto);
    
}
