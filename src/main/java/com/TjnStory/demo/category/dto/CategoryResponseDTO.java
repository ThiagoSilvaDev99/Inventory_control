package com.TjnStory.demo.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Objeto de transferência de dados contendo os detalhes de uma Categoria")
public record CategoryResponseDTO(

        @Schema(description = "ID único da categoria", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "Nome da categoria", example = "Moda Masculina")
        String name,

        @Schema(description = "ID da categoria pai, se houver", example = "987e6543-e21b-34d3-a456-426614174000", nullable = true)
        UUID parent
) {
}
