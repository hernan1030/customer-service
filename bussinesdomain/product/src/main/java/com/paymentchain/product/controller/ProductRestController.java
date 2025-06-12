/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.paymentchain.product.controller;

import com.paymentchain.product.dto.ProductDto;
import com.paymentchain.product.entity.Product;
import com.paymentchain.product.mapper.ProductMapper;

import com.paymentchain.product.repository.ProductRepository;
import com.paymentchain.product.services.LogicServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;



//para mirar debugs controlados
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 *
 * @author herna
 */
@RestController
@RequestMapping("/product")
@Tag(name="product",description = "Operaciones relacionadas con el producto del cliente")
public class ProductRestController {
    
   @Autowired
   private LogicServices logis;
   
   //Inyeccion del mapper del dto
   private final ProductMapper productMapper = ProductMapper.INSTANCE; 
   
   
    
    @Operation(summary = "Metodo de pruaba para comprobar que tiene coneccion con Eureka")
    @GetMapping("holaEureka")
    public String holaEureka(){
        System.out.println("### " + logis.getNameString());
        return logis.getNameString();
    }
    
    
    private final Logger logs = LoggerFactory.getLogger(ProductRestController.class);
    
    
    
    @Autowired
    private ProductRepository proRepository;
    
    
    
    @Operation(summary = "Lista todo los todos los productos")
    @GetMapping()
    public List<Product> finAll() {
        return proRepository.findAll();
    }
    
    
    
    @Operation(summary = "Lista los productos buscando por su Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductId(@PathVariable("id") Long id) {
        Optional<Product> optioanalProduct = proRepository.findById(id);
        
        if(optioanalProduct.isPresent()){
             
            ProductDto dto = productMapper.productToproductdto(optioanalProduct.get());
            return new ResponseEntity<>(optioanalProduct.get(),HttpStatus.OK);
        }else{
            logs.error("Cliente no encontrado:  {} ",id, optioanalProduct.get());
            return   ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clientes no encontrados");
        }
    }
    
    
    @Operation(summary = "Actualiza los los productos , buscando por su id")
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Product input) {
        
        Optional<Product> optionalProduct = proRepository.findById(id);
        
        if(optionalProduct.isPresent()){
            Product newProduct = optionalProduct.get();
            
            newProduct.setCode(input.getCode());
            newProduct.setName(input.getName());
            
            Product save = proRepository.save(newProduct);
            logs.debug("Actualizando... ID  {}",id, save);
            return new ResponseEntity<>(save,HttpStatus.OK);
        }else{
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado en la base de datos");
        }
    }
    
    @Operation(summary = "Guarda todos los productos tiene campos obligatorios como los son code y name")
    @PostMapping
    public ResponseEntity<?> post(@Valid  @RequestBody ProductDto inputdto) {
        
        Product product = productMapper.productDtoToProduct(inputdto);
        product.setId(null);
        Product saveProduct = proRepository.save(product);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(saveProduct);
    
       
    }
    
    @Operation(summary = "Elimina los productos buscando por su id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Product> optionalProduct = proRepository.findById(id);
                
        
        if(optionalProduct.isPresent()){
            proRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Cliente eliminado exitosamente");
        }else{
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente no se elimino ya que el id no existe");
        }
    }
    
    
    
    
}
