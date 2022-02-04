package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Job;
import com.mycompany.myapp.repository.JobRepository;
import com.mycompany.myapp.service.JobService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Job}.
 */
@Service
@Transactional
public class JobServiceImpl implements JobService {

    private final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Job save(Job job) {
        log.debug("Request to save Job : {}", job);
        return jobRepository.save(job);
    }

    @Override
    public Optional<Job> partialUpdate(Job job) {
        log.debug("Request to partially update Job : {}", job);

        return jobRepository
            .findById(job.getId())
            .map(existingJob -> {
                if (job.getJobTitle() != null) {
                    existingJob.setJobTitle(job.getJobTitle());
                }
                if (job.getMinSalary() != null) {
                    existingJob.setMinSalary(job.getMinSalary());
                }
                if (job.getMaxSalary() != null) {
                    existingJob.setMaxSalary(job.getMaxSalary());
                }

                return existingJob;
            })
            .map(jobRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Job> findAll(Pageable pageable) {
        log.debug("Request to get all Jobs");
        return jobRepository.findAll(pageable);
    }

    public Page<Job> findAllWithEagerRelationships(Pageable pageable) {
        return jobRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Job> findOne(Long id) {
        log.debug("Request to get Job : {}", id);
        return jobRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Job : {}", id);
        jobRepository.deleteById(id);
    }
}
