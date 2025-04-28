package com.thyago.MinhaLoja.business.repositories;

import com.thyago.MinhaLoja.business.models.SupplierModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuppliersRepository extends JpaRepository<SupplierModel, Long> {
}
