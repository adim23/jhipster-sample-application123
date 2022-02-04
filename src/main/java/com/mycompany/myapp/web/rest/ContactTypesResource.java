package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ContactTypes;
import com.mycompany.myapp.repository.ContactTypesRepository;
import com.mycompany.myapp.service.ContactTypesQueryService;
import com.mycompany.myapp.service.ContactTypesService;
import com.mycompany.myapp.service.criteria.ContactTypesCriteria;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ContactTypes}.
 */
@RestController
@RequestMapping("/api")
public class ContactTypesResource {

    private final Logger log = LoggerFactory.getLogger(ContactTypesResource.class);

    private static final String ENTITY_NAME = "contactTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactTypesService contactTypesService;

    private final ContactTypesRepository contactTypesRepository;

    private final ContactTypesQueryService contactTypesQueryService;

    public ContactTypesResource(
        ContactTypesService contactTypesService,
        ContactTypesRepository contactTypesRepository,
        ContactTypesQueryService contactTypesQueryService
    ) {
        this.contactTypesService = contactTypesService;
        this.contactTypesRepository = contactTypesRepository;
        this.contactTypesQueryService = contactTypesQueryService;
    }

    /**
     * {@code POST  /contact-types} : Create a new contactTypes.
     *
     * @param contactTypes the contactTypes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactTypes, or with status {@code 400 (Bad Request)} if the contactTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-types")
    public ResponseEntity<ContactTypes> createContactTypes(@Valid @RequestBody ContactTypes contactTypes) throws URISyntaxException {
        log.debug("REST request to save ContactTypes : {}", contactTypes);
        if (contactTypes.getId() != null) {
            throw new BadRequestAlertException("A new contactTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactTypes result = contactTypesService.save(contactTypes);
        return ResponseEntity
            .created(new URI("/api/contact-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-types/:id} : Updates an existing contactTypes.
     *
     * @param id the id of the contactTypes to save.
     * @param contactTypes the contactTypes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactTypes,
     * or with status {@code 400 (Bad Request)} if the contactTypes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactTypes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-types/{id}")
    public ResponseEntity<ContactTypes> updateContactTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContactTypes contactTypes
    ) throws URISyntaxException {
        log.debug("REST request to update ContactTypes : {}, {}", id, contactTypes);
        if (contactTypes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactTypes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContactTypes result = contactTypesService.save(contactTypes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactTypes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contact-types/:id} : Partial updates given fields of an existing contactTypes, field will ignore if it is null
     *
     * @param id the id of the contactTypes to save.
     * @param contactTypes the contactTypes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactTypes,
     * or with status {@code 400 (Bad Request)} if the contactTypes is not valid,
     * or with status {@code 404 (Not Found)} if the contactTypes is not found,
     * or with status {@code 500 (Internal Server Error)} if the contactTypes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contact-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContactTypes> partialUpdateContactTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContactTypes contactTypes
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContactTypes partially : {}, {}", id, contactTypes);
        if (contactTypes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactTypes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContactTypes> result = contactTypesService.partialUpdate(contactTypes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactTypes.getId().toString())
        );
    }

    /**
     * {@code GET  /contact-types} : get all the contactTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactTypes in body.
     */
    @GetMapping("/contact-types")
    public ResponseEntity<List<ContactTypes>> getAllContactTypes(
        ContactTypesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ContactTypes by criteria: {}", criteria);
        Page<ContactTypes> page = contactTypesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contact-types/count} : count all the contactTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/contact-types/count")
    public ResponseEntity<Long> countContactTypes(ContactTypesCriteria criteria) {
        log.debug("REST request to count ContactTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(contactTypesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contact-types/:id} : get the "id" contactTypes.
     *
     * @param id the id of the contactTypes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactTypes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-types/{id}")
    public ResponseEntity<ContactTypes> getContactTypes(@PathVariable Long id) {
        log.debug("REST request to get ContactTypes : {}", id);
        Optional<ContactTypes> contactTypes = contactTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactTypes);
    }

    /**
     * {@code DELETE  /contact-types/:id} : delete the "id" contactTypes.
     *
     * @param id the id of the contactTypes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-types/{id}")
    public ResponseEntity<Void> deleteContactTypes(@PathVariable Long id) {
        log.debug("REST request to delete ContactTypes : {}", id);
        contactTypesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
