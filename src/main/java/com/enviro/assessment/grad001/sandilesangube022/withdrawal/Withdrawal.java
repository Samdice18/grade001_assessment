package com.enviro.assessment.grad001.sandilesangube022.withdrawal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private BigDecimal withdrawalAmount;
    private String bankName;
    private String accountNumber;
    private LocalDate date;

    public Long getId(){
        return id;
    }

    public Long getProductId(){
        return productId;
    }

    public BigDecimal getWithdrawalAmount(){
        return withdrawalAmount;
    }

    public String getBankName(){
        return bankName;
    }

    public String getAccountNumber(){
        return accountNumber;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }
}
