package com.example.CountriesData.models.user;

import com.example.CountriesData.services.RoleGrantedAuthority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an entity for a user
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    public static final String USERNAME_REGEX = "[a-zA-Z0-9_]{1,64}";

    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-_@$!%*?&])[-_A-Za-z\\d@$!%*?&]{8,64}$";


    /**
     * The user's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user's username
     */
    @Getter
    @Column(unique = true, nullable = false, length = 64)
    private String username;

    /**
     * The user's encoded password
     */
    @Getter
    @Column(nullable = false, length = 64)
    private String password;

    /**
     * The user's role
     */
    @Getter
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

    public void setUsername(String username) {
        this.username = username;
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

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new RoleGrantedAuthority(getRole()));
        return authorities;
    }
}
