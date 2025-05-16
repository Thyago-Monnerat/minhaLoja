package com.thyago.MinhaLoja.controller.dtos.productDtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductAddDto(
        String name,
        String sku,
        String description,
        BigDecimal price,
        Integer quantity,
        LocalDate validity,
        Long supplierId
) {
}
