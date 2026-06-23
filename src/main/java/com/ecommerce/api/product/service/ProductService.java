package com.ecommerce.api.product.service;

import com.ecommerce.api.category.entity.Category;
import com.ecommerce.api.product.mapper.ProductMapper;
import com.ecommerce.api.product.repository.ProductRepository;
import com.ecommerce.api.product.dto.ProductCreateDTO;
import com.ecommerce.api.product.dto.ProductResponseDTO;
import com.ecommerce.api.product.dto.ProductUpdateDTO;
import com.ecommerce.api.product.entity.Product;
import com.ecommerce.api.category.repository.CategoryRepository;
import com.ecommerce.api.shared.exception.CategoryNotFoundException;
import com.ecommerce.api.shared.exception.ProductAlreadyExistsException;
import com.ecommerce.api.shared.exception.ProductNotFoundException;
import com.ecommerce.api.shared.util.NameNormalizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        List<Product> products = productRepository.findByActiveTrue();

        return products.stream().map(mapper::convertToDTO).toList();

    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(UUID id) {

        return mapper.convertToDTO(productRepository.findByIdAndActiveTrue(id).orElseThrow(() -> new ProductNotFoundException("Product not found")));
    }

    @Transactional
    public ProductResponseDTO createProduct(ProductCreateDTO dto) {

        Category category = findCategoryById(dto.categoryId());

        boolean exitsProduct = productRepository.existsByNameIgnoreCase(NameNormalizer.normalize(dto.name()));
        if(exitsProduct){
            throw new ProductAlreadyExistsException("Product already exists");
        }

        Product newProduct = mapper.convertToEntity(dto, category);
        newProduct = productRepository.save(newProduct);
        return mapper.convertToDTO(newProduct);

    }

    @Transactional
    public ProductResponseDTO update(UUID id, ProductUpdateDTO dto){
        Product product = findProductById(id);

        product.changePrice(dto.price());
        return mapper.convertToDTO(product);
    }

    @Transactional
    public void delete(UUID id){
        Product product = findProductById(id);
        product.deactivate();
    }

    private Product findProductById(UUID id){
        return productRepository.findByIdAndActiveTrue(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    private Category findCategoryById(UUID id) {

         return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }
}
