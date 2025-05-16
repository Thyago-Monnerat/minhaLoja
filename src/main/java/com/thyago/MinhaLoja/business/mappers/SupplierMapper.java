package com.thyago.MinhaLoja.business.mappers;

import com.thyago.MinhaLoja.business.models.SupplierModel;
import com.thyago.MinhaLoja.controller.dtos.supplierDtos.SupplierAddDto;
import com.thyago.MinhaLoja.controller.dtos.supplierDtos.SupplierDto;
import com.thyago.MinhaLoja.controller.dtos.supplierDtos.SupplierUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        uses = ProductMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SupplierMapper {
    SupplierDto toDto(SupplierModel supplierModel);

    SupplierModel fromAddDtoToModel(SupplierAddDto supplierAddDto);

    void updateModelFromDto(SupplierUpdateDto supplierUpdateDto, @MappingTarget SupplierModel supplierModel);
}
