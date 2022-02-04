package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SocialContacts;
import com.mycompany.myapp.repository.SocialContactsRepository;
import com.mycompany.myapp.service.SocialContactsQueryService;
import com.mycompany.myapp.service.SocialContactsService;
import com.mycompany.myapp.service.criteria.SocialContactsCriteria;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.SocialContacts}.
 */
@RestController
@RequestMapping("/api")
public class SocialContactsResource {

    private final Logger log = LoggerFactory.getLogger(SocialContactsResource.class);

    private static final String ENTITY_NAME = "socialContacts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocialContactsService socialContactsService;

    private final SocialContactsRepository socialContactsRepository;

    private final SocialContactsQueryService socialContactsQueryService;

    public SocialContactsResource(
        SocialContactsService socialContactsService,
        SocialContactsRepository socialContactsRepository,
        SocialContactsQueryService socialContactsQueryService
    ) {
        this.socialContactsService = socialContactsService;
        this.socialContactsRepository = socialContactsRepository;
        this.socialContactsQueryService = socialContactsQueryService;
    }

    /**
     * {@code POST  /social-contacts} : Create a new socialContacts.
     *
     * @param socialContacts the socialContacts to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new socialContacts, or with status {@code 400 (Bad Request)} if the socialContacts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/social-contacts")
    public ResponseEntity<SocialContacts> createSocialContacts(@Valid @RequestBody SocialContacts socialContacts)
        throws URISyntaxException {
        log.debug("REST request to save SocialContacts : {}", socialContacts);
        if (socialContacts.getId() != null) {
            throw new BadRequestAlertException("A new socialContacts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocialContacts result = socialContactsService.save(socialContacts);
        return ResponseEntity
            .created(new URI("/api/social-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /social-contacts/:id} : Updates an existing socialContacts.
     *
     * @param id the id of the socialContacts to save.
     * @param socialContacts the socialContacts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socialContacts,
     * or with status {@code 400 (Bad Request)} if the socialContacts is not valid,
     * or with status {@code 500 (Internal Server Error)} if the socialContacts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/social-contacts/{id}")
    public ResponseEntity<SocialContacts> updateSocialContacts(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SocialContacts socialContacts
    ) throws URISyntaxException {
        log.debug("REST request to update SocialContacts : {}, {}", id, socialContacts);
        if (socialContacts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socialContacts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socialContactsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocialContacts result = socialContactsService.save(socialContacts);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, socialContacts.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /social-contacts/:id} : Partial updates given fields of an existing socialContacts, field will ignore if it is null
     *
     * @param id the id of the socialContacts to save.
     * @param socialContacts the socialContacts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socialContacts,
     * or with status {@code 400 (Bad Request)} if the socialContacts is not valid,
     * or with status {@code 404 (Not Found)} if the socialContacts is not found,
     * or with status {@code 500 (Internal Server Error)} if the socialContacts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/social-contacts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocialContacts> partialUpdateSocialContacts(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SocialContacts socialContacts
    ) throws URISyntaxException {
        log.debug("REST request to partial update SocialContacts partially : {}, {}", id, socialContacts);
        if (socialContacts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socialContacts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socialContactsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocialContacts> result = socialContactsService.partialUpdate(socialContacts);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, socialContacts.getId().toString())
        );
    }

    /**
     * {@code GET  /social-contacts} : get all the socialContacts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of socialContacts in body.
     */
    @GetMapping("/social-contacts")
    public ResponseEntity<List<SocialContacts>> getAllSocialContacts(
        SocialContactsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SocialContacts by criteria: {}", criteria);
        Page<SocialContacts> page = socialContactsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /social-contacts/count} : count all the socialContacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/social-contacts/count")
    public ResponseEntity<Long> countSocialContacts(SocialContactsCriteria criteria) {
        log.debug("REST request to count SocialContacts by criteria: {}", criteria);
        return ResponseEntity.ok().body(socialContactsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /social-contacts/:id} : get the "id" socialContacts.
     *
     * @param id the id of the socialContacts to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the socialContacts, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/social-contacts/{id}")
    public ResponseEntity<SocialContacts> getSocialContacts(@PathVariable Long id) {
        log.debug("REST request to get SocialContacts : {}", id);
        Optional<SocialContacts> socialContacts = socialContactsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(socialContacts);
    }

    /**
     * {@code DELETE  /social-contacts/:id} : delete the "id" socialContacts.
     *
     * @param id the id of the socialContacts to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/social-contacts/{id}")
    public ResponseEntity<Void> deleteSocialContacts(@PathVariable Long id) {
        log.debug("REST request to delete SocialContacts : {}", id);
        socialContactsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
