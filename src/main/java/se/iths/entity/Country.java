package se.iths.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "country", schema = "demo")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "countryId", nullable = false)
    private Integer id;

    @Column(name = "countryName", nullable = false)
    private String name;

    @Column(name = "countryCapital", nullable = false)
    private String capital;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<City> cities = new ArrayList<>();

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lake> lakes = new ArrayList<>();

    public Country() {}

    public Country(String name, String capital) {
        this.name = name;
        this.capital = capital;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Lake> getLakes() {
        return lakes;
    }

    public void setLakes(List<Lake> lakes) {
        this.lakes = lakes;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryName='" + name + '\'' +
                ", countryCapital=" + capital +
                ", cities=" + cities +
                ", lakes=" + lakes +
                '}';
    }

}
