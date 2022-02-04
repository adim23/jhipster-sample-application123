package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Teams;
import com.mycompany.myapp.repository.TeamsRepository;
import com.mycompany.myapp.service.TeamsQueryService;
import com.mycompany.myapp.service.TeamsService;
import com.mycompany.myapp.service.criteria.TeamsCriteria;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Teams}.
 */
@RestController
@RequestMapping("/api")
public class TeamsResource {

    private final Logger log = LoggerFactory.getLogger(TeamsResource.class);

    private static final String ENTITY_NAME = "teams";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeamsService teamsService;

    private final TeamsRepository teamsRepository;

    private final TeamsQueryService teamsQueryService;

    public TeamsResource(TeamsService teamsService, TeamsRepository teamsRepository, TeamsQueryService teamsQueryService) {
        this.teamsService = teamsService;
        this.teamsRepository = teamsRepository;
        this.teamsQueryService = teamsQueryService;
    }

    /**
     * {@code POST  /teams} : Create a new teams.
     *
     * @param teams the teams to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teams, or with status {@code 400 (Bad Request)} if the teams has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/teams")
    public ResponseEntity<Teams> createTeams(@Valid @RequestBody Teams teams) throws URISyntaxException {
        log.debug("REST request to save Teams : {}", teams);
        if (teams.getId() != null) {
            throw new BadRequestAlertException("A new teams cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Teams result = teamsService.save(teams);
        return ResponseEntity
            .created(new URI("/api/teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /teams/:id} : Updates an existing teams.
     *
     * @param id the id of the teams to save.
     * @param teams the teams to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teams,
     * or with status {@code 400 (Bad Request)} if the teams is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teams couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/teams/{id}")
    public ResponseEntity<Teams> updateTeams(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Teams teams)
        throws URISyntaxException {
        log.debug("REST request to update Teams : {}, {}", id, teams);
        if (teams.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teams.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Teams result = teamsService.save(teams);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teams.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /teams/:id} : Partial updates given fields of an existing teams, field will ignore if it is null
     *
     * @param id the id of the teams to save.
     * @param teams the teams to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teams,
     * or with status {@code 400 (Bad Request)} if the teams is not valid,
     * or with status {@code 404 (Not Found)} if the teams is not found,
     * or with status {@code 500 (Internal Server Error)} if the teams couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/teams/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Teams> partialUpdateTeams(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Teams teams
    ) throws URISyntaxException {
        log.debug("REST request to partial update Teams partially : {}, {}", id, teams);
        if (teams.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teams.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Teams> result = teamsService.partialUpdate(teams);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teams.getId().toString())
        );
    }

    /**
     * {@code GET  /teams} : get all the teams.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teams in body.
     */
    @GetMapping("/teams")
    public ResponseEntity<List<Teams>> getAllTeams(
        TeamsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Teams by criteria: {}", criteria);
        Page<Teams> page = teamsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /teams/count} : count all the teams.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/teams/count")
    public ResponseEntity<Long> countTeams(TeamsCriteria criteria) {
        log.debug("REST request to count Teams by criteria: {}", criteria);
        return ResponseEntity.ok().body(teamsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /teams/:id} : get the "id" teams.
     *
     * @param id the id of the teams to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teams, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/teams/{id}")
    public ResponseEntity<Teams> getTeams(@PathVariable Long id) {
        log.debug("REST request to get Teams : {}", id);
        Optional<Teams> teams = teamsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teams);
    }

    /**
     * {@code DELETE  /teams/:id} : delete the "id" teams.
     *
     * @param id the id of the teams to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteTeams(@PathVariable Long id) {
        log.debug("REST request to delete Teams : {}", id);
        teamsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
