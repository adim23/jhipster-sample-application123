package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Addresses.
 */
@Entity
@Table(name = "addresses")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Addresses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "address_no")
    private String addressNo;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "prosf_letter")
    private String prosfLetter;

    @Column(name = "name_letter")
    private String nameLetter;

    @Column(name = "letter_close")
    private String letterClose;

    @Column(name = "first_label")
    private String firstLabel;

    @Column(name = "second_label")
    private String secondLabel;

    @Column(name = "third_label")
    private String thirdLabel;

    @Column(name = "fourth_label")
    private String fourthLabel;

    @Column(name = "fifth_label")
    private String fifthLabel;

    @Column(name = "favourite")
    private Boolean favourite;

    @ManyToOne
    private Countries country;

    @ManyToOne
    private ContactTypes kind;

    @ManyToOne
    @JsonIgnoreProperties(value = { "country" }, allowSetters = true)
    private Regions region;

    @ManyToOne
    @JsonIgnoreProperties(value = { "kind", "phones", "addresses" }, allowSetters = true)
    private Companies company;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "folder", "company", "maritalStatus", "team", "code", "origin", "job", "phones", "addresses", "socials", "emails", "relations",
        },
        allowSetters = true
    )
    private Citizens citizen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Addresses id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public Addresses address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressNo() {
        return this.addressNo;
    }

    public Addresses addressNo(String addressNo) {
        this.setAddressNo(addressNo);
        return this;
    }

    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public Addresses zipCode(String zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getProsfLetter() {
        return this.prosfLetter;
    }

    public Addresses prosfLetter(String prosfLetter) {
        this.setProsfLetter(prosfLetter);
        return this;
    }

    public void setProsfLetter(String prosfLetter) {
        this.prosfLetter = prosfLetter;
    }

    public String getNameLetter() {
        return this.nameLetter;
    }

    public Addresses nameLetter(String nameLetter) {
        this.setNameLetter(nameLetter);
        return this;
    }

    public void setNameLetter(String nameLetter) {
        this.nameLetter = nameLetter;
    }

    public String getLetterClose() {
        return this.letterClose;
    }

    public Addresses letterClose(String letterClose) {
        this.setLetterClose(letterClose);
        return this;
    }

    public void setLetterClose(String letterClose) {
        this.letterClose = letterClose;
    }

    public String getFirstLabel() {
        return this.firstLabel;
    }

    public Addresses firstLabel(String firstLabel) {
        this.setFirstLabel(firstLabel);
        return this;
    }

    public void setFirstLabel(String firstLabel) {
        this.firstLabel = firstLabel;
    }

    public String getSecondLabel() {
        return this.secondLabel;
    }

    public Addresses secondLabel(String secondLabel) {
        this.setSecondLabel(secondLabel);
        return this;
    }

    public void setSecondLabel(String secondLabel) {
        this.secondLabel = secondLabel;
    }

    public String getThirdLabel() {
        return this.thirdLabel;
    }

    public Addresses thirdLabel(String thirdLabel) {
        this.setThirdLabel(thirdLabel);
        return this;
    }

    public void setThirdLabel(String thirdLabel) {
        this.thirdLabel = thirdLabel;
    }

    public String getFourthLabel() {
        return this.fourthLabel;
    }

    public Addresses fourthLabel(String fourthLabel) {
        this.setFourthLabel(fourthLabel);
        return this;
    }

    public void setFourthLabel(String fourthLabel) {
        this.fourthLabel = fourthLabel;
    }

    public String getFifthLabel() {
        return this.fifthLabel;
    }

    public Addresses fifthLabel(String fifthLabel) {
        this.setFifthLabel(fifthLabel);
        return this;
    }

    public void setFifthLabel(String fifthLabel) {
        this.fifthLabel = fifthLabel;
    }

    public Boolean getFavourite() {
        return this.favourite;
    }

    public Addresses favourite(Boolean favourite) {
        this.setFavourite(favourite);
        return this;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public Countries getCountry() {
        return this.country;
    }

    public void setCountry(Countries countries) {
        this.country = countries;
    }

    public Addresses country(Countries countries) {
        this.setCountry(countries);
        return this;
    }

    public ContactTypes getKind() {
        return this.kind;
    }

    public void setKind(ContactTypes contactTypes) {
        this.kind = contactTypes;
    }

    public Addresses kind(ContactTypes contactTypes) {
        this.setKind(contactTypes);
        return this;
    }

    public Regions getRegion() {
        return this.region;
    }

    public void setRegion(Regions regions) {
        this.region = regions;
    }

    public Addresses region(Regions regions) {
        this.setRegion(regions);
        return this;
    }

    public Companies getCompany() {
        return this.company;
    }

    public void setCompany(Companies companies) {
        this.company = companies;
    }

    public Addresses company(Companies companies) {
        this.setCompany(companies);
        return this;
    }

    public Citizens getCitizen() {
        return this.citizen;
    }

    public void setCitizen(Citizens citizens) {
        this.citizen = citizens;
    }

    public Addresses citizen(Citizens citizens) {
        this.setCitizen(citizens);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Addresses)) {
            return false;
        }
        return id != null && id.equals(((Addresses) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Addresses{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", addressNo='" + getAddressNo() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", prosfLetter='" + getProsfLetter() + "'" +
            ", nameLetter='" + getNameLetter() + "'" +
            ", letterClose='" + getLetterClose() + "'" +
            ", firstLabel='" + getFirstLabel() + "'" +
            ", secondLabel='" + getSecondLabel() + "'" +
            ", thirdLabel='" + getThirdLabel() + "'" +
            ", fourthLabel='" + getFourthLabel() + "'" +
            ", fifthLabel='" + getFifthLabel() + "'" +
            ", favourite='" + getFavourite() + "'" +
            "}";
    }
}
