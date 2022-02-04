package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Codes;
import com.mycompany.myapp.repository.CodesRepository;
import com.mycompany.myapp.service.CodesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Codes}.
 */
@Service
@Transactional
public class CodesServiceImpl implements CodesService {

    private final Logger log = LoggerFactory.getLogger(CodesServiceImpl.class);

    private final CodesRepository codesRepository;

    public CodesServiceImpl(CodesRepository codesRepository) {
        this.codesRepository = codesRepository;
    }

    @Override
    public Codes save(Codes codes) {
        log.debug("Request to save Codes : {}", codes);
        return codesRepository.save(codes);
    }

    @Override
    public Optional<Codes> partialUpdate(Codes codes) {
        log.debug("Request to partially update Codes : {}", codes);

        return codesRepository
            .findById(codes.getId())
            .map(existingCodes -> {
                if (codes.getName() != null) {
                    existingCodes.setName(codes.getName());
                }

                return existingCodes;
            })
            .map(codesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Codes> findAll(Pageable pageable) {
        log.debug("Request to get all Codes");
        return codesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Codes> findOne(Long id) {
        log.debug("Request to get Codes : {}", id);
        return codesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Codes : {}", id);
        codesRepository.deleteById(id);
    }
}
