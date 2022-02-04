package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Emails;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Emails}.
 */
public interface EmailsService {
    /**
     * Save a emails.
     *
     * @param emails the entity to save.
     * @return the persisted entity.
     */
    Emails save(Emails emails);

    /**
     * Partially updates a emails.
     *
     * @param emails the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Emails> partialUpdate(Emails emails);

    /**
     * Get all the emails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Emails> findAll(Pageable pageable);

    /**
     * Get the "id" emails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Emails> findOne(Long id);

    /**
     * Delete the "id" emails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
