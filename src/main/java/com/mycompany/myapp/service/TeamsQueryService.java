package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Teams;
import com.mycompany.myapp.repository.TeamsRepository;
import com.mycompany.myapp.service.criteria.TeamsCriteria;
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
 * Service for executing complex queries for {@link Teams} entities in the database.
 * The main input is a {@link TeamsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Teams} or a {@link Page} of {@link Teams} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TeamsQueryService extends QueryService<Teams> {

    private final Logger log = LoggerFactory.getLogger(TeamsQueryService.class);

    private final TeamsRepository teamsRepository;

    public TeamsQueryService(TeamsRepository teamsRepository) {
        this.teamsRepository = teamsRepository;
    }

    /**
     * Return a {@link List} of {@link Teams} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Teams> findByCriteria(TeamsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Teams> specification = createSpecification(criteria);
        return teamsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Teams} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Teams> findByCriteria(TeamsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Teams> specification = createSpecification(criteria);
        return teamsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TeamsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Teams> specification = createSpecification(criteria);
        return teamsRepository.count(specification);
    }

    /**
     * Function to convert {@link TeamsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Teams> createSpecification(TeamsCriteria criteria) {
        Specification<Teams> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Teams_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Teams_.name));
            }
        }
        return specification;
    }
}
