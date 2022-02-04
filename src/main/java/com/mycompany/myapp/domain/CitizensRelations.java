package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CitizensRelations.
 */
@Entity
@Table(name = "citizens_relations")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CitizensRelations implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

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

    public CitizensRelations id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CitizensRelations name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Citizens getCitizen() {
        return this.citizen;
    }

    public void setCitizen(Citizens citizens) {
        this.citizen = citizens;
    }

    public CitizensRelations citizen(Citizens citizens) {
        this.setCitizen(citizens);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CitizensRelations)) {
            return false;
        }
        return id != null && id.equals(((CitizensRelations) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitizensRelations{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
