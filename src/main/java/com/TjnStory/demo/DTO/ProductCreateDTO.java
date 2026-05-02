package com.TjnStory.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductCreateDTO(
        @NotBlank(message = "Name is required.")
        String name,

        @NotNull(message = "Category is mandatory.")
        UUID categoryId,

        @NotNull(message = "Cost price is required.")
        @Positive(message = "Cost price must be positive.")
        BigDecimal costPrice,

        @NotNull(message = "Price is required.")
        @Positive(message = "The price should be positive.")
        BigDecimal price,

        @NotNull
        @PositiveOrZero(message = "Inventory cannot be negative.")
        Integer stockQuantity
) {
}
