package com.thyago.MinhaLoja.infrastructure;

import com.thyago.MinhaLoja.infrastructure.exceptions.AlreadyExists;
import com.thyago.MinhaLoja.infrastructure.exceptions.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(AlreadyExists.class)
    public ResponseEntity<String> handlerAlreadyExists(AlreadyExists e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<String> handlerNotFound(NotFound e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
