package com.TjnStory.demo.DTO;

import jakarta.validation.constraints.NotBlank;

public record CategoryUpdateNameDTO(

        @NotBlank(message = "Cost price is required.")
        String name
) {
}
