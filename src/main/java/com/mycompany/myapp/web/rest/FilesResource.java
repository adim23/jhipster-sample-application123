package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Files;
import com.mycompany.myapp.repository.FilesRepository;
import com.mycompany.myapp.service.FilesQueryService;
import com.mycompany.myapp.service.FilesService;
import com.mycompany.myapp.service.criteria.FilesCriteria;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Files}.
 */
@RestController
@RequestMapping("/api")
public class FilesResource {

    private final Logger log = LoggerFactory.getLogger(FilesResource.class);

    private static final String ENTITY_NAME = "files";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FilesService filesService;

    private final FilesRepository filesRepository;

    private final FilesQueryService filesQueryService;

    public FilesResource(FilesService filesService, FilesRepository filesRepository, FilesQueryService filesQueryService) {
        this.filesService = filesService;
        this.filesRepository = filesRepository;
        this.filesQueryService = filesQueryService;
    }

    /**
     * {@code POST  /files} : Create a new files.
     *
     * @param files the files to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new files, or with status {@code 400 (Bad Request)} if the files has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/files")
    public ResponseEntity<Files> createFiles(@RequestBody Files files) throws URISyntaxException {
        log.debug("REST request to save Files : {}", files);
        if (files.getId() != null) {
            throw new BadRequestAlertException("A new files cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Files result = filesService.save(files);
        return ResponseEntity
            .created(new URI("/api/files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /files/:id} : Updates an existing files.
     *
     * @param id the id of the files to save.
     * @param files the files to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated files,
     * or with status {@code 400 (Bad Request)} if the files is not valid,
     * or with status {@code 500 (Internal Server Error)} if the files couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/files/{id}")
    public ResponseEntity<Files> updateFiles(@PathVariable(value = "id", required = false) final Long id, @RequestBody Files files)
        throws URISyntaxException {
        log.debug("REST request to update Files : {}, {}", id, files);
        if (files.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, files.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Files result = filesService.save(files);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, files.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /files/:id} : Partial updates given fields of an existing files, field will ignore if it is null
     *
     * @param id the id of the files to save.
     * @param files the files to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated files,
     * or with status {@code 400 (Bad Request)} if the files is not valid,
     * or with status {@code 404 (Not Found)} if the files is not found,
     * or with status {@code 500 (Internal Server Error)} if the files couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/files/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Files> partialUpdateFiles(@PathVariable(value = "id", required = false) final Long id, @RequestBody Files files)
        throws URISyntaxException {
        log.debug("REST request to partial update Files partially : {}, {}", id, files);
        if (files.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, files.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Files> result = filesService.partialUpdate(files);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, files.getId().toString())
        );
    }

    /**
     * {@code GET  /files} : get all the files.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of files in body.
     */
    @GetMapping("/files")
    public ResponseEntity<List<Files>> getAllFiles(
        FilesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Files by criteria: {}", criteria);
        Page<Files> page = filesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /files/count} : count all the files.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/files/count")
    public ResponseEntity<Long> countFiles(FilesCriteria criteria) {
        log.debug("REST request to count Files by criteria: {}", criteria);
        return ResponseEntity.ok().body(filesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /files/:id} : get the "id" files.
     *
     * @param id the id of the files to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the files, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/files/{id}")
    public ResponseEntity<Files> getFiles(@PathVariable Long id) {
        log.debug("REST request to get Files : {}", id);
        Optional<Files> files = filesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(files);
    }

    /**
     * {@code DELETE  /files/:id} : delete the "id" files.
     *
     * @param id the id of the files to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/files/{id}")
    public ResponseEntity<Void> deleteFiles(@PathVariable Long id) {
        log.debug("REST request to delete Files : {}", id);
        filesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
