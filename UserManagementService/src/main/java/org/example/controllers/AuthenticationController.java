package org.example.controllers;

import org.example.dto.auth.JwtAuthResponseDto;
import org.example.dto.auth.LoginRequestDto;
import org.example.dto.auth.RegisterRequestDto;
import org.example.models.Role;
import org.example.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = {"/users/login"}, consumes = {"application/json"})
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginDto) {
        String token = "";
        try {
            token = authenticationService.login(loginDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        JwtAuthResponseDto jwtAuthResponse = new JwtAuthResponseDto();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/users/register"}, consumes = {"application/json"})
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerDto) {
        String response = "";
        try {
            response = authenticationService.register(registerDto, Role.USER);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("users/auth-check")
    public ResponseEntity<?> authCheck() {
        return authenticationService.authCheck();
    }

    @PutMapping(value = {"/admin/users"})
    public ResponseEntity<String> updateUserActivation(@RequestParam String username, @RequestParam boolean active) {
        String response = "";
        try {
            response = authenticationService.updateUserActivation(username, active);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = {"/admin/users"})
    public ResponseEntity<?> getAllUsersByAdmin() {
        List<String> result;
        try {
            result = authenticationService.getAllUsers();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
