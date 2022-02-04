package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CitizensMeetings.
 */
@Entity
@Table(name = "citizens_meetings")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CitizensMeetings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "meet_date", nullable = false)
    private LocalDate meetDate;

    @NotNull
    @Column(name = "agenda", nullable = false)
    private String agenda;

    @Lob
    @Column(name = "comments")
    private String comments;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "quantity")
    private Integer quantity;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "flag", nullable = false)
    private Boolean flag;

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

    public CitizensMeetings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMeetDate() {
        return this.meetDate;
    }

    public CitizensMeetings meetDate(LocalDate meetDate) {
        this.setMeetDate(meetDate);
        return this;
    }

    public void setMeetDate(LocalDate meetDate) {
        this.meetDate = meetDate;
    }

    public String getAgenda() {
        return this.agenda;
    }

    public CitizensMeetings agenda(String agenda) {
        this.setAgenda(agenda);
        return this;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getComments() {
        return this.comments;
    }

    public CitizensMeetings comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Float getAmount() {
        return this.amount;
    }

    public CitizensMeetings amount(Float amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public CitizensMeetings quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return this.status;
    }

    public CitizensMeetings status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getFlag() {
        return this.flag;
    }

    public CitizensMeetings flag(Boolean flag) {
        this.setFlag(flag);
        return this;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Citizens getCitizen() {
        return this.citizen;
    }

    public void setCitizen(Citizens citizens) {
        this.citizen = citizens;
    }

    public CitizensMeetings citizen(Citizens citizens) {
        this.setCitizen(citizens);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CitizensMeetings)) {
            return false;
        }
        return id != null && id.equals(((CitizensMeetings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitizensMeetings{" +
            "id=" + getId() +
            ", meetDate='" + getMeetDate() + "'" +
            ", agenda='" + getAgenda() + "'" +
            ", comments='" + getComments() + "'" +
            ", amount=" + getAmount() +
            ", quantity=" + getQuantity() +
            ", status='" + getStatus() + "'" +
            ", flag='" + getFlag() + "'" +
            "}";
    }
}
