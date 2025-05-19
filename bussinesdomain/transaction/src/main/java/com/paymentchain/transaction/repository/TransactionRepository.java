/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.paymentchain.transaction.repository;

import com.paymentchain.transaction.entity.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author herna
 */
public interface TransactionRepository  extends JpaRepository<Transaction, Long>{
    
    @Query("SELECT t FROM Transaction t WHERE t.accontIban= ?1")
    public List<Transaction> findByAcountIban(String iban);
    
}
