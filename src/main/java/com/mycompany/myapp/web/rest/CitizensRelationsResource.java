package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CitizensRelations;
import com.mycompany.myapp.repository.CitizensRelationsRepository;
import com.mycompany.myapp.service.CitizensRelationsQueryService;
import com.mycompany.myapp.service.CitizensRelationsService;
import com.mycompany.myapp.service.criteria.CitizensRelationsCriteria;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CitizensRelations}.
 */
@RestController
@RequestMapping("/api")
public class CitizensRelationsResource {

    private final Logger log = LoggerFactory.getLogger(CitizensRelationsResource.class);

    private static final String ENTITY_NAME = "citizensRelations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitizensRelationsService citizensRelationsService;

    private final CitizensRelationsRepository citizensRelationsRepository;

    private final CitizensRelationsQueryService citizensRelationsQueryService;

    public CitizensRelationsResource(
        CitizensRelationsService citizensRelationsService,
        CitizensRelationsRepository citizensRelationsRepository,
        CitizensRelationsQueryService citizensRelationsQueryService
    ) {
        this.citizensRelationsService = citizensRelationsService;
        this.citizensRelationsRepository = citizensRelationsRepository;
        this.citizensRelationsQueryService = citizensRelationsQueryService;
    }

    /**
     * {@code POST  /citizens-relations} : Create a new citizensRelations.
     *
     * @param citizensRelations the citizensRelations to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new citizensRelations, or with status {@code 400 (Bad Request)} if the citizensRelations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/citizens-relations")
    public ResponseEntity<CitizensRelations> createCitizensRelations(@Valid @RequestBody CitizensRelations citizensRelations)
        throws URISyntaxException {
        log.debug("REST request to save CitizensRelations : {}", citizensRelations);
        if (citizensRelations.getId() != null) {
            throw new BadRequestAlertException("A new citizensRelations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CitizensRelations result = citizensRelationsService.save(citizensRelations);
        return ResponseEntity
            .created(new URI("/api/citizens-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /citizens-relations/:id} : Updates an existing citizensRelations.
     *
     * @param id the id of the citizensRelations to save.
     * @param citizensRelations the citizensRelations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citizensRelations,
     * or with status {@code 400 (Bad Request)} if the citizensRelations is not valid,
     * or with status {@code 500 (Internal Server Error)} if the citizensRelations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/citizens-relations/{id}")
    public ResponseEntity<CitizensRelations> updateCitizensRelations(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CitizensRelations citizensRelations
    ) throws URISyntaxException {
        log.debug("REST request to update CitizensRelations : {}, {}", id, citizensRelations);
        if (citizensRelations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, citizensRelations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citizensRelationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CitizensRelations result = citizensRelationsService.save(citizensRelations);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citizensRelations.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /citizens-relations/:id} : Partial updates given fields of an existing citizensRelations, field will ignore if it is null
     *
     * @param id the id of the citizensRelations to save.
     * @param citizensRelations the citizensRelations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citizensRelations,
     * or with status {@code 400 (Bad Request)} if the citizensRelations is not valid,
     * or with status {@code 404 (Not Found)} if the citizensRelations is not found,
     * or with status {@code 500 (Internal Server Error)} if the citizensRelations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/citizens-relations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CitizensRelations> partialUpdateCitizensRelations(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CitizensRelations citizensRelations
    ) throws URISyntaxException {
        log.debug("REST request to partial update CitizensRelations partially : {}, {}", id, citizensRelations);
        if (citizensRelations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, citizensRelations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citizensRelationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CitizensRelations> result = citizensRelationsService.partialUpdate(citizensRelations);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citizensRelations.getId().toString())
        );
    }

    /**
     * {@code GET  /citizens-relations} : get all the citizensRelations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of citizensRelations in body.
     */
    @GetMapping("/citizens-relations")
    public ResponseEntity<List<CitizensRelations>> getAllCitizensRelations(
        CitizensRelationsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CitizensRelations by criteria: {}", criteria);
        Page<CitizensRelations> page = citizensRelationsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /citizens-relations/count} : count all the citizensRelations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/citizens-relations/count")
    public ResponseEntity<Long> countCitizensRelations(CitizensRelationsCriteria criteria) {
        log.debug("REST request to count CitizensRelations by criteria: {}", criteria);
        return ResponseEntity.ok().body(citizensRelationsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /citizens-relations/:id} : get the "id" citizensRelations.
     *
     * @param id the id of the citizensRelations to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the citizensRelations, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/citizens-relations/{id}")
    public ResponseEntity<CitizensRelations> getCitizensRelations(@PathVariable Long id) {
        log.debug("REST request to get CitizensRelations : {}", id);
        Optional<CitizensRelations> citizensRelations = citizensRelationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(citizensRelations);
    }

    /**
     * {@code DELETE  /citizens-relations/:id} : delete the "id" citizensRelations.
     *
     * @param id the id of the citizensRelations to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/citizens-relations/{id}")
    public ResponseEntity<Void> deleteCitizensRelations(@PathVariable Long id) {
        log.debug("REST request to delete CitizensRelations : {}", id);
        citizensRelationsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
