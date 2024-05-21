package com.example.CountriesData.dto.auth;

import com.example.CountriesData.models.user.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Request body of register request.

 */
public record RegisterRequestDto(
    @NotNull @Pattern(regexp = User.USERNAME_REGEX) String username,
    @NotNull @Pattern(regexp = User.PASSWORD_REGEX) String password
) {

    public RegisterRequestDto(
        String username,
        String password
    ) {
        this.username = username;
        this.password = password;
    }
}
