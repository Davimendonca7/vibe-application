package com.example.vibe.account.dto;

import com.example.vibe.account.entity.Account;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AccountResponseDto(
        UUID id,
        String email,
        String username,
        String biography,
        OffsetDateTime createdAt
) {
    public AccountResponseDto(Account account){
        this(
                account.getId(),
                account.getEmail(),
                account.getUsername(), 
                account.getBiography(),
                account.getCreatedAt()
        );
    }

}
