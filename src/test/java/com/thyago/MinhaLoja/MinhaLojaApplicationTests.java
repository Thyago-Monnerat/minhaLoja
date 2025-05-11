package com.thyago.MinhaLoja;

import com.thyago.MinhaLoja.business.mappers.ProductMapper;
import com.thyago.MinhaLoja.business.models.ProductModel;
import com.thyago.MinhaLoja.business.repositories.ProductRepository;
import com.thyago.MinhaLoja.business.services.ProductService;
import com.thyago.MinhaLoja.controller.dtos.ProductDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MinhaLojaApplicationTests {

    @Mock
    private ProductRepository productsRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldGetAProductGivenItsSKU() {
        ProductModel product = new ProductModel();
        product.setSku("1234");
        product.setName("product");

        when(productsRepository.findBySku("1234")).thenReturn(Optional.of(product));

        Optional<ProductModel> result = productsRepository.findBySku("1234");

        assertTrue(result.isPresent());
        assertEquals("1234", result.get().getSku());
        assertEquals("product", result.get().getName());
    }

    @Test
    void shouldGetAllProductsFromDatabase() {
        ProductModel p1 = new ProductModel();
        p1.setSku("SKU123");
        p1.setName("test1");

        ProductModel p2 = new ProductModel();
        p2.setSku("SKU345");
        p2.setName("test2");

        when(productsRepository.findAll()).thenReturn(List.of(p1, p2));
        when(productMapper.toDto(any())).thenAnswer(inv -> {
            ProductModel m = inv.getArgument(0);
            return new ProductDto(m.getName(), m.getSku(), null, null, m.getQuantity(), LocalDate.now(), 1L);
        });

        List<ProductDto> result = productService.getProducts();

        assertNotNull(result);
        assertThat(result).hasSize(2);
    }

    @Test
    void shouldUpdateAProductFromDatabaseAndSaveIt() {
        ProductModel existingProduct = new ProductModel();
        existingProduct.setSku("SKU");
        existingProduct.setName("Old Name");
        existingProduct.setQuantity(2);

        ProductDto dto = new ProductDto(
                "New Name",
                "SKU",
                "Category",
                null,
                5,
                LocalDate.now(),
                1L
        );

        when(productsRepository.findBySku("SKU")).thenReturn(Optional.of(existingProduct));

        doAnswer(invocation -> {
            ProductDto source = invocation.getArgument(0);
            ProductModel target = invocation.getArgument(1);
            target.setName(source.name());
            target.setQuantity(source.quantity());
            return null;
        }).when(productMapper).updateModelFromDto(eq(dto), eq(existingProduct));

        productService.updateProduct(dto);

        assertEquals("New Name", existingProduct.getName());
        assertEquals(5, existingProduct.getQuantity());
        verify(productsRepository).save(existingProduct);
    }

    @Test
    void shouldDeleteAProductByItsSku() {
        ProductModel existingProduct = new ProductModel();
        existingProduct.setSku("SKU");
        existingProduct.setName("Old Name");
        existingProduct.setQuantity(2);

        ProductDto dto = new ProductDto(
                "New Name",
                "SKU",
                "Category",
                null,
                5,
                LocalDate.now(),
                1L
        );

        when(productsRepository.findBySku("SKU")).thenReturn(Optional.of(existingProduct));

        productService.deleteProductBySku("SKU");

        verify(productsRepository).findBySku("SKU");
        verify(productsRepository).deleteBySku("SKU");
    }
}
