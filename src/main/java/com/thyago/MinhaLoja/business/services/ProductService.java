package com.thyago.MinhaLoja.business.services;

import com.thyago.MinhaLoja.business.mappers.ProductMapper;
import com.thyago.MinhaLoja.business.models.ProductModel;
import com.thyago.MinhaLoja.business.repositories.ProductRepository;
import com.thyago.MinhaLoja.controller.dtos.ProductDto;
import com.thyago.MinhaLoja.infrastructure.exceptions.ProductAlreadyExists;
import com.thyago.MinhaLoja.infrastructure.exceptions.ProductNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productsRepository;
    private final ProductMapper productMapper;

    private ProductModel findProductBySku(String sku) {
        return productsRepository.findBySku(sku).orElseThrow(() -> new ProductNotFound("Product not found"));
    }

    public List<ProductDto> getProducts() {
        return productsRepository.findAll().stream().map(productMapper::toDto).toList();
    }

    public void addProduct(ProductDto productDto) {
        if (productsRepository.findBySku(productDto.sku()).isPresent()) {
            throw new ProductAlreadyExists("Product already exists");
        }
        productsRepository.save(productMapper.toModel(productDto));
    }

    public void updateProduct(ProductDto productDto) {
        ProductModel productModel = findProductBySku(productDto.sku());

        productMapper.updateModelFromDto(productDto, productModel);

        productsRepository.save(productModel);
    }

    public void deleteProductBySku(String sku) {
        ProductModel productModel = findProductBySku(sku);

        productsRepository.deleteBySku(productModel.getSku());
    }

}
