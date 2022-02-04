package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SocialContacts;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SocialContacts}.
 */
public interface SocialContactsService {
    /**
     * Save a socialContacts.
     *
     * @param socialContacts the entity to save.
     * @return the persisted entity.
     */
    SocialContacts save(SocialContacts socialContacts);

    /**
     * Partially updates a socialContacts.
     *
     * @param socialContacts the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SocialContacts> partialUpdate(SocialContacts socialContacts);

    /**
     * Get all the socialContacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SocialContacts> findAll(Pageable pageable);

    /**
     * Get the "id" socialContacts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SocialContacts> findOne(Long id);

    /**
     * Delete the "id" socialContacts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
