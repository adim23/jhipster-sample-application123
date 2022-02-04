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
 * Criteria class for the {@link com.mycompany.myapp.domain.Citizens} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CitizensResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /citizens?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CitizensCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter lastname;

    private StringFilter firstname;

    private StringFilter fathersName;

    private LocalDateFilter birthDate;

    private StringFilter giortazi;

    private BooleanFilter male;

    private IntegerFilter meLetter;

    private IntegerFilter meLabel;

    private LongFilter folderId;

    private LongFilter companyId;

    private LongFilter maritalStatusId;

    private LongFilter teamId;

    private LongFilter codeId;

    private LongFilter originId;

    private LongFilter jobId;

    private LongFilter phonesId;

    private LongFilter addressesId;

    private LongFilter socialId;

    private LongFilter emailsId;

    private LongFilter relationsId;

    private Boolean distinct;

    public CitizensCriteria() {}

    public CitizensCriteria(CitizensCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.lastname = other.lastname == null ? null : other.lastname.copy();
        this.firstname = other.firstname == null ? null : other.firstname.copy();
        this.fathersName = other.fathersName == null ? null : other.fathersName.copy();
        this.birthDate = other.birthDate == null ? null : other.birthDate.copy();
        this.giortazi = other.giortazi == null ? null : other.giortazi.copy();
        this.male = other.male == null ? null : other.male.copy();
        this.meLetter = other.meLetter == null ? null : other.meLetter.copy();
        this.meLabel = other.meLabel == null ? null : other.meLabel.copy();
        this.folderId = other.folderId == null ? null : other.folderId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.maritalStatusId = other.maritalStatusId == null ? null : other.maritalStatusId.copy();
        this.teamId = other.teamId == null ? null : other.teamId.copy();
        this.codeId = other.codeId == null ? null : other.codeId.copy();
        this.originId = other.originId == null ? null : other.originId.copy();
        this.jobId = other.jobId == null ? null : other.jobId.copy();
        this.phonesId = other.phonesId == null ? null : other.phonesId.copy();
        this.addressesId = other.addressesId == null ? null : other.addressesId.copy();
        this.socialId = other.socialId == null ? null : other.socialId.copy();
        this.emailsId = other.emailsId == null ? null : other.emailsId.copy();
        this.relationsId = other.relationsId == null ? null : other.relationsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CitizensCriteria copy() {
        return new CitizensCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getLastname() {
        return lastname;
    }

    public StringFilter lastname() {
        if (lastname == null) {
            lastname = new StringFilter();
        }
        return lastname;
    }

    public void setLastname(StringFilter lastname) {
        this.lastname = lastname;
    }

    public StringFilter getFirstname() {
        return firstname;
    }

    public StringFilter firstname() {
        if (firstname == null) {
            firstname = new StringFilter();
        }
        return firstname;
    }

    public void setFirstname(StringFilter firstname) {
        this.firstname = firstname;
    }

    public StringFilter getFathersName() {
        return fathersName;
    }

    public StringFilter fathersName() {
        if (fathersName == null) {
            fathersName = new StringFilter();
        }
        return fathersName;
    }

    public void setFathersName(StringFilter fathersName) {
        this.fathersName = fathersName;
    }

    public LocalDateFilter getBirthDate() {
        return birthDate;
    }

    public LocalDateFilter birthDate() {
        if (birthDate == null) {
            birthDate = new LocalDateFilter();
        }
        return birthDate;
    }

    public void setBirthDate(LocalDateFilter birthDate) {
        this.birthDate = birthDate;
    }

    public StringFilter getGiortazi() {
        return giortazi;
    }

    public StringFilter giortazi() {
        if (giortazi == null) {
            giortazi = new StringFilter();
        }
        return giortazi;
    }

    public void setGiortazi(StringFilter giortazi) {
        this.giortazi = giortazi;
    }

    public BooleanFilter getMale() {
        return male;
    }

    public BooleanFilter male() {
        if (male == null) {
            male = new BooleanFilter();
        }
        return male;
    }

    public void setMale(BooleanFilter male) {
        this.male = male;
    }

    public IntegerFilter getMeLetter() {
        return meLetter;
    }

    public IntegerFilter meLetter() {
        if (meLetter == null) {
            meLetter = new IntegerFilter();
        }
        return meLetter;
    }

    public void setMeLetter(IntegerFilter meLetter) {
        this.meLetter = meLetter;
    }

    public IntegerFilter getMeLabel() {
        return meLabel;
    }

    public IntegerFilter meLabel() {
        if (meLabel == null) {
            meLabel = new IntegerFilter();
        }
        return meLabel;
    }

    public void setMeLabel(IntegerFilter meLabel) {
        this.meLabel = meLabel;
    }

    public LongFilter getFolderId() {
        return folderId;
    }

    public LongFilter folderId() {
        if (folderId == null) {
            folderId = new LongFilter();
        }
        return folderId;
    }

    public void setFolderId(LongFilter folderId) {
        this.folderId = folderId;
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

    public LongFilter getMaritalStatusId() {
        return maritalStatusId;
    }

    public LongFilter maritalStatusId() {
        if (maritalStatusId == null) {
            maritalStatusId = new LongFilter();
        }
        return maritalStatusId;
    }

    public void setMaritalStatusId(LongFilter maritalStatusId) {
        this.maritalStatusId = maritalStatusId;
    }

    public LongFilter getTeamId() {
        return teamId;
    }

    public LongFilter teamId() {
        if (teamId == null) {
            teamId = new LongFilter();
        }
        return teamId;
    }

    public void setTeamId(LongFilter teamId) {
        this.teamId = teamId;
    }

    public LongFilter getCodeId() {
        return codeId;
    }

    public LongFilter codeId() {
        if (codeId == null) {
            codeId = new LongFilter();
        }
        return codeId;
    }

    public void setCodeId(LongFilter codeId) {
        this.codeId = codeId;
    }

    public LongFilter getOriginId() {
        return originId;
    }

    public LongFilter originId() {
        if (originId == null) {
            originId = new LongFilter();
        }
        return originId;
    }

    public void setOriginId(LongFilter originId) {
        this.originId = originId;
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

    public LongFilter getEmailsId() {
        return emailsId;
    }

    public LongFilter emailsId() {
        if (emailsId == null) {
            emailsId = new LongFilter();
        }
        return emailsId;
    }

    public void setEmailsId(LongFilter emailsId) {
        this.emailsId = emailsId;
    }

    public LongFilter getRelationsId() {
        return relationsId;
    }

    public LongFilter relationsId() {
        if (relationsId == null) {
            relationsId = new LongFilter();
        }
        return relationsId;
    }

    public void setRelationsId(LongFilter relationsId) {
        this.relationsId = relationsId;
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
        final CitizensCriteria that = (CitizensCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(lastname, that.lastname) &&
            Objects.equals(firstname, that.firstname) &&
            Objects.equals(fathersName, that.fathersName) &&
            Objects.equals(birthDate, that.birthDate) &&
            Objects.equals(giortazi, that.giortazi) &&
            Objects.equals(male, that.male) &&
            Objects.equals(meLetter, that.meLetter) &&
            Objects.equals(meLabel, that.meLabel) &&
            Objects.equals(folderId, that.folderId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(maritalStatusId, that.maritalStatusId) &&
            Objects.equals(teamId, that.teamId) &&
            Objects.equals(codeId, that.codeId) &&
            Objects.equals(originId, that.originId) &&
            Objects.equals(jobId, that.jobId) &&
            Objects.equals(phonesId, that.phonesId) &&
            Objects.equals(addressesId, that.addressesId) &&
            Objects.equals(socialId, that.socialId) &&
            Objects.equals(emailsId, that.emailsId) &&
            Objects.equals(relationsId, that.relationsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            lastname,
            firstname,
            fathersName,
            birthDate,
            giortazi,
            male,
            meLetter,
            meLabel,
            folderId,
            companyId,
            maritalStatusId,
            teamId,
            codeId,
            originId,
            jobId,
            phonesId,
            addressesId,
            socialId,
            emailsId,
            relationsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitizensCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (lastname != null ? "lastname=" + lastname + ", " : "") +
            (firstname != null ? "firstname=" + firstname + ", " : "") +
            (fathersName != null ? "fathersName=" + fathersName + ", " : "") +
            (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
            (giortazi != null ? "giortazi=" + giortazi + ", " : "") +
            (male != null ? "male=" + male + ", " : "") +
            (meLetter != null ? "meLetter=" + meLetter + ", " : "") +
            (meLabel != null ? "meLabel=" + meLabel + ", " : "") +
            (folderId != null ? "folderId=" + folderId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (maritalStatusId != null ? "maritalStatusId=" + maritalStatusId + ", " : "") +
            (teamId != null ? "teamId=" + teamId + ", " : "") +
            (codeId != null ? "codeId=" + codeId + ", " : "") +
            (originId != null ? "originId=" + originId + ", " : "") +
            (jobId != null ? "jobId=" + jobId + ", " : "") +
            (phonesId != null ? "phonesId=" + phonesId + ", " : "") +
            (addressesId != null ? "addressesId=" + addressesId + ", " : "") +
            (socialId != null ? "socialId=" + socialId + ", " : "") +
            (emailsId != null ? "emailsId=" + emailsId + ", " : "") +
            (relationsId != null ? "relationsId=" + relationsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
