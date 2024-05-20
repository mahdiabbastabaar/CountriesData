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
import java.util.Optional;

@Service
public class CountriesService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Map<String, Object>> getAllCountries() {
        ParameterizedTypeReference<Map<String, Object>> typeRef = new ParameterizedTypeReference<>() {};
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "https://countriesnow.space/api/v0.1/countries",
                HttpMethod.GET,
                null,
                typeRef
        );

        Map<String, Object> responseBody = response.getBody();
        List<Map<String, Object>> data = (List<Map<String, Object>>) responseBody.get("data");

        return data;
    }

    public Optional<Map<String, Object>> getCountryByName(String name) {
        List<Map<String, Object>> countries = getAllCountries();
        return countries.stream()
                .filter(country -> country.get("country").equals(name))
                .findFirst();
    }
}
