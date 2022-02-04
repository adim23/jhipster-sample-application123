package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Origins;
import com.mycompany.myapp.repository.OriginsRepository;
import com.mycompany.myapp.service.criteria.OriginsCriteria;
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
 * Service for executing complex queries for {@link Origins} entities in the database.
 * The main input is a {@link OriginsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Origins} or a {@link Page} of {@link Origins} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OriginsQueryService extends QueryService<Origins> {

    private final Logger log = LoggerFactory.getLogger(OriginsQueryService.class);

    private final OriginsRepository originsRepository;

    public OriginsQueryService(OriginsRepository originsRepository) {
        this.originsRepository = originsRepository;
    }

    /**
     * Return a {@link List} of {@link Origins} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Origins> findByCriteria(OriginsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Origins> specification = createSpecification(criteria);
        return originsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Origins} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Origins> findByCriteria(OriginsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Origins> specification = createSpecification(criteria);
        return originsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OriginsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Origins> specification = createSpecification(criteria);
        return originsRepository.count(specification);
    }

    /**
     * Function to convert {@link OriginsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Origins> createSpecification(OriginsCriteria criteria) {
        Specification<Origins> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Origins_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Origins_.name));
            }
        }
        return specification;
    }
}
