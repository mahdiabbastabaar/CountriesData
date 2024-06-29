package org.example.models;

public class Country {
    private String name;
    private String capital;
    private String iso2;
    private double population;
    private double popGrowth;
    private Currency currency;

    // Getters and Setters
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

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }

    public double getPopGrowth() {
        return popGrowth;
    }

    public void setPopGrowth(double popGrowth) {
        this.popGrowth = popGrowth;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
