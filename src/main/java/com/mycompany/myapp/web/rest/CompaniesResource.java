package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Companies;
import com.mycompany.myapp.repository.CompaniesRepository;
import com.mycompany.myapp.service.CompaniesQueryService;
import com.mycompany.myapp.service.CompaniesService;
import com.mycompany.myapp.service.criteria.CompaniesCriteria;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Companies}.
 */
@RestController
@RequestMapping("/api")
public class CompaniesResource {

    private final Logger log = LoggerFactory.getLogger(CompaniesResource.class);

    private static final String ENTITY_NAME = "companies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompaniesService companiesService;

    private final CompaniesRepository companiesRepository;

    private final CompaniesQueryService companiesQueryService;

    public CompaniesResource(
        CompaniesService companiesService,
        CompaniesRepository companiesRepository,
        CompaniesQueryService companiesQueryService
    ) {
        this.companiesService = companiesService;
        this.companiesRepository = companiesRepository;
        this.companiesQueryService = companiesQueryService;
    }

    /**
     * {@code POST  /companies} : Create a new companies.
     *
     * @param companies the companies to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companies, or with status {@code 400 (Bad Request)} if the companies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/companies")
    public ResponseEntity<Companies> createCompanies(@Valid @RequestBody Companies companies) throws URISyntaxException {
        log.debug("REST request to save Companies : {}", companies);
        if (companies.getId() != null) {
            throw new BadRequestAlertException("A new companies cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Companies result = companiesService.save(companies);
        return ResponseEntity
            .created(new URI("/api/companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /companies/:id} : Updates an existing companies.
     *
     * @param id the id of the companies to save.
     * @param companies the companies to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companies,
     * or with status {@code 400 (Bad Request)} if the companies is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companies couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/companies/{id}")
    public ResponseEntity<Companies> updateCompanies(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Companies companies
    ) throws URISyntaxException {
        log.debug("REST request to update Companies : {}, {}", id, companies);
        if (companies.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companies.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Companies result = companiesService.save(companies);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companies.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /companies/:id} : Partial updates given fields of an existing companies, field will ignore if it is null
     *
     * @param id the id of the companies to save.
     * @param companies the companies to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companies,
     * or with status {@code 400 (Bad Request)} if the companies is not valid,
     * or with status {@code 404 (Not Found)} if the companies is not found,
     * or with status {@code 500 (Internal Server Error)} if the companies couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/companies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Companies> partialUpdateCompanies(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Companies companies
    ) throws URISyntaxException {
        log.debug("REST request to partial update Companies partially : {}, {}", id, companies);
        if (companies.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companies.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Companies> result = companiesService.partialUpdate(companies);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companies.getId().toString())
        );
    }

    /**
     * {@code GET  /companies} : get all the companies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companies in body.
     */
    @GetMapping("/companies")
    public ResponseEntity<List<Companies>> getAllCompanies(
        CompaniesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Companies by criteria: {}", criteria);
        Page<Companies> page = companiesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /companies/count} : count all the companies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/companies/count")
    public ResponseEntity<Long> countCompanies(CompaniesCriteria criteria) {
        log.debug("REST request to count Companies by criteria: {}", criteria);
        return ResponseEntity.ok().body(companiesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /companies/:id} : get the "id" companies.
     *
     * @param id the id of the companies to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companies, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/companies/{id}")
    public ResponseEntity<Companies> getCompanies(@PathVariable Long id) {
        log.debug("REST request to get Companies : {}", id);
        Optional<Companies> companies = companiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companies);
    }

    /**
     * {@code DELETE  /companies/:id} : delete the "id" companies.
     *
     * @param id the id of the companies to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompanies(@PathVariable Long id) {
        log.debug("REST request to delete Companies : {}", id);
        companiesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
