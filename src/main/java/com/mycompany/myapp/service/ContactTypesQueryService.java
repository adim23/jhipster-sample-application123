package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.ContactTypes;
import com.mycompany.myapp.repository.ContactTypesRepository;
import com.mycompany.myapp.service.criteria.ContactTypesCriteria;
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
 * Service for executing complex queries for {@link ContactTypes} entities in the database.
 * The main input is a {@link ContactTypesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContactTypes} or a {@link Page} of {@link ContactTypes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContactTypesQueryService extends QueryService<ContactTypes> {

    private final Logger log = LoggerFactory.getLogger(ContactTypesQueryService.class);

    private final ContactTypesRepository contactTypesRepository;

    public ContactTypesQueryService(ContactTypesRepository contactTypesRepository) {
        this.contactTypesRepository = contactTypesRepository;
    }

    /**
     * Return a {@link List} of {@link ContactTypes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContactTypes> findByCriteria(ContactTypesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ContactTypes> specification = createSpecification(criteria);
        return contactTypesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ContactTypes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactTypes> findByCriteria(ContactTypesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ContactTypes> specification = createSpecification(criteria);
        return contactTypesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContactTypesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ContactTypes> specification = createSpecification(criteria);
        return contactTypesRepository.count(specification);
    }

    /**
     * Function to convert {@link ContactTypesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ContactTypes> createSpecification(ContactTypesCriteria criteria) {
        Specification<ContactTypes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ContactTypes_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ContactTypes_.name));
            }
        }
        return specification;
    }
}
