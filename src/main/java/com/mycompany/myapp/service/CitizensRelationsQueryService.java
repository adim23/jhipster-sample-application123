package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CitizensRelations;
import com.mycompany.myapp.repository.CitizensRelationsRepository;
import com.mycompany.myapp.service.criteria.CitizensRelationsCriteria;
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
 * Service for executing complex queries for {@link CitizensRelations} entities in the database.
 * The main input is a {@link CitizensRelationsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CitizensRelations} or a {@link Page} of {@link CitizensRelations} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CitizensRelationsQueryService extends QueryService<CitizensRelations> {

    private final Logger log = LoggerFactory.getLogger(CitizensRelationsQueryService.class);

    private final CitizensRelationsRepository citizensRelationsRepository;

    public CitizensRelationsQueryService(CitizensRelationsRepository citizensRelationsRepository) {
        this.citizensRelationsRepository = citizensRelationsRepository;
    }

    /**
     * Return a {@link List} of {@link CitizensRelations} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CitizensRelations> findByCriteria(CitizensRelationsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CitizensRelations> specification = createSpecification(criteria);
        return citizensRelationsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CitizensRelations} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CitizensRelations> findByCriteria(CitizensRelationsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CitizensRelations> specification = createSpecification(criteria);
        return citizensRelationsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CitizensRelationsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CitizensRelations> specification = createSpecification(criteria);
        return citizensRelationsRepository.count(specification);
    }

    /**
     * Function to convert {@link CitizensRelationsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CitizensRelations> createSpecification(CitizensRelationsCriteria criteria) {
        Specification<CitizensRelations> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CitizensRelations_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CitizensRelations_.name));
            }
            if (criteria.getCitizenId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCitizenId(),
                            root -> root.join(CitizensRelations_.citizen, JoinType.LEFT).get(Citizens_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
