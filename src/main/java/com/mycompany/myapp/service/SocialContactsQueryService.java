package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.SocialContacts;
import com.mycompany.myapp.repository.SocialContactsRepository;
import com.mycompany.myapp.service.criteria.SocialContactsCriteria;
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
 * Service for executing complex queries for {@link SocialContacts} entities in the database.
 * The main input is a {@link SocialContactsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SocialContacts} or a {@link Page} of {@link SocialContacts} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SocialContactsQueryService extends QueryService<SocialContacts> {

    private final Logger log = LoggerFactory.getLogger(SocialContactsQueryService.class);

    private final SocialContactsRepository socialContactsRepository;

    public SocialContactsQueryService(SocialContactsRepository socialContactsRepository) {
        this.socialContactsRepository = socialContactsRepository;
    }

    /**
     * Return a {@link List} of {@link SocialContacts} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SocialContacts> findByCriteria(SocialContactsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SocialContacts> specification = createSpecification(criteria);
        return socialContactsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SocialContacts} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SocialContacts> findByCriteria(SocialContactsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SocialContacts> specification = createSpecification(criteria);
        return socialContactsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SocialContactsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SocialContacts> specification = createSpecification(criteria);
        return socialContactsRepository.count(specification);
    }

    /**
     * Function to convert {@link SocialContactsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SocialContacts> createSpecification(SocialContactsCriteria criteria) {
        Specification<SocialContacts> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SocialContacts_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SocialContacts_.name));
            }
            if (criteria.getFavored() != null) {
                specification = specification.and(buildSpecification(criteria.getFavored(), SocialContacts_.favored));
            }
            if (criteria.getSocialId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocialId(),
                            root -> root.join(SocialContacts_.social, JoinType.LEFT).get(SocialKinds_.id)
                        )
                    );
            }
            if (criteria.getKindId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getKindId(),
                            root -> root.join(SocialContacts_.kind, JoinType.LEFT).get(ContactTypes_.id)
                        )
                    );
            }
            if (criteria.getCitizenId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCitizenId(),
                            root -> root.join(SocialContacts_.citizen, JoinType.LEFT).get(Citizens_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
