package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Countries;
import com.mycompany.myapp.repository.CountriesRepository;
import com.mycompany.myapp.service.criteria.CountriesCriteria;
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
 * Service for executing complex queries for {@link Countries} entities in the database.
 * The main input is a {@link CountriesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Countries} or a {@link Page} of {@link Countries} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CountriesQueryService extends QueryService<Countries> {

    private final Logger log = LoggerFactory.getLogger(CountriesQueryService.class);

    private final CountriesRepository countriesRepository;

    public CountriesQueryService(CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    /**
     * Return a {@link List} of {@link Countries} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Countries> findByCriteria(CountriesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Countries> specification = createSpecification(criteria);
        return countriesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Countries} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Countries> findByCriteria(CountriesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Countries> specification = createSpecification(criteria);
        return countriesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CountriesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Countries> specification = createSpecification(criteria);
        return countriesRepository.count(specification);
    }

    /**
     * Function to convert {@link CountriesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Countries> createSpecification(CountriesCriteria criteria) {
        Specification<Countries> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Countries_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Countries_.name));
            }
            if (criteria.getIso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIso(), Countries_.iso));
            }
            if (criteria.getLanguage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguage(), Countries_.language));
            }
            if (criteria.getLang() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLang(), Countries_.lang));
            }
        }
        return specification;
    }
}
