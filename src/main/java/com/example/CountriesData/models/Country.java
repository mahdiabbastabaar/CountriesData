package com.example.CountriesData.models;

public class Country {

    private String name;
    private String capital;
    private String iso2;
    private int population;
    private double popGrowth;
    private Country country;

    public Country() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getPopGrowth() {
        return popGrowth;
    }

    public void setPopGrowth(double popGrowth) {
        this.popGrowth = popGrowth;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
