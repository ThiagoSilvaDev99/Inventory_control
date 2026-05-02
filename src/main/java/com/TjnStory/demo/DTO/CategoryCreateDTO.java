package com.TjnStory.demo.DTO;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CategoryCreateDTO(

        @NotBlank(message = "Name is required.")
        String name,

        UUID parentId
) {
}
