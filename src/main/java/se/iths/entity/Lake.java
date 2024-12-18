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
    private String lakeName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lake_countryId", nullable = false)
    private Country lakeCountry;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLakeName() {
        return lakeName;
    }

    public void setLakeName(String lakeName) {
        this.lakeName = lakeName;
    }

    public Country getLakeCountry() {
        return lakeCountry;
    }

    public void setLakeCountry(Country lakeCountry) {
        this.lakeCountry = lakeCountry;
    }

    @Override
    public String toString() {
        return "Lake{" +
                "id=" + id +
                ", lakeName='" + lakeName + '\'' +
                '}';
    }

}
