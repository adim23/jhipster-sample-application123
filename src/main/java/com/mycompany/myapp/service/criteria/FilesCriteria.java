package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.FileType;
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
import tech.jhipster.service.filter.UUIDFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Files} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.FilesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FilesCriteria implements Serializable, Criteria {

    /**
     * Class for filtering FileType
     */
    public static class FileTypeFilter extends Filter<FileType> {

        public FileTypeFilter() {}

        public FileTypeFilter(FileTypeFilter filter) {
            super(filter);
        }

        @Override
        public FileTypeFilter copy() {
            return new FileTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter uuid;

    private StringFilter filename;

    private FileTypeFilter fileType;

    private LongFilter fileSize;

    private LocalDateFilter createDate;

    private StringFilter filePath;

    private StringFilter version;

    private StringFilter mime;

    private LongFilter parentId;

    private LongFilter createdById;

    private LongFilter childrenId;

    private Boolean distinct;

    public FilesCriteria() {}

    public FilesCriteria(FilesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uuid = other.uuid == null ? null : other.uuid.copy();
        this.filename = other.filename == null ? null : other.filename.copy();
        this.fileType = other.fileType == null ? null : other.fileType.copy();
        this.fileSize = other.fileSize == null ? null : other.fileSize.copy();
        this.createDate = other.createDate == null ? null : other.createDate.copy();
        this.filePath = other.filePath == null ? null : other.filePath.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.mime = other.mime == null ? null : other.mime.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
        this.createdById = other.createdById == null ? null : other.createdById.copy();
        this.childrenId = other.childrenId == null ? null : other.childrenId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FilesCriteria copy() {
        return new FilesCriteria(this);
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

    public UUIDFilter getUuid() {
        return uuid;
    }

    public UUIDFilter uuid() {
        if (uuid == null) {
            uuid = new UUIDFilter();
        }
        return uuid;
    }

    public void setUuid(UUIDFilter uuid) {
        this.uuid = uuid;
    }

    public StringFilter getFilename() {
        return filename;
    }

    public StringFilter filename() {
        if (filename == null) {
            filename = new StringFilter();
        }
        return filename;
    }

    public void setFilename(StringFilter filename) {
        this.filename = filename;
    }

    public FileTypeFilter getFileType() {
        return fileType;
    }

    public FileTypeFilter fileType() {
        if (fileType == null) {
            fileType = new FileTypeFilter();
        }
        return fileType;
    }

    public void setFileType(FileTypeFilter fileType) {
        this.fileType = fileType;
    }

    public LongFilter getFileSize() {
        return fileSize;
    }

    public LongFilter fileSize() {
        if (fileSize == null) {
            fileSize = new LongFilter();
        }
        return fileSize;
    }

    public void setFileSize(LongFilter fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateFilter getCreateDate() {
        return createDate;
    }

    public LocalDateFilter createDate() {
        if (createDate == null) {
            createDate = new LocalDateFilter();
        }
        return createDate;
    }

    public void setCreateDate(LocalDateFilter createDate) {
        this.createDate = createDate;
    }

    public StringFilter getFilePath() {
        return filePath;
    }

    public StringFilter filePath() {
        if (filePath == null) {
            filePath = new StringFilter();
        }
        return filePath;
    }

    public void setFilePath(StringFilter filePath) {
        this.filePath = filePath;
    }

    public StringFilter getVersion() {
        return version;
    }

    public StringFilter version() {
        if (version == null) {
            version = new StringFilter();
        }
        return version;
    }

    public void setVersion(StringFilter version) {
        this.version = version;
    }

    public StringFilter getMime() {
        return mime;
    }

    public StringFilter mime() {
        if (mime == null) {
            mime = new StringFilter();
        }
        return mime;
    }

    public void setMime(StringFilter mime) {
        this.mime = mime;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public LongFilter parentId() {
        if (parentId == null) {
            parentId = new LongFilter();
        }
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public LongFilter getCreatedById() {
        return createdById;
    }

    public LongFilter createdById() {
        if (createdById == null) {
            createdById = new LongFilter();
        }
        return createdById;
    }

    public void setCreatedById(LongFilter createdById) {
        this.createdById = createdById;
    }

    public LongFilter getChildrenId() {
        return childrenId;
    }

    public LongFilter childrenId() {
        if (childrenId == null) {
            childrenId = new LongFilter();
        }
        return childrenId;
    }

    public void setChildrenId(LongFilter childrenId) {
        this.childrenId = childrenId;
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
        final FilesCriteria that = (FilesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(filename, that.filename) &&
            Objects.equals(fileType, that.fileType) &&
            Objects.equals(fileSize, that.fileSize) &&
            Objects.equals(createDate, that.createDate) &&
            Objects.equals(filePath, that.filePath) &&
            Objects.equals(version, that.version) &&
            Objects.equals(mime, that.mime) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(createdById, that.createdById) &&
            Objects.equals(childrenId, that.childrenId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            uuid,
            filename,
            fileType,
            fileSize,
            createDate,
            filePath,
            version,
            mime,
            parentId,
            createdById,
            childrenId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FilesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (uuid != null ? "uuid=" + uuid + ", " : "") +
            (filename != null ? "filename=" + filename + ", " : "") +
            (fileType != null ? "fileType=" + fileType + ", " : "") +
            (fileSize != null ? "fileSize=" + fileSize + ", " : "") +
            (createDate != null ? "createDate=" + createDate + ", " : "") +
            (filePath != null ? "filePath=" + filePath + ", " : "") +
            (version != null ? "version=" + version + ", " : "") +
            (mime != null ? "mime=" + mime + ", " : "") +
            (parentId != null ? "parentId=" + parentId + ", " : "") +
            (createdById != null ? "createdById=" + createdById + ", " : "") +
            (childrenId != null ? "childrenId=" + childrenId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
