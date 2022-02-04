package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.SocialContacts;
import com.mycompany.myapp.repository.SocialContactsRepository;
import com.mycompany.myapp.service.SocialContactsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SocialContacts}.
 */
@Service
@Transactional
public class SocialContactsServiceImpl implements SocialContactsService {

    private final Logger log = LoggerFactory.getLogger(SocialContactsServiceImpl.class);

    private final SocialContactsRepository socialContactsRepository;

    public SocialContactsServiceImpl(SocialContactsRepository socialContactsRepository) {
        this.socialContactsRepository = socialContactsRepository;
    }

    @Override
    public SocialContacts save(SocialContacts socialContacts) {
        log.debug("Request to save SocialContacts : {}", socialContacts);
        return socialContactsRepository.save(socialContacts);
    }

    @Override
    public Optional<SocialContacts> partialUpdate(SocialContacts socialContacts) {
        log.debug("Request to partially update SocialContacts : {}", socialContacts);

        return socialContactsRepository
            .findById(socialContacts.getId())
            .map(existingSocialContacts -> {
                if (socialContacts.getName() != null) {
                    existingSocialContacts.setName(socialContacts.getName());
                }
                if (socialContacts.getFavored() != null) {
                    existingSocialContacts.setFavored(socialContacts.getFavored());
                }

                return existingSocialContacts;
            })
            .map(socialContactsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SocialContacts> findAll(Pageable pageable) {
        log.debug("Request to get all SocialContacts");
        return socialContactsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SocialContacts> findOne(Long id) {
        log.debug("Request to get SocialContacts : {}", id);
        return socialContactsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SocialContacts : {}", id);
        socialContactsRepository.deleteById(id);
    }
}
