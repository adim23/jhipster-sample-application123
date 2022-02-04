package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Emails.
 */
@Entity
@Table(name = "emails")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Emails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "favourite")
    private Boolean favourite;

    @ManyToOne
    private ContactTypes kind;

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

    public Emails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public Emails email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return this.description;
    }

    public Emails description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFavourite() {
        return this.favourite;
    }

    public Emails favourite(Boolean favourite) {
        this.setFavourite(favourite);
        return this;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public ContactTypes getKind() {
        return this.kind;
    }

    public void setKind(ContactTypes contactTypes) {
        this.kind = contactTypes;
    }

    public Emails kind(ContactTypes contactTypes) {
        this.setKind(contactTypes);
        return this;
    }

    public Citizens getCitizen() {
        return this.citizen;
    }

    public void setCitizen(Citizens citizens) {
        this.citizen = citizens;
    }

    public Emails citizen(Citizens citizens) {
        this.setCitizen(citizens);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Emails)) {
            return false;
        }
        return id != null && id.equals(((Emails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Emails{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", description='" + getDescription() + "'" +
            ", favourite='" + getFavourite() + "'" +
            "}";
    }
}
