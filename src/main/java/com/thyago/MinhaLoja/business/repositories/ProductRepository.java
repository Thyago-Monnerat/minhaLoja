package com.thyago.MinhaLoja.business.repositories;

import com.thyago.MinhaLoja.business.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    Optional<ProductModel> findBySku(String sku);
    void deleteBySku(String sku);
}
