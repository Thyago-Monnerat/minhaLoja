package com.thyago.MinhaLoja.business.services;

import com.thyago.MinhaLoja.business.mappers.SupplierMapper;
import com.thyago.MinhaLoja.business.models.SupplierModel;
import com.thyago.MinhaLoja.business.repositories.SupplierRepository;
import com.thyago.MinhaLoja.controller.dtos.supplierDtos.SupplierAddDto;
import com.thyago.MinhaLoja.controller.dtos.supplierDtos.SupplierDto;
import com.thyago.MinhaLoja.controller.dtos.supplierDtos.SupplierUpdateDto;
import com.thyago.MinhaLoja.infrastructure.exceptions.AlreadyExists;
import com.thyago.MinhaLoja.infrastructure.exceptions.NotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    protected void findSupplierById(Long id) {
        supplierRepository.findById(id).orElseThrow(() -> new NotFound("Supplier not found"));
    }

    @Transactional(readOnly = true)
    public List<SupplierDto> getSuppliers() {
        return supplierRepository.findAll().stream().map(supplierMapper::toDto).toList();
    }

    @Transactional
    public void addSupplier(SupplierAddDto supplierAddDto) {
        if (supplierRepository.findByName(supplierAddDto.name()).isPresent()) {
            throw new AlreadyExists("Supplier already exists");
        }

        supplierRepository.save(supplierMapper.fromAddDtoToModel(supplierAddDto));
    }

    @Transactional
    public void updateSupplier(Long id, SupplierUpdateDto supplierUpdateDto) {
        SupplierModel supplierModel = supplierRepository.findById((id)).orElseThrow(()-> new NotFound("Supplier not found"));

        supplierMapper.updateModelFromDto(supplierUpdateDto, supplierModel);

        supplierRepository.save(supplierModel);
    }

    @Transactional
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}

