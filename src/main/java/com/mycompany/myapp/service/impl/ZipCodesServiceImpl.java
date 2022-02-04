package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ZipCodes;
import com.mycompany.myapp.repository.ZipCodesRepository;
import com.mycompany.myapp.service.ZipCodesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ZipCodes}.
 */
@Service
@Transactional
public class ZipCodesServiceImpl implements ZipCodesService {

    private final Logger log = LoggerFactory.getLogger(ZipCodesServiceImpl.class);

    private final ZipCodesRepository zipCodesRepository;

    public ZipCodesServiceImpl(ZipCodesRepository zipCodesRepository) {
        this.zipCodesRepository = zipCodesRepository;
    }

    @Override
    public ZipCodes save(ZipCodes zipCodes) {
        log.debug("Request to save ZipCodes : {}", zipCodes);
        return zipCodesRepository.save(zipCodes);
    }

    @Override
    public Optional<ZipCodes> partialUpdate(ZipCodes zipCodes) {
        log.debug("Request to partially update ZipCodes : {}", zipCodes);

        return zipCodesRepository
            .findById(zipCodes.getId())
            .map(existingZipCodes -> {
                if (zipCodes.getStreet() != null) {
                    existingZipCodes.setStreet(zipCodes.getStreet());
                }
                if (zipCodes.getArea() != null) {
                    existingZipCodes.setArea(zipCodes.getArea());
                }
                if (zipCodes.getFromNumber() != null) {
                    existingZipCodes.setFromNumber(zipCodes.getFromNumber());
                }
                if (zipCodes.getToNumber() != null) {
                    existingZipCodes.setToNumber(zipCodes.getToNumber());
                }

                return existingZipCodes;
            })
            .map(zipCodesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ZipCodes> findAll(Pageable pageable) {
        log.debug("Request to get all ZipCodes");
        return zipCodesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ZipCodes> findOne(Long id) {
        log.debug("Request to get ZipCodes : {}", id);
        return zipCodesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ZipCodes : {}", id);
        zipCodesRepository.deleteById(id);
    }
}
