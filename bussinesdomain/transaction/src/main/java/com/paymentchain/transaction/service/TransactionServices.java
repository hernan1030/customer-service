/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.transaction.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 *
 * @author herna
 */
@Service
public class TransactionServices {
    
    //genera numero aleatorios no repetidos y devolveria algo aso TX-12345678
    public String generateTransactionReference(){
        return "TX-" + UUID.randomUUID().toString().substring(0,8);    }
    
    
    
    //Calcular comision diferente a 0
    public BigDecimal calcularComision(BigDecimal fee, BigDecimal amount){
        
        if(fee.compareTo(BigDecimal.ZERO)>0){//verifica si el fee es mayor que 0
            amount = amount.subtract(fee);// si lo es amount sera algo asi ej: 100-5=95
            
        }else{
            throw new IllegalArgumentException("La comision no puede ser 0");
        }
        
        return amount.setScale(2,RoundingMode.HALF_UP);//redondea a 2 decimales
    }
    
    
    public String typeTransaction(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) < 0 ){
            return "Debito";
        }else if(amount.compareTo(BigDecimal.ZERO) > 0){
            return "Credito";
        }else{
            return "Sin saldo";
        }
    }
    
    
    public String descriptionTransaction(BigDecimal fee, BigDecimal amount,String type, LocalDateTime date){
        
       return "Valor de la comisión: " + fee + "\n" +
           "Nuevo monto: " + amount + "\n" +
           "Tipo de transacción: " + type + "\n" +
           "Fecha de la transacción: " + date;

               
        
        
    }
}
