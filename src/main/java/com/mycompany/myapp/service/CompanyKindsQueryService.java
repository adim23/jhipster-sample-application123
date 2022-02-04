package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CompanyKinds;
import com.mycompany.myapp.repository.CompanyKindsRepository;
import com.mycompany.myapp.service.criteria.CompanyKindsCriteria;
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
 * Service for executing complex queries for {@link CompanyKinds} entities in the database.
 * The main input is a {@link CompanyKindsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanyKinds} or a {@link Page} of {@link CompanyKinds} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyKindsQueryService extends QueryService<CompanyKinds> {

    private final Logger log = LoggerFactory.getLogger(CompanyKindsQueryService.class);

    private final CompanyKindsRepository companyKindsRepository;

    public CompanyKindsQueryService(CompanyKindsRepository companyKindsRepository) {
        this.companyKindsRepository = companyKindsRepository;
    }

    /**
     * Return a {@link List} of {@link CompanyKinds} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyKinds> findByCriteria(CompanyKindsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CompanyKinds> specification = createSpecification(criteria);
        return companyKindsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CompanyKinds} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyKinds> findByCriteria(CompanyKindsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CompanyKinds> specification = createSpecification(criteria);
        return companyKindsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanyKindsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CompanyKinds> specification = createSpecification(criteria);
        return companyKindsRepository.count(specification);
    }

    /**
     * Function to convert {@link CompanyKindsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CompanyKinds> createSpecification(CompanyKindsCriteria criteria) {
        Specification<CompanyKinds> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CompanyKinds_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CompanyKinds_.name));
            }
        }
        return specification;
    }
}
