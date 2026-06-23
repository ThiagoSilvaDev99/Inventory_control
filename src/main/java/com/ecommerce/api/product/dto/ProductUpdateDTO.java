package com.ecommerce.api.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Objeto de transferência de dados para mudança de preço do produto")
public record ProductUpdateDTO(

        @NotNull(message = "Price is required.")
        @Positive(message = "Price must be positive.")
        @Schema(description = "Novo preço do produto", example = "98.90")
        BigDecimal price
) {
}
