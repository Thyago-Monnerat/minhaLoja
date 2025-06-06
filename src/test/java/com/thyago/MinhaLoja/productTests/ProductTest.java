package com.thyago.MinhaLoja.productTests;

import com.thyago.MinhaLoja.business.mappers.ProductMapper;
import com.thyago.MinhaLoja.business.models.ProductModel;
import com.thyago.MinhaLoja.business.models.SupplierModel;
import com.thyago.MinhaLoja.business.repositories.ProductRepository;
import com.thyago.MinhaLoja.business.services.ProductService;
import com.thyago.MinhaLoja.business.services.SupplierService;
import com.thyago.MinhaLoja.controller.dtos.productDtos.ProductAddDto;
import com.thyago.MinhaLoja.controller.dtos.productDtos.ProductDto;
import com.thyago.MinhaLoja.controller.dtos.productDtos.ProductUpdateDto;
import com.thyago.MinhaLoja.infrastructure.exceptions.AlreadyExists;
import com.thyago.MinhaLoja.infrastructure.exceptions.NotFound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;
    @Mock
    ProductMapper productMapper;
    @Mock
    SupplierService supplierService;

    ProductModel productModel;
    ProductAddDto productAddDto;
    ProductUpdateDto productUpdateDto;
    ProductDto productDto;

    @BeforeEach
    void setup() {
        productModel = new ProductModel();
        productModel.setId(1L);
        productModel.setSku("SKU");

        productAddDto = new ProductAddDto(
                "SKU",
                "SKU",
                "",
                BigDecimal.ZERO,
                3,
                LocalDate.now(),
                1L
        );

        productUpdateDto = new ProductUpdateDto(
                "NAME",
                "SKU123",
                "",
                BigDecimal.ZERO,
                3,
                LocalDate.now(),
                1L
        );

        productDto = new ProductDto(
                1L,
                "NAME",
                "SKU",
                "",
                BigDecimal.ZERO,
                3,
                LocalDate.now(),
                1L);
    }

    @Test
    @DisplayName("Should return a list with registered products")
    void shouldReturnAllProduct() {
        when(productRepository.findAll()).thenReturn(List.of(productModel));
        when(productMapper.toDto(productModel)).thenReturn(productDto);

        List<ProductDto> result = productService.getProducts();

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("SKU", result.get(0).sku());
        verify(productRepository).findAll();
        verify(productMapper).toDto(productModel);
    }

    @Test
    @DisplayName("Should add a product")
    void shouldAddAProduct() {
        SupplierModel supplierModel = new SupplierModel();

        when(productMapper.fromAddDtoToModel(productAddDto)).thenReturn(productModel);

        doAnswer(invocation -> supplierModel)
                .when(supplierService).findSupplierById(1L);

        productService.addProduct(productAddDto);

        verify(supplierService).findSupplierById(1L);
        verify(productRepository).save(productModel);
    }

    @Test
    @DisplayName("Should throw a not found exception if the product supplier is not found by id")
    void shouldThrowNotFoundWhenAddProductWithoutSupplier() {
        doThrow(new NotFound("Supplier not found"))
                .when(supplierService).findSupplierById(1L);

        Assertions.assertThrows(NotFound.class, () ->
                productService.addProduct(productAddDto)
        );

        verify(supplierService).findSupplierById(1L);
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw a already exists exception when trying to add a product with duplicate sku")
    void shouldThrowAlreadyExistsWhenAddDupeProduct() {
        when(productRepository.findBySku(productAddDto.sku()))
                .thenReturn(Optional.of(new ProductModel()));

        Assertions.assertThrows(AlreadyExists.class, () ->
                productService.addProduct(productAddDto)
        );

        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update a existent product")
    void shouldUpdateAProduct() {
        ProductModel existingProduct = new ProductModel();
        existingProduct.setSku("SKU");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(existingProduct));

        doAnswer(invocation -> {
            ProductUpdateDto dto = invocation.getArgument(0);
            ProductModel model = invocation.getArgument(1);
            model.setSku(dto.sku());
            return null;
        }).when(productMapper).updateModelFromDto(any(), any());

        productService.updateProduct(1L, productUpdateDto);

        Assertions.assertEquals("SKU123", existingProduct.getSku());
    }

    @Test
    @DisplayName("Should throw not found exception when trying to update a product with a non existent id")
    void shouldThrowNotFoundWhenUpdateANonExistentProduct() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());


        Assertions.assertThrows(NotFound.class, () ->
                productService.updateProduct(1L, productUpdateDto)
        );

        verify(productRepository).findById(1L);
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw already exists exception when trying to update a product with duplicate name")
    void shouldThrowAlreadyExistsWhenUpdatingProductWithExistingName() {
        when(productRepository.findByName("NAME"))
                .thenReturn(Optional.of(productModel));
        when(productRepository.findById(2L))
                .thenReturn(Optional.of(productModel));


        Assertions.assertThrows(AlreadyExists.class, () ->
                productService.updateProduct(2L, productUpdateDto)
        );

        verify(productRepository).findByName("NAME");
        verify(productRepository, never()).save(any());
    }


    @Test
    @DisplayName("Should delete a existent product")
    void shouldDeleteAProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(productModel));

        productService.deleteProductById(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw a not found exception when trying to delete a non existent product")
    void shouldThrowNotFoundWhenDeleteAProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFound.class, () ->
                productService.deleteProductById(1L)
        );

        verify(productRepository, never()).deleteById(1L);
    }
}
