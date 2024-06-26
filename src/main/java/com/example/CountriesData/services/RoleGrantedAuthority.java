package com.example.CountriesData.services;

import com.example.CountriesData.models.user.Role;
import org.springframework.security.core.GrantedAuthority;

public class RoleGrantedAuthority implements GrantedAuthority {
    private final Role role;

    public RoleGrantedAuthority(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.name(); // or any other logic to get the authority string from the Role
    }
}
