package com.thyago.MinhaLoja.infrastructure.exceptions;

public class NotFound extends RuntimeException {
    public NotFound(String message) {
        super(message);
    }
}
