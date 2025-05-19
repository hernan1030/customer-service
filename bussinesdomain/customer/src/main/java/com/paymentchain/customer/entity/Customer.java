/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.customer.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.util.List;
import lombok.Data;

/**
 *
 * @author herna
 */
@Entity
@Data
public class Customer {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    private Long code;
    private String accountNumber;
    private String names;
    private String surname;
    private  String phone;
    private String address;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "customer",cascade = CascadeType.ALL, orphanRemoval = true)
    List<CustomerProduct> product;
    
    @Transient
    private List<?> transaction;
}
