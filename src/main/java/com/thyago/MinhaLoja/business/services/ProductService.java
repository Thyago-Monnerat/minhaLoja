package com.thyago.MinhaLoja.business.services;

import com.thyago.MinhaLoja.business.mappers.ProductMapper;
import com.thyago.MinhaLoja.business.models.ProductModel;
import com.thyago.MinhaLoja.business.repositories.ProductRepository;
import com.thyago.MinhaLoja.controller.dtos.productDtos.ProductAddDto;
import com.thyago.MinhaLoja.controller.dtos.productDtos.ProductDto;
import com.thyago.MinhaLoja.controller.dtos.productDtos.ProductUpdateDto;
import com.thyago.MinhaLoja.infrastructure.exceptions.AlreadyExists;
import com.thyago.MinhaLoja.infrastructure.exceptions.NotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productsRepository;
    private final ProductMapper productMapper;
    private final SupplierService supplierService;

    @Transactional(readOnly = true)
    public List<ProductDto> getProducts() {
        return productsRepository.findAll().stream().map(productMapper::toDto).toList();
    }

    @Transactional
    public void addProduct(ProductAddDto productAddDto) {
        if(productsRepository.findBySku(productAddDto.sku()).isPresent()){
            throw new AlreadyExists("Product already exists");
        }

        supplierService.findSupplierById(productAddDto.supplierId());

        productsRepository.save(productMapper.fromAddDtoToModel(productAddDto));
    }

    @Transactional
    public void updateProduct(Long id, ProductUpdateDto productUpdateDto) {
        ProductModel productModel = productsRepository.findById((id)).orElseThrow(()-> new NotFound("Product not found"));

        productsRepository.findByName(productUpdateDto.name()).ifPresent(actualProduct ->{
            if(!actualProduct.getId().equals(id)){
                throw new AlreadyExists("Can't update, product name already exists");
            }
        });

        productMapper.updateModelFromDto(productUpdateDto, productModel);
    }

    @Transactional
    public void deleteProductById(Long id) {
        productsRepository.findById(id).orElseThrow(()->new NotFound("Product not found"));

        productsRepository.deleteById(id);
    }

}
