package se.iths.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "country", schema = "demo")
public class Country {
    @Id
    @Column(name = "countryId", nullable = false)
    private Integer id;

    @Column(name = "countryName", nullable = false)
    private String countryName;

    @Column(name = "countryCapital")
    private Integer countryCapital;

    @OneToMany(mappedBy = "cityCountry", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<City> cities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "lakeCountry", fetch = FetchType.LAZY)
    private Set<Lake> lakes = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getCountryCapital() {
        return countryCapital;
    }

    public void setCountryCapital(Integer countryCapital) {
        this.countryCapital = countryCapital;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public Set<Lake> getLakes() {
        return lakes;
    }

    public void setLakes(Set<Lake> lakes) {
        this.lakes = lakes;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryName='" + countryName + '\'' +
                ", countryCapital=" + countryCapital +
                ", cities=" + cities +
                ", lakes=" + lakes +
                '}';
    }

}
