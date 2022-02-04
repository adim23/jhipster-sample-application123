package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CitizensMeetings;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CitizensMeetings}.
 */
public interface CitizensMeetingsService {
    /**
     * Save a citizensMeetings.
     *
     * @param citizensMeetings the entity to save.
     * @return the persisted entity.
     */
    CitizensMeetings save(CitizensMeetings citizensMeetings);

    /**
     * Partially updates a citizensMeetings.
     *
     * @param citizensMeetings the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CitizensMeetings> partialUpdate(CitizensMeetings citizensMeetings);

    /**
     * Get all the citizensMeetings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CitizensMeetings> findAll(Pageable pageable);

    /**
     * Get the "id" citizensMeetings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CitizensMeetings> findOne(Long id);

    /**
     * Delete the "id" citizensMeetings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
