package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ContactTypes;
import com.mycompany.myapp.repository.ContactTypesRepository;
import com.mycompany.myapp.service.ContactTypesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContactTypes}.
 */
@Service
@Transactional
public class ContactTypesServiceImpl implements ContactTypesService {

    private final Logger log = LoggerFactory.getLogger(ContactTypesServiceImpl.class);

    private final ContactTypesRepository contactTypesRepository;

    public ContactTypesServiceImpl(ContactTypesRepository contactTypesRepository) {
        this.contactTypesRepository = contactTypesRepository;
    }

    @Override
    public ContactTypes save(ContactTypes contactTypes) {
        log.debug("Request to save ContactTypes : {}", contactTypes);
        return contactTypesRepository.save(contactTypes);
    }

    @Override
    public Optional<ContactTypes> partialUpdate(ContactTypes contactTypes) {
        log.debug("Request to partially update ContactTypes : {}", contactTypes);

        return contactTypesRepository
            .findById(contactTypes.getId())
            .map(existingContactTypes -> {
                if (contactTypes.getName() != null) {
                    existingContactTypes.setName(contactTypes.getName());
                }

                return existingContactTypes;
            })
            .map(contactTypesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactTypes> findAll(Pageable pageable) {
        log.debug("Request to get all ContactTypes");
        return contactTypesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContactTypes> findOne(Long id) {
        log.debug("Request to get ContactTypes : {}", id);
        return contactTypesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactTypes : {}", id);
        contactTypesRepository.deleteById(id);
    }
}
