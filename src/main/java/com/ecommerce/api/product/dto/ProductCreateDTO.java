package com.ecommerce.api.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Objeto de transferência de dados para criação de um Produto")
public record ProductCreateDTO(

        @NotBlank(message = "Name is required.")
        @Schema(description = "Nome de vitrine do produto", example = "Camiseta Oversized Preta")
        String name,

        @NotNull(message = "Category is mandatory.")
        @Schema(description = "ID da Categoria à qual o produto pertence", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID categoryId,

        @NotNull(message = "Cost price is required.")
        @Positive(message = "Cost price must be positive.")
        @Schema(description = "Preço final de venda para o cliente", example = "45.00")
        BigDecimal costPrice,

        @NotNull(message = "Price is required.")
        @Positive(message = "The price should be positive.")
        @Schema(description = "Custo de aquisição/produção", example = "119.90")
        BigDecimal price,

        @NotNull
        @PositiveOrZero(message = "Inventory cannot be negative.")
        @Schema(description = "Quantidade atual disponível no estoque", example = "50")
        Integer stockQuantity
) {
}
