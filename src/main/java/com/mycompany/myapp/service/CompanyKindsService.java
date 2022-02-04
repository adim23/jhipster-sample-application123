package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CompanyKinds;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CompanyKinds}.
 */
public interface CompanyKindsService {
    /**
     * Save a companyKinds.
     *
     * @param companyKinds the entity to save.
     * @return the persisted entity.
     */
    CompanyKinds save(CompanyKinds companyKinds);

    /**
     * Partially updates a companyKinds.
     *
     * @param companyKinds the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompanyKinds> partialUpdate(CompanyKinds companyKinds);

    /**
     * Get all the companyKinds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyKinds> findAll(Pageable pageable);

    /**
     * Get the "id" companyKinds.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyKinds> findOne(Long id);

    /**
     * Delete the "id" companyKinds.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
