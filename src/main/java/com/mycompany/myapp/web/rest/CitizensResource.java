package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Citizens;
import com.mycompany.myapp.repository.CitizensRepository;
import com.mycompany.myapp.service.CitizensQueryService;
import com.mycompany.myapp.service.CitizensService;
import com.mycompany.myapp.service.criteria.CitizensCriteria;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Citizens}.
 */
@RestController
@RequestMapping("/api")
public class CitizensResource {

    private final Logger log = LoggerFactory.getLogger(CitizensResource.class);

    private static final String ENTITY_NAME = "citizens";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitizensService citizensService;

    private final CitizensRepository citizensRepository;

    private final CitizensQueryService citizensQueryService;

    public CitizensResource(
        CitizensService citizensService,
        CitizensRepository citizensRepository,
        CitizensQueryService citizensQueryService
    ) {
        this.citizensService = citizensService;
        this.citizensRepository = citizensRepository;
        this.citizensQueryService = citizensQueryService;
    }

    /**
     * {@code POST  /citizens} : Create a new citizens.
     *
     * @param citizens the citizens to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new citizens, or with status {@code 400 (Bad Request)} if the citizens has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/citizens")
    public ResponseEntity<Citizens> createCitizens(@RequestBody Citizens citizens) throws URISyntaxException {
        log.debug("REST request to save Citizens : {}", citizens);
        if (citizens.getId() != null) {
            throw new BadRequestAlertException("A new citizens cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Citizens result = citizensService.save(citizens);
        return ResponseEntity
            .created(new URI("/api/citizens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /citizens/:id} : Updates an existing citizens.
     *
     * @param id the id of the citizens to save.
     * @param citizens the citizens to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citizens,
     * or with status {@code 400 (Bad Request)} if the citizens is not valid,
     * or with status {@code 500 (Internal Server Error)} if the citizens couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/citizens/{id}")
    public ResponseEntity<Citizens> updateCitizens(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Citizens citizens
    ) throws URISyntaxException {
        log.debug("REST request to update Citizens : {}, {}", id, citizens);
        if (citizens.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, citizens.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citizensRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Citizens result = citizensService.save(citizens);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citizens.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /citizens/:id} : Partial updates given fields of an existing citizens, field will ignore if it is null
     *
     * @param id the id of the citizens to save.
     * @param citizens the citizens to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citizens,
     * or with status {@code 400 (Bad Request)} if the citizens is not valid,
     * or with status {@code 404 (Not Found)} if the citizens is not found,
     * or with status {@code 500 (Internal Server Error)} if the citizens couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/citizens/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Citizens> partialUpdateCitizens(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Citizens citizens
    ) throws URISyntaxException {
        log.debug("REST request to partial update Citizens partially : {}, {}", id, citizens);
        if (citizens.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, citizens.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citizensRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Citizens> result = citizensService.partialUpdate(citizens);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citizens.getId().toString())
        );
    }

    /**
     * {@code GET  /citizens} : get all the citizens.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of citizens in body.
     */
    @GetMapping("/citizens")
    public ResponseEntity<List<Citizens>> getAllCitizens(
        CitizensCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Citizens by criteria: {}", criteria);
        Page<Citizens> page = citizensQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /citizens/count} : count all the citizens.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/citizens/count")
    public ResponseEntity<Long> countCitizens(CitizensCriteria criteria) {
        log.debug("REST request to count Citizens by criteria: {}", criteria);
        return ResponseEntity.ok().body(citizensQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /citizens/:id} : get the "id" citizens.
     *
     * @param id the id of the citizens to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the citizens, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/citizens/{id}")
    public ResponseEntity<Citizens> getCitizens(@PathVariable Long id) {
        log.debug("REST request to get Citizens : {}", id);
        Optional<Citizens> citizens = citizensService.findOne(id);
        return ResponseUtil.wrapOrNotFound(citizens);
    }

    /**
     * {@code DELETE  /citizens/:id} : delete the "id" citizens.
     *
     * @param id the id of the citizens to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/citizens/{id}")
    public ResponseEntity<Void> deleteCitizens(@PathVariable Long id) {
        log.debug("REST request to delete Citizens : {}", id);
        citizensService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
