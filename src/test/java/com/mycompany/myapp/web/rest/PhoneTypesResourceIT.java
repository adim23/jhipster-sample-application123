package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PhoneTypes;
import com.mycompany.myapp.repository.PhoneTypesRepository;
import com.mycompany.myapp.service.criteria.PhoneTypesCriteria;
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
 * Integration tests for the {@link PhoneTypesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhoneTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/phone-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PhoneTypesRepository phoneTypesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhoneTypesMockMvc;

    private PhoneTypes phoneTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhoneTypes createEntity(EntityManager em) {
        PhoneTypes phoneTypes = new PhoneTypes().name(DEFAULT_NAME);
        return phoneTypes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhoneTypes createUpdatedEntity(EntityManager em) {
        PhoneTypes phoneTypes = new PhoneTypes().name(UPDATED_NAME);
        return phoneTypes;
    }

    @BeforeEach
    public void initTest() {
        phoneTypes = createEntity(em);
    }

    @Test
    @Transactional
    void createPhoneTypes() throws Exception {
        int databaseSizeBeforeCreate = phoneTypesRepository.findAll().size();
        // Create the PhoneTypes
        restPhoneTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phoneTypes)))
            .andExpect(status().isCreated());

        // Validate the PhoneTypes in the database
        List<PhoneTypes> phoneTypesList = phoneTypesRepository.findAll();
        assertThat(phoneTypesList).hasSize(databaseSizeBeforeCreate + 1);
        PhoneTypes testPhoneTypes = phoneTypesList.get(phoneTypesList.size() - 1);
        assertThat(testPhoneTypes.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createPhoneTypesWithExistingId() throws Exception {
        // Create the PhoneTypes with an existing ID
        phoneTypes.setId(1L);

        int databaseSizeBeforeCreate = phoneTypesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhoneTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phoneTypes)))
            .andExpect(status().isBadRequest());

        // Validate the PhoneTypes in the database
        List<PhoneTypes> phoneTypesList = phoneTypesRepository.findAll();
        assertThat(phoneTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneTypesRepository.findAll().size();
        // set the field null
        phoneTypes.setName(null);

        // Create the PhoneTypes, which fails.

        restPhoneTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phoneTypes)))
            .andExpect(status().isBadRequest());

        List<PhoneTypes> phoneTypesList = phoneTypesRepository.findAll();
        assertThat(phoneTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPhoneTypes() throws Exception {
        // Initialize the database
        phoneTypesRepository.saveAndFlush(phoneTypes);

        // Get all the phoneTypesList
        restPhoneTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phoneTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getPhoneTypes() throws Exception {
        // Initialize the database
        phoneTypesRepository.saveAndFlush(phoneTypes);

        // Get the phoneTypes
        restPhoneTypesMockMvc
            .perform(get(ENTITY_API_URL_ID, phoneTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phoneTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getPhoneTypesByIdFiltering() throws Exception {
        // Initialize the database
        phoneTypesRepository.saveAndFlush(phoneTypes);

        Long id = phoneTypes.getId();

        defaultPhoneTypesShouldBeFound("id.equals=" + id);
        defaultPhoneTypesShouldNotBeFound("id.notEquals=" + id);

        defaultPhoneTypesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPhoneTypesShouldNotBeFound("id.greaterThan=" + id);

        defaultPhoneTypesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPhoneTypesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPhoneTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        phoneTypesRepository.saveAndFlush(phoneTypes);

        // Get all the phoneTypesList where name equals to DEFAULT_NAME
        defaultPhoneTypesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the phoneTypesList where name equals to UPDATED_NAME
        defaultPhoneTypesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPhoneTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        phoneTypesRepository.saveAndFlush(phoneTypes);

        // Get all the phoneTypesList where name not equals to DEFAULT_NAME
        defaultPhoneTypesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the phoneTypesList where name not equals to UPDATED_NAME
        defaultPhoneTypesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPhoneTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        phoneTypesRepository.saveAndFlush(phoneTypes);

        // Get all the phoneTypesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPhoneTypesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the phoneTypesList where name equals to UPDATED_NAME
        defaultPhoneTypesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPhoneTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        phoneTypesRepository.saveAndFlush(phoneTypes);

        // Get all the phoneTypesList where name is not null
        defaultPhoneTypesShouldBeFound("name.specified=true");

        // Get all the phoneTypesList where name is null
        defaultPhoneTypesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllPhoneTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        phoneTypesRepository.saveAndFlush(phoneTypes);

        // Get all the phoneTypesList where name contains DEFAULT_NAME
        defaultPhoneTypesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the phoneTypesList where name contains UPDATED_NAME
        defaultPhoneTypesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPhoneTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        phoneTypesRepository.saveAndFlush(phoneTypes);

        // Get all the phoneTypesList where name does not contain DEFAULT_NAME
        defaultPhoneTypesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the phoneTypesList where name does not contain UPDATED_NAME
        defaultPhoneTypesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPhoneTypesShouldBeFound(String filter) throws Exception {
        restPhoneTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phoneTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restPhoneTypesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPhoneTypesShouldNotBeFound(String filter) throws Exception {
        restPhoneTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPhoneTypesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPhoneTypes() throws Exception {
        // Get the phoneTypes
        restPhoneTypesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPhoneTypes() throws Exception {
        // Initialize the database
        phoneTypesRepository.saveAndFlush(phoneTypes);

        int databaseSizeBeforeUpdate = phoneTypesRepository.findAll().size();

        // Update the phoneTypes
        PhoneTypes updatedPhoneTypes = phoneTypesRepository.findById(phoneTypes.getId()).get();
        // Disconnect from session so that the updates on updatedPhoneTypes are not directly saved in db
        em.detach(updatedPhoneTypes);
        updatedPhoneTypes.name(UPDATED_NAME);

        restPhoneTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPhoneTypes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPhoneTypes))
            )
            .andExpect(status().isOk());

        // Validate the PhoneTypes in the database
        List<PhoneTypes> phoneTypesList = phoneTypesRepository.findAll();
        assertThat(phoneTypesList).hasSize(databaseSizeBeforeUpdate);
        PhoneTypes testPhoneTypes = phoneTypesList.get(phoneTypesList.size() - 1);
        assertThat(testPhoneTypes.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingPhoneTypes() throws Exception {
        int databaseSizeBeforeUpdate = phoneTypesRepository.findAll().size();
        phoneTypes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhoneTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phoneTypes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phoneTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhoneTypes in the database
        List<PhoneTypes> phoneTypesList = phoneTypesRepository.findAll();
        assertThat(phoneTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPhoneTypes() throws Exception {
        int databaseSizeBeforeUpdate = phoneTypesRepository.findAll().size();
        phoneTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhoneTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phoneTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhoneTypes in the database
        List<PhoneTypes> phoneTypesList = phoneTypesRepository.findAll();
        assertThat(phoneTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPhoneTypes() throws Exception {
        int databaseSizeBeforeUpdate = phoneTypesRepository.findAll().size();
        phoneTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhoneTypesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phoneTypes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhoneTypes in the database
        List<PhoneTypes> phoneTypesList = phoneTypesRepository.findAll();
        assertThat(phoneTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePhoneTypesWithPatch() throws Exception {
        // Initialize the database
        phoneTypesRepository.saveAndFlush(phoneTypes);

        int databaseSizeBeforeUpdate = phoneTypesRepository.findAll().size();

        // Update the phoneTypes using partial update
        PhoneTypes partialUpdatedPhoneTypes = new PhoneTypes();
        partialUpdatedPhoneTypes.setId(phoneTypes.getId());

        partialUpdatedPhoneTypes.name(UPDATED_NAME);

        restPhoneTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhoneTypes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhoneTypes))
            )
            .andExpect(status().isOk());

        // Validate the PhoneTypes in the database
        List<PhoneTypes> phoneTypesList = phoneTypesRepository.findAll();
        assertThat(phoneTypesList).hasSize(databaseSizeBeforeUpdate);
        PhoneTypes testPhoneTypes = phoneTypesList.get(phoneTypesList.size() - 1);
        assertThat(testPhoneTypes.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdatePhoneTypesWithPatch() throws Exception {
        // Initialize the database
        phoneTypesRepository.saveAndFlush(phoneTypes);

        int databaseSizeBeforeUpdate = phoneTypesRepository.findAll().size();

        // Update the phoneTypes using partial update
        PhoneTypes partialUpdatedPhoneTypes = new PhoneTypes();
        partialUpdatedPhoneTypes.setId(phoneTypes.getId());

        partialUpdatedPhoneTypes.name(UPDATED_NAME);

        restPhoneTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhoneTypes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhoneTypes))
            )
            .andExpect(status().isOk());

        // Validate the PhoneTypes in the database
        List<PhoneTypes> phoneTypesList = phoneTypesRepository.findAll();
        assertThat(phoneTypesList).hasSize(databaseSizeBeforeUpdate);
        PhoneTypes testPhoneTypes = phoneTypesList.get(phoneTypesList.size() - 1);
        assertThat(testPhoneTypes.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingPhoneTypes() throws Exception {
        int databaseSizeBeforeUpdate = phoneTypesRepository.findAll().size();
        phoneTypes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhoneTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, phoneTypes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phoneTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhoneTypes in the database
        List<PhoneTypes> phoneTypesList = phoneTypesRepository.findAll();
        assertThat(phoneTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPhoneTypes() throws Exception {
        int databaseSizeBeforeUpdate = phoneTypesRepository.findAll().size();
        phoneTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhoneTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phoneTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhoneTypes in the database
        List<PhoneTypes> phoneTypesList = phoneTypesRepository.findAll();
        assertThat(phoneTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPhoneTypes() throws Exception {
        int databaseSizeBeforeUpdate = phoneTypesRepository.findAll().size();
        phoneTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhoneTypesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(phoneTypes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhoneTypes in the database
        List<PhoneTypes> phoneTypesList = phoneTypesRepository.findAll();
        assertThat(phoneTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePhoneTypes() throws Exception {
        // Initialize the database
        phoneTypesRepository.saveAndFlush(phoneTypes);

        int databaseSizeBeforeDelete = phoneTypesRepository.findAll().size();

        // Delete the phoneTypes
        restPhoneTypesMockMvc
            .perform(delete(ENTITY_API_URL_ID, phoneTypes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhoneTypes> phoneTypesList = phoneTypesRepository.findAll();
        assertThat(phoneTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
