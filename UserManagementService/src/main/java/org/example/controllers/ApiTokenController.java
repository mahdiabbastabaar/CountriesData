package org.example.controllers;

import org.example.dto.apiToken.ApiTokenRequestDto;

import org.example.models.ApiToken;
import org.example.services.ApiTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/user/api-tokens")
@AllArgsConstructor
public class ApiTokenController {

    @Autowired
    private ApiTokenService apiTokenService;

    @PostMapping(value = {"/"}, consumes = {"application/json"})
    public ResponseEntity<?> createApiToken(Authentication authentication, @RequestBody ApiTokenRequestDto apiTokenRequestDto) {
        ApiToken token;
        try {
            token = apiTokenService.create(apiTokenRequestDto.name(), apiTokenRequestDto.expirationDate(), authentication.getName());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(token);
    }

    @GetMapping(value = {"/"})
    public ResponseEntity<?> getAllUserTokens(Authentication authentication) {
        Map<String, Object> tokens;
        try {
            tokens = apiTokenService.getAllUserApiTokens(authentication.getName());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(tokens);
    }

    @DeleteMapping (value = {"/"})
    public ResponseEntity<?> deleteApiToken(Authentication authentication, @RequestHeader("Authorization") String token) {
        Map<String, Object> result;
        try {
            result = apiTokenService.deleteToken(token, authentication.getName());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(result);
    }


}
