package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Phones;
import com.mycompany.myapp.repository.PhonesRepository;
import com.mycompany.myapp.service.PhonesQueryService;
import com.mycompany.myapp.service.PhonesService;
import com.mycompany.myapp.service.criteria.PhonesCriteria;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Phones}.
 */
@RestController
@RequestMapping("/api")
public class PhonesResource {

    private final Logger log = LoggerFactory.getLogger(PhonesResource.class);

    private static final String ENTITY_NAME = "phones";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhonesService phonesService;

    private final PhonesRepository phonesRepository;

    private final PhonesQueryService phonesQueryService;

    public PhonesResource(PhonesService phonesService, PhonesRepository phonesRepository, PhonesQueryService phonesQueryService) {
        this.phonesService = phonesService;
        this.phonesRepository = phonesRepository;
        this.phonesQueryService = phonesQueryService;
    }

    /**
     * {@code POST  /phones} : Create a new phones.
     *
     * @param phones the phones to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phones, or with status {@code 400 (Bad Request)} if the phones has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phones")
    public ResponseEntity<Phones> createPhones(@Valid @RequestBody Phones phones) throws URISyntaxException {
        log.debug("REST request to save Phones : {}", phones);
        if (phones.getId() != null) {
            throw new BadRequestAlertException("A new phones cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Phones result = phonesService.save(phones);
        return ResponseEntity
            .created(new URI("/api/phones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phones/:id} : Updates an existing phones.
     *
     * @param id the id of the phones to save.
     * @param phones the phones to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phones,
     * or with status {@code 400 (Bad Request)} if the phones is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phones couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phones/{id}")
    public ResponseEntity<Phones> updatePhones(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Phones phones
    ) throws URISyntaxException {
        log.debug("REST request to update Phones : {}, {}", id, phones);
        if (phones.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phones.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phonesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Phones result = phonesService.save(phones);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phones.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /phones/:id} : Partial updates given fields of an existing phones, field will ignore if it is null
     *
     * @param id the id of the phones to save.
     * @param phones the phones to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phones,
     * or with status {@code 400 (Bad Request)} if the phones is not valid,
     * or with status {@code 404 (Not Found)} if the phones is not found,
     * or with status {@code 500 (Internal Server Error)} if the phones couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/phones/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Phones> partialUpdatePhones(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Phones phones
    ) throws URISyntaxException {
        log.debug("REST request to partial update Phones partially : {}, {}", id, phones);
        if (phones.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phones.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phonesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Phones> result = phonesService.partialUpdate(phones);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phones.getId().toString())
        );
    }

    /**
     * {@code GET  /phones} : get all the phones.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phones in body.
     */
    @GetMapping("/phones")
    public ResponseEntity<List<Phones>> getAllPhones(
        PhonesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Phones by criteria: {}", criteria);
        Page<Phones> page = phonesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /phones/count} : count all the phones.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/phones/count")
    public ResponseEntity<Long> countPhones(PhonesCriteria criteria) {
        log.debug("REST request to count Phones by criteria: {}", criteria);
        return ResponseEntity.ok().body(phonesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /phones/:id} : get the "id" phones.
     *
     * @param id the id of the phones to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phones, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phones/{id}")
    public ResponseEntity<Phones> getPhones(@PathVariable Long id) {
        log.debug("REST request to get Phones : {}", id);
        Optional<Phones> phones = phonesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phones);
    }

    /**
     * {@code DELETE  /phones/:id} : delete the "id" phones.
     *
     * @param id the id of the phones to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phones/{id}")
    public ResponseEntity<Void> deletePhones(@PathVariable Long id) {
        log.debug("REST request to delete Phones : {}", id);
        phonesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
