package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Citizens;
import com.mycompany.myapp.repository.CitizensRepository;
import com.mycompany.myapp.service.CitizensService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Citizens}.
 */
@Service
@Transactional
public class CitizensServiceImpl implements CitizensService {

    private final Logger log = LoggerFactory.getLogger(CitizensServiceImpl.class);

    private final CitizensRepository citizensRepository;

    public CitizensServiceImpl(CitizensRepository citizensRepository) {
        this.citizensRepository = citizensRepository;
    }

    @Override
    public Citizens save(Citizens citizens) {
        log.debug("Request to save Citizens : {}", citizens);
        return citizensRepository.save(citizens);
    }

    @Override
    public Optional<Citizens> partialUpdate(Citizens citizens) {
        log.debug("Request to partially update Citizens : {}", citizens);

        return citizensRepository
            .findById(citizens.getId())
            .map(existingCitizens -> {
                if (citizens.getTitle() != null) {
                    existingCitizens.setTitle(citizens.getTitle());
                }
                if (citizens.getLastname() != null) {
                    existingCitizens.setLastname(citizens.getLastname());
                }
                if (citizens.getFirstname() != null) {
                    existingCitizens.setFirstname(citizens.getFirstname());
                }
                if (citizens.getFathersName() != null) {
                    existingCitizens.setFathersName(citizens.getFathersName());
                }
                if (citizens.getComments() != null) {
                    existingCitizens.setComments(citizens.getComments());
                }
                if (citizens.getBirthDate() != null) {
                    existingCitizens.setBirthDate(citizens.getBirthDate());
                }
                if (citizens.getGiortazi() != null) {
                    existingCitizens.setGiortazi(citizens.getGiortazi());
                }
                if (citizens.getMale() != null) {
                    existingCitizens.setMale(citizens.getMale());
                }
                if (citizens.getMeLetter() != null) {
                    existingCitizens.setMeLetter(citizens.getMeLetter());
                }
                if (citizens.getMeLabel() != null) {
                    existingCitizens.setMeLabel(citizens.getMeLabel());
                }
                if (citizens.getImage() != null) {
                    existingCitizens.setImage(citizens.getImage());
                }
                if (citizens.getImageContentType() != null) {
                    existingCitizens.setImageContentType(citizens.getImageContentType());
                }

                return existingCitizens;
            })
            .map(citizensRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Citizens> findAll(Pageable pageable) {
        log.debug("Request to get all Citizens");
        return citizensRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Citizens> findOne(Long id) {
        log.debug("Request to get Citizens : {}", id);
        return citizensRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Citizens : {}", id);
        citizensRepository.deleteById(id);
    }
}
