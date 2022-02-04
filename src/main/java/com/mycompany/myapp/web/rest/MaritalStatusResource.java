package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.MaritalStatus;
import com.mycompany.myapp.repository.MaritalStatusRepository;
import com.mycompany.myapp.service.MaritalStatusQueryService;
import com.mycompany.myapp.service.MaritalStatusService;
import com.mycompany.myapp.service.criteria.MaritalStatusCriteria;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.MaritalStatus}.
 */
@RestController
@RequestMapping("/api")
public class MaritalStatusResource {

    private final Logger log = LoggerFactory.getLogger(MaritalStatusResource.class);

    private static final String ENTITY_NAME = "maritalStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaritalStatusService maritalStatusService;

    private final MaritalStatusRepository maritalStatusRepository;

    private final MaritalStatusQueryService maritalStatusQueryService;

    public MaritalStatusResource(
        MaritalStatusService maritalStatusService,
        MaritalStatusRepository maritalStatusRepository,
        MaritalStatusQueryService maritalStatusQueryService
    ) {
        this.maritalStatusService = maritalStatusService;
        this.maritalStatusRepository = maritalStatusRepository;
        this.maritalStatusQueryService = maritalStatusQueryService;
    }

    /**
     * {@code POST  /marital-statuses} : Create a new maritalStatus.
     *
     * @param maritalStatus the maritalStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new maritalStatus, or with status {@code 400 (Bad Request)} if the maritalStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/marital-statuses")
    public ResponseEntity<MaritalStatus> createMaritalStatus(@Valid @RequestBody MaritalStatus maritalStatus) throws URISyntaxException {
        log.debug("REST request to save MaritalStatus : {}", maritalStatus);
        if (maritalStatus.getId() != null) {
            throw new BadRequestAlertException("A new maritalStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaritalStatus result = maritalStatusService.save(maritalStatus);
        return ResponseEntity
            .created(new URI("/api/marital-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /marital-statuses/:id} : Updates an existing maritalStatus.
     *
     * @param id the id of the maritalStatus to save.
     * @param maritalStatus the maritalStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated maritalStatus,
     * or with status {@code 400 (Bad Request)} if the maritalStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the maritalStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/marital-statuses/{id}")
    public ResponseEntity<MaritalStatus> updateMaritalStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MaritalStatus maritalStatus
    ) throws URISyntaxException {
        log.debug("REST request to update MaritalStatus : {}, {}", id, maritalStatus);
        if (maritalStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, maritalStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!maritalStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MaritalStatus result = maritalStatusService.save(maritalStatus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, maritalStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /marital-statuses/:id} : Partial updates given fields of an existing maritalStatus, field will ignore if it is null
     *
     * @param id the id of the maritalStatus to save.
     * @param maritalStatus the maritalStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated maritalStatus,
     * or with status {@code 400 (Bad Request)} if the maritalStatus is not valid,
     * or with status {@code 404 (Not Found)} if the maritalStatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the maritalStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/marital-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MaritalStatus> partialUpdateMaritalStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MaritalStatus maritalStatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update MaritalStatus partially : {}, {}", id, maritalStatus);
        if (maritalStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, maritalStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!maritalStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MaritalStatus> result = maritalStatusService.partialUpdate(maritalStatus);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, maritalStatus.getId().toString())
        );
    }

    /**
     * {@code GET  /marital-statuses} : get all the maritalStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of maritalStatuses in body.
     */
    @GetMapping("/marital-statuses")
    public ResponseEntity<List<MaritalStatus>> getAllMaritalStatuses(
        MaritalStatusCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MaritalStatuses by criteria: {}", criteria);
        Page<MaritalStatus> page = maritalStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /marital-statuses/count} : count all the maritalStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/marital-statuses/count")
    public ResponseEntity<Long> countMaritalStatuses(MaritalStatusCriteria criteria) {
        log.debug("REST request to count MaritalStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(maritalStatusQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /marital-statuses/:id} : get the "id" maritalStatus.
     *
     * @param id the id of the maritalStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the maritalStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/marital-statuses/{id}")
    public ResponseEntity<MaritalStatus> getMaritalStatus(@PathVariable Long id) {
        log.debug("REST request to get MaritalStatus : {}", id);
        Optional<MaritalStatus> maritalStatus = maritalStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(maritalStatus);
    }

    /**
     * {@code DELETE  /marital-statuses/:id} : delete the "id" maritalStatus.
     *
     * @param id the id of the maritalStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/marital-statuses/{id}")
    public ResponseEntity<Void> deleteMaritalStatus(@PathVariable Long id) {
        log.debug("REST request to delete MaritalStatus : {}", id);
        maritalStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
