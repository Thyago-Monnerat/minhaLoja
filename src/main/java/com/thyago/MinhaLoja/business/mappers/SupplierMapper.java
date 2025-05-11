package com.thyago.MinhaLoja.business.mappers;

import com.thyago.MinhaLoja.business.models.SupplierModel;
import com.thyago.MinhaLoja.controller.dtos.SupplierDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SupplierMapper {
    SupplierDto toDto(SupplierModel supplierModel);

    SupplierModel toModel(SupplierDto supplierDto);

    void updateModelFromDto(SupplierDto supplierDto, @MappingTarget SupplierModel supplierModel);
}
