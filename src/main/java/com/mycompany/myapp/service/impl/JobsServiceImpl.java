package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Jobs;
import com.mycompany.myapp.repository.JobsRepository;
import com.mycompany.myapp.service.JobsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Jobs}.
 */
@Service
@Transactional
public class JobsServiceImpl implements JobsService {

    private final Logger log = LoggerFactory.getLogger(JobsServiceImpl.class);

    private final JobsRepository jobsRepository;

    public JobsServiceImpl(JobsRepository jobsRepository) {
        this.jobsRepository = jobsRepository;
    }

    @Override
    public Jobs save(Jobs jobs) {
        log.debug("Request to save Jobs : {}", jobs);
        return jobsRepository.save(jobs);
    }

    @Override
    public Optional<Jobs> partialUpdate(Jobs jobs) {
        log.debug("Request to partially update Jobs : {}", jobs);

        return jobsRepository
            .findById(jobs.getId())
            .map(existingJobs -> {
                if (jobs.getName() != null) {
                    existingJobs.setName(jobs.getName());
                }
                if (jobs.getDescription() != null) {
                    existingJobs.setDescription(jobs.getDescription());
                }

                return existingJobs;
            })
            .map(jobsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Jobs> findAll(Pageable pageable) {
        log.debug("Request to get all Jobs");
        return jobsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Jobs> findOne(Long id) {
        log.debug("Request to get Jobs : {}", id);
        return jobsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Jobs : {}", id);
        jobsRepository.deleteById(id);
    }
}
