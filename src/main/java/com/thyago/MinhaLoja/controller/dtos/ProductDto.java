package com.thyago.MinhaLoja.controller.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductDto(
        String name,
        String sku,
        String description,
        BigDecimal price,
        Integer quantity,
        LocalDate validity,
        Long supplierId
) {
}
