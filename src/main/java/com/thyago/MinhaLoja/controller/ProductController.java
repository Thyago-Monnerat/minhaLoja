package com.thyago.MinhaLoja.controller;

import com.thyago.MinhaLoja.business.services.ProductService;
import com.thyago.MinhaLoja.controller.dtos.ProductDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<Void> getProducts(){
        productService.getProducts();
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<Void> addProduct(@RequestBody @Valid ProductDto productDto){
        productService.addProduct(productDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{sku}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String sku){
        productService.deleteProductBySku(sku);
        return ResponseEntity.ok().build();
    }
}
