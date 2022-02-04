package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Cities;
import com.mycompany.myapp.repository.CitiesRepository;
import com.mycompany.myapp.service.criteria.CitiesCriteria;
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
 * Service for executing complex queries for {@link Cities} entities in the database.
 * The main input is a {@link CitiesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Cities} or a {@link Page} of {@link Cities} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CitiesQueryService extends QueryService<Cities> {

    private final Logger log = LoggerFactory.getLogger(CitiesQueryService.class);

    private final CitiesRepository citiesRepository;

    public CitiesQueryService(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    /**
     * Return a {@link List} of {@link Cities} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Cities> findByCriteria(CitiesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cities> specification = createSpecification(criteria);
        return citiesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Cities} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Cities> findByCriteria(CitiesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cities> specification = createSpecification(criteria);
        return citiesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CitiesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cities> specification = createSpecification(criteria);
        return citiesRepository.count(specification);
    }

    /**
     * Function to convert {@link CitiesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cities> createSpecification(CitiesCriteria criteria) {
        Specification<Cities> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cities_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Cities_.name));
            }
            if (criteria.getPresident() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPresident(), Cities_.president));
            }
            if (criteria.getPresidentsPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPresidentsPhone(), Cities_.presidentsPhone));
            }
            if (criteria.getSecretary() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSecretary(), Cities_.secretary));
            }
            if (criteria.getSecretarysPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSecretarysPhone(), Cities_.secretarysPhone));
            }
            if (criteria.getPolice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPolice(), Cities_.police));
            }
            if (criteria.getPolicesPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPolicesPhone(), Cities_.policesPhone));
            }
            if (criteria.getDoctor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDoctor(), Cities_.doctor));
            }
            if (criteria.getDoctorsPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDoctorsPhone(), Cities_.doctorsPhone));
            }
            if (criteria.getTeacher() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeacher(), Cities_.teacher));
            }
            if (criteria.getTeachersPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeachersPhone(), Cities_.teachersPhone));
            }
            if (criteria.getPriest() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPriest(), Cities_.priest));
            }
            if (criteria.getPriestsPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPriestsPhone(), Cities_.priestsPhone));
            }
            if (criteria.getCountryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCountryId(), root -> root.join(Cities_.country, JoinType.LEFT).get(Countries_.id))
                    );
            }
            if (criteria.getRegionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRegionId(), root -> root.join(Cities_.region, JoinType.LEFT).get(Regions_.id))
                    );
            }
        }
        return specification;
    }
}
