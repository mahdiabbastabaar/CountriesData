package com.example.CountriesData.services;

import com.example.CountriesData.models.Country;
import com.example.CountriesData.models.Currency;
import com.example.CountriesData.models.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountriesService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_NINJAS_COUNTRY_URL = "https://api.api-ninjas.com/v1/country?name=";
    private static final String API_NINJAS_WEATHER_URL = "https://api.api-ninjas.com/v1/weather?city=";
    private static final String API_KEY = "Ps+GJy6f0PRq7ykxqnUliw==FuGuhWkl8mnzefnQ";  // Replace with your actual API key

    public Optional<Country> getCountryByName(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                API_NINJAS_COUNTRY_URL + name,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && !response.getBody().isEmpty()) {
            Map<String, Object> responseBody = response.getBody().get(0);

            Country country = new Country();
            country.setName((String) responseBody.get("name"));
            country.setCapital((String) responseBody.get("capital"));
            country.setIso2((String) responseBody.get("iso2"));
            country.setPopulation(((Number) responseBody.get("population")).doubleValue());
            country.setPopGrowth(((Number) responseBody.get("pop_growth")).doubleValue());

            Map<String, String> currencyMap = (Map<String, String>) responseBody.get("currency");

            Currency currency = new Currency();
            currency.setCode(currencyMap.get("code").toString());
            currency.setName(currencyMap.get("name").toString());
            country.setCurrency(currency);

            return Optional.of(country);
        }

        return Optional.empty();
    }

    public Optional<Weather> getCountryWeather(String country) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                API_NINJAS_WEATHER_URL + country,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Map<String, Object> responseBody = response.getBody();

            Weather weather = new Weather();
            weather.setCountryName(country);
            weather.setCapital("Tehran");  // Replace with the actual capital if available
            weather.setWindSpeed(((Number) responseBody.get("wind_speed")).doubleValue());
            weather.setWindDegrees((Integer) responseBody.get("wind_degrees"));
            weather.setTemp((Integer) responseBody.get("temp"));
            weather.setHumidity((Integer) responseBody.get("humidity"));

            return Optional.of(weather);
        }

        return Optional.empty();
    }

    public List<Map<String, String>> getAllCountryNames() {
        ParameterizedTypeReference<Map<String, Object>> typeRef = new ParameterizedTypeReference<>() {};
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "https://countriesnow.space/api/v0.1/countries",
                HttpMethod.GET,
                null,
                typeRef
        );

        Map<String, Object> responseBody = response.getBody();
        List<Map<String, Object>> data = (List<Map<String, Object>>) responseBody.get("data");

        return data.stream()
                .map(item -> Map.of("name", (String) item.get("country")))
                .collect(Collectors.toList());
    }
}
