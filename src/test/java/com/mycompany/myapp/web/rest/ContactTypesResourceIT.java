package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ContactTypes;
import com.mycompany.myapp.repository.ContactTypesRepository;
import com.mycompany.myapp.service.criteria.ContactTypesCriteria;
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
 * Integration tests for the {@link ContactTypesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contact-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactTypesRepository contactTypesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactTypesMockMvc;

    private ContactTypes contactTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactTypes createEntity(EntityManager em) {
        ContactTypes contactTypes = new ContactTypes().name(DEFAULT_NAME);
        return contactTypes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactTypes createUpdatedEntity(EntityManager em) {
        ContactTypes contactTypes = new ContactTypes().name(UPDATED_NAME);
        return contactTypes;
    }

    @BeforeEach
    public void initTest() {
        contactTypes = createEntity(em);
    }

    @Test
    @Transactional
    void createContactTypes() throws Exception {
        int databaseSizeBeforeCreate = contactTypesRepository.findAll().size();
        // Create the ContactTypes
        restContactTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactTypes)))
            .andExpect(status().isCreated());

        // Validate the ContactTypes in the database
        List<ContactTypes> contactTypesList = contactTypesRepository.findAll();
        assertThat(contactTypesList).hasSize(databaseSizeBeforeCreate + 1);
        ContactTypes testContactTypes = contactTypesList.get(contactTypesList.size() - 1);
        assertThat(testContactTypes.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createContactTypesWithExistingId() throws Exception {
        // Create the ContactTypes with an existing ID
        contactTypes.setId(1L);

        int databaseSizeBeforeCreate = contactTypesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactTypes)))
            .andExpect(status().isBadRequest());

        // Validate the ContactTypes in the database
        List<ContactTypes> contactTypesList = contactTypesRepository.findAll();
        assertThat(contactTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactTypesRepository.findAll().size();
        // set the field null
        contactTypes.setName(null);

        // Create the ContactTypes, which fails.

        restContactTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactTypes)))
            .andExpect(status().isBadRequest());

        List<ContactTypes> contactTypesList = contactTypesRepository.findAll();
        assertThat(contactTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContactTypes() throws Exception {
        // Initialize the database
        contactTypesRepository.saveAndFlush(contactTypes);

        // Get all the contactTypesList
        restContactTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getContactTypes() throws Exception {
        // Initialize the database
        contactTypesRepository.saveAndFlush(contactTypes);

        // Get the contactTypes
        restContactTypesMockMvc
            .perform(get(ENTITY_API_URL_ID, contactTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getContactTypesByIdFiltering() throws Exception {
        // Initialize the database
        contactTypesRepository.saveAndFlush(contactTypes);

        Long id = contactTypes.getId();

        defaultContactTypesShouldBeFound("id.equals=" + id);
        defaultContactTypesShouldNotBeFound("id.notEquals=" + id);

        defaultContactTypesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContactTypesShouldNotBeFound("id.greaterThan=" + id);

        defaultContactTypesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContactTypesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllContactTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contactTypesRepository.saveAndFlush(contactTypes);

        // Get all the contactTypesList where name equals to DEFAULT_NAME
        defaultContactTypesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the contactTypesList where name equals to UPDATED_NAME
        defaultContactTypesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contactTypesRepository.saveAndFlush(contactTypes);

        // Get all the contactTypesList where name not equals to DEFAULT_NAME
        defaultContactTypesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the contactTypesList where name not equals to UPDATED_NAME
        defaultContactTypesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        contactTypesRepository.saveAndFlush(contactTypes);

        // Get all the contactTypesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultContactTypesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the contactTypesList where name equals to UPDATED_NAME
        defaultContactTypesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactTypesRepository.saveAndFlush(contactTypes);

        // Get all the contactTypesList where name is not null
        defaultContactTypesShouldBeFound("name.specified=true");

        // Get all the contactTypesList where name is null
        defaultContactTypesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllContactTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        contactTypesRepository.saveAndFlush(contactTypes);

        // Get all the contactTypesList where name contains DEFAULT_NAME
        defaultContactTypesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the contactTypesList where name contains UPDATED_NAME
        defaultContactTypesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        contactTypesRepository.saveAndFlush(contactTypes);

        // Get all the contactTypesList where name does not contain DEFAULT_NAME
        defaultContactTypesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the contactTypesList where name does not contain UPDATED_NAME
        defaultContactTypesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContactTypesShouldBeFound(String filter) throws Exception {
        restContactTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restContactTypesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContactTypesShouldNotBeFound(String filter) throws Exception {
        restContactTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContactTypesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingContactTypes() throws Exception {
        // Get the contactTypes
        restContactTypesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContactTypes() throws Exception {
        // Initialize the database
        contactTypesRepository.saveAndFlush(contactTypes);

        int databaseSizeBeforeUpdate = contactTypesRepository.findAll().size();

        // Update the contactTypes
        ContactTypes updatedContactTypes = contactTypesRepository.findById(contactTypes.getId()).get();
        // Disconnect from session so that the updates on updatedContactTypes are not directly saved in db
        em.detach(updatedContactTypes);
        updatedContactTypes.name(UPDATED_NAME);

        restContactTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContactTypes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContactTypes))
            )
            .andExpect(status().isOk());

        // Validate the ContactTypes in the database
        List<ContactTypes> contactTypesList = contactTypesRepository.findAll();
        assertThat(contactTypesList).hasSize(databaseSizeBeforeUpdate);
        ContactTypes testContactTypes = contactTypesList.get(contactTypesList.size() - 1);
        assertThat(testContactTypes.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingContactTypes() throws Exception {
        int databaseSizeBeforeUpdate = contactTypesRepository.findAll().size();
        contactTypes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactTypes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactTypes in the database
        List<ContactTypes> contactTypesList = contactTypesRepository.findAll();
        assertThat(contactTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContactTypes() throws Exception {
        int databaseSizeBeforeUpdate = contactTypesRepository.findAll().size();
        contactTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactTypes in the database
        List<ContactTypes> contactTypesList = contactTypesRepository.findAll();
        assertThat(contactTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContactTypes() throws Exception {
        int databaseSizeBeforeUpdate = contactTypesRepository.findAll().size();
        contactTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactTypesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactTypes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactTypes in the database
        List<ContactTypes> contactTypesList = contactTypesRepository.findAll();
        assertThat(contactTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactTypesWithPatch() throws Exception {
        // Initialize the database
        contactTypesRepository.saveAndFlush(contactTypes);

        int databaseSizeBeforeUpdate = contactTypesRepository.findAll().size();

        // Update the contactTypes using partial update
        ContactTypes partialUpdatedContactTypes = new ContactTypes();
        partialUpdatedContactTypes.setId(contactTypes.getId());

        restContactTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactTypes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactTypes))
            )
            .andExpect(status().isOk());

        // Validate the ContactTypes in the database
        List<ContactTypes> contactTypesList = contactTypesRepository.findAll();
        assertThat(contactTypesList).hasSize(databaseSizeBeforeUpdate);
        ContactTypes testContactTypes = contactTypesList.get(contactTypesList.size() - 1);
        assertThat(testContactTypes.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateContactTypesWithPatch() throws Exception {
        // Initialize the database
        contactTypesRepository.saveAndFlush(contactTypes);

        int databaseSizeBeforeUpdate = contactTypesRepository.findAll().size();

        // Update the contactTypes using partial update
        ContactTypes partialUpdatedContactTypes = new ContactTypes();
        partialUpdatedContactTypes.setId(contactTypes.getId());

        partialUpdatedContactTypes.name(UPDATED_NAME);

        restContactTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactTypes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactTypes))
            )
            .andExpect(status().isOk());

        // Validate the ContactTypes in the database
        List<ContactTypes> contactTypesList = contactTypesRepository.findAll();
        assertThat(contactTypesList).hasSize(databaseSizeBeforeUpdate);
        ContactTypes testContactTypes = contactTypesList.get(contactTypesList.size() - 1);
        assertThat(testContactTypes.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingContactTypes() throws Exception {
        int databaseSizeBeforeUpdate = contactTypesRepository.findAll().size();
        contactTypes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactTypes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactTypes in the database
        List<ContactTypes> contactTypesList = contactTypesRepository.findAll();
        assertThat(contactTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContactTypes() throws Exception {
        int databaseSizeBeforeUpdate = contactTypesRepository.findAll().size();
        contactTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactTypes in the database
        List<ContactTypes> contactTypesList = contactTypesRepository.findAll();
        assertThat(contactTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContactTypes() throws Exception {
        int databaseSizeBeforeUpdate = contactTypesRepository.findAll().size();
        contactTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactTypesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contactTypes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactTypes in the database
        List<ContactTypes> contactTypesList = contactTypesRepository.findAll();
        assertThat(contactTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContactTypes() throws Exception {
        // Initialize the database
        contactTypesRepository.saveAndFlush(contactTypes);

        int databaseSizeBeforeDelete = contactTypesRepository.findAll().size();

        // Delete the contactTypes
        restContactTypesMockMvc
            .perform(delete(ENTITY_API_URL_ID, contactTypes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactTypes> contactTypesList = contactTypesRepository.findAll();
        assertThat(contactTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
