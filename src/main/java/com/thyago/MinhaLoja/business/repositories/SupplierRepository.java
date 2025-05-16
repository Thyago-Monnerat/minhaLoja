package com.thyago.MinhaLoja.business.repositories;

import com.thyago.MinhaLoja.business.models.SupplierModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<SupplierModel, Long> {
    Optional<SupplierModel> findByName(String name);

}
