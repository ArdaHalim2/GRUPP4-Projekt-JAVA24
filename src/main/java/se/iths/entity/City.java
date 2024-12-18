package se.iths.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "city", schema = "demo")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cityId", nullable = false)
    private Integer id;

    @Column(name = "cityName", nullable = false)
    private String cityName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_countryId", nullable = false)
    private se.iths.entity.Country cityCountry;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public se.iths.entity.Country getCityCountry() {
        return cityCountry;
    }

    public void setCityCountry(se.iths.entity.Country cityCountry) {
        this.cityCountry = cityCountry;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                '}';
    }

}
