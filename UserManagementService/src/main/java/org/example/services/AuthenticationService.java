package org.example.services;

import org.example.dto.auth.ExternalUserDto;
import org.example.dto.auth.LoginRequestDto;
import org.example.dto.auth.RegisterRequestDto;
import org.example.models.Role;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.example.configurations.filters.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(LoginRequestDto loginDto) throws Exception {
        if (!userRepository.getUserByUsername(loginDto.username()).isEnabled()) {
            throw new Exception("user is not active!");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginDto.username(), loginDto.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Transactional
    public String register(RegisterRequestDto registerDto, Role role) throws Exception {
        if(userRepository.existsUserByUsername(registerDto.username())){
            throw new Exception("Username already exists!.");
        }
        //TODO handle the Role input
        User user = new User();
        user.setUsername(registerDto.username());
        user.setPassword(passwordEncoder.encode(registerDto.password()));
        user.setEnabled(role == Role.ADMIN);
        user.setRole(role);
        userRepository.save(user);

        return "Registered Successfully!";
    }

    public String updateUserActivation(String username, boolean active) throws Exception {
        if(!userRepository.existsUserByUsername(username)){
            throw new Exception("User doesn't exist!.");
        }

        User user = userRepository.getUserByUsername(username);
        user.setEnabled(active);
        userRepository.save(user);
        return "Successful!";
    }

    public List<String> getAllUsers() {
        return userRepository.findAllUsernames();
    }

    public ResponseEntity<?> authCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.ok().body(null);
        } else {
            User authenticatedUser = (User) authentication.getPrincipal();
            ExternalUserDto externalUserDto = new ExternalUserDto(authenticatedUser.getUsername());
            return ResponseEntity.ok().body(externalUserDto);
        }
    }
}
