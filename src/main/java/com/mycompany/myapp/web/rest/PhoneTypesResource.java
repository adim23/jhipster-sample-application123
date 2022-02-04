package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PhoneTypes;
import com.mycompany.myapp.repository.PhoneTypesRepository;
import com.mycompany.myapp.service.PhoneTypesQueryService;
import com.mycompany.myapp.service.PhoneTypesService;
import com.mycompany.myapp.service.criteria.PhoneTypesCriteria;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PhoneTypes}.
 */
@RestController
@RequestMapping("/api")
public class PhoneTypesResource {

    private final Logger log = LoggerFactory.getLogger(PhoneTypesResource.class);

    private static final String ENTITY_NAME = "phoneTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhoneTypesService phoneTypesService;

    private final PhoneTypesRepository phoneTypesRepository;

    private final PhoneTypesQueryService phoneTypesQueryService;

    public PhoneTypesResource(
        PhoneTypesService phoneTypesService,
        PhoneTypesRepository phoneTypesRepository,
        PhoneTypesQueryService phoneTypesQueryService
    ) {
        this.phoneTypesService = phoneTypesService;
        this.phoneTypesRepository = phoneTypesRepository;
        this.phoneTypesQueryService = phoneTypesQueryService;
    }

    /**
     * {@code POST  /phone-types} : Create a new phoneTypes.
     *
     * @param phoneTypes the phoneTypes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phoneTypes, or with status {@code 400 (Bad Request)} if the phoneTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phone-types")
    public ResponseEntity<PhoneTypes> createPhoneTypes(@Valid @RequestBody PhoneTypes phoneTypes) throws URISyntaxException {
        log.debug("REST request to save PhoneTypes : {}", phoneTypes);
        if (phoneTypes.getId() != null) {
            throw new BadRequestAlertException("A new phoneTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhoneTypes result = phoneTypesService.save(phoneTypes);
        return ResponseEntity
            .created(new URI("/api/phone-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phone-types/:id} : Updates an existing phoneTypes.
     *
     * @param id the id of the phoneTypes to save.
     * @param phoneTypes the phoneTypes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phoneTypes,
     * or with status {@code 400 (Bad Request)} if the phoneTypes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phoneTypes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phone-types/{id}")
    public ResponseEntity<PhoneTypes> updatePhoneTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PhoneTypes phoneTypes
    ) throws URISyntaxException {
        log.debug("REST request to update PhoneTypes : {}, {}", id, phoneTypes);
        if (phoneTypes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phoneTypes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phoneTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PhoneTypes result = phoneTypesService.save(phoneTypes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phoneTypes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /phone-types/:id} : Partial updates given fields of an existing phoneTypes, field will ignore if it is null
     *
     * @param id the id of the phoneTypes to save.
     * @param phoneTypes the phoneTypes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phoneTypes,
     * or with status {@code 400 (Bad Request)} if the phoneTypes is not valid,
     * or with status {@code 404 (Not Found)} if the phoneTypes is not found,
     * or with status {@code 500 (Internal Server Error)} if the phoneTypes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/phone-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PhoneTypes> partialUpdatePhoneTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PhoneTypes phoneTypes
    ) throws URISyntaxException {
        log.debug("REST request to partial update PhoneTypes partially : {}, {}", id, phoneTypes);
        if (phoneTypes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phoneTypes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phoneTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PhoneTypes> result = phoneTypesService.partialUpdate(phoneTypes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phoneTypes.getId().toString())
        );
    }

    /**
     * {@code GET  /phone-types} : get all the phoneTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phoneTypes in body.
     */
    @GetMapping("/phone-types")
    public ResponseEntity<List<PhoneTypes>> getAllPhoneTypes(
        PhoneTypesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PhoneTypes by criteria: {}", criteria);
        Page<PhoneTypes> page = phoneTypesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /phone-types/count} : count all the phoneTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/phone-types/count")
    public ResponseEntity<Long> countPhoneTypes(PhoneTypesCriteria criteria) {
        log.debug("REST request to count PhoneTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(phoneTypesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /phone-types/:id} : get the "id" phoneTypes.
     *
     * @param id the id of the phoneTypes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phoneTypes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phone-types/{id}")
    public ResponseEntity<PhoneTypes> getPhoneTypes(@PathVariable Long id) {
        log.debug("REST request to get PhoneTypes : {}", id);
        Optional<PhoneTypes> phoneTypes = phoneTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phoneTypes);
    }

    /**
     * {@code DELETE  /phone-types/:id} : delete the "id" phoneTypes.
     *
     * @param id the id of the phoneTypes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phone-types/{id}")
    public ResponseEntity<Void> deletePhoneTypes(@PathVariable Long id) {
        log.debug("REST request to delete PhoneTypes : {}", id);
        phoneTypesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
