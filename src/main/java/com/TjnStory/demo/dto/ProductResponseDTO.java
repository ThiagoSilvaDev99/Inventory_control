package com.TjnStory.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Objeto de transferência de dados contendo os detalhes de um Produto")
public record ProductResponseDTO(

        @Schema(description = "ID único do produto", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "Nome do produto", example = "Camiseta Preta TJN")
        String name,

        @Schema(description = "Preço de venda", example = "89.90")
        BigDecimal price,

        @Schema(description = "Quantidade em estoque", example = "20")
        Integer stockQuantity,

        @Schema(description = "Dados completos da categoria à qual o produto pertence")
        CategoryResponseDTO category
) {
}
