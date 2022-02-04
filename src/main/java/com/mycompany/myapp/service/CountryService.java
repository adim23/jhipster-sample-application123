package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Country;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Country}.
 */
public interface CountryService {
    /**
     * Save a country.
     *
     * @param country the entity to save.
     * @return the persisted entity.
     */
    Country save(Country country);

    /**
     * Partially updates a country.
     *
     * @param country the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Country> partialUpdate(Country country);

    /**
     * Get all the countries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Country> findAll(Pageable pageable);

    /**
     * Get the "id" country.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Country> findOne(Long id);

    /**
     * Delete the "id" country.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
