package com.thyago.MinhaLoja.controller.dtos.supplierDtos;

import com.thyago.MinhaLoja.controller.dtos.productDtos.ProductDto;

import java.util.List;

public record SupplierDto (
        Long id,
        String name,
        String contact,
        List<ProductDto> products
){
}
