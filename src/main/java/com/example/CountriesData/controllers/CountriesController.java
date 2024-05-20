package com.example.CountriesData.controllers;

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
    public ResponseEntity<?> listAllCountries(){
        List<Map<String, Object>> allCountries = countriesService.getAllCountries();
        Map<String, Object> response = new HashMap<>();
        response.put("countries", allCountries);
        response.put("count", allCountries.size());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getCountryData(@PathVariable String name){
        Optional<Map<String, Object>> countryData = countriesService.getCountryByName(name);
        if (countryData.isPresent()) {
            return new ResponseEntity<>(countryData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Country not found", HttpStatus.NOT_FOUND);
        }
    }
}
