package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SocialContacts.
 */
@Entity
@Table(name = "social_contacts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SocialContacts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "favored")
    private Boolean favored;

    @ManyToOne
    private SocialKinds social;

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

    public SocialContacts id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public SocialContacts name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFavored() {
        return this.favored;
    }

    public SocialContacts favored(Boolean favored) {
        this.setFavored(favored);
        return this;
    }

    public void setFavored(Boolean favored) {
        this.favored = favored;
    }

    public SocialKinds getSocial() {
        return this.social;
    }

    public void setSocial(SocialKinds socialKinds) {
        this.social = socialKinds;
    }

    public SocialContacts social(SocialKinds socialKinds) {
        this.setSocial(socialKinds);
        return this;
    }

    public ContactTypes getKind() {
        return this.kind;
    }

    public void setKind(ContactTypes contactTypes) {
        this.kind = contactTypes;
    }

    public SocialContacts kind(ContactTypes contactTypes) {
        this.setKind(contactTypes);
        return this;
    }

    public Citizens getCitizen() {
        return this.citizen;
    }

    public void setCitizen(Citizens citizens) {
        this.citizen = citizens;
    }

    public SocialContacts citizen(Citizens citizens) {
        this.setCitizen(citizens);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocialContacts)) {
            return false;
        }
        return id != null && id.equals(((SocialContacts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocialContacts{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", favored='" + getFavored() + "'" +
            "}";
    }
}
