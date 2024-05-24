package com.example.CountriesData.controllers;

import com.example.CountriesData.models.Country;
import com.example.CountriesData.models.Weather;
import com.example.CountriesData.services.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/countries")
public class CountriesController {

    @Autowired
    private CountriesService countriesService;

    @GetMapping("/")
    public ResponseEntity<?> listAllCountries() {
        List<Map<String, String>> allCountryNames = countriesService.getAllCountryNames();
        Map<String, Object> response = new HashMap<>();
        response.put("countries", allCountryNames);
        response.put("count", allCountryNames.size());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getCountryData(@PathVariable String name) {
        Optional<Country> countryData = countriesService.getCountryByName(name);
        if (countryData.isPresent()) {
            return new ResponseEntity<>(countryData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Country not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{name}/weather")
    public ResponseEntity<?> getCountryWeatherData(@PathVariable String name) {
        Optional<Country> countryData = countriesService.getCountryByName(name);
        if (countryData.isPresent()) {
            String capital = countryData.get().getCapital();
            Optional<Weather> weatherData = countriesService.getCountryWeather(name, capital);
            if (weatherData.isPresent()) {
                return new ResponseEntity<>(weatherData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Weather data not found", HttpStatus.NOT_FOUND);
            }
        } else {
            // If country not found, fetch weather by country name directly
            Optional<Weather> weatherData = countriesService.getCountryWeather(name, null);
            if (weatherData.isPresent()) {
                return new ResponseEntity<>(weatherData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Country and Weather data not found", HttpStatus.NOT_FOUND);
            }
        }
    }
}
