package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Citizens;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Citizens}.
 */
public interface CitizensService {
    /**
     * Save a citizens.
     *
     * @param citizens the entity to save.
     * @return the persisted entity.
     */
    Citizens save(Citizens citizens);

    /**
     * Partially updates a citizens.
     *
     * @param citizens the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Citizens> partialUpdate(Citizens citizens);

    /**
     * Get all the citizens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Citizens> findAll(Pageable pageable);

    /**
     * Get the "id" citizens.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Citizens> findOne(Long id);

    /**
     * Delete the "id" citizens.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
