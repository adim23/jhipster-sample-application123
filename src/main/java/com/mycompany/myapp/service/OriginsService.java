package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Origins;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Origins}.
 */
public interface OriginsService {
    /**
     * Save a origins.
     *
     * @param origins the entity to save.
     * @return the persisted entity.
     */
    Origins save(Origins origins);

    /**
     * Partially updates a origins.
     *
     * @param origins the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Origins> partialUpdate(Origins origins);

    /**
     * Get all the origins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Origins> findAll(Pageable pageable);

    /**
     * Get the "id" origins.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Origins> findOne(Long id);

    /**
     * Delete the "id" origins.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
