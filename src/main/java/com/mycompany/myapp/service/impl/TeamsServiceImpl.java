package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Teams;
import com.mycompany.myapp.repository.TeamsRepository;
import com.mycompany.myapp.service.TeamsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Teams}.
 */
@Service
@Transactional
public class TeamsServiceImpl implements TeamsService {

    private final Logger log = LoggerFactory.getLogger(TeamsServiceImpl.class);

    private final TeamsRepository teamsRepository;

    public TeamsServiceImpl(TeamsRepository teamsRepository) {
        this.teamsRepository = teamsRepository;
    }

    @Override
    public Teams save(Teams teams) {
        log.debug("Request to save Teams : {}", teams);
        return teamsRepository.save(teams);
    }

    @Override
    public Optional<Teams> partialUpdate(Teams teams) {
        log.debug("Request to partially update Teams : {}", teams);

        return teamsRepository
            .findById(teams.getId())
            .map(existingTeams -> {
                if (teams.getName() != null) {
                    existingTeams.setName(teams.getName());
                }

                return existingTeams;
            })
            .map(teamsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Teams> findAll(Pageable pageable) {
        log.debug("Request to get all Teams");
        return teamsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Teams> findOne(Long id) {
        log.debug("Request to get Teams : {}", id);
        return teamsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Teams : {}", id);
        teamsRepository.deleteById(id);
    }
}
