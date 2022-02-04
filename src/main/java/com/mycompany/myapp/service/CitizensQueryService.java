package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Citizens;
import com.mycompany.myapp.repository.CitizensRepository;
import com.mycompany.myapp.service.criteria.CitizensCriteria;
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
 * Service for executing complex queries for {@link Citizens} entities in the database.
 * The main input is a {@link CitizensCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Citizens} or a {@link Page} of {@link Citizens} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CitizensQueryService extends QueryService<Citizens> {

    private final Logger log = LoggerFactory.getLogger(CitizensQueryService.class);

    private final CitizensRepository citizensRepository;

    public CitizensQueryService(CitizensRepository citizensRepository) {
        this.citizensRepository = citizensRepository;
    }

    /**
     * Return a {@link List} of {@link Citizens} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Citizens> findByCriteria(CitizensCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Citizens> specification = createSpecification(criteria);
        return citizensRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Citizens} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Citizens> findByCriteria(CitizensCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Citizens> specification = createSpecification(criteria);
        return citizensRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CitizensCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Citizens> specification = createSpecification(criteria);
        return citizensRepository.count(specification);
    }

    /**
     * Function to convert {@link CitizensCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Citizens> createSpecification(CitizensCriteria criteria) {
        Specification<Citizens> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Citizens_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Citizens_.title));
            }
            if (criteria.getLastname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastname(), Citizens_.lastname));
            }
            if (criteria.getFirstname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstname(), Citizens_.firstname));
            }
            if (criteria.getFathersName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFathersName(), Citizens_.fathersName));
            }
            if (criteria.getBirthDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthDate(), Citizens_.birthDate));
            }
            if (criteria.getGiortazi() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGiortazi(), Citizens_.giortazi));
            }
            if (criteria.getMale() != null) {
                specification = specification.and(buildSpecification(criteria.getMale(), Citizens_.male));
            }
            if (criteria.getMeLetter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMeLetter(), Citizens_.meLetter));
            }
            if (criteria.getMeLabel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMeLabel(), Citizens_.meLabel));
            }
            if (criteria.getFolderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFolderId(),
                            root -> root.join(Citizens_.folder, JoinType.LEFT).get(CitizenFolders_.id)
                        )
                    );
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCompanyId(), root -> root.join(Citizens_.company, JoinType.LEFT).get(Companies_.id))
                    );
            }
            if (criteria.getMaritalStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMaritalStatusId(),
                            root -> root.join(Citizens_.maritalStatus, JoinType.LEFT).get(MaritalStatus_.id)
                        )
                    );
            }
            if (criteria.getTeamId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTeamId(), root -> root.join(Citizens_.team, JoinType.LEFT).get(Teams_.id))
                    );
            }
            if (criteria.getCodeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCodeId(), root -> root.join(Citizens_.code, JoinType.LEFT).get(Codes_.id))
                    );
            }
            if (criteria.getOriginId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getOriginId(), root -> root.join(Citizens_.origin, JoinType.LEFT).get(Origins_.id))
                    );
            }
            if (criteria.getJobId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getJobId(), root -> root.join(Citizens_.job, JoinType.LEFT).get(Jobs_.id))
                    );
            }
            if (criteria.getPhonesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPhonesId(), root -> root.join(Citizens_.phones, JoinType.LEFT).get(Phones_.id))
                    );
            }
            if (criteria.getAddressesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAddressesId(),
                            root -> root.join(Citizens_.addresses, JoinType.LEFT).get(Addresses_.id)
                        )
                    );
            }
            if (criteria.getSocialId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocialId(),
                            root -> root.join(Citizens_.socials, JoinType.LEFT).get(SocialContacts_.id)
                        )
                    );
            }
            if (criteria.getEmailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEmailsId(), root -> root.join(Citizens_.emails, JoinType.LEFT).get(Emails_.id))
                    );
            }
            if (criteria.getRelationsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRelationsId(),
                            root -> root.join(Citizens_.relations, JoinType.LEFT).get(CitizensRelations_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
