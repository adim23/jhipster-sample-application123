package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CitizensRelations;
import com.mycompany.myapp.repository.CitizensRelationsRepository;
import com.mycompany.myapp.service.CitizensRelationsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CitizensRelations}.
 */
@Service
@Transactional
public class CitizensRelationsServiceImpl implements CitizensRelationsService {

    private final Logger log = LoggerFactory.getLogger(CitizensRelationsServiceImpl.class);

    private final CitizensRelationsRepository citizensRelationsRepository;

    public CitizensRelationsServiceImpl(CitizensRelationsRepository citizensRelationsRepository) {
        this.citizensRelationsRepository = citizensRelationsRepository;
    }

    @Override
    public CitizensRelations save(CitizensRelations citizensRelations) {
        log.debug("Request to save CitizensRelations : {}", citizensRelations);
        return citizensRelationsRepository.save(citizensRelations);
    }

    @Override
    public Optional<CitizensRelations> partialUpdate(CitizensRelations citizensRelations) {
        log.debug("Request to partially update CitizensRelations : {}", citizensRelations);

        return citizensRelationsRepository
            .findById(citizensRelations.getId())
            .map(existingCitizensRelations -> {
                if (citizensRelations.getName() != null) {
                    existingCitizensRelations.setName(citizensRelations.getName());
                }

                return existingCitizensRelations;
            })
            .map(citizensRelationsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CitizensRelations> findAll(Pageable pageable) {
        log.debug("Request to get all CitizensRelations");
        return citizensRelationsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CitizensRelations> findOne(Long id) {
        log.debug("Request to get CitizensRelations : {}", id);
        return citizensRelationsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CitizensRelations : {}", id);
        citizensRelationsRepository.deleteById(id);
    }
}
