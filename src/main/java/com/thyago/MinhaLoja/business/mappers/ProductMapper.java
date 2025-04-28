package com.thyago.MinhaLoja.business.mappers;

import com.thyago.MinhaLoja.business.models.ProductModel;
import com.thyago.MinhaLoja.controller.dtos.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    ProductDto toDto(ProductModel productModel);

    ProductModel toModel(ProductDto productDto);
}
