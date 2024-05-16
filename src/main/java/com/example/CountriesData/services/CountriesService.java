package com.example.CountriesData.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CountriesService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Map<String, String>> getAllCountries() {
        ParameterizedTypeReference<Map<String, Object>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            "https://countriesnow.space/api/v0.1/countries",
            HttpMethod.GET,
            null,
            typeRef
        );

        Map<String, Object> responseBody = response.getBody();
        List<Map<String, Object>> data = (List<Map<String, Object>>) responseBody.get("data");

        List<Map<String, String>> countries = data.stream()
            .map(item -> {
                Map<String, String> country = new HashMap<>();
                country.put("name", (String) item.get("country"));
                return country;
            })
            .toList();

        return countries;

    }
}
