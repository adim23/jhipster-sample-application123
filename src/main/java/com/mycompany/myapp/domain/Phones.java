package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Phones.
 */
@Entity
@Table(name = "phones")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Phones implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "description")
    private String description;

    @Column(name = "favourite")
    private Boolean favourite;

    @ManyToOne
    private PhoneTypes kind;

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

    public Phones id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return this.phone;
    }

    public Phones phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return this.description;
    }

    public Phones description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFavourite() {
        return this.favourite;
    }

    public Phones favourite(Boolean favourite) {
        this.setFavourite(favourite);
        return this;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public PhoneTypes getKind() {
        return this.kind;
    }

    public void setKind(PhoneTypes phoneTypes) {
        this.kind = phoneTypes;
    }

    public Phones kind(PhoneTypes phoneTypes) {
        this.setKind(phoneTypes);
        return this;
    }

    public Companies getCompany() {
        return this.company;
    }

    public void setCompany(Companies companies) {
        this.company = companies;
    }

    public Phones company(Companies companies) {
        this.setCompany(companies);
        return this;
    }

    public Citizens getCitizen() {
        return this.citizen;
    }

    public void setCitizen(Citizens citizens) {
        this.citizen = citizens;
    }

    public Phones citizen(Citizens citizens) {
        this.setCitizen(citizens);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phones)) {
            return false;
        }
        return id != null && id.equals(((Phones) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Phones{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", description='" + getDescription() + "'" +
            ", favourite='" + getFavourite() + "'" +
            "}";
    }
}
