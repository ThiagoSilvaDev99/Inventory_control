package com.TjnStory.demo.product.service;

import com.TjnStory.demo.category.entity.Category;
import com.TjnStory.demo.product.mapper.ProductMapper;
import com.TjnStory.demo.product.repository.ProductRepository;
import com.TjnStory.demo.product.dto.ProductCreateDTO;
import com.TjnStory.demo.product.dto.ProductResponseDTO;
import com.TjnStory.demo.product.dto.ProductUpdateDTO;
import com.TjnStory.demo.product.entity.Product;
import com.TjnStory.demo.category.repository.CategoryRepository;
import com.TjnStory.demo.shared.exception.CategoryNotFoundException;
import com.TjnStory.demo.shared.exception.ProductAlreadyExistsException;
import com.TjnStory.demo.shared.exception.ProductNotFoundException;
import com.TjnStory.demo.shared.util.NameNormalizer;
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
