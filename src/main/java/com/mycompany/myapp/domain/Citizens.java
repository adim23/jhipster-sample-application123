package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Citizens.
 */
@Entity
@Table(name = "citizens")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Citizens implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "fathers_name")
    private String fathersName;

    @Lob
    @Column(name = "comments")
    private String comments;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "giortazi")
    private String giortazi;

    @Column(name = "male")
    private Boolean male;

    @Column(name = "me_letter")
    private Integer meLetter;

    @Column(name = "me_label")
    private Integer meLabel;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToOne
    private CitizenFolders folder;

    @ManyToOne
    @JsonIgnoreProperties(value = { "kind", "phones", "addresses" }, allowSetters = true)
    private Companies company;

    @ManyToOne
    private MaritalStatus maritalStatus;

    @ManyToOne
    private Teams team;

    @ManyToOne
    private Codes code;

    @ManyToOne
    private Origins origin;

    @ManyToOne
    private Jobs job;

    @OneToMany(mappedBy = "citizen")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "kind", "company", "citizen" }, allowSetters = true)
    private Set<Phones> phones = new HashSet<>();

    @OneToMany(mappedBy = "citizen")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "country", "kind", "region", "company", "citizen" }, allowSetters = true)
    private Set<Addresses> addresses = new HashSet<>();

    @OneToMany(mappedBy = "citizen")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "social", "kind", "citizen" }, allowSetters = true)
    private Set<SocialContacts> socials = new HashSet<>();

    @OneToMany(mappedBy = "citizen")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "kind", "citizen" }, allowSetters = true)
    private Set<Emails> emails = new HashSet<>();

    @OneToMany(mappedBy = "citizen")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "citizen" }, allowSetters = true)
    private Set<CitizensRelations> relations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Citizens id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Citizens title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastname() {
        return this.lastname;
    }

    public Citizens lastname(String lastname) {
        this.setLastname(lastname);
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public Citizens firstname(String firstname) {
        this.setFirstname(firstname);
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFathersName() {
        return this.fathersName;
    }

    public Citizens fathersName(String fathersName) {
        this.setFathersName(fathersName);
        return this;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getComments() {
        return this.comments;
    }

    public Citizens comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public Citizens birthDate(LocalDate birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGiortazi() {
        return this.giortazi;
    }

    public Citizens giortazi(String giortazi) {
        this.setGiortazi(giortazi);
        return this;
    }

    public void setGiortazi(String giortazi) {
        this.giortazi = giortazi;
    }

    public Boolean getMale() {
        return this.male;
    }

    public Citizens male(Boolean male) {
        this.setMale(male);
        return this;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public Integer getMeLetter() {
        return this.meLetter;
    }

    public Citizens meLetter(Integer meLetter) {
        this.setMeLetter(meLetter);
        return this;
    }

    public void setMeLetter(Integer meLetter) {
        this.meLetter = meLetter;
    }

    public Integer getMeLabel() {
        return this.meLabel;
    }

    public Citizens meLabel(Integer meLabel) {
        this.setMeLabel(meLabel);
        return this;
    }

    public void setMeLabel(Integer meLabel) {
        this.meLabel = meLabel;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Citizens image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Citizens imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public CitizenFolders getFolder() {
        return this.folder;
    }

    public void setFolder(CitizenFolders citizenFolders) {
        this.folder = citizenFolders;
    }

    public Citizens folder(CitizenFolders citizenFolders) {
        this.setFolder(citizenFolders);
        return this;
    }

    public Companies getCompany() {
        return this.company;
    }

    public void setCompany(Companies companies) {
        this.company = companies;
    }

    public Citizens company(Companies companies) {
        this.setCompany(companies);
        return this;
    }

    public MaritalStatus getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Citizens maritalStatus(MaritalStatus maritalStatus) {
        this.setMaritalStatus(maritalStatus);
        return this;
    }

    public Teams getTeam() {
        return this.team;
    }

    public void setTeam(Teams teams) {
        this.team = teams;
    }

    public Citizens team(Teams teams) {
        this.setTeam(teams);
        return this;
    }

    public Codes getCode() {
        return this.code;
    }

    public void setCode(Codes codes) {
        this.code = codes;
    }

    public Citizens code(Codes codes) {
        this.setCode(codes);
        return this;
    }

    public Origins getOrigin() {
        return this.origin;
    }

    public void setOrigin(Origins origins) {
        this.origin = origins;
    }

    public Citizens origin(Origins origins) {
        this.setOrigin(origins);
        return this;
    }

    public Jobs getJob() {
        return this.job;
    }

    public void setJob(Jobs jobs) {
        this.job = jobs;
    }

    public Citizens job(Jobs jobs) {
        this.setJob(jobs);
        return this;
    }

    public Set<Phones> getPhones() {
        return this.phones;
    }

    public void setPhones(Set<Phones> phones) {
        if (this.phones != null) {
            this.phones.forEach(i -> i.setCitizen(null));
        }
        if (phones != null) {
            phones.forEach(i -> i.setCitizen(this));
        }
        this.phones = phones;
    }

    public Citizens phones(Set<Phones> phones) {
        this.setPhones(phones);
        return this;
    }

    public Citizens addPhones(Phones phones) {
        this.phones.add(phones);
        phones.setCitizen(this);
        return this;
    }

    public Citizens removePhones(Phones phones) {
        this.phones.remove(phones);
        phones.setCitizen(null);
        return this;
    }

    public Set<Addresses> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Addresses> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setCitizen(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setCitizen(this));
        }
        this.addresses = addresses;
    }

    public Citizens addresses(Set<Addresses> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public Citizens addAddresses(Addresses addresses) {
        this.addresses.add(addresses);
        addresses.setCitizen(this);
        return this;
    }

    public Citizens removeAddresses(Addresses addresses) {
        this.addresses.remove(addresses);
        addresses.setCitizen(null);
        return this;
    }

    public Set<SocialContacts> getSocials() {
        return this.socials;
    }

    public void setSocials(Set<SocialContacts> socialContacts) {
        if (this.socials != null) {
            this.socials.forEach(i -> i.setCitizen(null));
        }
        if (socialContacts != null) {
            socialContacts.forEach(i -> i.setCitizen(this));
        }
        this.socials = socialContacts;
    }

    public Citizens socials(Set<SocialContacts> socialContacts) {
        this.setSocials(socialContacts);
        return this;
    }

    public Citizens addSocial(SocialContacts socialContacts) {
        this.socials.add(socialContacts);
        socialContacts.setCitizen(this);
        return this;
    }

    public Citizens removeSocial(SocialContacts socialContacts) {
        this.socials.remove(socialContacts);
        socialContacts.setCitizen(null);
        return this;
    }

    public Set<Emails> getEmails() {
        return this.emails;
    }

    public void setEmails(Set<Emails> emails) {
        if (this.emails != null) {
            this.emails.forEach(i -> i.setCitizen(null));
        }
        if (emails != null) {
            emails.forEach(i -> i.setCitizen(this));
        }
        this.emails = emails;
    }

    public Citizens emails(Set<Emails> emails) {
        this.setEmails(emails);
        return this;
    }

    public Citizens addEmails(Emails emails) {
        this.emails.add(emails);
        emails.setCitizen(this);
        return this;
    }

    public Citizens removeEmails(Emails emails) {
        this.emails.remove(emails);
        emails.setCitizen(null);
        return this;
    }

    public Set<CitizensRelations> getRelations() {
        return this.relations;
    }

    public void setRelations(Set<CitizensRelations> citizensRelations) {
        if (this.relations != null) {
            this.relations.forEach(i -> i.setCitizen(null));
        }
        if (citizensRelations != null) {
            citizensRelations.forEach(i -> i.setCitizen(this));
        }
        this.relations = citizensRelations;
    }

    public Citizens relations(Set<CitizensRelations> citizensRelations) {
        this.setRelations(citizensRelations);
        return this;
    }

    public Citizens addRelations(CitizensRelations citizensRelations) {
        this.relations.add(citizensRelations);
        citizensRelations.setCitizen(this);
        return this;
    }

    public Citizens removeRelations(CitizensRelations citizensRelations) {
        this.relations.remove(citizensRelations);
        citizensRelations.setCitizen(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Citizens)) {
            return false;
        }
        return id != null && id.equals(((Citizens) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Citizens{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", fathersName='" + getFathersName() + "'" +
            ", comments='" + getComments() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", giortazi='" + getGiortazi() + "'" +
            ", male='" + getMale() + "'" +
            ", meLetter=" + getMeLetter() +
            ", meLabel=" + getMeLabel() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
