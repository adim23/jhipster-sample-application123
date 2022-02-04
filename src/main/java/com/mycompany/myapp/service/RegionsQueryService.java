package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Regions;
import com.mycompany.myapp.repository.RegionsRepository;
import com.mycompany.myapp.service.criteria.RegionsCriteria;
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
 * Service for executing complex queries for {@link Regions} entities in the database.
 * The main input is a {@link RegionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Regions} or a {@link Page} of {@link Regions} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegionsQueryService extends QueryService<Regions> {

    private final Logger log = LoggerFactory.getLogger(RegionsQueryService.class);

    private final RegionsRepository regionsRepository;

    public RegionsQueryService(RegionsRepository regionsRepository) {
        this.regionsRepository = regionsRepository;
    }

    /**
     * Return a {@link List} of {@link Regions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Regions> findByCriteria(RegionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Regions> specification = createSpecification(criteria);
        return regionsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Regions} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Regions> findByCriteria(RegionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Regions> specification = createSpecification(criteria);
        return regionsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Regions> specification = createSpecification(criteria);
        return regionsRepository.count(specification);
    }

    /**
     * Function to convert {@link RegionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Regions> createSpecification(RegionsCriteria criteria) {
        Specification<Regions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Regions_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Regions_.name));
            }
            if (criteria.getCountryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCountryId(), root -> root.join(Regions_.country, JoinType.LEFT).get(Countries_.id))
                    );
            }
        }
        return specification;
    }
}
