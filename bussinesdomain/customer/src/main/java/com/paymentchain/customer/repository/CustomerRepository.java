/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.paymentchain.customer.repository;

import com.paymentchain.customer.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
Desde este rspositorio mepermite crear un 
* crud desde el rescontroller y tambien metodos personalizados
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
    @Query("SELECT c FROM Customer c WHERE c.code=?1")
    public Optional<Customer> findByCode(Long code);
    
}
