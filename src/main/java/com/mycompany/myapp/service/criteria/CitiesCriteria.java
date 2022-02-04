package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Cities} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CitiesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CitiesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter president;

    private StringFilter presidentsPhone;

    private StringFilter secretary;

    private StringFilter secretarysPhone;

    private StringFilter police;

    private StringFilter policesPhone;

    private StringFilter doctor;

    private StringFilter doctorsPhone;

    private StringFilter teacher;

    private StringFilter teachersPhone;

    private StringFilter priest;

    private StringFilter priestsPhone;

    private LongFilter countryId;

    private LongFilter regionId;

    private Boolean distinct;

    public CitiesCriteria() {}

    public CitiesCriteria(CitiesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.president = other.president == null ? null : other.president.copy();
        this.presidentsPhone = other.presidentsPhone == null ? null : other.presidentsPhone.copy();
        this.secretary = other.secretary == null ? null : other.secretary.copy();
        this.secretarysPhone = other.secretarysPhone == null ? null : other.secretarysPhone.copy();
        this.police = other.police == null ? null : other.police.copy();
        this.policesPhone = other.policesPhone == null ? null : other.policesPhone.copy();
        this.doctor = other.doctor == null ? null : other.doctor.copy();
        this.doctorsPhone = other.doctorsPhone == null ? null : other.doctorsPhone.copy();
        this.teacher = other.teacher == null ? null : other.teacher.copy();
        this.teachersPhone = other.teachersPhone == null ? null : other.teachersPhone.copy();
        this.priest = other.priest == null ? null : other.priest.copy();
        this.priestsPhone = other.priestsPhone == null ? null : other.priestsPhone.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
        this.regionId = other.regionId == null ? null : other.regionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CitiesCriteria copy() {
        return new CitiesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getPresident() {
        return president;
    }

    public StringFilter president() {
        if (president == null) {
            president = new StringFilter();
        }
        return president;
    }

    public void setPresident(StringFilter president) {
        this.president = president;
    }

    public StringFilter getPresidentsPhone() {
        return presidentsPhone;
    }

    public StringFilter presidentsPhone() {
        if (presidentsPhone == null) {
            presidentsPhone = new StringFilter();
        }
        return presidentsPhone;
    }

    public void setPresidentsPhone(StringFilter presidentsPhone) {
        this.presidentsPhone = presidentsPhone;
    }

    public StringFilter getSecretary() {
        return secretary;
    }

    public StringFilter secretary() {
        if (secretary == null) {
            secretary = new StringFilter();
        }
        return secretary;
    }

    public void setSecretary(StringFilter secretary) {
        this.secretary = secretary;
    }

    public StringFilter getSecretarysPhone() {
        return secretarysPhone;
    }

    public StringFilter secretarysPhone() {
        if (secretarysPhone == null) {
            secretarysPhone = new StringFilter();
        }
        return secretarysPhone;
    }

    public void setSecretarysPhone(StringFilter secretarysPhone) {
        this.secretarysPhone = secretarysPhone;
    }

    public StringFilter getPolice() {
        return police;
    }

    public StringFilter police() {
        if (police == null) {
            police = new StringFilter();
        }
        return police;
    }

    public void setPolice(StringFilter police) {
        this.police = police;
    }

    public StringFilter getPolicesPhone() {
        return policesPhone;
    }

    public StringFilter policesPhone() {
        if (policesPhone == null) {
            policesPhone = new StringFilter();
        }
        return policesPhone;
    }

    public void setPolicesPhone(StringFilter policesPhone) {
        this.policesPhone = policesPhone;
    }

    public StringFilter getDoctor() {
        return doctor;
    }

    public StringFilter doctor() {
        if (doctor == null) {
            doctor = new StringFilter();
        }
        return doctor;
    }

    public void setDoctor(StringFilter doctor) {
        this.doctor = doctor;
    }

    public StringFilter getDoctorsPhone() {
        return doctorsPhone;
    }

    public StringFilter doctorsPhone() {
        if (doctorsPhone == null) {
            doctorsPhone = new StringFilter();
        }
        return doctorsPhone;
    }

    public void setDoctorsPhone(StringFilter doctorsPhone) {
        this.doctorsPhone = doctorsPhone;
    }

    public StringFilter getTeacher() {
        return teacher;
    }

    public StringFilter teacher() {
        if (teacher == null) {
            teacher = new StringFilter();
        }
        return teacher;
    }

    public void setTeacher(StringFilter teacher) {
        this.teacher = teacher;
    }

    public StringFilter getTeachersPhone() {
        return teachersPhone;
    }

    public StringFilter teachersPhone() {
        if (teachersPhone == null) {
            teachersPhone = new StringFilter();
        }
        return teachersPhone;
    }

    public void setTeachersPhone(StringFilter teachersPhone) {
        this.teachersPhone = teachersPhone;
    }

    public StringFilter getPriest() {
        return priest;
    }

    public StringFilter priest() {
        if (priest == null) {
            priest = new StringFilter();
        }
        return priest;
    }

    public void setPriest(StringFilter priest) {
        this.priest = priest;
    }

    public StringFilter getPriestsPhone() {
        return priestsPhone;
    }

    public StringFilter priestsPhone() {
        if (priestsPhone == null) {
            priestsPhone = new StringFilter();
        }
        return priestsPhone;
    }

    public void setPriestsPhone(StringFilter priestsPhone) {
        this.priestsPhone = priestsPhone;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public LongFilter countryId() {
        if (countryId == null) {
            countryId = new LongFilter();
        }
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    public LongFilter getRegionId() {
        return regionId;
    }

    public LongFilter regionId() {
        if (regionId == null) {
            regionId = new LongFilter();
        }
        return regionId;
    }

    public void setRegionId(LongFilter regionId) {
        this.regionId = regionId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CitiesCriteria that = (CitiesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(president, that.president) &&
            Objects.equals(presidentsPhone, that.presidentsPhone) &&
            Objects.equals(secretary, that.secretary) &&
            Objects.equals(secretarysPhone, that.secretarysPhone) &&
            Objects.equals(police, that.police) &&
            Objects.equals(policesPhone, that.policesPhone) &&
            Objects.equals(doctor, that.doctor) &&
            Objects.equals(doctorsPhone, that.doctorsPhone) &&
            Objects.equals(teacher, that.teacher) &&
            Objects.equals(teachersPhone, that.teachersPhone) &&
            Objects.equals(priest, that.priest) &&
            Objects.equals(priestsPhone, that.priestsPhone) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(regionId, that.regionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            president,
            presidentsPhone,
            secretary,
            secretarysPhone,
            police,
            policesPhone,
            doctor,
            doctorsPhone,
            teacher,
            teachersPhone,
            priest,
            priestsPhone,
            countryId,
            regionId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitiesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (president != null ? "president=" + president + ", " : "") +
            (presidentsPhone != null ? "presidentsPhone=" + presidentsPhone + ", " : "") +
            (secretary != null ? "secretary=" + secretary + ", " : "") +
            (secretarysPhone != null ? "secretarysPhone=" + secretarysPhone + ", " : "") +
            (police != null ? "police=" + police + ", " : "") +
            (policesPhone != null ? "policesPhone=" + policesPhone + ", " : "") +
            (doctor != null ? "doctor=" + doctor + ", " : "") +
            (doctorsPhone != null ? "doctorsPhone=" + doctorsPhone + ", " : "") +
            (teacher != null ? "teacher=" + teacher + ", " : "") +
            (teachersPhone != null ? "teachersPhone=" + teachersPhone + ", " : "") +
            (priest != null ? "priest=" + priest + ", " : "") +
            (priestsPhone != null ? "priestsPhone=" + priestsPhone + ", " : "") +
            (countryId != null ? "countryId=" + countryId + ", " : "") +
            (regionId != null ? "regionId=" + regionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
