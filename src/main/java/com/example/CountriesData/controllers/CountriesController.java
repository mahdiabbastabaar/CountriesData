package com.example.CountriesData.controllers;

import com.example.CountriesData.services.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/countries")
public class CountriesController {

    @Autowired
    private CountriesService countriesService;
    @GetMapping("/")
    public ResponseEntity<?> listAllCountries(){
        List<Map<String, String>> allCountries = countriesService.getAllCountries();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getCountryData(@PathVariable String name){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{name}/weather")
    public ResponseEntity<?> getCountryWeatherData(@PathVariable String name){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
