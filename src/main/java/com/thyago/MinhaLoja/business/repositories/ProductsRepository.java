package com.thyago.MinhaLoja.business.repositories;

import com.thyago.MinhaLoja.business.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<ProductModel, Long> {
}
