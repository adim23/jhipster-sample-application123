package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Companies;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Companies}.
 */
public interface CompaniesService {
    /**
     * Save a companies.
     *
     * @param companies the entity to save.
     * @return the persisted entity.
     */
    Companies save(Companies companies);

    /**
     * Partially updates a companies.
     *
     * @param companies the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Companies> partialUpdate(Companies companies);

    /**
     * Get all the companies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Companies> findAll(Pageable pageable);

    /**
     * Get the "id" companies.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Companies> findOne(Long id);

    /**
     * Delete the "id" companies.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
