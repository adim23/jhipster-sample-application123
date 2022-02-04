package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cities.
 */
@Entity
@Table(name = "cities")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "president")
    private String president;

    @Column(name = "presidents_phone")
    private String presidentsPhone;

    @Column(name = "secretary")
    private String secretary;

    @Column(name = "secretarys_phone")
    private String secretarysPhone;

    @Column(name = "police")
    private String police;

    @Column(name = "polices_phone")
    private String policesPhone;

    @Column(name = "doctor")
    private String doctor;

    @Column(name = "doctors_phone")
    private String doctorsPhone;

    @Column(name = "teacher")
    private String teacher;

    @Column(name = "teachers_phone")
    private String teachersPhone;

    @Column(name = "priest")
    private String priest;

    @Column(name = "priests_phone")
    private String priestsPhone;

    @ManyToOne(optional = false)
    @NotNull
    private Countries country;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "country" }, allowSetters = true)
    private Regions region;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cities id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Cities name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresident() {
        return this.president;
    }

    public Cities president(String president) {
        this.setPresident(president);
        return this;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getPresidentsPhone() {
        return this.presidentsPhone;
    }

    public Cities presidentsPhone(String presidentsPhone) {
        this.setPresidentsPhone(presidentsPhone);
        return this;
    }

    public void setPresidentsPhone(String presidentsPhone) {
        this.presidentsPhone = presidentsPhone;
    }

    public String getSecretary() {
        return this.secretary;
    }

    public Cities secretary(String secretary) {
        this.setSecretary(secretary);
        return this;
    }

    public void setSecretary(String secretary) {
        this.secretary = secretary;
    }

    public String getSecretarysPhone() {
        return this.secretarysPhone;
    }

    public Cities secretarysPhone(String secretarysPhone) {
        this.setSecretarysPhone(secretarysPhone);
        return this;
    }

    public void setSecretarysPhone(String secretarysPhone) {
        this.secretarysPhone = secretarysPhone;
    }

    public String getPolice() {
        return this.police;
    }

    public Cities police(String police) {
        this.setPolice(police);
        return this;
    }

    public void setPolice(String police) {
        this.police = police;
    }

    public String getPolicesPhone() {
        return this.policesPhone;
    }

    public Cities policesPhone(String policesPhone) {
        this.setPolicesPhone(policesPhone);
        return this;
    }

    public void setPolicesPhone(String policesPhone) {
        this.policesPhone = policesPhone;
    }

    public String getDoctor() {
        return this.doctor;
    }

    public Cities doctor(String doctor) {
        this.setDoctor(doctor);
        return this;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDoctorsPhone() {
        return this.doctorsPhone;
    }

    public Cities doctorsPhone(String doctorsPhone) {
        this.setDoctorsPhone(doctorsPhone);
        return this;
    }

    public void setDoctorsPhone(String doctorsPhone) {
        this.doctorsPhone = doctorsPhone;
    }

    public String getTeacher() {
        return this.teacher;
    }

    public Cities teacher(String teacher) {
        this.setTeacher(teacher);
        return this;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTeachersPhone() {
        return this.teachersPhone;
    }

    public Cities teachersPhone(String teachersPhone) {
        this.setTeachersPhone(teachersPhone);
        return this;
    }

    public void setTeachersPhone(String teachersPhone) {
        this.teachersPhone = teachersPhone;
    }

    public String getPriest() {
        return this.priest;
    }

    public Cities priest(String priest) {
        this.setPriest(priest);
        return this;
    }

    public void setPriest(String priest) {
        this.priest = priest;
    }

    public String getPriestsPhone() {
        return this.priestsPhone;
    }

    public Cities priestsPhone(String priestsPhone) {
        this.setPriestsPhone(priestsPhone);
        return this;
    }

    public void setPriestsPhone(String priestsPhone) {
        this.priestsPhone = priestsPhone;
    }

    public Countries getCountry() {
        return this.country;
    }

    public void setCountry(Countries countries) {
        this.country = countries;
    }

    public Cities country(Countries countries) {
        this.setCountry(countries);
        return this;
    }

    public Regions getRegion() {
        return this.region;
    }

    public void setRegion(Regions regions) {
        this.region = regions;
    }

    public Cities region(Regions regions) {
        this.setRegion(regions);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cities)) {
            return false;
        }
        return id != null && id.equals(((Cities) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cities{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", president='" + getPresident() + "'" +
            ", presidentsPhone='" + getPresidentsPhone() + "'" +
            ", secretary='" + getSecretary() + "'" +
            ", secretarysPhone='" + getSecretarysPhone() + "'" +
            ", police='" + getPolice() + "'" +
            ", policesPhone='" + getPolicesPhone() + "'" +
            ", doctor='" + getDoctor() + "'" +
            ", doctorsPhone='" + getDoctorsPhone() + "'" +
            ", teacher='" + getTeacher() + "'" +
            ", teachersPhone='" + getTeachersPhone() + "'" +
            ", priest='" + getPriest() + "'" +
            ", priestsPhone='" + getPriestsPhone() + "'" +
            "}";
    }
}
