package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Region;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Region}.
 */
public interface RegionService {
    /**
     * Save a region.
     *
     * @param region the entity to save.
     * @return the persisted entity.
     */
    Region save(Region region);

    /**
     * Partially updates a region.
     *
     * @param region the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Region> partialUpdate(Region region);

    /**
     * Get all the regions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Region> findAll(Pageable pageable);

    /**
     * Get the "id" region.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Region> findOne(Long id);

    /**
     * Delete the "id" region.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
