package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.ZipCodes;
import com.mycompany.myapp.repository.ZipCodesRepository;
import com.mycompany.myapp.service.criteria.ZipCodesCriteria;
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
 * Service for executing complex queries for {@link ZipCodes} entities in the database.
 * The main input is a {@link ZipCodesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZipCodes} or a {@link Page} of {@link ZipCodes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZipCodesQueryService extends QueryService<ZipCodes> {

    private final Logger log = LoggerFactory.getLogger(ZipCodesQueryService.class);

    private final ZipCodesRepository zipCodesRepository;

    public ZipCodesQueryService(ZipCodesRepository zipCodesRepository) {
        this.zipCodesRepository = zipCodesRepository;
    }

    /**
     * Return a {@link List} of {@link ZipCodes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ZipCodes> findByCriteria(ZipCodesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ZipCodes> specification = createSpecification(criteria);
        return zipCodesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ZipCodes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ZipCodes> findByCriteria(ZipCodesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ZipCodes> specification = createSpecification(criteria);
        return zipCodesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ZipCodesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ZipCodes> specification = createSpecification(criteria);
        return zipCodesRepository.count(specification);
    }

    /**
     * Function to convert {@link ZipCodesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ZipCodes> createSpecification(ZipCodesCriteria criteria) {
        Specification<ZipCodes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ZipCodes_.id));
            }
            if (criteria.getStreet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreet(), ZipCodes_.street));
            }
            if (criteria.getArea() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArea(), ZipCodes_.area));
            }
            if (criteria.getFromNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFromNumber(), ZipCodes_.fromNumber));
            }
            if (criteria.getToNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getToNumber(), ZipCodes_.toNumber));
            }
            if (criteria.getCountryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCountryId(), root -> root.join(ZipCodes_.country, JoinType.LEFT).get(Countries_.id))
                    );
            }
            if (criteria.getRegionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRegionId(), root -> root.join(ZipCodes_.region, JoinType.LEFT).get(Regions_.id))
                    );
            }
            if (criteria.getCityId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCityId(), root -> root.join(ZipCodes_.city, JoinType.LEFT).get(Cities_.id))
                    );
            }
        }
        return specification;
    }
}
