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
 * Criteria class for the {@link com.mycompany.myapp.domain.Department} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.DepartmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /departments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DepartmentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter departmentName;

    private LongFilter locationId;

    private LongFilter employeeId;

    private Boolean distinct;

    public DepartmentCriteria() {}

    public DepartmentCriteria(DepartmentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.departmentName = other.departmentName == null ? null : other.departmentName.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DepartmentCriteria copy() {
        return new DepartmentCriteria(this);
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

    public StringFilter getDepartmentName() {
        return departmentName;
    }

    public StringFilter departmentName() {
        if (departmentName == null) {
            departmentName = new StringFilter();
        }
        return departmentName;
    }

    public void setDepartmentName(StringFilter departmentName) {
        this.departmentName = departmentName;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public LongFilter locationId() {
        if (locationId == null) {
            locationId = new LongFilter();
        }
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
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
        final DepartmentCriteria that = (DepartmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(departmentName, that.departmentName) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departmentName, locationId, employeeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartmentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (departmentName != null ? "departmentName=" + departmentName + ", " : "") +
            (locationId != null ? "locationId=" + locationId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
