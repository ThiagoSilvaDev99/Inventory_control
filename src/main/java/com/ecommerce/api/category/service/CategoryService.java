package com.ecommerce.api.category.service;

import com.ecommerce.api.category.mapper.CategoryMapper;
import com.ecommerce.api.category.repository.CategoryRepository;
import com.ecommerce.api.category.dto.CategoryCreateDTO;
import com.ecommerce.api.category.dto.CategoryResponseDTO;
import com.ecommerce.api.category.dto.CategoryUpdateNameDTO;
import com.ecommerce.api.category.dto.CategoryUpdateParentDTO;
import com.ecommerce.api.category.entity.Category;
import com.ecommerce.api.shared.exception.CategoryAlreadyExistsException;
import com.ecommerce.api.shared.exception.CategoryNotDeletableException;
import com.ecommerce.api.shared.exception.CategoryNotFoundException;
import com.ecommerce.api.product.repository.ProductRepository;
import com.ecommerce.api.shared.util.NameNormalizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll(){
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(category -> mapper.convertToDTO(category)).toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO findById(UUID id){
        Category category = findCategoryById(id);

        return mapper.convertToDTO(category);
    }

    @Transactional
    public CategoryResponseDTO createCategory(CategoryCreateDTO dto){

        findExistingCategory(NameNormalizer.normalize(dto.name()));

        Category parent = null;
        if(dto.parentId() != null){
            parent = findCategoryById(dto.parentId());
        }

        Category newCategory = mapper.convertToEntity(dto, parent);
        Category savedCategory = categoryRepository.save(newCategory);
        return mapper.convertToDTO(savedCategory);

    }

    @Transactional
    public CategoryResponseDTO updateName(UUID id, CategoryUpdateNameDTO dto){
        Category category = findCategoryById(id);

        category.changeName(dto.name());
        return mapper.convertToDTO(category);
    }

    @Transactional
    public CategoryResponseDTO changeParent(UUID id, CategoryUpdateParentDTO dto){
        Category category = findCategoryById(id);

        Category newParent = findCategoryById(dto.newParent());

        category.changeParent(newParent);

        return mapper.convertToDTO(category);
    }

    @Transactional
    public void deleteCategory(UUID id){
        Category category = findCategoryById(id);

        checkHasChildren(category.getId());

        checkProduct(category.getId());

        category.deactivate();
    }

    private void checkHasChildren(UUID id){
        if(categoryRepository.existsByParentIdAndActiveTrue(id)){
            throw new CategoryNotDeletableException("Cannot delete category because it has active subcategories.");
        }

    }

    private void checkProduct(UUID id){
        if(productRepository.existsByCategoryIdAndActiveTrue(id)){
            throw new CategoryNotDeletableException("Cannot delete category because there are active products linked to it.");
        }
    }

    private void findExistingCategory(String name){
        boolean existsCategory = categoryRepository.existsByNameIgnoreCase(name);
        if (existsCategory) {
            throw new CategoryAlreadyExistsException("Category already exists");
        }
    }

    private Category findCategoryById(UUID id) {

        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }
}
