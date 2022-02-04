package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Addresses;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Addresses}.
 */
public interface AddressesService {
    /**
     * Save a addresses.
     *
     * @param addresses the entity to save.
     * @return the persisted entity.
     */
    Addresses save(Addresses addresses);

    /**
     * Partially updates a addresses.
     *
     * @param addresses the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Addresses> partialUpdate(Addresses addresses);

    /**
     * Get all the addresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Addresses> findAll(Pageable pageable);

    /**
     * Get the "id" addresses.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Addresses> findOne(Long id);

    /**
     * Delete the "id" addresses.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
