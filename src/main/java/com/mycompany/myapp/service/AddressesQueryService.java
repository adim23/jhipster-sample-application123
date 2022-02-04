package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Addresses;
import com.mycompany.myapp.repository.AddressesRepository;
import com.mycompany.myapp.service.criteria.AddressesCriteria;
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
 * Service for executing complex queries for {@link Addresses} entities in the database.
 * The main input is a {@link AddressesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Addresses} or a {@link Page} of {@link Addresses} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AddressesQueryService extends QueryService<Addresses> {

    private final Logger log = LoggerFactory.getLogger(AddressesQueryService.class);

    private final AddressesRepository addressesRepository;

    public AddressesQueryService(AddressesRepository addressesRepository) {
        this.addressesRepository = addressesRepository;
    }

    /**
     * Return a {@link List} of {@link Addresses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Addresses> findByCriteria(AddressesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Addresses> specification = createSpecification(criteria);
        return addressesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Addresses} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Addresses> findByCriteria(AddressesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Addresses> specification = createSpecification(criteria);
        return addressesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AddressesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Addresses> specification = createSpecification(criteria);
        return addressesRepository.count(specification);
    }

    /**
     * Function to convert {@link AddressesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Addresses> createSpecification(AddressesCriteria criteria) {
        Specification<Addresses> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Addresses_.id));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Addresses_.address));
            }
            if (criteria.getAddressNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressNo(), Addresses_.addressNo));
            }
            if (criteria.getZipCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZipCode(), Addresses_.zipCode));
            }
            if (criteria.getProsfLetter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProsfLetter(), Addresses_.prosfLetter));
            }
            if (criteria.getNameLetter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameLetter(), Addresses_.nameLetter));
            }
            if (criteria.getLetterClose() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLetterClose(), Addresses_.letterClose));
            }
            if (criteria.getFirstLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstLabel(), Addresses_.firstLabel));
            }
            if (criteria.getSecondLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSecondLabel(), Addresses_.secondLabel));
            }
            if (criteria.getThirdLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThirdLabel(), Addresses_.thirdLabel));
            }
            if (criteria.getFourthLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFourthLabel(), Addresses_.fourthLabel));
            }
            if (criteria.getFifthLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFifthLabel(), Addresses_.fifthLabel));
            }
            if (criteria.getFavourite() != null) {
                specification = specification.and(buildSpecification(criteria.getFavourite(), Addresses_.favourite));
            }
            if (criteria.getCountryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCountryId(), root -> root.join(Addresses_.country, JoinType.LEFT).get(Countries_.id))
                    );
            }
            if (criteria.getKindId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getKindId(), root -> root.join(Addresses_.kind, JoinType.LEFT).get(ContactTypes_.id))
                    );
            }
            if (criteria.getRegionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRegionId(), root -> root.join(Addresses_.region, JoinType.LEFT).get(Regions_.id))
                    );
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCompanyId(), root -> root.join(Addresses_.company, JoinType.LEFT).get(Companies_.id))
                    );
            }
            if (criteria.getCitizenId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCitizenId(), root -> root.join(Addresses_.citizen, JoinType.LEFT).get(Citizens_.id))
                    );
            }
        }
        return specification;
    }
}
