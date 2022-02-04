package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Codes;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Codes}.
 */
public interface CodesService {
    /**
     * Save a codes.
     *
     * @param codes the entity to save.
     * @return the persisted entity.
     */
    Codes save(Codes codes);

    /**
     * Partially updates a codes.
     *
     * @param codes the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Codes> partialUpdate(Codes codes);

    /**
     * Get all the codes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Codes> findAll(Pageable pageable);

    /**
     * Get the "id" codes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Codes> findOne(Long id);

    /**
     * Delete the "id" codes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
