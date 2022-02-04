package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Origins;
import com.mycompany.myapp.repository.OriginsRepository;
import com.mycompany.myapp.service.OriginsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Origins}.
 */
@Service
@Transactional
public class OriginsServiceImpl implements OriginsService {

    private final Logger log = LoggerFactory.getLogger(OriginsServiceImpl.class);

    private final OriginsRepository originsRepository;

    public OriginsServiceImpl(OriginsRepository originsRepository) {
        this.originsRepository = originsRepository;
    }

    @Override
    public Origins save(Origins origins) {
        log.debug("Request to save Origins : {}", origins);
        return originsRepository.save(origins);
    }

    @Override
    public Optional<Origins> partialUpdate(Origins origins) {
        log.debug("Request to partially update Origins : {}", origins);

        return originsRepository
            .findById(origins.getId())
            .map(existingOrigins -> {
                if (origins.getName() != null) {
                    existingOrigins.setName(origins.getName());
                }

                return existingOrigins;
            })
            .map(originsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Origins> findAll(Pageable pageable) {
        log.debug("Request to get all Origins");
        return originsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Origins> findOne(Long id) {
        log.debug("Request to get Origins : {}", id);
        return originsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Origins : {}", id);
        originsRepository.deleteById(id);
    }
}
