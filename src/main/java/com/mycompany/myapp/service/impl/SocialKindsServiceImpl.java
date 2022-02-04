package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.SocialKinds;
import com.mycompany.myapp.repository.SocialKindsRepository;
import com.mycompany.myapp.service.SocialKindsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SocialKinds}.
 */
@Service
@Transactional
public class SocialKindsServiceImpl implements SocialKindsService {

    private final Logger log = LoggerFactory.getLogger(SocialKindsServiceImpl.class);

    private final SocialKindsRepository socialKindsRepository;

    public SocialKindsServiceImpl(SocialKindsRepository socialKindsRepository) {
        this.socialKindsRepository = socialKindsRepository;
    }

    @Override
    public SocialKinds save(SocialKinds socialKinds) {
        log.debug("Request to save SocialKinds : {}", socialKinds);
        return socialKindsRepository.save(socialKinds);
    }

    @Override
    public Optional<SocialKinds> partialUpdate(SocialKinds socialKinds) {
        log.debug("Request to partially update SocialKinds : {}", socialKinds);

        return socialKindsRepository
            .findById(socialKinds.getId())
            .map(existingSocialKinds -> {
                if (socialKinds.getCode() != null) {
                    existingSocialKinds.setCode(socialKinds.getCode());
                }
                if (socialKinds.getName() != null) {
                    existingSocialKinds.setName(socialKinds.getName());
                }
                if (socialKinds.getCall() != null) {
                    existingSocialKinds.setCall(socialKinds.getCall());
                }

                return existingSocialKinds;
            })
            .map(socialKindsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SocialKinds> findAll(Pageable pageable) {
        log.debug("Request to get all SocialKinds");
        return socialKindsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SocialKinds> findOne(Long id) {
        log.debug("Request to get SocialKinds : {}", id);
        return socialKindsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SocialKinds : {}", id);
        socialKindsRepository.deleteById(id);
    }
}
