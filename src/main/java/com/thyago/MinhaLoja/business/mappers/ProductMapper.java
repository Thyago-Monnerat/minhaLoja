package com.thyago.MinhaLoja.business.mappers;

import com.thyago.MinhaLoja.business.models.ProductModel;
import com.thyago.MinhaLoja.controller.dtos.productDtos.ProductAddDto;
import com.thyago.MinhaLoja.controller.dtos.productDtos.ProductDto;
import com.thyago.MinhaLoja.controller.dtos.productDtos.ProductUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    @Mapping(target = "supplierId", source = "supplier.id")
    ProductDto toDto(ProductModel productModel);

    @Mapping(target = "supplier.id", source = "supplierId")
    ProductModel fromAddDtoToModel(ProductAddDto productAddDto);

    void updateModelFromDto(ProductUpdateDto productUpdateDto, @MappingTarget ProductModel productModel);
}
