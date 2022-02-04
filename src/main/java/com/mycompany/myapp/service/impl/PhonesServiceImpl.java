package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Phones;
import com.mycompany.myapp.repository.PhonesRepository;
import com.mycompany.myapp.service.PhonesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Phones}.
 */
@Service
@Transactional
public class PhonesServiceImpl implements PhonesService {

    private final Logger log = LoggerFactory.getLogger(PhonesServiceImpl.class);

    private final PhonesRepository phonesRepository;

    public PhonesServiceImpl(PhonesRepository phonesRepository) {
        this.phonesRepository = phonesRepository;
    }

    @Override
    public Phones save(Phones phones) {
        log.debug("Request to save Phones : {}", phones);
        return phonesRepository.save(phones);
    }

    @Override
    public Optional<Phones> partialUpdate(Phones phones) {
        log.debug("Request to partially update Phones : {}", phones);

        return phonesRepository
            .findById(phones.getId())
            .map(existingPhones -> {
                if (phones.getPhone() != null) {
                    existingPhones.setPhone(phones.getPhone());
                }
                if (phones.getDescription() != null) {
                    existingPhones.setDescription(phones.getDescription());
                }
                if (phones.getFavourite() != null) {
                    existingPhones.setFavourite(phones.getFavourite());
                }

                return existingPhones;
            })
            .map(phonesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Phones> findAll(Pageable pageable) {
        log.debug("Request to get all Phones");
        return phonesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Phones> findOne(Long id) {
        log.debug("Request to get Phones : {}", id);
        return phonesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Phones : {}", id);
        phonesRepository.deleteById(id);
    }
}
