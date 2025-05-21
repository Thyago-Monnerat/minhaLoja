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

    ProductAddDto productAddDto;

    ProductUpdateDto productUpdateDto;

    @BeforeEach
    void setup() {
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
                "SKU",
                "SKU123",
                "",
                BigDecimal.ZERO,
                3,
                LocalDate.now(),
                1L
        );
    }

    @Test
    void shouldReturnsAllProduct() {
        ProductModel model = new ProductModel();
        model.setId(1L);
        model.setSku("SKU");

        ProductDto dto = new ProductDto(1L, "SKU", "SKU","", BigDecimal.ZERO, 3, LocalDate.now(), 1L);

        when(productRepository.findAll()).thenReturn(List.of(model));
        when(productMapper.toDto(model)).thenReturn(dto);

        List<ProductDto> result = productService.getProducts();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("SKU", result.get(0).sku());
        verify(productRepository).findAll();
        verify(productMapper).toDto(model);
    }

    @Test
    void shouldAddAProduct() {
        SupplierModel supplierModel = new SupplierModel();
        when(supplierService.findSupplierById(1L)).thenReturn(supplierModel);

        ProductModel productModel = new ProductModel();
        when(productMapper.fromAddDtoToModel(productAddDto)).thenReturn(productModel);

        productService.addProduct(productAddDto);

        verify(productRepository).save(productModel);
    }

    @Test
    void shouldThrowNotFoundWhenAddProductWithoutSupplier() {
        when(supplierService.findSupplierById(1L)).thenThrow(new NotFound("Supplier not found"));

        Assertions.assertThrows(NotFound.class, () -> {
            productService.addProduct(productAddDto);
        });

        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldThrowAlreadyExistsWhenAddDupeProduct() {
        when(productRepository.findBySku(productAddDto.sku()))
                .thenReturn(Optional.of(new ProductModel()));

        Assertions.assertThrows(AlreadyExists.class, () -> {
            productService.addProduct(productAddDto);
        });

        verify(productRepository, never()).save(any());
    }

    @Test
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
    void shouldThrowNotFoundWhenUpdateANonExistentProduct() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());


        Assertions.assertThrows(NotFound.class, () -> {
            productService.updateProduct(1L, productUpdateDto);
        });

        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldDeleteAProduct() {
        ProductModel p = new ProductModel();
        p.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(p));

        productService.deleteProductById(p.getId());

        verify(productRepository).deleteById(p.getId());
    }

    @Test
    void shouldThrowNotFoundWhenDeleteAProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFound.class, () -> {
            productService.deleteProductById(1L);
        });

        verify(productRepository, never()).deleteById(any());
    }
}
