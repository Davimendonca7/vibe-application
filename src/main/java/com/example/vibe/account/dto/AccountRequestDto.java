package com.example.vibe.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AccountRequestDto(
        @NotBlank
        String password,
        @Email
        @NotBlank
        String email,
        @NotBlank
        String username,
        String biography
) {
}
