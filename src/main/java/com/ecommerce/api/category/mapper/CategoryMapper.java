package com.ecommerce.api.category.mapper;

import com.ecommerce.api.category.dto.CategoryCreateDTO;
import com.ecommerce.api.category.dto.CategoryResponseDTO;
import com.ecommerce.api.category.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    default Category convertToEntity(CategoryCreateDTO dto, Category parent){

        if (dto == null) {
            return null;
        }

        return Category.createCategory(dto.name(), parent);
    }

    @Mapping(source = "parent.id", target = "parent")
    CategoryResponseDTO convertToDTO(Category category);
}
