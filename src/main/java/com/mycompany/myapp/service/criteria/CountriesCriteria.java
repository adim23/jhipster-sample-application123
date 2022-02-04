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
 * Criteria class for the {@link com.mycompany.myapp.domain.Countries} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CountriesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /countries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CountriesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter iso;

    private StringFilter language;

    private StringFilter lang;

    private Boolean distinct;

    public CountriesCriteria() {}

    public CountriesCriteria(CountriesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.iso = other.iso == null ? null : other.iso.copy();
        this.language = other.language == null ? null : other.language.copy();
        this.lang = other.lang == null ? null : other.lang.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CountriesCriteria copy() {
        return new CountriesCriteria(this);
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

    public StringFilter getIso() {
        return iso;
    }

    public StringFilter iso() {
        if (iso == null) {
            iso = new StringFilter();
        }
        return iso;
    }

    public void setIso(StringFilter iso) {
        this.iso = iso;
    }

    public StringFilter getLanguage() {
        return language;
    }

    public StringFilter language() {
        if (language == null) {
            language = new StringFilter();
        }
        return language;
    }

    public void setLanguage(StringFilter language) {
        this.language = language;
    }

    public StringFilter getLang() {
        return lang;
    }

    public StringFilter lang() {
        if (lang == null) {
            lang = new StringFilter();
        }
        return lang;
    }

    public void setLang(StringFilter lang) {
        this.lang = lang;
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
        final CountriesCriteria that = (CountriesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(iso, that.iso) &&
            Objects.equals(language, that.language) &&
            Objects.equals(lang, that.lang) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, iso, language, lang, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountriesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (iso != null ? "iso=" + iso + ", " : "") +
            (language != null ? "language=" + language + ", " : "") +
            (lang != null ? "lang=" + lang + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
