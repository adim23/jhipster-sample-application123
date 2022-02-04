package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CitizenFolders;
import com.mycompany.myapp.repository.CitizenFoldersRepository;
import com.mycompany.myapp.service.CitizenFoldersService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CitizenFolders}.
 */
@Service
@Transactional
public class CitizenFoldersServiceImpl implements CitizenFoldersService {

    private final Logger log = LoggerFactory.getLogger(CitizenFoldersServiceImpl.class);

    private final CitizenFoldersRepository citizenFoldersRepository;

    public CitizenFoldersServiceImpl(CitizenFoldersRepository citizenFoldersRepository) {
        this.citizenFoldersRepository = citizenFoldersRepository;
    }

    @Override
    public CitizenFolders save(CitizenFolders citizenFolders) {
        log.debug("Request to save CitizenFolders : {}", citizenFolders);
        return citizenFoldersRepository.save(citizenFolders);
    }

    @Override
    public Optional<CitizenFolders> partialUpdate(CitizenFolders citizenFolders) {
        log.debug("Request to partially update CitizenFolders : {}", citizenFolders);

        return citizenFoldersRepository
            .findById(citizenFolders.getId())
            .map(existingCitizenFolders -> {
                if (citizenFolders.getName() != null) {
                    existingCitizenFolders.setName(citizenFolders.getName());
                }
                if (citizenFolders.getDescription() != null) {
                    existingCitizenFolders.setDescription(citizenFolders.getDescription());
                }
                if (citizenFolders.getSpecial() != null) {
                    existingCitizenFolders.setSpecial(citizenFolders.getSpecial());
                }

                return existingCitizenFolders;
            })
            .map(citizenFoldersRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CitizenFolders> findAll(Pageable pageable) {
        log.debug("Request to get all CitizenFolders");
        return citizenFoldersRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CitizenFolders> findOne(Long id) {
        log.debug("Request to get CitizenFolders : {}", id);
        return citizenFoldersRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CitizenFolders : {}", id);
        citizenFoldersRepository.deleteById(id);
    }
}
