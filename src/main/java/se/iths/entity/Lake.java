package se.iths.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lake", schema = "demo")
public class Lake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lakeId", nullable = false)
    private Integer id;

    @Column(name = "lakeName", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lake_countryId", nullable = false)
    private Country country;

    public Lake() {}

    public Lake(String name, Country country) {
        this.name = name;
        this.country = country;
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

    public void setName(String lakeName) {
        this.name = lakeName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) { this.country = country; }

    @Override
    public String toString() {
        return "Lake{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
