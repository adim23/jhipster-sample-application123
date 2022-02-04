package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Cities;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Cities}.
 */
public interface CitiesService {
    /**
     * Save a cities.
     *
     * @param cities the entity to save.
     * @return the persisted entity.
     */
    Cities save(Cities cities);

    /**
     * Partially updates a cities.
     *
     * @param cities the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Cities> partialUpdate(Cities cities);

    /**
     * Get all the cities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cities> findAll(Pageable pageable);

    /**
     * Get the "id" cities.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cities> findOne(Long id);

    /**
     * Delete the "id" cities.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
