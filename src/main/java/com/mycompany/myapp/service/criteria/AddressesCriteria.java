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
 * Criteria class for the {@link com.mycompany.myapp.domain.Addresses} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.AddressesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AddressesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter address;

    private StringFilter addressNo;

    private StringFilter zipCode;

    private StringFilter prosfLetter;

    private StringFilter nameLetter;

    private StringFilter letterClose;

    private StringFilter firstLabel;

    private StringFilter secondLabel;

    private StringFilter thirdLabel;

    private StringFilter fourthLabel;

    private StringFilter fifthLabel;

    private BooleanFilter favourite;

    private LongFilter countryId;

    private LongFilter kindId;

    private LongFilter regionId;

    private LongFilter companyId;

    private LongFilter citizenId;

    private Boolean distinct;

    public AddressesCriteria() {}

    public AddressesCriteria(AddressesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.addressNo = other.addressNo == null ? null : other.addressNo.copy();
        this.zipCode = other.zipCode == null ? null : other.zipCode.copy();
        this.prosfLetter = other.prosfLetter == null ? null : other.prosfLetter.copy();
        this.nameLetter = other.nameLetter == null ? null : other.nameLetter.copy();
        this.letterClose = other.letterClose == null ? null : other.letterClose.copy();
        this.firstLabel = other.firstLabel == null ? null : other.firstLabel.copy();
        this.secondLabel = other.secondLabel == null ? null : other.secondLabel.copy();
        this.thirdLabel = other.thirdLabel == null ? null : other.thirdLabel.copy();
        this.fourthLabel = other.fourthLabel == null ? null : other.fourthLabel.copy();
        this.fifthLabel = other.fifthLabel == null ? null : other.fifthLabel.copy();
        this.favourite = other.favourite == null ? null : other.favourite.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
        this.kindId = other.kindId == null ? null : other.kindId.copy();
        this.regionId = other.regionId == null ? null : other.regionId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.citizenId = other.citizenId == null ? null : other.citizenId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AddressesCriteria copy() {
        return new AddressesCriteria(this);
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

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getAddressNo() {
        return addressNo;
    }

    public StringFilter addressNo() {
        if (addressNo == null) {
            addressNo = new StringFilter();
        }
        return addressNo;
    }

    public void setAddressNo(StringFilter addressNo) {
        this.addressNo = addressNo;
    }

    public StringFilter getZipCode() {
        return zipCode;
    }

    public StringFilter zipCode() {
        if (zipCode == null) {
            zipCode = new StringFilter();
        }
        return zipCode;
    }

    public void setZipCode(StringFilter zipCode) {
        this.zipCode = zipCode;
    }

    public StringFilter getProsfLetter() {
        return prosfLetter;
    }

    public StringFilter prosfLetter() {
        if (prosfLetter == null) {
            prosfLetter = new StringFilter();
        }
        return prosfLetter;
    }

    public void setProsfLetter(StringFilter prosfLetter) {
        this.prosfLetter = prosfLetter;
    }

    public StringFilter getNameLetter() {
        return nameLetter;
    }

    public StringFilter nameLetter() {
        if (nameLetter == null) {
            nameLetter = new StringFilter();
        }
        return nameLetter;
    }

    public void setNameLetter(StringFilter nameLetter) {
        this.nameLetter = nameLetter;
    }

    public StringFilter getLetterClose() {
        return letterClose;
    }

    public StringFilter letterClose() {
        if (letterClose == null) {
            letterClose = new StringFilter();
        }
        return letterClose;
    }

    public void setLetterClose(StringFilter letterClose) {
        this.letterClose = letterClose;
    }

    public StringFilter getFirstLabel() {
        return firstLabel;
    }

    public StringFilter firstLabel() {
        if (firstLabel == null) {
            firstLabel = new StringFilter();
        }
        return firstLabel;
    }

    public void setFirstLabel(StringFilter firstLabel) {
        this.firstLabel = firstLabel;
    }

    public StringFilter getSecondLabel() {
        return secondLabel;
    }

    public StringFilter secondLabel() {
        if (secondLabel == null) {
            secondLabel = new StringFilter();
        }
        return secondLabel;
    }

    public void setSecondLabel(StringFilter secondLabel) {
        this.secondLabel = secondLabel;
    }

    public StringFilter getThirdLabel() {
        return thirdLabel;
    }

    public StringFilter thirdLabel() {
        if (thirdLabel == null) {
            thirdLabel = new StringFilter();
        }
        return thirdLabel;
    }

    public void setThirdLabel(StringFilter thirdLabel) {
        this.thirdLabel = thirdLabel;
    }

    public StringFilter getFourthLabel() {
        return fourthLabel;
    }

    public StringFilter fourthLabel() {
        if (fourthLabel == null) {
            fourthLabel = new StringFilter();
        }
        return fourthLabel;
    }

    public void setFourthLabel(StringFilter fourthLabel) {
        this.fourthLabel = fourthLabel;
    }

    public StringFilter getFifthLabel() {
        return fifthLabel;
    }

    public StringFilter fifthLabel() {
        if (fifthLabel == null) {
            fifthLabel = new StringFilter();
        }
        return fifthLabel;
    }

    public void setFifthLabel(StringFilter fifthLabel) {
        this.fifthLabel = fifthLabel;
    }

    public BooleanFilter getFavourite() {
        return favourite;
    }

    public BooleanFilter favourite() {
        if (favourite == null) {
            favourite = new BooleanFilter();
        }
        return favourite;
    }

    public void setFavourite(BooleanFilter favourite) {
        this.favourite = favourite;
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
        final AddressesCriteria that = (AddressesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(address, that.address) &&
            Objects.equals(addressNo, that.addressNo) &&
            Objects.equals(zipCode, that.zipCode) &&
            Objects.equals(prosfLetter, that.prosfLetter) &&
            Objects.equals(nameLetter, that.nameLetter) &&
            Objects.equals(letterClose, that.letterClose) &&
            Objects.equals(firstLabel, that.firstLabel) &&
            Objects.equals(secondLabel, that.secondLabel) &&
            Objects.equals(thirdLabel, that.thirdLabel) &&
            Objects.equals(fourthLabel, that.fourthLabel) &&
            Objects.equals(fifthLabel, that.fifthLabel) &&
            Objects.equals(favourite, that.favourite) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(kindId, that.kindId) &&
            Objects.equals(regionId, that.regionId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(citizenId, that.citizenId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            address,
            addressNo,
            zipCode,
            prosfLetter,
            nameLetter,
            letterClose,
            firstLabel,
            secondLabel,
            thirdLabel,
            fourthLabel,
            fifthLabel,
            favourite,
            countryId,
            kindId,
            regionId,
            companyId,
            citizenId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (addressNo != null ? "addressNo=" + addressNo + ", " : "") +
            (zipCode != null ? "zipCode=" + zipCode + ", " : "") +
            (prosfLetter != null ? "prosfLetter=" + prosfLetter + ", " : "") +
            (nameLetter != null ? "nameLetter=" + nameLetter + ", " : "") +
            (letterClose != null ? "letterClose=" + letterClose + ", " : "") +
            (firstLabel != null ? "firstLabel=" + firstLabel + ", " : "") +
            (secondLabel != null ? "secondLabel=" + secondLabel + ", " : "") +
            (thirdLabel != null ? "thirdLabel=" + thirdLabel + ", " : "") +
            (fourthLabel != null ? "fourthLabel=" + fourthLabel + ", " : "") +
            (fifthLabel != null ? "fifthLabel=" + fifthLabel + ", " : "") +
            (favourite != null ? "favourite=" + favourite + ", " : "") +
            (countryId != null ? "countryId=" + countryId + ", " : "") +
            (kindId != null ? "kindId=" + kindId + ", " : "") +
            (regionId != null ? "regionId=" + regionId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (citizenId != null ? "citizenId=" + citizenId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
