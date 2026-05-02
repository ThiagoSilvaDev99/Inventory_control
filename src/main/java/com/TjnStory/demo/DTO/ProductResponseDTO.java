package com.TjnStory.demo.DTO;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(

        UUID id,
        String name,
        BigDecimal price,
        Integer stockQuantity,
        CategoryResponseDTO category
) {
}
