package com.thyago.MinhaLoja.controller.dtos;

import java.util.Set;

public record SupplierDto (
        Long id,
        String name,
        String contact,
        Set<ProductDto> products
){
}
