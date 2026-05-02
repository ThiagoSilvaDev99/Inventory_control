package com.TjnStory.demo.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductUpdateDTO(

        @NotNull(message = "Cost price is required.")
        @Positive(message = "Cost price must be positive.")
        BigDecimal price
) {
}
