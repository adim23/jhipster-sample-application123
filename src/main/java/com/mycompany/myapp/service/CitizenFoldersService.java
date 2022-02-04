package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CitizenFolders;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CitizenFolders}.
 */
public interface CitizenFoldersService {
    /**
     * Save a citizenFolders.
     *
     * @param citizenFolders the entity to save.
     * @return the persisted entity.
     */
    CitizenFolders save(CitizenFolders citizenFolders);

    /**
     * Partially updates a citizenFolders.
     *
     * @param citizenFolders the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CitizenFolders> partialUpdate(CitizenFolders citizenFolders);

    /**
     * Get all the citizenFolders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CitizenFolders> findAll(Pageable pageable);

    /**
     * Get the "id" citizenFolders.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CitizenFolders> findOne(Long id);

    /**
     * Delete the "id" citizenFolders.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
