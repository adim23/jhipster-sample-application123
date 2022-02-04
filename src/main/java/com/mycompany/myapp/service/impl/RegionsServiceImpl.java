package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Regions;
import com.mycompany.myapp.repository.RegionsRepository;
import com.mycompany.myapp.service.RegionsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Regions}.
 */
@Service
@Transactional
public class RegionsServiceImpl implements RegionsService {

    private final Logger log = LoggerFactory.getLogger(RegionsServiceImpl.class);

    private final RegionsRepository regionsRepository;

    public RegionsServiceImpl(RegionsRepository regionsRepository) {
        this.regionsRepository = regionsRepository;
    }

    @Override
    public Regions save(Regions regions) {
        log.debug("Request to save Regions : {}", regions);
        return regionsRepository.save(regions);
    }

    @Override
    public Optional<Regions> partialUpdate(Regions regions) {
        log.debug("Request to partially update Regions : {}", regions);

        return regionsRepository
            .findById(regions.getId())
            .map(existingRegions -> {
                if (regions.getName() != null) {
                    existingRegions.setName(regions.getName());
                }

                return existingRegions;
            })
            .map(regionsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Regions> findAll(Pageable pageable) {
        log.debug("Request to get all Regions");
        return regionsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Regions> findOne(Long id) {
        log.debug("Request to get Regions : {}", id);
        return regionsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Regions : {}", id);
        regionsRepository.deleteById(id);
    }
}
