package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CitizensMeetings;
import com.mycompany.myapp.repository.CitizensMeetingsRepository;
import com.mycompany.myapp.service.criteria.CitizensMeetingsCriteria;
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
 * Service for executing complex queries for {@link CitizensMeetings} entities in the database.
 * The main input is a {@link CitizensMeetingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CitizensMeetings} or a {@link Page} of {@link CitizensMeetings} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CitizensMeetingsQueryService extends QueryService<CitizensMeetings> {

    private final Logger log = LoggerFactory.getLogger(CitizensMeetingsQueryService.class);

    private final CitizensMeetingsRepository citizensMeetingsRepository;

    public CitizensMeetingsQueryService(CitizensMeetingsRepository citizensMeetingsRepository) {
        this.citizensMeetingsRepository = citizensMeetingsRepository;
    }

    /**
     * Return a {@link List} of {@link CitizensMeetings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CitizensMeetings> findByCriteria(CitizensMeetingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CitizensMeetings> specification = createSpecification(criteria);
        return citizensMeetingsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CitizensMeetings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CitizensMeetings> findByCriteria(CitizensMeetingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CitizensMeetings> specification = createSpecification(criteria);
        return citizensMeetingsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CitizensMeetingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CitizensMeetings> specification = createSpecification(criteria);
        return citizensMeetingsRepository.count(specification);
    }

    /**
     * Function to convert {@link CitizensMeetingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CitizensMeetings> createSpecification(CitizensMeetingsCriteria criteria) {
        Specification<CitizensMeetings> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CitizensMeetings_.id));
            }
            if (criteria.getMeetDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMeetDate(), CitizensMeetings_.meetDate));
            }
            if (criteria.getAgenda() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAgenda(), CitizensMeetings_.agenda));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), CitizensMeetings_.amount));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), CitizensMeetings_.quantity));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), CitizensMeetings_.status));
            }
            if (criteria.getFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getFlag(), CitizensMeetings_.flag));
            }
            if (criteria.getCitizenId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCitizenId(),
                            root -> root.join(CitizensMeetings_.citizen, JoinType.LEFT).get(Citizens_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
