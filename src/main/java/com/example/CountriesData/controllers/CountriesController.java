package com.example.CountriesData.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
public class CountriesController {

    @GetMapping("/")
    public List<?> listAllCountries(){

    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getCountryData(@PathVariable String name){

    }

    @GetMapping("/{name}/weather")
    public ResponseEntity<?> getCountryWeatherData(@PathVariable String name){

    }
}
