package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.PhoneTypes;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PhoneTypes}.
 */
public interface PhoneTypesService {
    /**
     * Save a phoneTypes.
     *
     * @param phoneTypes the entity to save.
     * @return the persisted entity.
     */
    PhoneTypes save(PhoneTypes phoneTypes);

    /**
     * Partially updates a phoneTypes.
     *
     * @param phoneTypes the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PhoneTypes> partialUpdate(PhoneTypes phoneTypes);

    /**
     * Get all the phoneTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PhoneTypes> findAll(Pageable pageable);

    /**
     * Get the "id" phoneTypes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PhoneTypes> findOne(Long id);

    /**
     * Delete the "id" phoneTypes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
