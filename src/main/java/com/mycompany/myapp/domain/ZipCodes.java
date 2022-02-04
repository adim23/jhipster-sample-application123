package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ZipCodes.
 */
@Entity
@Table(name = "zip_codes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ZipCodes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "area")
    private String area;

    @Column(name = "from_number")
    private String fromNumber;

    @Column(name = "to_number")
    private String toNumber;

    @ManyToOne
    private Countries country;

    @ManyToOne
    @JsonIgnoreProperties(value = { "country" }, allowSetters = true)
    private Regions region;

    @ManyToOne
    @JsonIgnoreProperties(value = { "country", "region" }, allowSetters = true)
    private Cities city;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ZipCodes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return this.street;
    }

    public ZipCodes street(String street) {
        this.setStreet(street);
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return this.area;
    }

    public ZipCodes area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFromNumber() {
        return this.fromNumber;
    }

    public ZipCodes fromNumber(String fromNumber) {
        this.setFromNumber(fromNumber);
        return this;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getToNumber() {
        return this.toNumber;
    }

    public ZipCodes toNumber(String toNumber) {
        this.setToNumber(toNumber);
        return this;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public Countries getCountry() {
        return this.country;
    }

    public void setCountry(Countries countries) {
        this.country = countries;
    }

    public ZipCodes country(Countries countries) {
        this.setCountry(countries);
        return this;
    }

    public Regions getRegion() {
        return this.region;
    }

    public void setRegion(Regions regions) {
        this.region = regions;
    }

    public ZipCodes region(Regions regions) {
        this.setRegion(regions);
        return this;
    }

    public Cities getCity() {
        return this.city;
    }

    public void setCity(Cities cities) {
        this.city = cities;
    }

    public ZipCodes city(Cities cities) {
        this.setCity(cities);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ZipCodes)) {
            return false;
        }
        return id != null && id.equals(((ZipCodes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ZipCodes{" +
            "id=" + getId() +
            ", street='" + getStreet() + "'" +
            ", area='" + getArea() + "'" +
            ", fromNumber='" + getFromNumber() + "'" +
            ", toNumber='" + getToNumber() + "'" +
            "}";
    }
}
