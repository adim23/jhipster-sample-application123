package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.MaritalStatus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MaritalStatus}.
 */
public interface MaritalStatusService {
    /**
     * Save a maritalStatus.
     *
     * @param maritalStatus the entity to save.
     * @return the persisted entity.
     */
    MaritalStatus save(MaritalStatus maritalStatus);

    /**
     * Partially updates a maritalStatus.
     *
     * @param maritalStatus the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MaritalStatus> partialUpdate(MaritalStatus maritalStatus);

    /**
     * Get all the maritalStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MaritalStatus> findAll(Pageable pageable);

    /**
     * Get the "id" maritalStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MaritalStatus> findOne(Long id);

    /**
     * Delete the "id" maritalStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
