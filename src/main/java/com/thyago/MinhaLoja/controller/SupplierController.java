package com.thyago.MinhaLoja.controller;

import com.thyago.MinhaLoja.business.services.SupplierService;
import com.thyago.MinhaLoja.controller.dtos.supplierDtos.SupplierAddDto;
import com.thyago.MinhaLoja.controller.dtos.supplierDtos.SupplierDto;
import com.thyago.MinhaLoja.controller.dtos.supplierDtos.SupplierUpdateDto;
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
@RequestMapping("supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    @Operation(summary = "Return all suppliers", description = "Returns a list of all registered suppliers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All registered suppliers"),
    })
    public ResponseEntity<List<SupplierDto>> getSuppliers() {
        return ResponseEntity.ok(supplierService.getSuppliers());
    }

    @PostMapping
    @Operation(summary = "Register a supplier", description = "Registers a new supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A supplier was registered successfully"),
            @ApiResponse(responseCode = "400", description = "The supplier already exists"),
    })
    public ResponseEntity<Void> addSupplier(@RequestBody @Valid SupplierAddDto supplierAddDto) {
        supplierService.addSupplier(supplierAddDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("{id}")
    @Operation(summary = "Update a supplier", description = "Updates a registered supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A supplier was updated successfully"),
            @ApiResponse(responseCode = "400", description = "The supplier was not found or data is invalid"),
    })
    public ResponseEntity<Void> updateSupplier(@PathVariable Long id, @RequestBody @Valid SupplierUpdateDto supplierUpdateDto) {
        supplierService.updateSupplier(id, supplierUpdateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a supplier", description = "Deletes a registered supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "A supplier was deleted successfully"),
            @ApiResponse(responseCode = "400", description = "The supplier id was not found"),
    })
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}
