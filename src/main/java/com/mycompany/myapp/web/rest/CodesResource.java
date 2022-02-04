package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Codes;
import com.mycompany.myapp.repository.CodesRepository;
import com.mycompany.myapp.service.CodesQueryService;
import com.mycompany.myapp.service.CodesService;
import com.mycompany.myapp.service.criteria.CodesCriteria;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Codes}.
 */
@RestController
@RequestMapping("/api")
public class CodesResource {

    private final Logger log = LoggerFactory.getLogger(CodesResource.class);

    private static final String ENTITY_NAME = "codes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CodesService codesService;

    private final CodesRepository codesRepository;

    private final CodesQueryService codesQueryService;

    public CodesResource(CodesService codesService, CodesRepository codesRepository, CodesQueryService codesQueryService) {
        this.codesService = codesService;
        this.codesRepository = codesRepository;
        this.codesQueryService = codesQueryService;
    }

    /**
     * {@code POST  /codes} : Create a new codes.
     *
     * @param codes the codes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new codes, or with status {@code 400 (Bad Request)} if the codes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/codes")
    public ResponseEntity<Codes> createCodes(@Valid @RequestBody Codes codes) throws URISyntaxException {
        log.debug("REST request to save Codes : {}", codes);
        if (codes.getId() != null) {
            throw new BadRequestAlertException("A new codes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Codes result = codesService.save(codes);
        return ResponseEntity
            .created(new URI("/api/codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /codes/:id} : Updates an existing codes.
     *
     * @param id the id of the codes to save.
     * @param codes the codes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codes,
     * or with status {@code 400 (Bad Request)} if the codes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the codes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/codes/{id}")
    public ResponseEntity<Codes> updateCodes(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Codes codes)
        throws URISyntaxException {
        log.debug("REST request to update Codes : {}, {}", id, codes);
        if (codes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, codes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Codes result = codesService.save(codes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /codes/:id} : Partial updates given fields of an existing codes, field will ignore if it is null
     *
     * @param id the id of the codes to save.
     * @param codes the codes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codes,
     * or with status {@code 400 (Bad Request)} if the codes is not valid,
     * or with status {@code 404 (Not Found)} if the codes is not found,
     * or with status {@code 500 (Internal Server Error)} if the codes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/codes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Codes> partialUpdateCodes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Codes codes
    ) throws URISyntaxException {
        log.debug("REST request to partial update Codes partially : {}, {}", id, codes);
        if (codes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, codes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Codes> result = codesService.partialUpdate(codes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codes.getId().toString())
        );
    }

    /**
     * {@code GET  /codes} : get all the codes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of codes in body.
     */
    @GetMapping("/codes")
    public ResponseEntity<List<Codes>> getAllCodes(
        CodesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Codes by criteria: {}", criteria);
        Page<Codes> page = codesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /codes/count} : count all the codes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/codes/count")
    public ResponseEntity<Long> countCodes(CodesCriteria criteria) {
        log.debug("REST request to count Codes by criteria: {}", criteria);
        return ResponseEntity.ok().body(codesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /codes/:id} : get the "id" codes.
     *
     * @param id the id of the codes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the codes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/codes/{id}")
    public ResponseEntity<Codes> getCodes(@PathVariable Long id) {
        log.debug("REST request to get Codes : {}", id);
        Optional<Codes> codes = codesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(codes);
    }

    /**
     * {@code DELETE  /codes/:id} : delete the "id" codes.
     *
     * @param id the id of the codes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/codes/{id}")
    public ResponseEntity<Void> deleteCodes(@PathVariable Long id) {
        log.debug("REST request to delete Codes : {}", id);
        codesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
