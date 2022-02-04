package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Employee entity.
 */
@Schema(description = "The Employee entity.")
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The firstname attribute.
     */
    @Schema(description = "The firstname attribute.", required = true)
    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotNull
    @Pattern(regexp = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date_time")
    private Instant hireDateTime;

    @Column(name = "zoned_hire_date_time")
    private ZonedDateTime zonedHireDateTime;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Min(value = 0L)
    @Column(name = "salary")
    private Long salary;

    @Min(value = 0L)
    @Max(value = 100L)
    @Column(name = "commission_pct")
    private Long commissionPct;

    @Column(name = "duration")
    private Duration duration;

    @Lob
    @Column(name = "pict")
    private byte[] pict;

    @Column(name = "pict_content_type")
    private String pictContentType;

    @Lob
    @Column(name = "comments")
    private String comments;

    @Lob
    @Column(name = "cv")
    private byte[] cv;

    @Column(name = "cv_content_type")
    private String cvContentType;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tasks", "employee" }, allowSetters = true)
    private Set<Job> jobs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "jobs", "manager", "department", "employees" }, allowSetters = true)
    private Employee manager;

    /**
     * Another side of the same relationship
     */
    @Schema(description = "Another side of the same relationship")
    @ManyToOne
    @JsonIgnoreProperties(value = { "location", "employees" }, allowSetters = true)
    private Department department;

    @OneToMany(mappedBy = "manager")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "jobs", "manager", "department", "employees" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Employee firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Employee lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Employee fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public Employee email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Employee phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getHireDateTime() {
        return this.hireDateTime;
    }

    public Employee hireDateTime(Instant hireDateTime) {
        this.setHireDateTime(hireDateTime);
        return this;
    }

    public void setHireDateTime(Instant hireDateTime) {
        this.hireDateTime = hireDateTime;
    }

    public ZonedDateTime getZonedHireDateTime() {
        return this.zonedHireDateTime;
    }

    public Employee zonedHireDateTime(ZonedDateTime zonedHireDateTime) {
        this.setZonedHireDateTime(zonedHireDateTime);
        return this;
    }

    public void setZonedHireDateTime(ZonedDateTime zonedHireDateTime) {
        this.zonedHireDateTime = zonedHireDateTime;
    }

    public LocalDate getHireDate() {
        return this.hireDate;
    }

    public Employee hireDate(LocalDate hireDate) {
        this.setHireDate(hireDate);
        return this;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Long getSalary() {
        return this.salary;
    }

    public Employee salary(Long salary) {
        this.setSalary(salary);
        return this;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Long getCommissionPct() {
        return this.commissionPct;
    }

    public Employee commissionPct(Long commissionPct) {
        this.setCommissionPct(commissionPct);
        return this;
    }

    public void setCommissionPct(Long commissionPct) {
        this.commissionPct = commissionPct;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public Employee duration(Duration duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public byte[] getPict() {
        return this.pict;
    }

    public Employee pict(byte[] pict) {
        this.setPict(pict);
        return this;
    }

    public void setPict(byte[] pict) {
        this.pict = pict;
    }

    public String getPictContentType() {
        return this.pictContentType;
    }

    public Employee pictContentType(String pictContentType) {
        this.pictContentType = pictContentType;
        return this;
    }

    public void setPictContentType(String pictContentType) {
        this.pictContentType = pictContentType;
    }

    public String getComments() {
        return this.comments;
    }

    public Employee comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public byte[] getCv() {
        return this.cv;
    }

    public Employee cv(byte[] cv) {
        this.setCv(cv);
        return this;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }

    public String getCvContentType() {
        return this.cvContentType;
    }

    public Employee cvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
        return this;
    }

    public void setCvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Employee active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Job> getJobs() {
        return this.jobs;
    }

    public void setJobs(Set<Job> jobs) {
        if (this.jobs != null) {
            this.jobs.forEach(i -> i.setEmployee(null));
        }
        if (jobs != null) {
            jobs.forEach(i -> i.setEmployee(this));
        }
        this.jobs = jobs;
    }

    public Employee jobs(Set<Job> jobs) {
        this.setJobs(jobs);
        return this;
    }

    public Employee addJob(Job job) {
        this.jobs.add(job);
        job.setEmployee(this);
        return this;
    }

    public Employee removeJob(Job job) {
        this.jobs.remove(job);
        job.setEmployee(null);
        return this;
    }

    public Employee getManager() {
        return this.manager;
    }

    public void setManager(Employee employee) {
        this.manager = employee;
    }

    public Employee manager(Employee employee) {
        this.setManager(employee);
        return this;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee department(Department department) {
        this.setDepartment(department);
        return this;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setManager(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setManager(this));
        }
        this.employees = employees;
    }

    public Employee employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Employee addEmployees(Employee employee) {
        this.employees.add(employee);
        employee.setManager(this);
        return this;
    }

    public Employee removeEmployees(Employee employee) {
        this.employees.remove(employee);
        employee.setManager(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hireDateTime='" + getHireDateTime() + "'" +
            ", zonedHireDateTime='" + getZonedHireDateTime() + "'" +
            ", hireDate='" + getHireDate() + "'" +
            ", salary=" + getSalary() +
            ", commissionPct=" + getCommissionPct() +
            ", duration='" + getDuration() + "'" +
            ", pict='" + getPict() + "'" +
            ", pictContentType='" + getPictContentType() + "'" +
            ", comments='" + getComments() + "'" +
            ", cv='" + getCv() + "'" +
            ", cvContentType='" + getCvContentType() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
