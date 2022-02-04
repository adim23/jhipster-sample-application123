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
 * Criteria class for the {@link com.mycompany.myapp.domain.Phones} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PhonesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /phones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PhonesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter phone;

    private StringFilter description;

    private BooleanFilter favourite;

    private LongFilter kindId;

    private LongFilter companyId;

    private LongFilter citizenId;

    private Boolean distinct;

    public PhonesCriteria() {}

    public PhonesCriteria(PhonesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.favourite = other.favourite == null ? null : other.favourite.copy();
        this.kindId = other.kindId == null ? null : other.kindId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.citizenId = other.citizenId == null ? null : other.citizenId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PhonesCriteria copy() {
        return new PhonesCriteria(this);
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

    public StringFilter getPhone() {
        return phone;
    }

    public StringFilter phone() {
        if (phone == null) {
            phone = new StringFilter();
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getFavourite() {
        return favourite;
    }

    public BooleanFilter favourite() {
        if (favourite == null) {
            favourite = new BooleanFilter();
        }
        return favourite;
    }

    public void setFavourite(BooleanFilter favourite) {
        this.favourite = favourite;
    }

    public LongFilter getKindId() {
        return kindId;
    }

    public LongFilter kindId() {
        if (kindId == null) {
            kindId = new LongFilter();
        }
        return kindId;
    }

    public void setKindId(LongFilter kindId) {
        this.kindId = kindId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public LongFilter companyId() {
        if (companyId == null) {
            companyId = new LongFilter();
        }
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }

    public LongFilter getCitizenId() {
        return citizenId;
    }

    public LongFilter citizenId() {
        if (citizenId == null) {
            citizenId = new LongFilter();
        }
        return citizenId;
    }

    public void setCitizenId(LongFilter citizenId) {
        this.citizenId = citizenId;
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
        final PhonesCriteria that = (PhonesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(description, that.description) &&
            Objects.equals(favourite, that.favourite) &&
            Objects.equals(kindId, that.kindId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(citizenId, that.citizenId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phone, description, favourite, kindId, companyId, citizenId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhonesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (favourite != null ? "favourite=" + favourite + ", " : "") +
            (kindId != null ? "kindId=" + kindId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (citizenId != null ? "citizenId=" + citizenId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
