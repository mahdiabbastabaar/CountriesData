package com.example.CountriesData.services;

import com.example.CountriesData.dto.auth.LoginRequestDto;
import com.example.CountriesData.dto.auth.RegisterRequestDto;
import com.example.CountriesData.models.user.Role;
import com.example.CountriesData.models.user.User;
import com.example.CountriesData.repositories.UserRepository;
import com.example.CountriesData.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public String login(LoginRequestDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginDto.username(), loginDto.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    public String register(RegisterRequestDto registerDto) throws Exception {
        if(userRepository.existsUserByUsername(registerDto.username())){
            throw new Exception("Username is already exists!.");
        }

        User user = new User();
        user.setUsername(registerDto.username());
        user.setPassword(passwordEncoder.encode(registerDto.password()));
        user.setEnabled(false);
        user.setRole(Role.USER);
        userRepository.save(user);

        return "Registered Successfully";
    }

    public String updateUserActivation(String username, boolean active) {
        return "";
    }

}
