package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Phones;
import com.mycompany.myapp.repository.PhonesRepository;
import com.mycompany.myapp.service.criteria.PhonesCriteria;
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
 * Service for executing complex queries for {@link Phones} entities in the database.
 * The main input is a {@link PhonesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Phones} or a {@link Page} of {@link Phones} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PhonesQueryService extends QueryService<Phones> {

    private final Logger log = LoggerFactory.getLogger(PhonesQueryService.class);

    private final PhonesRepository phonesRepository;

    public PhonesQueryService(PhonesRepository phonesRepository) {
        this.phonesRepository = phonesRepository;
    }

    /**
     * Return a {@link List} of {@link Phones} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Phones> findByCriteria(PhonesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Phones> specification = createSpecification(criteria);
        return phonesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Phones} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Phones> findByCriteria(PhonesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Phones> specification = createSpecification(criteria);
        return phonesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PhonesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Phones> specification = createSpecification(criteria);
        return phonesRepository.count(specification);
    }

    /**
     * Function to convert {@link PhonesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Phones> createSpecification(PhonesCriteria criteria) {
        Specification<Phones> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Phones_.id));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Phones_.phone));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Phones_.description));
            }
            if (criteria.getFavourite() != null) {
                specification = specification.and(buildSpecification(criteria.getFavourite(), Phones_.favourite));
            }
            if (criteria.getKindId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getKindId(), root -> root.join(Phones_.kind, JoinType.LEFT).get(PhoneTypes_.id))
                    );
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCompanyId(), root -> root.join(Phones_.company, JoinType.LEFT).get(Companies_.id))
                    );
            }
            if (criteria.getCitizenId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCitizenId(), root -> root.join(Phones_.citizen, JoinType.LEFT).get(Citizens_.id))
                    );
            }
        }
        return specification;
    }
}
