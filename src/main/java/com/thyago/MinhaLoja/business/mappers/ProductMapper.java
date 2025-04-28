package com.thyago.MinhaLoja.business.mappers;

import com.thyago.MinhaLoja.business.models.ProductModel;
import com.thyago.MinhaLoja.controller.dtos.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(ProductModel productModel) {
        return new ProductDto(
                productModel.getName(),
                productModel.getSku(),
                productModel.getDescription(),
                productModel.getPrice(),
                productModel.getQuantity(),
                productModel.getValidity()
        );

    }
}
