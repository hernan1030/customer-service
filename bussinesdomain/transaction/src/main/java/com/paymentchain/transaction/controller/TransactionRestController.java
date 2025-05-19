/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.paymentchain.transaction.controller;

import com.paymentchain.transaction.entity.Transaction;
import com.paymentchain.transaction.repository.TransactionRepository;
import com.paymentchain.transaction.service.TransactionServices;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author herna
 */
@RestController
@RequestMapping("/transaction")
public class TransactionRestController {
    
    
    private final TransactionRepository tranRepository;
    private final TransactionServices tranServices;

    @Autowired
    public TransactionRestController(TransactionRepository tranRepository, TransactionServices tranServices) {
        this.tranRepository = tranRepository;
        this.tranServices = tranServices;
    }
    
    
    
    @GetMapping()
    public List<Transaction> getAll() {
        return tranRepository.findAll();
    }
    
    
    
    @GetMapping("customer/transaction")
    public List<Transaction> getTransactionIban(@RequestParam("accontIban") String accontIban ){
        
        return tranRepository.findByAcountIban(accontIban);
    }
    
    
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionId(@PathVariable("id") Long id) {
        Optional<Transaction> optioanlTransaction = tranRepository.findById(id);
        
        if(optioanlTransaction.isPresent()){
            return new ResponseEntity<>(optioanlTransaction.get(), HttpStatus.OK);
                  
        }else{
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> putTransactionId(@PathVariable("id") Long id, @RequestBody Transaction input) {
        Optional<Transaction> optionalTransaction = tranRepository.findById(id);
        
        if(optionalTransaction.isPresent()){
            Transaction newTransaction = optionalTransaction.get();
            
            
            
            newTransaction.setReference(tranServices.generateTransactionReference());
            newTransaction.setAccontIban(input.getAccontIban());
            newTransaction.setTransactionDate(input.getTransactionDate());
            
            newTransaction.setAmount(input.getAmount());
            newTransaction.setFee(input.getFee());
            newTransaction.setDescription(input.getDescription());
            newTransaction.setStatus(input.getStatus());
            newTransaction.setChannel(input.getChannel());
            
            String typeTransaction = tranServices.typeTransaction(input.getAmount());
            BigDecimal totalComision= tranServices.calcularComision(input.getFee(), input.getAmount());
            
            newTransaction.setAmount(totalComision);
            newTransaction.setDescription(tranServices.descriptionTransaction(input.getFee(), totalComision, typeTransaction, input.getTransactionDate()));
            
            
            Transaction save = tranRepository.save(newTransaction);
            
            return new ResponseEntity<>(save, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Transaction input) {
        input.setId(null);
        
        input.setReference(tranServices.generateTransactionReference());
        
        String typeTrnsaction = tranServices.typeTransaction(input.getAmount());
        
        BigDecimal total = tranServices.calcularComision(input.getFee(), input.getAmount());
        
        input.setAmount(total);
        
        input.setDescription(tranServices.descriptionTransaction(input.getFee(), input.getAmount(), typeTrnsaction, input.getTransactionDate()));

        Transaction save = tranRepository.save(input);
        
        return ResponseEntity.ok(save);
        
        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        
        Optional<Transaction> optionalTransaction = tranRepository.findById(id);
        
        if(optionalTransaction.isPresent()){
            tranRepository.deleteById(id);
            
            return ResponseEntity.status(HttpStatus.OK).body("Cliente eliminado exitosamente");
        }else{
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro ningun cliente");
        }
    }
    
}
