package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.MaritalStatus;
import com.mycompany.myapp.repository.MaritalStatusRepository;
import com.mycompany.myapp.service.criteria.MaritalStatusCriteria;
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
 * Service for executing complex queries for {@link MaritalStatus} entities in the database.
 * The main input is a {@link MaritalStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MaritalStatus} or a {@link Page} of {@link MaritalStatus} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MaritalStatusQueryService extends QueryService<MaritalStatus> {

    private final Logger log = LoggerFactory.getLogger(MaritalStatusQueryService.class);

    private final MaritalStatusRepository maritalStatusRepository;

    public MaritalStatusQueryService(MaritalStatusRepository maritalStatusRepository) {
        this.maritalStatusRepository = maritalStatusRepository;
    }

    /**
     * Return a {@link List} of {@link MaritalStatus} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MaritalStatus> findByCriteria(MaritalStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MaritalStatus> specification = createSpecification(criteria);
        return maritalStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MaritalStatus} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MaritalStatus> findByCriteria(MaritalStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MaritalStatus> specification = createSpecification(criteria);
        return maritalStatusRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MaritalStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MaritalStatus> specification = createSpecification(criteria);
        return maritalStatusRepository.count(specification);
    }

    /**
     * Function to convert {@link MaritalStatusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MaritalStatus> createSpecification(MaritalStatusCriteria criteria) {
        Specification<MaritalStatus> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MaritalStatus_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MaritalStatus_.name));
            }
        }
        return specification;
    }
}
