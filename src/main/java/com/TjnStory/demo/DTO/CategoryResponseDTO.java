package com.TjnStory.demo.DTO;

import java.util.UUID;

public record CategoryResponseDTO(
        UUID id,
        String categoryName,
        UUID parent
) {
}
