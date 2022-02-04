package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.FileType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Files.
 */
@Entity
@Table(name = "files")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Type(type = "uuid-char")
    @Column(name = "uuid", length = 36)
    private UUID uuid;

    @Column(name = "filename")
    private String filename;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FileType fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "version")
    private String version;

    @Column(name = "mime")
    private String mime;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parent", "createdBy", "children" }, allowSetters = true)
    private Files parent;

    @ManyToOne
    private User createdBy;

    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parent", "createdBy", "children" }, allowSetters = true)
    private Set<Files> children = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Files id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Files uuid(UUID uuid) {
        this.setUuid(uuid);
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFilename() {
        return this.filename;
    }

    public Files filename(String filename) {
        this.setFilename(filename);
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public FileType getFileType() {
        return this.fileType;
    }

    public Files fileType(FileType fileType) {
        this.setFileType(fileType);
        return this;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public Files fileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDate getCreateDate() {
        return this.createDate;
    }

    public Files createDate(LocalDate createDate) {
        this.setCreateDate(createDate);
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Files filePath(String filePath) {
        this.setFilePath(filePath);
        return this;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getVersion() {
        return this.version;
    }

    public Files version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMime() {
        return this.mime;
    }

    public Files mime(String mime) {
        this.setMime(mime);
        return this;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public Files getParent() {
        return this.parent;
    }

    public void setParent(Files files) {
        this.parent = files;
    }

    public Files parent(Files files) {
        this.setParent(files);
        return this;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public Files createdBy(User user) {
        this.setCreatedBy(user);
        return this;
    }

    public Set<Files> getChildren() {
        return this.children;
    }

    public void setChildren(Set<Files> files) {
        if (this.children != null) {
            this.children.forEach(i -> i.setParent(null));
        }
        if (files != null) {
            files.forEach(i -> i.setParent(this));
        }
        this.children = files;
    }

    public Files children(Set<Files> files) {
        this.setChildren(files);
        return this;
    }

    public Files addChildren(Files files) {
        this.children.add(files);
        files.setParent(this);
        return this;
    }

    public Files removeChildren(Files files) {
        this.children.remove(files);
        files.setParent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Files)) {
            return false;
        }
        return id != null && id.equals(((Files) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Files{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", filename='" + getFilename() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", fileSize=" + getFileSize() +
            ", createDate='" + getCreateDate() + "'" +
            ", filePath='" + getFilePath() + "'" +
            ", version='" + getVersion() + "'" +
            ", mime='" + getMime() + "'" +
            "}";
    }
}
