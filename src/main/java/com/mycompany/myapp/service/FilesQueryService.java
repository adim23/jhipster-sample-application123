package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Files;
import com.mycompany.myapp.repository.FilesRepository;
import com.mycompany.myapp.service.criteria.FilesCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Files} entities in the database.
 * The main input is a {@link FilesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Files} or a {@link Page} of {@link Files} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FilesQueryService extends QueryService<Files> {

    private final Logger log = LoggerFactory.getLogger(FilesQueryService.class);

    private final FilesRepository filesRepository;

    public FilesQueryService(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    /**
     * Return a {@link List} of {@link Files} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Files> findByCriteria(FilesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Files> specification = createSpecification(criteria);
        return filesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Files} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Files> findByCriteria(FilesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Files> specification = createSpecification(criteria);
        return filesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FilesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Files> specification = createSpecification(criteria);
        return filesRepository.count(specification);
    }

    /**
     * Function to convert {@link FilesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Files> createSpecification(FilesCriteria criteria) {
        Specification<Files> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Files_.id));
            }
            if (criteria.getUuid() != null) {
                specification = specification.and(buildSpecification(criteria.getUuid(), Files_.uuid));
            }
            if (criteria.getFilename() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFilename(), Files_.filename));
            }
            if (criteria.getFileType() != null) {
                specification = specification.and(buildSpecification(criteria.getFileType(), Files_.fileType));
            }
            if (criteria.getFileSize() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFileSize(), Files_.fileSize));
            }
            if (criteria.getCreateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateDate(), Files_.createDate));
            }
            if (criteria.getFilePath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFilePath(), Files_.filePath));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVersion(), Files_.version));
            }
            if (criteria.getMime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMime(), Files_.mime));
            }
            if (criteria.getParentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getParentId(), root -> root.join(Files_.parent, JoinType.LEFT).get(Files_.id))
                    );
            }
            if (criteria.getCreatedById() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCreatedById(), root -> root.join(Files_.createdBy, JoinType.LEFT).get(User_.id))
                    );
            }
            if (criteria.getChildrenId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getChildrenId(), root -> root.join(Files_.children, JoinType.LEFT).get(Files_.id))
                    );
            }
        }
        return specification;
    }
}
