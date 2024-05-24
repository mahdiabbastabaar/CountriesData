package com.example.CountriesData.controllers;

import com.example.CountriesData.dto.auth.LoginRequestDto;
import com.example.CountriesData.dto.auth.RegisterRequestDto;
import com.example.CountriesData.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = {"/users/login"}, consumes = {"application/json"})
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginDto) {
        String response = "";
        try {
            response = authenticationService.login(loginDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = {"/users/register"}, consumes = {"application/json"})
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerDto) {
        String response = "";
        try {
            response = authenticationService.register(registerDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = {"/admin/users"})
    public ResponseEntity<String> updateUserActivation(@RequestParam String username, @RequestParam boolean active) {
        String response = "";
        try {
            response = authenticationService.updateUserActivation(username, active);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
