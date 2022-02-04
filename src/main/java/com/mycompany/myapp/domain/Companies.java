package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Companies.
 */
@Entity
@Table(name = "companies")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Companies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    private CompanyKinds kind;

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "kind", "company", "citizen" }, allowSetters = true)
    private Set<Phones> phones = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "country", "kind", "region", "company", "citizen" }, allowSetters = true)
    private Set<Addresses> addresses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Companies id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Companies name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompanyKinds getKind() {
        return this.kind;
    }

    public void setKind(CompanyKinds companyKinds) {
        this.kind = companyKinds;
    }

    public Companies kind(CompanyKinds companyKinds) {
        this.setKind(companyKinds);
        return this;
    }

    public Set<Phones> getPhones() {
        return this.phones;
    }

    public void setPhones(Set<Phones> phones) {
        if (this.phones != null) {
            this.phones.forEach(i -> i.setCompany(null));
        }
        if (phones != null) {
            phones.forEach(i -> i.setCompany(this));
        }
        this.phones = phones;
    }

    public Companies phones(Set<Phones> phones) {
        this.setPhones(phones);
        return this;
    }

    public Companies addPhones(Phones phones) {
        this.phones.add(phones);
        phones.setCompany(this);
        return this;
    }

    public Companies removePhones(Phones phones) {
        this.phones.remove(phones);
        phones.setCompany(null);
        return this;
    }

    public Set<Addresses> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Addresses> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setCompany(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setCompany(this));
        }
        this.addresses = addresses;
    }

    public Companies addresses(Set<Addresses> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public Companies addAddresses(Addresses addresses) {
        this.addresses.add(addresses);
        addresses.setCompany(this);
        return this;
    }

    public Companies removeAddresses(Addresses addresses) {
        this.addresses.remove(addresses);
        addresses.setCompany(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Companies)) {
            return false;
        }
        return id != null && id.equals(((Companies) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Companies{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
