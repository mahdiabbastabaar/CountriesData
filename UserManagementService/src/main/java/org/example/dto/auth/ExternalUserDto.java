package org.example.dto.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.example.models.User;

public record ExternalUserDto(
    @NotNull @Pattern(regexp = User.USERNAME_REGEX) String username
) {
    public ExternalUserDto(
        String username
    ) {
        this.username = username;
    }
}
