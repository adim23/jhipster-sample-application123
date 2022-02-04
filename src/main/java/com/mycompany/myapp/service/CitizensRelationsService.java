package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CitizensRelations;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CitizensRelations}.
 */
public interface CitizensRelationsService {
    /**
     * Save a citizensRelations.
     *
     * @param citizensRelations the entity to save.
     * @return the persisted entity.
     */
    CitizensRelations save(CitizensRelations citizensRelations);

    /**
     * Partially updates a citizensRelations.
     *
     * @param citizensRelations the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CitizensRelations> partialUpdate(CitizensRelations citizensRelations);

    /**
     * Get all the citizensRelations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CitizensRelations> findAll(Pageable pageable);

    /**
     * Get the "id" citizensRelations.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CitizensRelations> findOne(Long id);

    /**
     * Delete the "id" citizensRelations.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
