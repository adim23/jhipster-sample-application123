package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Emails;
import com.mycompany.myapp.repository.EmailsRepository;
import com.mycompany.myapp.service.EmailsQueryService;
import com.mycompany.myapp.service.EmailsService;
import com.mycompany.myapp.service.criteria.EmailsCriteria;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Emails}.
 */
@RestController
@RequestMapping("/api")
public class EmailsResource {

    private final Logger log = LoggerFactory.getLogger(EmailsResource.class);

    private static final String ENTITY_NAME = "emails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailsService emailsService;

    private final EmailsRepository emailsRepository;

    private final EmailsQueryService emailsQueryService;

    public EmailsResource(EmailsService emailsService, EmailsRepository emailsRepository, EmailsQueryService emailsQueryService) {
        this.emailsService = emailsService;
        this.emailsRepository = emailsRepository;
        this.emailsQueryService = emailsQueryService;
    }

    /**
     * {@code POST  /emails} : Create a new emails.
     *
     * @param emails the emails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emails, or with status {@code 400 (Bad Request)} if the emails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emails")
    public ResponseEntity<Emails> createEmails(@Valid @RequestBody Emails emails) throws URISyntaxException {
        log.debug("REST request to save Emails : {}", emails);
        if (emails.getId() != null) {
            throw new BadRequestAlertException("A new emails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Emails result = emailsService.save(emails);
        return ResponseEntity
            .created(new URI("/api/emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emails/:id} : Updates an existing emails.
     *
     * @param id the id of the emails to save.
     * @param emails the emails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emails,
     * or with status {@code 400 (Bad Request)} if the emails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emails/{id}")
    public ResponseEntity<Emails> updateEmails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Emails emails
    ) throws URISyntaxException {
        log.debug("REST request to update Emails : {}, {}", id, emails);
        if (emails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Emails result = emailsService.save(emails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /emails/:id} : Partial updates given fields of an existing emails, field will ignore if it is null
     *
     * @param id the id of the emails to save.
     * @param emails the emails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emails,
     * or with status {@code 400 (Bad Request)} if the emails is not valid,
     * or with status {@code 404 (Not Found)} if the emails is not found,
     * or with status {@code 500 (Internal Server Error)} if the emails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/emails/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Emails> partialUpdateEmails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Emails emails
    ) throws URISyntaxException {
        log.debug("REST request to partial update Emails partially : {}, {}", id, emails);
        if (emails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Emails> result = emailsService.partialUpdate(emails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emails.getId().toString())
        );
    }

    /**
     * {@code GET  /emails} : get all the emails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emails in body.
     */
    @GetMapping("/emails")
    public ResponseEntity<List<Emails>> getAllEmails(
        EmailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Emails by criteria: {}", criteria);
        Page<Emails> page = emailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emails/count} : count all the emails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/emails/count")
    public ResponseEntity<Long> countEmails(EmailsCriteria criteria) {
        log.debug("REST request to count Emails by criteria: {}", criteria);
        return ResponseEntity.ok().body(emailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emails/:id} : get the "id" emails.
     *
     * @param id the id of the emails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emails/{id}")
    public ResponseEntity<Emails> getEmails(@PathVariable Long id) {
        log.debug("REST request to get Emails : {}", id);
        Optional<Emails> emails = emailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emails);
    }

    /**
     * {@code DELETE  /emails/:id} : delete the "id" emails.
     *
     * @param id the id of the emails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emails/{id}")
    public ResponseEntity<Void> deleteEmails(@PathVariable Long id) {
        log.debug("REST request to delete Emails : {}", id);
        emailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
