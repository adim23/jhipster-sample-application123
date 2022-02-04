package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Files;
import com.mycompany.myapp.repository.FilesRepository;
import com.mycompany.myapp.service.FilesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Files}.
 */
@Service
@Transactional
public class FilesServiceImpl implements FilesService {

    private final Logger log = LoggerFactory.getLogger(FilesServiceImpl.class);

    private final FilesRepository filesRepository;

    public FilesServiceImpl(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    @Override
    public Files save(Files files) {
        log.debug("Request to save Files : {}", files);
        return filesRepository.save(files);
    }

    @Override
    public Optional<Files> partialUpdate(Files files) {
        log.debug("Request to partially update Files : {}", files);

        return filesRepository
            .findById(files.getId())
            .map(existingFiles -> {
                if (files.getUuid() != null) {
                    existingFiles.setUuid(files.getUuid());
                }
                if (files.getFilename() != null) {
                    existingFiles.setFilename(files.getFilename());
                }
                if (files.getFileType() != null) {
                    existingFiles.setFileType(files.getFileType());
                }
                if (files.getFileSize() != null) {
                    existingFiles.setFileSize(files.getFileSize());
                }
                if (files.getCreateDate() != null) {
                    existingFiles.setCreateDate(files.getCreateDate());
                }
                if (files.getFilePath() != null) {
                    existingFiles.setFilePath(files.getFilePath());
                }
                if (files.getVersion() != null) {
                    existingFiles.setVersion(files.getVersion());
                }
                if (files.getMime() != null) {
                    existingFiles.setMime(files.getMime());
                }

                return existingFiles;
            })
            .map(filesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Files> findAll(Pageable pageable) {
        log.debug("Request to get all Files");
        return filesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Files> findOne(Long id) {
        log.debug("Request to get Files : {}", id);
        return filesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Files : {}", id);
        filesRepository.deleteById(id);
    }
}
