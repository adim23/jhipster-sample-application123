package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CitizenFolders;
import com.mycompany.myapp.repository.CitizenFoldersRepository;
import com.mycompany.myapp.service.criteria.CitizenFoldersCriteria;
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
 * Service for executing complex queries for {@link CitizenFolders} entities in the database.
 * The main input is a {@link CitizenFoldersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CitizenFolders} or a {@link Page} of {@link CitizenFolders} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CitizenFoldersQueryService extends QueryService<CitizenFolders> {

    private final Logger log = LoggerFactory.getLogger(CitizenFoldersQueryService.class);

    private final CitizenFoldersRepository citizenFoldersRepository;

    public CitizenFoldersQueryService(CitizenFoldersRepository citizenFoldersRepository) {
        this.citizenFoldersRepository = citizenFoldersRepository;
    }

    /**
     * Return a {@link List} of {@link CitizenFolders} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CitizenFolders> findByCriteria(CitizenFoldersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CitizenFolders> specification = createSpecification(criteria);
        return citizenFoldersRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CitizenFolders} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CitizenFolders> findByCriteria(CitizenFoldersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CitizenFolders> specification = createSpecification(criteria);
        return citizenFoldersRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CitizenFoldersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CitizenFolders> specification = createSpecification(criteria);
        return citizenFoldersRepository.count(specification);
    }

    /**
     * Function to convert {@link CitizenFoldersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CitizenFolders> createSpecification(CitizenFoldersCriteria criteria) {
        Specification<CitizenFolders> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CitizenFolders_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CitizenFolders_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CitizenFolders_.description));
            }
            if (criteria.getSpecial() != null) {
                specification = specification.and(buildSpecification(criteria.getSpecial(), CitizenFolders_.special));
            }
        }
        return specification;
    }
}
