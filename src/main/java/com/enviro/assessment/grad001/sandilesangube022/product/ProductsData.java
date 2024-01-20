package com.enviro.assessment.grad001.sandilesangube022.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsData extends JpaRepository<Product, Long> {
    List<Product> findByInvestorId(Long investorId);
}
