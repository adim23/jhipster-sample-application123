package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.MaritalStatus;
import com.mycompany.myapp.repository.MaritalStatusRepository;
import com.mycompany.myapp.service.MaritalStatusService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MaritalStatus}.
 */
@Service
@Transactional
public class MaritalStatusServiceImpl implements MaritalStatusService {

    private final Logger log = LoggerFactory.getLogger(MaritalStatusServiceImpl.class);

    private final MaritalStatusRepository maritalStatusRepository;

    public MaritalStatusServiceImpl(MaritalStatusRepository maritalStatusRepository) {
        this.maritalStatusRepository = maritalStatusRepository;
    }

    @Override
    public MaritalStatus save(MaritalStatus maritalStatus) {
        log.debug("Request to save MaritalStatus : {}", maritalStatus);
        return maritalStatusRepository.save(maritalStatus);
    }

    @Override
    public Optional<MaritalStatus> partialUpdate(MaritalStatus maritalStatus) {
        log.debug("Request to partially update MaritalStatus : {}", maritalStatus);

        return maritalStatusRepository
            .findById(maritalStatus.getId())
            .map(existingMaritalStatus -> {
                if (maritalStatus.getName() != null) {
                    existingMaritalStatus.setName(maritalStatus.getName());
                }

                return existingMaritalStatus;
            })
            .map(maritalStatusRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MaritalStatus> findAll(Pageable pageable) {
        log.debug("Request to get all MaritalStatuses");
        return maritalStatusRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MaritalStatus> findOne(Long id) {
        log.debug("Request to get MaritalStatus : {}", id);
        return maritalStatusRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MaritalStatus : {}", id);
        maritalStatusRepository.deleteById(id);
    }
}
