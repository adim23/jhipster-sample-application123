package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CitizensMeetings;
import com.mycompany.myapp.repository.CitizensMeetingsRepository;
import com.mycompany.myapp.service.CitizensMeetingsQueryService;
import com.mycompany.myapp.service.CitizensMeetingsService;
import com.mycompany.myapp.service.criteria.CitizensMeetingsCriteria;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CitizensMeetings}.
 */
@RestController
@RequestMapping("/api")
public class CitizensMeetingsResource {

    private final Logger log = LoggerFactory.getLogger(CitizensMeetingsResource.class);

    private static final String ENTITY_NAME = "citizensMeetings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitizensMeetingsService citizensMeetingsService;

    private final CitizensMeetingsRepository citizensMeetingsRepository;

    private final CitizensMeetingsQueryService citizensMeetingsQueryService;

    public CitizensMeetingsResource(
        CitizensMeetingsService citizensMeetingsService,
        CitizensMeetingsRepository citizensMeetingsRepository,
        CitizensMeetingsQueryService citizensMeetingsQueryService
    ) {
        this.citizensMeetingsService = citizensMeetingsService;
        this.citizensMeetingsRepository = citizensMeetingsRepository;
        this.citizensMeetingsQueryService = citizensMeetingsQueryService;
    }

    /**
     * {@code POST  /citizens-meetings} : Create a new citizensMeetings.
     *
     * @param citizensMeetings the citizensMeetings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new citizensMeetings, or with status {@code 400 (Bad Request)} if the citizensMeetings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/citizens-meetings")
    public ResponseEntity<CitizensMeetings> createCitizensMeetings(@Valid @RequestBody CitizensMeetings citizensMeetings)
        throws URISyntaxException {
        log.debug("REST request to save CitizensMeetings : {}", citizensMeetings);
        if (citizensMeetings.getId() != null) {
            throw new BadRequestAlertException("A new citizensMeetings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CitizensMeetings result = citizensMeetingsService.save(citizensMeetings);
        return ResponseEntity
            .created(new URI("/api/citizens-meetings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /citizens-meetings/:id} : Updates an existing citizensMeetings.
     *
     * @param id the id of the citizensMeetings to save.
     * @param citizensMeetings the citizensMeetings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citizensMeetings,
     * or with status {@code 400 (Bad Request)} if the citizensMeetings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the citizensMeetings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/citizens-meetings/{id}")
    public ResponseEntity<CitizensMeetings> updateCitizensMeetings(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CitizensMeetings citizensMeetings
    ) throws URISyntaxException {
        log.debug("REST request to update CitizensMeetings : {}, {}", id, citizensMeetings);
        if (citizensMeetings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, citizensMeetings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citizensMeetingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CitizensMeetings result = citizensMeetingsService.save(citizensMeetings);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citizensMeetings.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /citizens-meetings/:id} : Partial updates given fields of an existing citizensMeetings, field will ignore if it is null
     *
     * @param id the id of the citizensMeetings to save.
     * @param citizensMeetings the citizensMeetings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citizensMeetings,
     * or with status {@code 400 (Bad Request)} if the citizensMeetings is not valid,
     * or with status {@code 404 (Not Found)} if the citizensMeetings is not found,
     * or with status {@code 500 (Internal Server Error)} if the citizensMeetings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/citizens-meetings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CitizensMeetings> partialUpdateCitizensMeetings(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CitizensMeetings citizensMeetings
    ) throws URISyntaxException {
        log.debug("REST request to partial update CitizensMeetings partially : {}, {}", id, citizensMeetings);
        if (citizensMeetings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, citizensMeetings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citizensMeetingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CitizensMeetings> result = citizensMeetingsService.partialUpdate(citizensMeetings);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citizensMeetings.getId().toString())
        );
    }

    /**
     * {@code GET  /citizens-meetings} : get all the citizensMeetings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of citizensMeetings in body.
     */
    @GetMapping("/citizens-meetings")
    public ResponseEntity<List<CitizensMeetings>> getAllCitizensMeetings(
        CitizensMeetingsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CitizensMeetings by criteria: {}", criteria);
        Page<CitizensMeetings> page = citizensMeetingsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /citizens-meetings/count} : count all the citizensMeetings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/citizens-meetings/count")
    public ResponseEntity<Long> countCitizensMeetings(CitizensMeetingsCriteria criteria) {
        log.debug("REST request to count CitizensMeetings by criteria: {}", criteria);
        return ResponseEntity.ok().body(citizensMeetingsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /citizens-meetings/:id} : get the "id" citizensMeetings.
     *
     * @param id the id of the citizensMeetings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the citizensMeetings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/citizens-meetings/{id}")
    public ResponseEntity<CitizensMeetings> getCitizensMeetings(@PathVariable Long id) {
        log.debug("REST request to get CitizensMeetings : {}", id);
        Optional<CitizensMeetings> citizensMeetings = citizensMeetingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(citizensMeetings);
    }

    /**
     * {@code DELETE  /citizens-meetings/:id} : delete the "id" citizensMeetings.
     *
     * @param id the id of the citizensMeetings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/citizens-meetings/{id}")
    public ResponseEntity<Void> deleteCitizensMeetings(@PathVariable Long id) {
        log.debug("REST request to delete CitizensMeetings : {}", id);
        citizensMeetingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
