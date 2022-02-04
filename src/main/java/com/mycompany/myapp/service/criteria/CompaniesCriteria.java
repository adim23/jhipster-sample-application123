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
 * Criteria class for the {@link com.mycompany.myapp.domain.Companies} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CompaniesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /companies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompaniesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter kindId;

    private LongFilter phonesId;

    private LongFilter addressesId;

    private Boolean distinct;

    public CompaniesCriteria() {}

    public CompaniesCriteria(CompaniesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.kindId = other.kindId == null ? null : other.kindId.copy();
        this.phonesId = other.phonesId == null ? null : other.phonesId.copy();
        this.addressesId = other.addressesId == null ? null : other.addressesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CompaniesCriteria copy() {
        return new CompaniesCriteria(this);
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

    public LongFilter getPhonesId() {
        return phonesId;
    }

    public LongFilter phonesId() {
        if (phonesId == null) {
            phonesId = new LongFilter();
        }
        return phonesId;
    }

    public void setPhonesId(LongFilter phonesId) {
        this.phonesId = phonesId;
    }

    public LongFilter getAddressesId() {
        return addressesId;
    }

    public LongFilter addressesId() {
        if (addressesId == null) {
            addressesId = new LongFilter();
        }
        return addressesId;
    }

    public void setAddressesId(LongFilter addressesId) {
        this.addressesId = addressesId;
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
        final CompaniesCriteria that = (CompaniesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(kindId, that.kindId) &&
            Objects.equals(phonesId, that.phonesId) &&
            Objects.equals(addressesId, that.addressesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, kindId, phonesId, addressesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompaniesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (kindId != null ? "kindId=" + kindId + ", " : "") +
            (phonesId != null ? "phonesId=" + phonesId + ", " : "") +
            (addressesId != null ? "addressesId=" + addressesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
