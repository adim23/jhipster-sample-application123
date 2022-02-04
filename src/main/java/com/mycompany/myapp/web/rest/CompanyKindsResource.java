package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CompanyKinds;
import com.mycompany.myapp.repository.CompanyKindsRepository;
import com.mycompany.myapp.service.CompanyKindsQueryService;
import com.mycompany.myapp.service.CompanyKindsService;
import com.mycompany.myapp.service.criteria.CompanyKindsCriteria;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CompanyKinds}.
 */
@RestController
@RequestMapping("/api")
public class CompanyKindsResource {

    private final Logger log = LoggerFactory.getLogger(CompanyKindsResource.class);

    private static final String ENTITY_NAME = "companyKinds";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyKindsService companyKindsService;

    private final CompanyKindsRepository companyKindsRepository;

    private final CompanyKindsQueryService companyKindsQueryService;

    public CompanyKindsResource(
        CompanyKindsService companyKindsService,
        CompanyKindsRepository companyKindsRepository,
        CompanyKindsQueryService companyKindsQueryService
    ) {
        this.companyKindsService = companyKindsService;
        this.companyKindsRepository = companyKindsRepository;
        this.companyKindsQueryService = companyKindsQueryService;
    }

    /**
     * {@code POST  /company-kinds} : Create a new companyKinds.
     *
     * @param companyKinds the companyKinds to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyKinds, or with status {@code 400 (Bad Request)} if the companyKinds has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-kinds")
    public ResponseEntity<CompanyKinds> createCompanyKinds(@Valid @RequestBody CompanyKinds companyKinds) throws URISyntaxException {
        log.debug("REST request to save CompanyKinds : {}", companyKinds);
        if (companyKinds.getId() != null) {
            throw new BadRequestAlertException("A new companyKinds cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyKinds result = companyKindsService.save(companyKinds);
        return ResponseEntity
            .created(new URI("/api/company-kinds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-kinds/:id} : Updates an existing companyKinds.
     *
     * @param id the id of the companyKinds to save.
     * @param companyKinds the companyKinds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyKinds,
     * or with status {@code 400 (Bad Request)} if the companyKinds is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyKinds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-kinds/{id}")
    public ResponseEntity<CompanyKinds> updateCompanyKinds(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompanyKinds companyKinds
    ) throws URISyntaxException {
        log.debug("REST request to update CompanyKinds : {}, {}", id, companyKinds);
        if (companyKinds.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyKinds.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyKindsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompanyKinds result = companyKindsService.save(companyKinds);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyKinds.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /company-kinds/:id} : Partial updates given fields of an existing companyKinds, field will ignore if it is null
     *
     * @param id the id of the companyKinds to save.
     * @param companyKinds the companyKinds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyKinds,
     * or with status {@code 400 (Bad Request)} if the companyKinds is not valid,
     * or with status {@code 404 (Not Found)} if the companyKinds is not found,
     * or with status {@code 500 (Internal Server Error)} if the companyKinds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/company-kinds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompanyKinds> partialUpdateCompanyKinds(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompanyKinds companyKinds
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompanyKinds partially : {}, {}", id, companyKinds);
        if (companyKinds.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyKinds.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyKindsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompanyKinds> result = companyKindsService.partialUpdate(companyKinds);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyKinds.getId().toString())
        );
    }

    /**
     * {@code GET  /company-kinds} : get all the companyKinds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyKinds in body.
     */
    @GetMapping("/company-kinds")
    public ResponseEntity<List<CompanyKinds>> getAllCompanyKinds(
        CompanyKindsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CompanyKinds by criteria: {}", criteria);
        Page<CompanyKinds> page = companyKindsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-kinds/count} : count all the companyKinds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/company-kinds/count")
    public ResponseEntity<Long> countCompanyKinds(CompanyKindsCriteria criteria) {
        log.debug("REST request to count CompanyKinds by criteria: {}", criteria);
        return ResponseEntity.ok().body(companyKindsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /company-kinds/:id} : get the "id" companyKinds.
     *
     * @param id the id of the companyKinds to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyKinds, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-kinds/{id}")
    public ResponseEntity<CompanyKinds> getCompanyKinds(@PathVariable Long id) {
        log.debug("REST request to get CompanyKinds : {}", id);
        Optional<CompanyKinds> companyKinds = companyKindsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyKinds);
    }

    /**
     * {@code DELETE  /company-kinds/:id} : delete the "id" companyKinds.
     *
     * @param id the id of the companyKinds to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-kinds/{id}")
    public ResponseEntity<Void> deleteCompanyKinds(@PathVariable Long id) {
        log.debug("REST request to delete CompanyKinds : {}", id);
        companyKindsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
