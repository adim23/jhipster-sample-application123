package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ZipCodes;
import com.mycompany.myapp.repository.ZipCodesRepository;
import com.mycompany.myapp.service.ZipCodesQueryService;
import com.mycompany.myapp.service.ZipCodesService;
import com.mycompany.myapp.service.criteria.ZipCodesCriteria;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ZipCodes}.
 */
@RestController
@RequestMapping("/api")
public class ZipCodesResource {

    private final Logger log = LoggerFactory.getLogger(ZipCodesResource.class);

    private static final String ENTITY_NAME = "zipCodes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZipCodesService zipCodesService;

    private final ZipCodesRepository zipCodesRepository;

    private final ZipCodesQueryService zipCodesQueryService;

    public ZipCodesResource(
        ZipCodesService zipCodesService,
        ZipCodesRepository zipCodesRepository,
        ZipCodesQueryService zipCodesQueryService
    ) {
        this.zipCodesService = zipCodesService;
        this.zipCodesRepository = zipCodesRepository;
        this.zipCodesQueryService = zipCodesQueryService;
    }

    /**
     * {@code POST  /zip-codes} : Create a new zipCodes.
     *
     * @param zipCodes the zipCodes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zipCodes, or with status {@code 400 (Bad Request)} if the zipCodes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/zip-codes")
    public ResponseEntity<ZipCodes> createZipCodes(@RequestBody ZipCodes zipCodes) throws URISyntaxException {
        log.debug("REST request to save ZipCodes : {}", zipCodes);
        if (zipCodes.getId() != null) {
            throw new BadRequestAlertException("A new zipCodes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZipCodes result = zipCodesService.save(zipCodes);
        return ResponseEntity
            .created(new URI("/api/zip-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /zip-codes/:id} : Updates an existing zipCodes.
     *
     * @param id the id of the zipCodes to save.
     * @param zipCodes the zipCodes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zipCodes,
     * or with status {@code 400 (Bad Request)} if the zipCodes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zipCodes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/zip-codes/{id}")
    public ResponseEntity<ZipCodes> updateZipCodes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ZipCodes zipCodes
    ) throws URISyntaxException {
        log.debug("REST request to update ZipCodes : {}, {}", id, zipCodes);
        if (zipCodes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zipCodes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zipCodesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ZipCodes result = zipCodesService.save(zipCodes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, zipCodes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /zip-codes/:id} : Partial updates given fields of an existing zipCodes, field will ignore if it is null
     *
     * @param id the id of the zipCodes to save.
     * @param zipCodes the zipCodes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zipCodes,
     * or with status {@code 400 (Bad Request)} if the zipCodes is not valid,
     * or with status {@code 404 (Not Found)} if the zipCodes is not found,
     * or with status {@code 500 (Internal Server Error)} if the zipCodes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/zip-codes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ZipCodes> partialUpdateZipCodes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ZipCodes zipCodes
    ) throws URISyntaxException {
        log.debug("REST request to partial update ZipCodes partially : {}, {}", id, zipCodes);
        if (zipCodes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zipCodes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zipCodesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ZipCodes> result = zipCodesService.partialUpdate(zipCodes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, zipCodes.getId().toString())
        );
    }

    /**
     * {@code GET  /zip-codes} : get all the zipCodes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zipCodes in body.
     */
    @GetMapping("/zip-codes")
    public ResponseEntity<List<ZipCodes>> getAllZipCodes(
        ZipCodesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ZipCodes by criteria: {}", criteria);
        Page<ZipCodes> page = zipCodesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /zip-codes/count} : count all the zipCodes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/zip-codes/count")
    public ResponseEntity<Long> countZipCodes(ZipCodesCriteria criteria) {
        log.debug("REST request to count ZipCodes by criteria: {}", criteria);
        return ResponseEntity.ok().body(zipCodesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /zip-codes/:id} : get the "id" zipCodes.
     *
     * @param id the id of the zipCodes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zipCodes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/zip-codes/{id}")
    public ResponseEntity<ZipCodes> getZipCodes(@PathVariable Long id) {
        log.debug("REST request to get ZipCodes : {}", id);
        Optional<ZipCodes> zipCodes = zipCodesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zipCodes);
    }

    /**
     * {@code DELETE  /zip-codes/:id} : delete the "id" zipCodes.
     *
     * @param id the id of the zipCodes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/zip-codes/{id}")
    public ResponseEntity<Void> deleteZipCodes(@PathVariable Long id) {
        log.debug("REST request to delete ZipCodes : {}", id);
        zipCodesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
