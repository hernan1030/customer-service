/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.paymentchain.customer.controller;

import com.paymentchain.customer.entity.Customer;
import com.paymentchain.customer.repository.CustomerRepository;
import com.paymentchain.customer.services.ProductServiceClient;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

//Capturar logs en mi servidor
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author herna
 */
@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    //con la variable log creo logs que puedo ver en mi servidor.
    private final Logger log = LoggerFactory.getLogger(CustomerRestController.class);
    
    
    private final ProductServiceClient productServiceClient;
    private final  CustomerRepository customerrepository;
    
    @Autowired
    public CustomerRestController(ProductServiceClient productServiceClient, CustomerRepository customerrepository) {
        this.productServiceClient = productServiceClient;
        this.customerrepository = customerrepository;
    }

    
    
    
    
    //Trae todos los clientes desde la base de datos
    @GetMapping()
    public List<Customer> findAll() {
        return customerrepository.findAll();
    }

    //trae clientes  y los busca por su id que tienen en customer
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerId(@PathVariable("id") Long id) {
        
        log.debug("GET Buscando... id de cliente: {}  ", id);
        Optional<Customer> optionalCustomer = customerrepository.findById(id);
        
        if (optionalCustomer.isPresent()) {
            
            log.debug("Cliente encontrado con ID: {} ", id, optionalCustomer.get());
            return new ResponseEntity<>(optionalCustomer.get(), HttpStatus.OK);
            
        } else {
            log.warn("Cliente con ID {} no encontrado ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cliente no contrado.....");
        }
        
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Customer input) {
        
        log.debug("PUT Buscando... id de cliente: {}  ", id);
        Optional<Customer> optionalCustomer = customerrepository.findById(id);
        
        if (optionalCustomer.isPresent()) {
            Customer newCustomer = optionalCustomer.get();
            
            newCustomer.setCode(input.getCode());
            newCustomer.setAccountNumber(input.getAccountNumber());
            newCustomer.setNames(input.getNames());
            newCustomer.setSurname(input.getSurname());
            newCustomer.setPhone(input.getPhone());
            newCustomer.setAddress(input.getAddress());
            
            Customer save = customerrepository.save(newCustomer);
            log.debug("Nueva informacion a guardar: {}", save);
            return new ResponseEntity<>(save, HttpStatus.OK);
            
        } else {
            log.warn("Cliente con ID {} no encontrado ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No fue posible la actualizacion de lo clientes ");
        }
        
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Customer input) {

        //aseguramos que hibernate no genere el id el mismo y me de un error como lo es javax.persistence.EntityExitsException
        input.setId(null);
        
        input.getProduct().forEach(product -> {
            product.setId(null);
            product.setCustomer(input);
        });
        
        Customer save = customerrepository.save(input);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        
        Optional<Customer> optionalCustomer = customerrepository.findById(id);
        
        if (optionalCustomer.isPresent()) {
            customerrepository.deleteById(id);
            log.debug("Eliminando..... cliente con ID {} ", id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Cliente eliminado correctamente ");
        } else {
            log.warn("Cliente con ID {} no encontrado ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no Eliminado");
        }
        
    }
    
    @GetMapping("/full")
    public ResponseEntity<?> getByCode(@RequestParam("code") Long code) {
        
        try {
            log.debug("Code extraido de customer: {}  ", code);
            Customer customer = customerrepository.findByCode(code)
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

            //procesar producto
            if (customer.getProduct() != null) {
                customer.getProduct().forEach(product -> {
                    try {
                        product.setProductName(productServiceClient.getProductName(product.getProductId()));
                        System.out.println("######"+ product.getProductName());
                    } catch (Exception e) {
                        log.warn("Error obteniendo nombre para producto {}: {}",product.getProductId());
                        product.setProductName("Nombre de producto no disponible");
                    }
                });
                
                
            }
            
            
            try {
                List<Object> transaction = productServiceClient.getTransactionIban(customer.getAccountNumber());
                if(transaction != null && !transaction.isEmpty()){
                    customer.setTransaction(transaction);
                }
            } catch (Exception e) {
                log.warn("Error al encontrar las transacciones: {}", customer.getAccountNumber() , e.getMessage());
                //continuara con las transacciones lo dejara vacio
                
            }
            
            return ResponseEntity.ok(customer);
            
        } catch (Exception e) {
            throw e;
            
        }
        
    }
    
}
