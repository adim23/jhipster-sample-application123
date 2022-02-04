package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Companies;
import com.mycompany.myapp.repository.CompaniesRepository;
import com.mycompany.myapp.service.criteria.CompaniesCriteria;
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
 * Service for executing complex queries for {@link Companies} entities in the database.
 * The main input is a {@link CompaniesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Companies} or a {@link Page} of {@link Companies} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompaniesQueryService extends QueryService<Companies> {

    private final Logger log = LoggerFactory.getLogger(CompaniesQueryService.class);

    private final CompaniesRepository companiesRepository;

    public CompaniesQueryService(CompaniesRepository companiesRepository) {
        this.companiesRepository = companiesRepository;
    }

    /**
     * Return a {@link List} of {@link Companies} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Companies> findByCriteria(CompaniesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Companies> specification = createSpecification(criteria);
        return companiesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Companies} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Companies> findByCriteria(CompaniesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Companies> specification = createSpecification(criteria);
        return companiesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompaniesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Companies> specification = createSpecification(criteria);
        return companiesRepository.count(specification);
    }

    /**
     * Function to convert {@link CompaniesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Companies> createSpecification(CompaniesCriteria criteria) {
        Specification<Companies> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Companies_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Companies_.name));
            }
            if (criteria.getKindId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getKindId(), root -> root.join(Companies_.kind, JoinType.LEFT).get(CompanyKinds_.id))
                    );
            }
            if (criteria.getPhonesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPhonesId(), root -> root.join(Companies_.phones, JoinType.LEFT).get(Phones_.id))
                    );
            }
            if (criteria.getAddressesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAddressesId(),
                            root -> root.join(Companies_.addresses, JoinType.LEFT).get(Addresses_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
