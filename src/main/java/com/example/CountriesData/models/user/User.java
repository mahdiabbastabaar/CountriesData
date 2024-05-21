package com.example.CountriesData.models.user;

import jakarta.persistence.*;

/**
 * Represents an entity for a user
 */
@Entity
@Table(name = "users")
public class User {

    public static final String USERNAME_REGEX = "[a-zA-Z0-9_]{1,64}";

    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-_@$!%*?&])[-_A-Za-z\\d@$!%*?&]{8,64}$";


    /**
     * The user's id
     */
    @Id
    private Long id;

    /**
     * The user's username
     */
    @Column(unique = true, nullable = false, length = 64)
    private String username;

    /**
     * The user's encoded password
     */
    @Column(nullable = false, length = 64)
    private String password;

    /**
     * The user's role
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * The user's authorities
     */
    @Column(nullable = false)
    private boolean isEnabled;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
