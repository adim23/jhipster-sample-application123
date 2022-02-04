package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Companies;
import com.mycompany.myapp.repository.CompaniesRepository;
import com.mycompany.myapp.service.CompaniesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Companies}.
 */
@Service
@Transactional
public class CompaniesServiceImpl implements CompaniesService {

    private final Logger log = LoggerFactory.getLogger(CompaniesServiceImpl.class);

    private final CompaniesRepository companiesRepository;

    public CompaniesServiceImpl(CompaniesRepository companiesRepository) {
        this.companiesRepository = companiesRepository;
    }

    @Override
    public Companies save(Companies companies) {
        log.debug("Request to save Companies : {}", companies);
        return companiesRepository.save(companies);
    }

    @Override
    public Optional<Companies> partialUpdate(Companies companies) {
        log.debug("Request to partially update Companies : {}", companies);

        return companiesRepository
            .findById(companies.getId())
            .map(existingCompanies -> {
                if (companies.getName() != null) {
                    existingCompanies.setName(companies.getName());
                }

                return existingCompanies;
            })
            .map(companiesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Companies> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        return companiesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Companies> findOne(Long id) {
        log.debug("Request to get Companies : {}", id);
        return companiesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Companies : {}", id);
        companiesRepository.deleteById(id);
    }
}
