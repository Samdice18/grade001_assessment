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
@RequestMapping("/products")
public class ProductsHandler {
    @Autowired
    private ProductsData productsData;

    @Autowired
    private InvestorsData investorsData;

    @GetMapping
    public List<Product> getAllProducts(){
        return productsData.findAll();
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product){
        Long investorId = product.getInvestorId();
        Optional<Investor> investor = investorsData.findById(investorId);

        if (investor.isPresent()){
            if (product.getType().toLowerCase().strip().equals("retirement")){
                Investor investor1 = investor.get();
                if (investor1.getAge() > 65){
                    productsData.save(product);
                    return ResponseEntity.ok("Product added successfully\n");
                }else {
                    return ResponseEntity.ok("Failed to validate product\n");
                }
            }
            productsData.save(product);
            return ResponseEntity.ok("Product added successfully\n");
        }else {
            return ResponseEntity.ok("Failed to add product\n");
        }
        /*
        How to post product:
        curl -X POST -H "Content-Type: application/json" -d '{"name": "New Product", "type": "Some Type",
         "balance": 100.0, "investorId": 1}' http://localhost:8080/products
         */

    }
}
