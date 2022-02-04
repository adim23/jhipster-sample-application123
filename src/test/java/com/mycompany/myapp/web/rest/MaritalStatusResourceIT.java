package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.MaritalStatus;
import com.mycompany.myapp.repository.MaritalStatusRepository;
import com.mycompany.myapp.service.criteria.MaritalStatusCriteria;
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
 * Integration tests for the {@link MaritalStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaritalStatusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/marital-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MaritalStatusRepository maritalStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaritalStatusMockMvc;

    private MaritalStatus maritalStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaritalStatus createEntity(EntityManager em) {
        MaritalStatus maritalStatus = new MaritalStatus().name(DEFAULT_NAME);
        return maritalStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaritalStatus createUpdatedEntity(EntityManager em) {
        MaritalStatus maritalStatus = new MaritalStatus().name(UPDATED_NAME);
        return maritalStatus;
    }

    @BeforeEach
    public void initTest() {
        maritalStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createMaritalStatus() throws Exception {
        int databaseSizeBeforeCreate = maritalStatusRepository.findAll().size();
        // Create the MaritalStatus
        restMaritalStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(maritalStatus)))
            .andExpect(status().isCreated());

        // Validate the MaritalStatus in the database
        List<MaritalStatus> maritalStatusList = maritalStatusRepository.findAll();
        assertThat(maritalStatusList).hasSize(databaseSizeBeforeCreate + 1);
        MaritalStatus testMaritalStatus = maritalStatusList.get(maritalStatusList.size() - 1);
        assertThat(testMaritalStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createMaritalStatusWithExistingId() throws Exception {
        // Create the MaritalStatus with an existing ID
        maritalStatus.setId(1L);

        int databaseSizeBeforeCreate = maritalStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaritalStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(maritalStatus)))
            .andExpect(status().isBadRequest());

        // Validate the MaritalStatus in the database
        List<MaritalStatus> maritalStatusList = maritalStatusRepository.findAll();
        assertThat(maritalStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = maritalStatusRepository.findAll().size();
        // set the field null
        maritalStatus.setName(null);

        // Create the MaritalStatus, which fails.

        restMaritalStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(maritalStatus)))
            .andExpect(status().isBadRequest());

        List<MaritalStatus> maritalStatusList = maritalStatusRepository.findAll();
        assertThat(maritalStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMaritalStatuses() throws Exception {
        // Initialize the database
        maritalStatusRepository.saveAndFlush(maritalStatus);

        // Get all the maritalStatusList
        restMaritalStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maritalStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getMaritalStatus() throws Exception {
        // Initialize the database
        maritalStatusRepository.saveAndFlush(maritalStatus);

        // Get the maritalStatus
        restMaritalStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, maritalStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(maritalStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getMaritalStatusesByIdFiltering() throws Exception {
        // Initialize the database
        maritalStatusRepository.saveAndFlush(maritalStatus);

        Long id = maritalStatus.getId();

        defaultMaritalStatusShouldBeFound("id.equals=" + id);
        defaultMaritalStatusShouldNotBeFound("id.notEquals=" + id);

        defaultMaritalStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMaritalStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultMaritalStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMaritalStatusShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        maritalStatusRepository.saveAndFlush(maritalStatus);

        // Get all the maritalStatusList where name equals to DEFAULT_NAME
        defaultMaritalStatusShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the maritalStatusList where name equals to UPDATED_NAME
        defaultMaritalStatusShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        maritalStatusRepository.saveAndFlush(maritalStatus);

        // Get all the maritalStatusList where name not equals to DEFAULT_NAME
        defaultMaritalStatusShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the maritalStatusList where name not equals to UPDATED_NAME
        defaultMaritalStatusShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        maritalStatusRepository.saveAndFlush(maritalStatus);

        // Get all the maritalStatusList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMaritalStatusShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the maritalStatusList where name equals to UPDATED_NAME
        defaultMaritalStatusShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        maritalStatusRepository.saveAndFlush(maritalStatus);

        // Get all the maritalStatusList where name is not null
        defaultMaritalStatusShouldBeFound("name.specified=true");

        // Get all the maritalStatusList where name is null
        defaultMaritalStatusShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByNameContainsSomething() throws Exception {
        // Initialize the database
        maritalStatusRepository.saveAndFlush(maritalStatus);

        // Get all the maritalStatusList where name contains DEFAULT_NAME
        defaultMaritalStatusShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the maritalStatusList where name contains UPDATED_NAME
        defaultMaritalStatusShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMaritalStatusesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        maritalStatusRepository.saveAndFlush(maritalStatus);

        // Get all the maritalStatusList where name does not contain DEFAULT_NAME
        defaultMaritalStatusShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the maritalStatusList where name does not contain UPDATED_NAME
        defaultMaritalStatusShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMaritalStatusShouldBeFound(String filter) throws Exception {
        restMaritalStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maritalStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restMaritalStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMaritalStatusShouldNotBeFound(String filter) throws Exception {
        restMaritalStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMaritalStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMaritalStatus() throws Exception {
        // Get the maritalStatus
        restMaritalStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMaritalStatus() throws Exception {
        // Initialize the database
        maritalStatusRepository.saveAndFlush(maritalStatus);

        int databaseSizeBeforeUpdate = maritalStatusRepository.findAll().size();

        // Update the maritalStatus
        MaritalStatus updatedMaritalStatus = maritalStatusRepository.findById(maritalStatus.getId()).get();
        // Disconnect from session so that the updates on updatedMaritalStatus are not directly saved in db
        em.detach(updatedMaritalStatus);
        updatedMaritalStatus.name(UPDATED_NAME);

        restMaritalStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaritalStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMaritalStatus))
            )
            .andExpect(status().isOk());

        // Validate the MaritalStatus in the database
        List<MaritalStatus> maritalStatusList = maritalStatusRepository.findAll();
        assertThat(maritalStatusList).hasSize(databaseSizeBeforeUpdate);
        MaritalStatus testMaritalStatus = maritalStatusList.get(maritalStatusList.size() - 1);
        assertThat(testMaritalStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingMaritalStatus() throws Exception {
        int databaseSizeBeforeUpdate = maritalStatusRepository.findAll().size();
        maritalStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaritalStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, maritalStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maritalStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaritalStatus in the database
        List<MaritalStatus> maritalStatusList = maritalStatusRepository.findAll();
        assertThat(maritalStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaritalStatus() throws Exception {
        int databaseSizeBeforeUpdate = maritalStatusRepository.findAll().size();
        maritalStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaritalStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(maritalStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaritalStatus in the database
        List<MaritalStatus> maritalStatusList = maritalStatusRepository.findAll();
        assertThat(maritalStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaritalStatus() throws Exception {
        int databaseSizeBeforeUpdate = maritalStatusRepository.findAll().size();
        maritalStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaritalStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(maritalStatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaritalStatus in the database
        List<MaritalStatus> maritalStatusList = maritalStatusRepository.findAll();
        assertThat(maritalStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaritalStatusWithPatch() throws Exception {
        // Initialize the database
        maritalStatusRepository.saveAndFlush(maritalStatus);

        int databaseSizeBeforeUpdate = maritalStatusRepository.findAll().size();

        // Update the maritalStatus using partial update
        MaritalStatus partialUpdatedMaritalStatus = new MaritalStatus();
        partialUpdatedMaritalStatus.setId(maritalStatus.getId());

        partialUpdatedMaritalStatus.name(UPDATED_NAME);

        restMaritalStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaritalStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaritalStatus))
            )
            .andExpect(status().isOk());

        // Validate the MaritalStatus in the database
        List<MaritalStatus> maritalStatusList = maritalStatusRepository.findAll();
        assertThat(maritalStatusList).hasSize(databaseSizeBeforeUpdate);
        MaritalStatus testMaritalStatus = maritalStatusList.get(maritalStatusList.size() - 1);
        assertThat(testMaritalStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateMaritalStatusWithPatch() throws Exception {
        // Initialize the database
        maritalStatusRepository.saveAndFlush(maritalStatus);

        int databaseSizeBeforeUpdate = maritalStatusRepository.findAll().size();

        // Update the maritalStatus using partial update
        MaritalStatus partialUpdatedMaritalStatus = new MaritalStatus();
        partialUpdatedMaritalStatus.setId(maritalStatus.getId());

        partialUpdatedMaritalStatus.name(UPDATED_NAME);

        restMaritalStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaritalStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaritalStatus))
            )
            .andExpect(status().isOk());

        // Validate the MaritalStatus in the database
        List<MaritalStatus> maritalStatusList = maritalStatusRepository.findAll();
        assertThat(maritalStatusList).hasSize(databaseSizeBeforeUpdate);
        MaritalStatus testMaritalStatus = maritalStatusList.get(maritalStatusList.size() - 1);
        assertThat(testMaritalStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingMaritalStatus() throws Exception {
        int databaseSizeBeforeUpdate = maritalStatusRepository.findAll().size();
        maritalStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaritalStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, maritalStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(maritalStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaritalStatus in the database
        List<MaritalStatus> maritalStatusList = maritalStatusRepository.findAll();
        assertThat(maritalStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaritalStatus() throws Exception {
        int databaseSizeBeforeUpdate = maritalStatusRepository.findAll().size();
        maritalStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaritalStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(maritalStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaritalStatus in the database
        List<MaritalStatus> maritalStatusList = maritalStatusRepository.findAll();
        assertThat(maritalStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaritalStatus() throws Exception {
        int databaseSizeBeforeUpdate = maritalStatusRepository.findAll().size();
        maritalStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaritalStatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(maritalStatus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaritalStatus in the database
        List<MaritalStatus> maritalStatusList = maritalStatusRepository.findAll();
        assertThat(maritalStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaritalStatus() throws Exception {
        // Initialize the database
        maritalStatusRepository.saveAndFlush(maritalStatus);

        int databaseSizeBeforeDelete = maritalStatusRepository.findAll().size();

        // Delete the maritalStatus
        restMaritalStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, maritalStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaritalStatus> maritalStatusList = maritalStatusRepository.findAll();
        assertThat(maritalStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
