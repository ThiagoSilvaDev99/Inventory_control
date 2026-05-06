package com.TjnStory.demo.product.mapper;

import com.TjnStory.demo.category.entity.Category;
import com.TjnStory.demo.category.mapper.CategoryMapper;
import com.TjnStory.demo.product.dto.ProductCreateDTO;
import com.TjnStory.demo.product.dto.ProductResponseDTO;
import com.TjnStory.demo.product.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

     default Product convertToEntity(ProductCreateDTO dto, Category category) {

         if (dto == null) {
             return null;
         }

        return Product.create(dto.name(), category, dto.costPrice(), dto.price(), dto.stockQuantity());
    }

    ProductResponseDTO convertToDTO(Product entity);
}
