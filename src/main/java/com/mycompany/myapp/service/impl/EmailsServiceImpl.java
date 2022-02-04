package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Emails;
import com.mycompany.myapp.repository.EmailsRepository;
import com.mycompany.myapp.service.EmailsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Emails}.
 */
@Service
@Transactional
public class EmailsServiceImpl implements EmailsService {

    private final Logger log = LoggerFactory.getLogger(EmailsServiceImpl.class);

    private final EmailsRepository emailsRepository;

    public EmailsServiceImpl(EmailsRepository emailsRepository) {
        this.emailsRepository = emailsRepository;
    }

    @Override
    public Emails save(Emails emails) {
        log.debug("Request to save Emails : {}", emails);
        return emailsRepository.save(emails);
    }

    @Override
    public Optional<Emails> partialUpdate(Emails emails) {
        log.debug("Request to partially update Emails : {}", emails);

        return emailsRepository
            .findById(emails.getId())
            .map(existingEmails -> {
                if (emails.getEmail() != null) {
                    existingEmails.setEmail(emails.getEmail());
                }
                if (emails.getDescription() != null) {
                    existingEmails.setDescription(emails.getDescription());
                }
                if (emails.getFavourite() != null) {
                    existingEmails.setFavourite(emails.getFavourite());
                }

                return existingEmails;
            })
            .map(emailsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Emails> findAll(Pageable pageable) {
        log.debug("Request to get all Emails");
        return emailsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Emails> findOne(Long id) {
        log.debug("Request to get Emails : {}", id);
        return emailsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Emails : {}", id);
        emailsRepository.deleteById(id);
    }
}
