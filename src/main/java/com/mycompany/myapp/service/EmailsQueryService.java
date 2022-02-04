package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Emails;
import com.mycompany.myapp.repository.EmailsRepository;
import com.mycompany.myapp.service.criteria.EmailsCriteria;
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
 * Service for executing complex queries for {@link Emails} entities in the database.
 * The main input is a {@link EmailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Emails} or a {@link Page} of {@link Emails} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmailsQueryService extends QueryService<Emails> {

    private final Logger log = LoggerFactory.getLogger(EmailsQueryService.class);

    private final EmailsRepository emailsRepository;

    public EmailsQueryService(EmailsRepository emailsRepository) {
        this.emailsRepository = emailsRepository;
    }

    /**
     * Return a {@link List} of {@link Emails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Emails> findByCriteria(EmailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Emails> specification = createSpecification(criteria);
        return emailsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Emails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Emails> findByCriteria(EmailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Emails> specification = createSpecification(criteria);
        return emailsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Emails> specification = createSpecification(criteria);
        return emailsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Emails> createSpecification(EmailsCriteria criteria) {
        Specification<Emails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Emails_.id));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Emails_.email));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Emails_.description));
            }
            if (criteria.getFavourite() != null) {
                specification = specification.and(buildSpecification(criteria.getFavourite(), Emails_.favourite));
            }
            if (criteria.getKindId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getKindId(), root -> root.join(Emails_.kind, JoinType.LEFT).get(ContactTypes_.id))
                    );
            }
            if (criteria.getCitizenId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCitizenId(), root -> root.join(Emails_.citizen, JoinType.LEFT).get(Citizens_.id))
                    );
            }
        }
        return specification;
    }
}
