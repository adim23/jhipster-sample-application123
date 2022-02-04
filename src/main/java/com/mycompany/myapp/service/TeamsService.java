package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Teams;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Teams}.
 */
public interface TeamsService {
    /**
     * Save a teams.
     *
     * @param teams the entity to save.
     * @return the persisted entity.
     */
    Teams save(Teams teams);

    /**
     * Partially updates a teams.
     *
     * @param teams the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Teams> partialUpdate(Teams teams);

    /**
     * Get all the teams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Teams> findAll(Pageable pageable);

    /**
     * Get the "id" teams.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Teams> findOne(Long id);

    /**
     * Delete the "id" teams.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
