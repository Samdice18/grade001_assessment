package com.enviro.assessment.grad001.sandilesangube022;

import com.enviro.assessment.grad001.sandilesangube022.investor.Investor;
import com.enviro.assessment.grad001.sandilesangube022.investor.InvestorsData;
import com.enviro.assessment.grad001.sandilesangube022.product.Product;
import com.enviro.assessment.grad001.sandilesangube022.product.ProductsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/investors")
public class InvestorsHandler {
    @Autowired
    private InvestorsData investorsData;

    @Autowired
    private ProductsData productsData;

    @GetMapping
    public List<Investor> getAllInvestors(){
        return investorsData.findAll();
    }

    @GetMapping("/{investorId}/products")
    public ResponseEntity<List<Product>> getInvestorProducts(@PathVariable Long investorId){
        Optional<Investor> investor = investorsData.findById(investorId);
        if (investor.isPresent()){
            List<Product> investorProducts = productsData.findByInvestorId(investorId);
            return ResponseEntity.ok(investorProducts);
        }else {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> addInvestor (@RequestBody Investor investor){
        /*
        how to post investor:
        curl -X POST -H "Content-Type: application/json" -d '{"name": "NameOfInvestor", "address": "AddressOfInvestor",
         "contact": "ContactOfInvestor", "age":age }' http://localhost:8080/investors
         */
        investorsData.save(investor);
        return ResponseEntity.ok("Investor added successfully\n");
    }
}

