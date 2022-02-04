package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.PhoneTypes;
import com.mycompany.myapp.repository.PhoneTypesRepository;
import com.mycompany.myapp.service.PhoneTypesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PhoneTypes}.
 */
@Service
@Transactional
public class PhoneTypesServiceImpl implements PhoneTypesService {

    private final Logger log = LoggerFactory.getLogger(PhoneTypesServiceImpl.class);

    private final PhoneTypesRepository phoneTypesRepository;

    public PhoneTypesServiceImpl(PhoneTypesRepository phoneTypesRepository) {
        this.phoneTypesRepository = phoneTypesRepository;
    }

    @Override
    public PhoneTypes save(PhoneTypes phoneTypes) {
        log.debug("Request to save PhoneTypes : {}", phoneTypes);
        return phoneTypesRepository.save(phoneTypes);
    }

    @Override
    public Optional<PhoneTypes> partialUpdate(PhoneTypes phoneTypes) {
        log.debug("Request to partially update PhoneTypes : {}", phoneTypes);

        return phoneTypesRepository
            .findById(phoneTypes.getId())
            .map(existingPhoneTypes -> {
                if (phoneTypes.getName() != null) {
                    existingPhoneTypes.setName(phoneTypes.getName());
                }

                return existingPhoneTypes;
            })
            .map(phoneTypesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhoneTypes> findAll(Pageable pageable) {
        log.debug("Request to get all PhoneTypes");
        return phoneTypesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PhoneTypes> findOne(Long id) {
        log.debug("Request to get PhoneTypes : {}", id);
        return phoneTypesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PhoneTypes : {}", id);
        phoneTypesRepository.deleteById(id);
    }
}
