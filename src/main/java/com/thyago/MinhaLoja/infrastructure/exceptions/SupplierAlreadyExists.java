package com.thyago.MinhaLoja.infrastructure.exceptions;

public class SupplierAlreadyExists extends RuntimeException {
    public SupplierAlreadyExists(String message) {
        super(message);
    }
}
