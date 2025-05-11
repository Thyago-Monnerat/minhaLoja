package com.thyago.MinhaLoja.business.services;

import com.thyago.MinhaLoja.business.mappers.SupplierMapper;
import com.thyago.MinhaLoja.business.models.SupplierModel;
import com.thyago.MinhaLoja.business.repositories.SuppliersRepository;
import com.thyago.MinhaLoja.controller.dtos.SupplierDto;
import com.thyago.MinhaLoja.infrastructure.exceptions.SupplierAlreadyExists;
import com.thyago.MinhaLoja.infrastructure.exceptions.SupplierNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SuppliersRepository suppliersRepository;
    private final SupplierMapper supplierMapper;

    private SupplierModel findSupplierById(Long id) {
        return suppliersRepository.findById(id).orElseThrow(() -> new SupplierNotFound("Supplier not found"));
    }

    public List<SupplierDto> getSuppliers() {
        return suppliersRepository.findAll().stream().map(supplierMapper::toDto).toList();
    }

    public void addSupplier(SupplierDto supplierDto) {
        if (suppliersRepository.findById(supplierDto.id()).isPresent()) {
            throw new SupplierAlreadyExists("Supplier already exists");
        }
        suppliersRepository.save(supplierMapper.toModel(supplierDto));
    }

    public void updateSupplier(SupplierDto supplierDto) {
        SupplierModel supplierModel = findSupplierById(supplierDto.id());

        supplierMapper.updateModelFromDto(supplierDto, supplierModel);

        suppliersRepository.save(supplierModel);
    }

    public void deleteSupplier(SupplierDto supplierDto) {
        SupplierModel supplierModel = findSupplierById(supplierDto.id());

        suppliersRepository.deleteById(supplierModel.getId());
    }
}

