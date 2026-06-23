package com.ecommerce.api.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Schema(description = "Objeto de transferência de dados para criação de uma Categoria")
public record CategoryCreateDTO(

        @NotBlank(message = "Name is required.")
        @Schema(description = "Nome de exibição da categoria", example = "Moda Masculina")
        String name,

        @Schema(description = "ID da categoria pai. Deixe nulo para criar uma categoria raiz.", example = "123e4567-e89b-12d3-a456-426614174000", nullable = true)
        UUID parentId
) {
}
