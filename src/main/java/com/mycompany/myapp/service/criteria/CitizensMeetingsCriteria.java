package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.CitizensMeetings} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CitizensMeetingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /citizens-meetings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CitizensMeetingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter meetDate;

    private StringFilter agenda;

    private FloatFilter amount;

    private IntegerFilter quantity;

    private StringFilter status;

    private BooleanFilter flag;

    private LongFilter citizenId;

    private Boolean distinct;

    public CitizensMeetingsCriteria() {}

    public CitizensMeetingsCriteria(CitizensMeetingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.meetDate = other.meetDate == null ? null : other.meetDate.copy();
        this.agenda = other.agenda == null ? null : other.agenda.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.flag = other.flag == null ? null : other.flag.copy();
        this.citizenId = other.citizenId == null ? null : other.citizenId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CitizensMeetingsCriteria copy() {
        return new CitizensMeetingsCriteria(this);
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

    public LocalDateFilter getMeetDate() {
        return meetDate;
    }

    public LocalDateFilter meetDate() {
        if (meetDate == null) {
            meetDate = new LocalDateFilter();
        }
        return meetDate;
    }

    public void setMeetDate(LocalDateFilter meetDate) {
        this.meetDate = meetDate;
    }

    public StringFilter getAgenda() {
        return agenda;
    }

    public StringFilter agenda() {
        if (agenda == null) {
            agenda = new StringFilter();
        }
        return agenda;
    }

    public void setAgenda(StringFilter agenda) {
        this.agenda = agenda;
    }

    public FloatFilter getAmount() {
        return amount;
    }

    public FloatFilter amount() {
        if (amount == null) {
            amount = new FloatFilter();
        }
        return amount;
    }

    public void setAmount(FloatFilter amount) {
        this.amount = amount;
    }

    public IntegerFilter getQuantity() {
        return quantity;
    }

    public IntegerFilter quantity() {
        if (quantity == null) {
            quantity = new IntegerFilter();
        }
        return quantity;
    }

    public void setQuantity(IntegerFilter quantity) {
        this.quantity = quantity;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public BooleanFilter getFlag() {
        return flag;
    }

    public BooleanFilter flag() {
        if (flag == null) {
            flag = new BooleanFilter();
        }
        return flag;
    }

    public void setFlag(BooleanFilter flag) {
        this.flag = flag;
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
        final CitizensMeetingsCriteria that = (CitizensMeetingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(meetDate, that.meetDate) &&
            Objects.equals(agenda, that.agenda) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(status, that.status) &&
            Objects.equals(flag, that.flag) &&
            Objects.equals(citizenId, that.citizenId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, meetDate, agenda, amount, quantity, status, flag, citizenId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitizensMeetingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (meetDate != null ? "meetDate=" + meetDate + ", " : "") +
            (agenda != null ? "agenda=" + agenda + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (flag != null ? "flag=" + flag + ", " : "") +
            (citizenId != null ? "citizenId=" + citizenId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
