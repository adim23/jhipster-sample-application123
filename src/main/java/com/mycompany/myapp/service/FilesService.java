package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Files;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Files}.
 */
public interface FilesService {
    /**
     * Save a files.
     *
     * @param files the entity to save.
     * @return the persisted entity.
     */
    Files save(Files files);

    /**
     * Partially updates a files.
     *
     * @param files the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Files> partialUpdate(Files files);

    /**
     * Get all the files.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Files> findAll(Pageable pageable);

    /**
     * Get the "id" files.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Files> findOne(Long id);

    /**
     * Delete the "id" files.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
