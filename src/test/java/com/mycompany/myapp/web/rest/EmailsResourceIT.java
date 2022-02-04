package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Citizens;
import com.mycompany.myapp.domain.ContactTypes;
import com.mycompany.myapp.domain.Emails;
import com.mycompany.myapp.repository.EmailsRepository;
import com.mycompany.myapp.service.criteria.EmailsCriteria;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EmailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmailsResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FAVOURITE = false;
    private static final Boolean UPDATED_FAVOURITE = true;

    private static final String ENTITY_API_URL = "/api/emails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmailsRepository emailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailsMockMvc;

    private Emails emails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emails createEntity(EntityManager em) {
        Emails emails = new Emails().email(DEFAULT_EMAIL).description(DEFAULT_DESCRIPTION).favourite(DEFAULT_FAVOURITE);
        return emails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emails createUpdatedEntity(EntityManager em) {
        Emails emails = new Emails().email(UPDATED_EMAIL).description(UPDATED_DESCRIPTION).favourite(UPDATED_FAVOURITE);
        return emails;
    }

    @BeforeEach
    public void initTest() {
        emails = createEntity(em);
    }

    @Test
    @Transactional
    void createEmails() throws Exception {
        int databaseSizeBeforeCreate = emailsRepository.findAll().size();
        // Create the Emails
        restEmailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emails)))
            .andExpect(status().isCreated());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeCreate + 1);
        Emails testEmails = emailsList.get(emailsList.size() - 1);
        assertThat(testEmails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEmails.getFavourite()).isEqualTo(DEFAULT_FAVOURITE);
    }

    @Test
    @Transactional
    void createEmailsWithExistingId() throws Exception {
        // Create the Emails with an existing ID
        emails.setId(1L);

        int databaseSizeBeforeCreate = emailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emails)))
            .andExpect(status().isBadRequest());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailsRepository.findAll().size();
        // set the field null
        emails.setEmail(null);

        // Create the Emails, which fails.

        restEmailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emails)))
            .andExpect(status().isBadRequest());

        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmails() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList
        restEmailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emails.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].favourite").value(hasItem(DEFAULT_FAVOURITE.booleanValue())));
    }

    @Test
    @Transactional
    void getEmails() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get the emails
        restEmailsMockMvc
            .perform(get(ENTITY_API_URL_ID, emails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emails.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.favourite").value(DEFAULT_FAVOURITE.booleanValue()));
    }

    @Test
    @Transactional
    void getEmailsByIdFiltering() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        Long id = emails.getId();

        defaultEmailsShouldBeFound("id.equals=" + id);
        defaultEmailsShouldNotBeFound("id.notEquals=" + id);

        defaultEmailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmailsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmailsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where email equals to DEFAULT_EMAIL
        defaultEmailsShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the emailsList where email equals to UPDATED_EMAIL
        defaultEmailsShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmailsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where email not equals to DEFAULT_EMAIL
        defaultEmailsShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the emailsList where email not equals to UPDATED_EMAIL
        defaultEmailsShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmailsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEmailsShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the emailsList where email equals to UPDATED_EMAIL
        defaultEmailsShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmailsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where email is not null
        defaultEmailsShouldBeFound("email.specified=true");

        // Get all the emailsList where email is null
        defaultEmailsShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailsByEmailContainsSomething() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where email contains DEFAULT_EMAIL
        defaultEmailsShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the emailsList where email contains UPDATED_EMAIL
        defaultEmailsShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmailsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where email does not contain DEFAULT_EMAIL
        defaultEmailsShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the emailsList where email does not contain UPDATED_EMAIL
        defaultEmailsShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmailsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where description equals to DEFAULT_DESCRIPTION
        defaultEmailsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the emailsList where description equals to UPDATED_DESCRIPTION
        defaultEmailsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmailsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where description not equals to DEFAULT_DESCRIPTION
        defaultEmailsShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the emailsList where description not equals to UPDATED_DESCRIPTION
        defaultEmailsShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmailsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultEmailsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the emailsList where description equals to UPDATED_DESCRIPTION
        defaultEmailsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmailsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where description is not null
        defaultEmailsShouldBeFound("description.specified=true");

        // Get all the emailsList where description is null
        defaultEmailsShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where description contains DEFAULT_DESCRIPTION
        defaultEmailsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the emailsList where description contains UPDATED_DESCRIPTION
        defaultEmailsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmailsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where description does not contain DEFAULT_DESCRIPTION
        defaultEmailsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the emailsList where description does not contain UPDATED_DESCRIPTION
        defaultEmailsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmailsByFavouriteIsEqualToSomething() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where favourite equals to DEFAULT_FAVOURITE
        defaultEmailsShouldBeFound("favourite.equals=" + DEFAULT_FAVOURITE);

        // Get all the emailsList where favourite equals to UPDATED_FAVOURITE
        defaultEmailsShouldNotBeFound("favourite.equals=" + UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void getAllEmailsByFavouriteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where favourite not equals to DEFAULT_FAVOURITE
        defaultEmailsShouldNotBeFound("favourite.notEquals=" + DEFAULT_FAVOURITE);

        // Get all the emailsList where favourite not equals to UPDATED_FAVOURITE
        defaultEmailsShouldBeFound("favourite.notEquals=" + UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void getAllEmailsByFavouriteIsInShouldWork() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where favourite in DEFAULT_FAVOURITE or UPDATED_FAVOURITE
        defaultEmailsShouldBeFound("favourite.in=" + DEFAULT_FAVOURITE + "," + UPDATED_FAVOURITE);

        // Get all the emailsList where favourite equals to UPDATED_FAVOURITE
        defaultEmailsShouldNotBeFound("favourite.in=" + UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void getAllEmailsByFavouriteIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        // Get all the emailsList where favourite is not null
        defaultEmailsShouldBeFound("favourite.specified=true");

        // Get all the emailsList where favourite is null
        defaultEmailsShouldNotBeFound("favourite.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailsByKindIsEqualToSomething() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);
        ContactTypes kind;
        if (TestUtil.findAll(em, ContactTypes.class).isEmpty()) {
            kind = ContactTypesResourceIT.createEntity(em);
            em.persist(kind);
            em.flush();
        } else {
            kind = TestUtil.findAll(em, ContactTypes.class).get(0);
        }
        em.persist(kind);
        em.flush();
        emails.setKind(kind);
        emailsRepository.saveAndFlush(emails);
        Long kindId = kind.getId();

        // Get all the emailsList where kind equals to kindId
        defaultEmailsShouldBeFound("kindId.equals=" + kindId);

        // Get all the emailsList where kind equals to (kindId + 1)
        defaultEmailsShouldNotBeFound("kindId.equals=" + (kindId + 1));
    }

    @Test
    @Transactional
    void getAllEmailsByCitizenIsEqualToSomething() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);
        Citizens citizen;
        if (TestUtil.findAll(em, Citizens.class).isEmpty()) {
            citizen = CitizensResourceIT.createEntity(em);
            em.persist(citizen);
            em.flush();
        } else {
            citizen = TestUtil.findAll(em, Citizens.class).get(0);
        }
        em.persist(citizen);
        em.flush();
        emails.setCitizen(citizen);
        emailsRepository.saveAndFlush(emails);
        Long citizenId = citizen.getId();

        // Get all the emailsList where citizen equals to citizenId
        defaultEmailsShouldBeFound("citizenId.equals=" + citizenId);

        // Get all the emailsList where citizen equals to (citizenId + 1)
        defaultEmailsShouldNotBeFound("citizenId.equals=" + (citizenId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmailsShouldBeFound(String filter) throws Exception {
        restEmailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emails.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].favourite").value(hasItem(DEFAULT_FAVOURITE.booleanValue())));

        // Check, that the count call also returns 1
        restEmailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmailsShouldNotBeFound(String filter) throws Exception {
        restEmailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmails() throws Exception {
        // Get the emails
        restEmailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmails() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();

        // Update the emails
        Emails updatedEmails = emailsRepository.findById(emails.getId()).get();
        // Disconnect from session so that the updates on updatedEmails are not directly saved in db
        em.detach(updatedEmails);
        updatedEmails.email(UPDATED_EMAIL).description(UPDATED_DESCRIPTION).favourite(UPDATED_FAVOURITE);

        restEmailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmails))
            )
            .andExpect(status().isOk());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
        Emails testEmails = emailsList.get(emailsList.size() - 1);
        assertThat(testEmails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEmails.getFavourite()).isEqualTo(UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void putNonExistingEmails() throws Exception {
        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();
        emails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emails))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmails() throws Exception {
        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();
        emails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emails))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmails() throws Exception {
        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();
        emails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emails)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmailsWithPatch() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();

        // Update the emails using partial update
        Emails partialUpdatedEmails = new Emails();
        partialUpdatedEmails.setId(emails.getId());

        partialUpdatedEmails.email(UPDATED_EMAIL).favourite(UPDATED_FAVOURITE);

        restEmailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmails))
            )
            .andExpect(status().isOk());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
        Emails testEmails = emailsList.get(emailsList.size() - 1);
        assertThat(testEmails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEmails.getFavourite()).isEqualTo(UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void fullUpdateEmailsWithPatch() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();

        // Update the emails using partial update
        Emails partialUpdatedEmails = new Emails();
        partialUpdatedEmails.setId(emails.getId());

        partialUpdatedEmails.email(UPDATED_EMAIL).description(UPDATED_DESCRIPTION).favourite(UPDATED_FAVOURITE);

        restEmailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmails))
            )
            .andExpect(status().isOk());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
        Emails testEmails = emailsList.get(emailsList.size() - 1);
        assertThat(testEmails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEmails.getFavourite()).isEqualTo(UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void patchNonExistingEmails() throws Exception {
        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();
        emails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emails))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmails() throws Exception {
        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();
        emails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emails))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmails() throws Exception {
        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();
        emails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(emails)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmails() throws Exception {
        // Initialize the database
        emailsRepository.saveAndFlush(emails);

        int databaseSizeBeforeDelete = emailsRepository.findAll().size();

        // Delete the emails
        restEmailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, emails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
