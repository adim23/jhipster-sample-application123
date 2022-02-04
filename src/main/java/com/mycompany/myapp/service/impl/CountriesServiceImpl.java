package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Countries;
import com.mycompany.myapp.repository.CountriesRepository;
import com.mycompany.myapp.service.CountriesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Countries}.
 */
@Service
@Transactional
public class CountriesServiceImpl implements CountriesService {

    private final Logger log = LoggerFactory.getLogger(CountriesServiceImpl.class);

    private final CountriesRepository countriesRepository;

    public CountriesServiceImpl(CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    @Override
    public Countries save(Countries countries) {
        log.debug("Request to save Countries : {}", countries);
        return countriesRepository.save(countries);
    }

    @Override
    public Optional<Countries> partialUpdate(Countries countries) {
        log.debug("Request to partially update Countries : {}", countries);

        return countriesRepository
            .findById(countries.getId())
            .map(existingCountries -> {
                if (countries.getName() != null) {
                    existingCountries.setName(countries.getName());
                }
                if (countries.getIso() != null) {
                    existingCountries.setIso(countries.getIso());
                }
                if (countries.getLanguage() != null) {
                    existingCountries.setLanguage(countries.getLanguage());
                }
                if (countries.getLang() != null) {
                    existingCountries.setLang(countries.getLang());
                }

                return existingCountries;
            })
            .map(countriesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Countries> findAll(Pageable pageable) {
        log.debug("Request to get all Countries");
        return countriesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Countries> findOne(Long id) {
        log.debug("Request to get Countries : {}", id);
        return countriesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Countries : {}", id);
        countriesRepository.deleteById(id);
    }
}
