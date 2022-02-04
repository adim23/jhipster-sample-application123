package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Task entity.\n@author The JHipster team.
 */
@Schema(description = "Task entity.\n@author The JHipster team.")
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Min(value = 0L)
    @Max(value = 100L)
    @Column(name = "percent_completed")
    private Long percentCompleted;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dependsOn", "dependents", "jobs" }, allowSetters = true)
    private Task dependsOn;

    @OneToMany(mappedBy = "dependsOn")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dependsOn", "dependents", "jobs" }, allowSetters = true)
    private Set<Task> dependents = new HashSet<>();

    @ManyToMany(mappedBy = "tasks")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tasks", "employee" }, allowSetters = true)
    private Set<Job> jobs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Task id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Task title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Task description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Task startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Task endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getPercentCompleted() {
        return this.percentCompleted;
    }

    public Task percentCompleted(Long percentCompleted) {
        this.setPercentCompleted(percentCompleted);
        return this;
    }

    public void setPercentCompleted(Long percentCompleted) {
        this.percentCompleted = percentCompleted;
    }

    public Task getDependsOn() {
        return this.dependsOn;
    }

    public void setDependsOn(Task task) {
        this.dependsOn = task;
    }

    public Task dependsOn(Task task) {
        this.setDependsOn(task);
        return this;
    }

    public Set<Task> getDependents() {
        return this.dependents;
    }

    public void setDependents(Set<Task> tasks) {
        if (this.dependents != null) {
            this.dependents.forEach(i -> i.setDependsOn(null));
        }
        if (tasks != null) {
            tasks.forEach(i -> i.setDependsOn(this));
        }
        this.dependents = tasks;
    }

    public Task dependents(Set<Task> tasks) {
        this.setDependents(tasks);
        return this;
    }

    public Task addDependents(Task task) {
        this.dependents.add(task);
        task.setDependsOn(this);
        return this;
    }

    public Task removeDependents(Task task) {
        this.dependents.remove(task);
        task.setDependsOn(null);
        return this;
    }

    public Set<Job> getJobs() {
        return this.jobs;
    }

    public void setJobs(Set<Job> jobs) {
        if (this.jobs != null) {
            this.jobs.forEach(i -> i.removeTask(this));
        }
        if (jobs != null) {
            jobs.forEach(i -> i.addTask(this));
        }
        this.jobs = jobs;
    }

    public Task jobs(Set<Job> jobs) {
        this.setJobs(jobs);
        return this;
    }

    public Task addJob(Job job) {
        this.jobs.add(job);
        job.getTasks().add(this);
        return this;
    }

    public Task removeJob(Job job) {
        this.jobs.remove(job);
        job.getTasks().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", percentCompleted=" + getPercentCompleted() +
            "}";
    }
}
