package com.ecommerce.api.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Objeto de transferência de dados para alteração da hierarquia (pai) de uma Categoria")
public record CategoryUpdateParentDTO(

        @Schema(description = "ID da categoria pai. Deixe nulo para mudar a categoria para raiz.", example = "123e4567-e89b-12d3-a456-426614174000", nullable = true)
        UUID newParent
) {
}
