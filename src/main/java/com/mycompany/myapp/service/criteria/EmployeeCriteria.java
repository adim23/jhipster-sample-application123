package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.DurationFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Employee} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter fullName;

    private StringFilter email;

    private StringFilter phoneNumber;

    private InstantFilter hireDateTime;

    private ZonedDateTimeFilter zonedHireDateTime;

    private LocalDateFilter hireDate;

    private LongFilter salary;

    private LongFilter commissionPct;

    private DurationFilter duration;

    private BooleanFilter active;

    private LongFilter jobId;

    private LongFilter managerId;

    private LongFilter departmentId;

    private LongFilter employeesId;

    private Boolean distinct;

    public EmployeeCriteria() {}

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.hireDateTime = other.hireDateTime == null ? null : other.hireDateTime.copy();
        this.zonedHireDateTime = other.zonedHireDateTime == null ? null : other.zonedHireDateTime.copy();
        this.hireDate = other.hireDate == null ? null : other.hireDate.copy();
        this.salary = other.salary == null ? null : other.salary.copy();
        this.commissionPct = other.commissionPct == null ? null : other.commissionPct.copy();
        this.duration = other.duration == null ? null : other.duration.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.jobId = other.jobId == null ? null : other.jobId.copy();
        this.managerId = other.managerId == null ? null : other.managerId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.employeesId = other.employeesId == null ? null : other.employeesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
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

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public StringFilter fullName() {
        if (fullName == null) {
            fullName = new StringFilter();
        }
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public StringFilter phoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new StringFilter();
        }
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public InstantFilter getHireDateTime() {
        return hireDateTime;
    }

    public InstantFilter hireDateTime() {
        if (hireDateTime == null) {
            hireDateTime = new InstantFilter();
        }
        return hireDateTime;
    }

    public void setHireDateTime(InstantFilter hireDateTime) {
        this.hireDateTime = hireDateTime;
    }

    public ZonedDateTimeFilter getZonedHireDateTime() {
        return zonedHireDateTime;
    }

    public ZonedDateTimeFilter zonedHireDateTime() {
        if (zonedHireDateTime == null) {
            zonedHireDateTime = new ZonedDateTimeFilter();
        }
        return zonedHireDateTime;
    }

    public void setZonedHireDateTime(ZonedDateTimeFilter zonedHireDateTime) {
        this.zonedHireDateTime = zonedHireDateTime;
    }

    public LocalDateFilter getHireDate() {
        return hireDate;
    }

    public LocalDateFilter hireDate() {
        if (hireDate == null) {
            hireDate = new LocalDateFilter();
        }
        return hireDate;
    }

    public void setHireDate(LocalDateFilter hireDate) {
        this.hireDate = hireDate;
    }

    public LongFilter getSalary() {
        return salary;
    }

    public LongFilter salary() {
        if (salary == null) {
            salary = new LongFilter();
        }
        return salary;
    }

    public void setSalary(LongFilter salary) {
        this.salary = salary;
    }

    public LongFilter getCommissionPct() {
        return commissionPct;
    }

    public LongFilter commissionPct() {
        if (commissionPct == null) {
            commissionPct = new LongFilter();
        }
        return commissionPct;
    }

    public void setCommissionPct(LongFilter commissionPct) {
        this.commissionPct = commissionPct;
    }

    public DurationFilter getDuration() {
        return duration;
    }

    public DurationFilter duration() {
        if (duration == null) {
            duration = new DurationFilter();
        }
        return duration;
    }

    public void setDuration(DurationFilter duration) {
        this.duration = duration;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public BooleanFilter active() {
        if (active == null) {
            active = new BooleanFilter();
        }
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getJobId() {
        return jobId;
    }

    public LongFilter jobId() {
        if (jobId == null) {
            jobId = new LongFilter();
        }
        return jobId;
    }

    public void setJobId(LongFilter jobId) {
        this.jobId = jobId;
    }

    public LongFilter getManagerId() {
        return managerId;
    }

    public LongFilter managerId() {
        if (managerId == null) {
            managerId = new LongFilter();
        }
        return managerId;
    }

    public void setManagerId(LongFilter managerId) {
        this.managerId = managerId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public LongFilter departmentId() {
        if (departmentId == null) {
            departmentId = new LongFilter();
        }
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public LongFilter getEmployeesId() {
        return employeesId;
    }

    public LongFilter employeesId() {
        if (employeesId == null) {
            employeesId = new LongFilter();
        }
        return employeesId;
    }

    public void setEmployeesId(LongFilter employeesId) {
        this.employeesId = employeesId;
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
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(hireDateTime, that.hireDateTime) &&
            Objects.equals(zonedHireDateTime, that.zonedHireDateTime) &&
            Objects.equals(hireDate, that.hireDate) &&
            Objects.equals(salary, that.salary) &&
            Objects.equals(commissionPct, that.commissionPct) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(active, that.active) &&
            Objects.equals(jobId, that.jobId) &&
            Objects.equals(managerId, that.managerId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(employeesId, that.employeesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            firstName,
            lastName,
            fullName,
            email,
            phoneNumber,
            hireDateTime,
            zonedHireDateTime,
            hireDate,
            salary,
            commissionPct,
            duration,
            active,
            jobId,
            managerId,
            departmentId,
            employeesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (fullName != null ? "fullName=" + fullName + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (hireDateTime != null ? "hireDateTime=" + hireDateTime + ", " : "") +
            (zonedHireDateTime != null ? "zonedHireDateTime=" + zonedHireDateTime + ", " : "") +
            (hireDate != null ? "hireDate=" + hireDate + ", " : "") +
            (salary != null ? "salary=" + salary + ", " : "") +
            (commissionPct != null ? "commissionPct=" + commissionPct + ", " : "") +
            (duration != null ? "duration=" + duration + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (jobId != null ? "jobId=" + jobId + ", " : "") +
            (managerId != null ? "managerId=" + managerId + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (employeesId != null ? "employeesId=" + employeesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
