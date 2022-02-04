package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CompanyKinds;
import com.mycompany.myapp.repository.CompanyKindsRepository;
import com.mycompany.myapp.service.CompanyKindsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompanyKinds}.
 */
@Service
@Transactional
public class CompanyKindsServiceImpl implements CompanyKindsService {

    private final Logger log = LoggerFactory.getLogger(CompanyKindsServiceImpl.class);

    private final CompanyKindsRepository companyKindsRepository;

    public CompanyKindsServiceImpl(CompanyKindsRepository companyKindsRepository) {
        this.companyKindsRepository = companyKindsRepository;
    }

    @Override
    public CompanyKinds save(CompanyKinds companyKinds) {
        log.debug("Request to save CompanyKinds : {}", companyKinds);
        return companyKindsRepository.save(companyKinds);
    }

    @Override
    public Optional<CompanyKinds> partialUpdate(CompanyKinds companyKinds) {
        log.debug("Request to partially update CompanyKinds : {}", companyKinds);

        return companyKindsRepository
            .findById(companyKinds.getId())
            .map(existingCompanyKinds -> {
                if (companyKinds.getName() != null) {
                    existingCompanyKinds.setName(companyKinds.getName());
                }

                return existingCompanyKinds;
            })
            .map(companyKindsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyKinds> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyKinds");
        return companyKindsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyKinds> findOne(Long id) {
        log.debug("Request to get CompanyKinds : {}", id);
        return companyKindsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyKinds : {}", id);
        companyKindsRepository.deleteById(id);
    }
}
