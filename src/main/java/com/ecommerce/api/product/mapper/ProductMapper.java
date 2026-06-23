package com.ecommerce.api.product.mapper;

import com.ecommerce.api.category.entity.Category;
import com.ecommerce.api.category.mapper.CategoryMapper;
import com.ecommerce.api.product.dto.ProductCreateDTO;
import com.ecommerce.api.product.dto.ProductResponseDTO;
import com.ecommerce.api.product.entity.Product;
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
