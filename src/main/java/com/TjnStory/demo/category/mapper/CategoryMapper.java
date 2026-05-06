package com.TjnStory.demo.category.mapper;

import com.TjnStory.demo.category.dto.CategoryCreateDTO;
import com.TjnStory.demo.category.dto.CategoryResponseDTO;
import com.TjnStory.demo.category.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
