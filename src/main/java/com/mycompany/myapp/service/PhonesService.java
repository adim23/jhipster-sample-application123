package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Phones;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Phones}.
 */
public interface PhonesService {
    /**
     * Save a phones.
     *
     * @param phones the entity to save.
     * @return the persisted entity.
     */
    Phones save(Phones phones);

    /**
     * Partially updates a phones.
     *
     * @param phones the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Phones> partialUpdate(Phones phones);

    /**
     * Get all the phones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Phones> findAll(Pageable pageable);

    /**
     * Get the "id" phones.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Phones> findOne(Long id);

    /**
     * Delete the "id" phones.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
