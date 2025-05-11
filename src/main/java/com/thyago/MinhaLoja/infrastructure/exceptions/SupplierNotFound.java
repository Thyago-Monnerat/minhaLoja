package com.thyago.MinhaLoja.infrastructure.exceptions;

public class SupplierNotFound extends RuntimeException {
    public SupplierNotFound(String message) {
        super(message);
    }
}
