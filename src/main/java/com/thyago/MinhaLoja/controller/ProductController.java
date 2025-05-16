package com.thyago.MinhaLoja.controller;

import com.thyago.MinhaLoja.business.services.ProductService;
import com.thyago.MinhaLoja.controller.dtos.productDtos.ProductAddDto;
import com.thyago.MinhaLoja.controller.dtos.productDtos.ProductDto;
import com.thyago.MinhaLoja.controller.dtos.productDtos.ProductUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Return all products", description = "Returns a list of all registered products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All registered products"),
    })
    public ResponseEntity<List<ProductDto>> getProducts() {

        return ResponseEntity.ok(productService.getProducts());
    }

    @Operation(summary = "Register a product", description = "Registers a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A product was registered successfully"),
            @ApiResponse(responseCode = "400", description = "The SKU is already registered"),
    })
    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody @Valid ProductAddDto productAddDto) {
        productService.addProduct(productAddDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update a product", description = "Updates a registered product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A product was updated successfully"),
            @ApiResponse(responseCode = "400", description = "The product SKU is already registered or the supplier id was not found"),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductUpdateDto productUpdateDto) {
        productService.updateProduct(id, productUpdateDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a product", description = "Deletes a registered product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "A product was deleted successfully"),
            @ApiResponse(responseCode = "400", description = "The product id was not found"),
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
