package com.example.CountriesData.dto.apiToken;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record ApiTokenRequestDto(
    @NotNull String name,
    @NotNull Date expirationDate
) {
    public ApiTokenRequestDto (
        String name,
        Date expirationDate
    ) {
        this.name = name;
        this.expirationDate = expirationDate;
    }
}
