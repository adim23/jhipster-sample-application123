package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SocialKinds;
import com.mycompany.myapp.repository.SocialKindsRepository;
import com.mycompany.myapp.service.SocialKindsQueryService;
import com.mycompany.myapp.service.SocialKindsService;
import com.mycompany.myapp.service.criteria.SocialKindsCriteria;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.SocialKinds}.
 */
@RestController
@RequestMapping("/api")
public class SocialKindsResource {

    private final Logger log = LoggerFactory.getLogger(SocialKindsResource.class);

    private static final String ENTITY_NAME = "socialKinds";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocialKindsService socialKindsService;

    private final SocialKindsRepository socialKindsRepository;

    private final SocialKindsQueryService socialKindsQueryService;

    public SocialKindsResource(
        SocialKindsService socialKindsService,
        SocialKindsRepository socialKindsRepository,
        SocialKindsQueryService socialKindsQueryService
    ) {
        this.socialKindsService = socialKindsService;
        this.socialKindsRepository = socialKindsRepository;
        this.socialKindsQueryService = socialKindsQueryService;
    }

    /**
     * {@code POST  /social-kinds} : Create a new socialKinds.
     *
     * @param socialKinds the socialKinds to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new socialKinds, or with status {@code 400 (Bad Request)} if the socialKinds has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/social-kinds")
    public ResponseEntity<SocialKinds> createSocialKinds(@Valid @RequestBody SocialKinds socialKinds) throws URISyntaxException {
        log.debug("REST request to save SocialKinds : {}", socialKinds);
        if (socialKinds.getId() != null) {
            throw new BadRequestAlertException("A new socialKinds cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocialKinds result = socialKindsService.save(socialKinds);
        return ResponseEntity
            .created(new URI("/api/social-kinds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /social-kinds/:id} : Updates an existing socialKinds.
     *
     * @param id the id of the socialKinds to save.
     * @param socialKinds the socialKinds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socialKinds,
     * or with status {@code 400 (Bad Request)} if the socialKinds is not valid,
     * or with status {@code 500 (Internal Server Error)} if the socialKinds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/social-kinds/{id}")
    public ResponseEntity<SocialKinds> updateSocialKinds(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SocialKinds socialKinds
    ) throws URISyntaxException {
        log.debug("REST request to update SocialKinds : {}, {}", id, socialKinds);
        if (socialKinds.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socialKinds.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socialKindsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocialKinds result = socialKindsService.save(socialKinds);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, socialKinds.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /social-kinds/:id} : Partial updates given fields of an existing socialKinds, field will ignore if it is null
     *
     * @param id the id of the socialKinds to save.
     * @param socialKinds the socialKinds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socialKinds,
     * or with status {@code 400 (Bad Request)} if the socialKinds is not valid,
     * or with status {@code 404 (Not Found)} if the socialKinds is not found,
     * or with status {@code 500 (Internal Server Error)} if the socialKinds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/social-kinds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocialKinds> partialUpdateSocialKinds(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SocialKinds socialKinds
    ) throws URISyntaxException {
        log.debug("REST request to partial update SocialKinds partially : {}, {}", id, socialKinds);
        if (socialKinds.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socialKinds.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socialKindsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocialKinds> result = socialKindsService.partialUpdate(socialKinds);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, socialKinds.getId().toString())
        );
    }

    /**
     * {@code GET  /social-kinds} : get all the socialKinds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of socialKinds in body.
     */
    @GetMapping("/social-kinds")
    public ResponseEntity<List<SocialKinds>> getAllSocialKinds(
        SocialKindsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SocialKinds by criteria: {}", criteria);
        Page<SocialKinds> page = socialKindsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /social-kinds/count} : count all the socialKinds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/social-kinds/count")
    public ResponseEntity<Long> countSocialKinds(SocialKindsCriteria criteria) {
        log.debug("REST request to count SocialKinds by criteria: {}", criteria);
        return ResponseEntity.ok().body(socialKindsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /social-kinds/:id} : get the "id" socialKinds.
     *
     * @param id the id of the socialKinds to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the socialKinds, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/social-kinds/{id}")
    public ResponseEntity<SocialKinds> getSocialKinds(@PathVariable Long id) {
        log.debug("REST request to get SocialKinds : {}", id);
        Optional<SocialKinds> socialKinds = socialKindsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(socialKinds);
    }

    /**
     * {@code DELETE  /social-kinds/:id} : delete the "id" socialKinds.
     *
     * @param id the id of the socialKinds to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/social-kinds/{id}")
    public ResponseEntity<Void> deleteSocialKinds(@PathVariable Long id) {
        log.debug("REST request to delete SocialKinds : {}", id);
        socialKindsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
