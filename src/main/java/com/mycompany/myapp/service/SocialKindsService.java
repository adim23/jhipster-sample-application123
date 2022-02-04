package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SocialKinds;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SocialKinds}.
 */
public interface SocialKindsService {
    /**
     * Save a socialKinds.
     *
     * @param socialKinds the entity to save.
     * @return the persisted entity.
     */
    SocialKinds save(SocialKinds socialKinds);

    /**
     * Partially updates a socialKinds.
     *
     * @param socialKinds the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SocialKinds> partialUpdate(SocialKinds socialKinds);

    /**
     * Get all the socialKinds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SocialKinds> findAll(Pageable pageable);

    /**
     * Get the "id" socialKinds.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SocialKinds> findOne(Long id);

    /**
     * Delete the "id" socialKinds.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
