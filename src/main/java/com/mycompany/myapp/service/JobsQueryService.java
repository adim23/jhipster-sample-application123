package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Jobs;
import com.mycompany.myapp.repository.JobsRepository;
import com.mycompany.myapp.service.criteria.JobsCriteria;
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
 * Service for executing complex queries for {@link Jobs} entities in the database.
 * The main input is a {@link JobsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Jobs} or a {@link Page} of {@link Jobs} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JobsQueryService extends QueryService<Jobs> {

    private final Logger log = LoggerFactory.getLogger(JobsQueryService.class);

    private final JobsRepository jobsRepository;

    public JobsQueryService(JobsRepository jobsRepository) {
        this.jobsRepository = jobsRepository;
    }

    /**
     * Return a {@link List} of {@link Jobs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Jobs> findByCriteria(JobsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Jobs> specification = createSpecification(criteria);
        return jobsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Jobs} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Jobs> findByCriteria(JobsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Jobs> specification = createSpecification(criteria);
        return jobsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JobsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Jobs> specification = createSpecification(criteria);
        return jobsRepository.count(specification);
    }

    /**
     * Function to convert {@link JobsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Jobs> createSpecification(JobsCriteria criteria) {
        Specification<Jobs> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Jobs_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Jobs_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Jobs_.description));
            }
        }
        return specification;
    }
}
