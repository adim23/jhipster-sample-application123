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
 * Criteria class for the {@link com.mycompany.myapp.domain.ZipCodes} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ZipCodesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /zip-codes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZipCodesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter street;

    private StringFilter area;

    private StringFilter fromNumber;

    private StringFilter toNumber;

    private LongFilter countryId;

    private LongFilter regionId;

    private LongFilter cityId;

    private Boolean distinct;

    public ZipCodesCriteria() {}

    public ZipCodesCriteria(ZipCodesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.street = other.street == null ? null : other.street.copy();
        this.area = other.area == null ? null : other.area.copy();
        this.fromNumber = other.fromNumber == null ? null : other.fromNumber.copy();
        this.toNumber = other.toNumber == null ? null : other.toNumber.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
        this.regionId = other.regionId == null ? null : other.regionId.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ZipCodesCriteria copy() {
        return new ZipCodesCriteria(this);
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

    public StringFilter getStreet() {
        return street;
    }

    public StringFilter street() {
        if (street == null) {
            street = new StringFilter();
        }
        return street;
    }

    public void setStreet(StringFilter street) {
        this.street = street;
    }

    public StringFilter getArea() {
        return area;
    }

    public StringFilter area() {
        if (area == null) {
            area = new StringFilter();
        }
        return area;
    }

    public void setArea(StringFilter area) {
        this.area = area;
    }

    public StringFilter getFromNumber() {
        return fromNumber;
    }

    public StringFilter fromNumber() {
        if (fromNumber == null) {
            fromNumber = new StringFilter();
        }
        return fromNumber;
    }

    public void setFromNumber(StringFilter fromNumber) {
        this.fromNumber = fromNumber;
    }

    public StringFilter getToNumber() {
        return toNumber;
    }

    public StringFilter toNumber() {
        if (toNumber == null) {
            toNumber = new StringFilter();
        }
        return toNumber;
    }

    public void setToNumber(StringFilter toNumber) {
        this.toNumber = toNumber;
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

    public LongFilter getCityId() {
        return cityId;
    }

    public LongFilter cityId() {
        if (cityId == null) {
            cityId = new LongFilter();
        }
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
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
        final ZipCodesCriteria that = (ZipCodesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(street, that.street) &&
            Objects.equals(area, that.area) &&
            Objects.equals(fromNumber, that.fromNumber) &&
            Objects.equals(toNumber, that.toNumber) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(regionId, that.regionId) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, area, fromNumber, toNumber, countryId, regionId, cityId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ZipCodesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (street != null ? "street=" + street + ", " : "") +
            (area != null ? "area=" + area + ", " : "") +
            (fromNumber != null ? "fromNumber=" + fromNumber + ", " : "") +
            (toNumber != null ? "toNumber=" + toNumber + ", " : "") +
            (countryId != null ? "countryId=" + countryId + ", " : "") +
            (regionId != null ? "regionId=" + regionId + ", " : "") +
            (cityId != null ? "cityId=" + cityId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
