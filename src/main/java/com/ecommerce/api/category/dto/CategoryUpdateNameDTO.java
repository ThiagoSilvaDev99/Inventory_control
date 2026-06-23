package com.ecommerce.api.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Objeto de transferência de dados para mudança de nome de uma Categoria")
public record CategoryUpdateNameDTO(

        @NotBlank(message = "The category name cannot be blank.")
        @Schema(description = "Novo nome da categoria", example = "Moda Feminina")
        String name
) {
}
