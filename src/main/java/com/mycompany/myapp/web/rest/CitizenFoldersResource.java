package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CitizenFolders;
import com.mycompany.myapp.repository.CitizenFoldersRepository;
import com.mycompany.myapp.service.CitizenFoldersQueryService;
import com.mycompany.myapp.service.CitizenFoldersService;
import com.mycompany.myapp.service.criteria.CitizenFoldersCriteria;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CitizenFolders}.
 */
@RestController
@RequestMapping("/api")
public class CitizenFoldersResource {

    private final Logger log = LoggerFactory.getLogger(CitizenFoldersResource.class);

    private static final String ENTITY_NAME = "citizenFolders";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitizenFoldersService citizenFoldersService;

    private final CitizenFoldersRepository citizenFoldersRepository;

    private final CitizenFoldersQueryService citizenFoldersQueryService;

    public CitizenFoldersResource(
        CitizenFoldersService citizenFoldersService,
        CitizenFoldersRepository citizenFoldersRepository,
        CitizenFoldersQueryService citizenFoldersQueryService
    ) {
        this.citizenFoldersService = citizenFoldersService;
        this.citizenFoldersRepository = citizenFoldersRepository;
        this.citizenFoldersQueryService = citizenFoldersQueryService;
    }

    /**
     * {@code POST  /citizen-folders} : Create a new citizenFolders.
     *
     * @param citizenFolders the citizenFolders to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new citizenFolders, or with status {@code 400 (Bad Request)} if the citizenFolders has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/citizen-folders")
    public ResponseEntity<CitizenFolders> createCitizenFolders(@Valid @RequestBody CitizenFolders citizenFolders)
        throws URISyntaxException {
        log.debug("REST request to save CitizenFolders : {}", citizenFolders);
        if (citizenFolders.getId() != null) {
            throw new BadRequestAlertException("A new citizenFolders cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CitizenFolders result = citizenFoldersService.save(citizenFolders);
        return ResponseEntity
            .created(new URI("/api/citizen-folders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /citizen-folders/:id} : Updates an existing citizenFolders.
     *
     * @param id the id of the citizenFolders to save.
     * @param citizenFolders the citizenFolders to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citizenFolders,
     * or with status {@code 400 (Bad Request)} if the citizenFolders is not valid,
     * or with status {@code 500 (Internal Server Error)} if the citizenFolders couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/citizen-folders/{id}")
    public ResponseEntity<CitizenFolders> updateCitizenFolders(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CitizenFolders citizenFolders
    ) throws URISyntaxException {
        log.debug("REST request to update CitizenFolders : {}, {}", id, citizenFolders);
        if (citizenFolders.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, citizenFolders.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citizenFoldersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CitizenFolders result = citizenFoldersService.save(citizenFolders);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citizenFolders.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /citizen-folders/:id} : Partial updates given fields of an existing citizenFolders, field will ignore if it is null
     *
     * @param id the id of the citizenFolders to save.
     * @param citizenFolders the citizenFolders to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citizenFolders,
     * or with status {@code 400 (Bad Request)} if the citizenFolders is not valid,
     * or with status {@code 404 (Not Found)} if the citizenFolders is not found,
     * or with status {@code 500 (Internal Server Error)} if the citizenFolders couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/citizen-folders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CitizenFolders> partialUpdateCitizenFolders(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CitizenFolders citizenFolders
    ) throws URISyntaxException {
        log.debug("REST request to partial update CitizenFolders partially : {}, {}", id, citizenFolders);
        if (citizenFolders.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, citizenFolders.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citizenFoldersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CitizenFolders> result = citizenFoldersService.partialUpdate(citizenFolders);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citizenFolders.getId().toString())
        );
    }

    /**
     * {@code GET  /citizen-folders} : get all the citizenFolders.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of citizenFolders in body.
     */
    @GetMapping("/citizen-folders")
    public ResponseEntity<List<CitizenFolders>> getAllCitizenFolders(
        CitizenFoldersCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CitizenFolders by criteria: {}", criteria);
        Page<CitizenFolders> page = citizenFoldersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /citizen-folders/count} : count all the citizenFolders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/citizen-folders/count")
    public ResponseEntity<Long> countCitizenFolders(CitizenFoldersCriteria criteria) {
        log.debug("REST request to count CitizenFolders by criteria: {}", criteria);
        return ResponseEntity.ok().body(citizenFoldersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /citizen-folders/:id} : get the "id" citizenFolders.
     *
     * @param id the id of the citizenFolders to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the citizenFolders, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/citizen-folders/{id}")
    public ResponseEntity<CitizenFolders> getCitizenFolders(@PathVariable Long id) {
        log.debug("REST request to get CitizenFolders : {}", id);
        Optional<CitizenFolders> citizenFolders = citizenFoldersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(citizenFolders);
    }

    /**
     * {@code DELETE  /citizen-folders/:id} : delete the "id" citizenFolders.
     *
     * @param id the id of the citizenFolders to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/citizen-folders/{id}")
    public ResponseEntity<Void> deleteCitizenFolders(@PathVariable Long id) {
        log.debug("REST request to delete CitizenFolders : {}", id);
        citizenFoldersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
