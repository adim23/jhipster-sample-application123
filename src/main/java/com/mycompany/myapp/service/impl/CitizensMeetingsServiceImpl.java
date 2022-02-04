package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CitizensMeetings;
import com.mycompany.myapp.repository.CitizensMeetingsRepository;
import com.mycompany.myapp.service.CitizensMeetingsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CitizensMeetings}.
 */
@Service
@Transactional
public class CitizensMeetingsServiceImpl implements CitizensMeetingsService {

    private final Logger log = LoggerFactory.getLogger(CitizensMeetingsServiceImpl.class);

    private final CitizensMeetingsRepository citizensMeetingsRepository;

    public CitizensMeetingsServiceImpl(CitizensMeetingsRepository citizensMeetingsRepository) {
        this.citizensMeetingsRepository = citizensMeetingsRepository;
    }

    @Override
    public CitizensMeetings save(CitizensMeetings citizensMeetings) {
        log.debug("Request to save CitizensMeetings : {}", citizensMeetings);
        return citizensMeetingsRepository.save(citizensMeetings);
    }

    @Override
    public Optional<CitizensMeetings> partialUpdate(CitizensMeetings citizensMeetings) {
        log.debug("Request to partially update CitizensMeetings : {}", citizensMeetings);

        return citizensMeetingsRepository
            .findById(citizensMeetings.getId())
            .map(existingCitizensMeetings -> {
                if (citizensMeetings.getMeetDate() != null) {
                    existingCitizensMeetings.setMeetDate(citizensMeetings.getMeetDate());
                }
                if (citizensMeetings.getAgenda() != null) {
                    existingCitizensMeetings.setAgenda(citizensMeetings.getAgenda());
                }
                if (citizensMeetings.getComments() != null) {
                    existingCitizensMeetings.setComments(citizensMeetings.getComments());
                }
                if (citizensMeetings.getAmount() != null) {
                    existingCitizensMeetings.setAmount(citizensMeetings.getAmount());
                }
                if (citizensMeetings.getQuantity() != null) {
                    existingCitizensMeetings.setQuantity(citizensMeetings.getQuantity());
                }
                if (citizensMeetings.getStatus() != null) {
                    existingCitizensMeetings.setStatus(citizensMeetings.getStatus());
                }
                if (citizensMeetings.getFlag() != null) {
                    existingCitizensMeetings.setFlag(citizensMeetings.getFlag());
                }

                return existingCitizensMeetings;
            })
            .map(citizensMeetingsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CitizensMeetings> findAll(Pageable pageable) {
        log.debug("Request to get all CitizensMeetings");
        return citizensMeetingsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CitizensMeetings> findOne(Long id) {
        log.debug("Request to get CitizensMeetings : {}", id);
        return citizensMeetingsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CitizensMeetings : {}", id);
        citizensMeetingsRepository.deleteById(id);
    }
}
