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
 * Criteria class for the {@link com.mycompany.myapp.domain.SocialContacts} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.SocialContactsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /social-contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SocialContactsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter favored;

    private LongFilter socialId;

    private LongFilter kindId;

    private LongFilter citizenId;

    private Boolean distinct;

    public SocialContactsCriteria() {}

    public SocialContactsCriteria(SocialContactsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.favored = other.favored == null ? null : other.favored.copy();
        this.socialId = other.socialId == null ? null : other.socialId.copy();
        this.kindId = other.kindId == null ? null : other.kindId.copy();
        this.citizenId = other.citizenId == null ? null : other.citizenId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SocialContactsCriteria copy() {
        return new SocialContactsCriteria(this);
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

    public BooleanFilter getFavored() {
        return favored;
    }

    public BooleanFilter favored() {
        if (favored == null) {
            favored = new BooleanFilter();
        }
        return favored;
    }

    public void setFavored(BooleanFilter favored) {
        this.favored = favored;
    }

    public LongFilter getSocialId() {
        return socialId;
    }

    public LongFilter socialId() {
        if (socialId == null) {
            socialId = new LongFilter();
        }
        return socialId;
    }

    public void setSocialId(LongFilter socialId) {
        this.socialId = socialId;
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
        final SocialContactsCriteria that = (SocialContactsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(favored, that.favored) &&
            Objects.equals(socialId, that.socialId) &&
            Objects.equals(kindId, that.kindId) &&
            Objects.equals(citizenId, that.citizenId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, favored, socialId, kindId, citizenId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocialContactsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (favored != null ? "favored=" + favored + ", " : "") +
            (socialId != null ? "socialId=" + socialId + ", " : "") +
            (kindId != null ? "kindId=" + kindId + ", " : "") +
            (citizenId != null ? "citizenId=" + citizenId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
