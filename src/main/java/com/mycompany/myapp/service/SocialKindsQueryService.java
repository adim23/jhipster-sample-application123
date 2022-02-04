package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.SocialKinds;
import com.mycompany.myapp.repository.SocialKindsRepository;
import com.mycompany.myapp.service.criteria.SocialKindsCriteria;
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
 * Service for executing complex queries for {@link SocialKinds} entities in the database.
 * The main input is a {@link SocialKindsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SocialKinds} or a {@link Page} of {@link SocialKinds} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SocialKindsQueryService extends QueryService<SocialKinds> {

    private final Logger log = LoggerFactory.getLogger(SocialKindsQueryService.class);

    private final SocialKindsRepository socialKindsRepository;

    public SocialKindsQueryService(SocialKindsRepository socialKindsRepository) {
        this.socialKindsRepository = socialKindsRepository;
    }

    /**
     * Return a {@link List} of {@link SocialKinds} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SocialKinds> findByCriteria(SocialKindsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SocialKinds> specification = createSpecification(criteria);
        return socialKindsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SocialKinds} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SocialKinds> findByCriteria(SocialKindsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SocialKinds> specification = createSpecification(criteria);
        return socialKindsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SocialKindsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SocialKinds> specification = createSpecification(criteria);
        return socialKindsRepository.count(specification);
    }

    /**
     * Function to convert {@link SocialKindsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SocialKinds> createSpecification(SocialKindsCriteria criteria) {
        Specification<SocialKinds> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SocialKinds_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), SocialKinds_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SocialKinds_.name));
            }
            if (criteria.getCall() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCall(), SocialKinds_.call));
            }
        }
        return specification;
    }
}
