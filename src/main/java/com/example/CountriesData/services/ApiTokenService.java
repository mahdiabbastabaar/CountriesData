package com.example.CountriesData.services;

import com.example.CountriesData.models.user.ApiToken;
import com.example.CountriesData.models.user.User;
import com.example.CountriesData.repositories.ApiTokenRepository;
import com.example.CountriesData.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApiTokenService {
    private ApiTokenRepository apiTokenRepository;
    private UserRepository userRepository;

    public ApiToken create(String name, Date expiartionDate, String username) throws Exception {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new Exception("User not found!");
        }
        String token = generateRandomString();
        ApiToken apiToken = new ApiToken(token, name, expiartionDate, user);
        return apiTokenRepository.save(apiToken);
    }

    public Map<String, Object> getAllUserApiTokens(String username) throws Exception {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new Exception("User not found!");
        }
        Map<String, Object> result = new HashMap<>();
        List<ApiToken> apiTokens = apiTokenRepository.findAllByUser(user);
        result.put("tokens", apiTokens.stream().map(
            apiToken -> new ApiToken("API ***", apiToken.getName(), apiToken.getExpirationDate(), apiToken.getUser())
        ));
        result.put("count", apiTokens.size());
        return result;
    }

    public Map<String, Object> deleteToken(String token, String username) {
        ApiToken apiToken = apiTokenRepository.findApiTokenByToken(token);
        apiTokenRepository.delete(apiToken);
        Map<String, Object> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }

    private String generateRandomString() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

}
