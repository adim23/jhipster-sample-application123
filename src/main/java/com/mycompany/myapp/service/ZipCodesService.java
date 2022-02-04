package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ZipCodes;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ZipCodes}.
 */
public interface ZipCodesService {
    /**
     * Save a zipCodes.
     *
     * @param zipCodes the entity to save.
     * @return the persisted entity.
     */
    ZipCodes save(ZipCodes zipCodes);

    /**
     * Partially updates a zipCodes.
     *
     * @param zipCodes the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ZipCodes> partialUpdate(ZipCodes zipCodes);

    /**
     * Get all the zipCodes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ZipCodes> findAll(Pageable pageable);

    /**
     * Get the "id" zipCodes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ZipCodes> findOne(Long id);

    /**
     * Delete the "id" zipCodes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
