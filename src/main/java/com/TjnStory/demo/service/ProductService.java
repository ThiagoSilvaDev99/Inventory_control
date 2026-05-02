package com.TjnStory.demo.service;

import com.TjnStory.demo.DTO.ProductUpdateDTO;
import com.TjnStory.demo.exceptions.*;
import com.TjnStory.demo.DTO.ProductCreateDTO;
import com.TjnStory.demo.DTO.ProductResponseDTO;
import com.TjnStory.demo.entities.Category;
import com.TjnStory.demo.entities.Product;
import com.TjnStory.demo.repository.CategoryRepository;
import com.TjnStory.demo.repository.ProductRepository;
import com.TjnStory.demo.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        List<Product> products = productRepository.findByActiveTrue();

        return products.stream().map(produto -> mapper.convertToDTO(produto)).toList();

    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(UUID id) {

        return mapper.convertToDTO(productRepository.findByIdAndActiveTrue(id).orElseThrow(() -> new ProductNotFoundException("Product not found")));
    }

    @Transactional
    public ProductResponseDTO createProduct(ProductCreateDTO dto) {

        Category category = findCategoryById(dto.categoryId());

        boolean exitsProduct = productRepository.existsByNameIgnoreCase(dto.name());
        if(exitsProduct){
            throw new ProductAlreadyExistsException("Product already exists");
        }

        Product newProduct = mapper.convertToEntity(dto, category);
        Product saveProduct = productRepository.save(newProduct);
        return mapper.convertToDTO(saveProduct);

    }

    @Transactional
    public ProductResponseDTO update(UUID id, ProductUpdateDTO dto){
        Product product = findProductById(id);

        product.changePrice(dto.price());
        //productRepository.save(product);
        return mapper.convertToDTO(product);
    }

    @Transactional
    public void delete(UUID id){
        Product product = findProductById(id);
        product.deactivate();
        //productRepository.save(product);
    }

    private Product findProductById(UUID id){
        return productRepository.findByIdAndActiveTrue(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    private Category findCategoryById(UUID id) {

         return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }
}
