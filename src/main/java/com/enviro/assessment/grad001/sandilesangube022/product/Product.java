package com.enviro.assessment.grad001.sandilesangube022.product;

import com.enviro.assessment.grad001.sandilesangube022.investor.Investor;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private BigDecimal balance;
    private Long investorId;

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public BigDecimal getBalance(){
        return balance;
    }

    public Long getInvestorId(){
        return investorId;
    }

    public void changeBalance(BigDecimal balance){
        this.balance = balance;
    }

}
