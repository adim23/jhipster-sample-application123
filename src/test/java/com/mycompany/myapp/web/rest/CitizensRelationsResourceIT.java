package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Citizens;
import com.mycompany.myapp.domain.CitizensRelations;
import com.mycompany.myapp.repository.CitizensRelationsRepository;
import com.mycompany.myapp.service.criteria.CitizensRelationsCriteria;
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
 * Integration tests for the {@link CitizensRelationsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CitizensRelationsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/citizens-relations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CitizensRelationsRepository citizensRelationsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitizensRelationsMockMvc;

    private CitizensRelations citizensRelations;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CitizensRelations createEntity(EntityManager em) {
        CitizensRelations citizensRelations = new CitizensRelations().name(DEFAULT_NAME);
        return citizensRelations;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CitizensRelations createUpdatedEntity(EntityManager em) {
        CitizensRelations citizensRelations = new CitizensRelations().name(UPDATED_NAME);
        return citizensRelations;
    }

    @BeforeEach
    public void initTest() {
        citizensRelations = createEntity(em);
    }

    @Test
    @Transactional
    void createCitizensRelations() throws Exception {
        int databaseSizeBeforeCreate = citizensRelationsRepository.findAll().size();
        // Create the CitizensRelations
        restCitizensRelationsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizensRelations))
            )
            .andExpect(status().isCreated());

        // Validate the CitizensRelations in the database
        List<CitizensRelations> citizensRelationsList = citizensRelationsRepository.findAll();
        assertThat(citizensRelationsList).hasSize(databaseSizeBeforeCreate + 1);
        CitizensRelations testCitizensRelations = citizensRelationsList.get(citizensRelationsList.size() - 1);
        assertThat(testCitizensRelations.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createCitizensRelationsWithExistingId() throws Exception {
        // Create the CitizensRelations with an existing ID
        citizensRelations.setId(1L);

        int databaseSizeBeforeCreate = citizensRelationsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitizensRelationsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizensRelations))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitizensRelations in the database
        List<CitizensRelations> citizensRelationsList = citizensRelationsRepository.findAll();
        assertThat(citizensRelationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = citizensRelationsRepository.findAll().size();
        // set the field null
        citizensRelations.setName(null);

        // Create the CitizensRelations, which fails.

        restCitizensRelationsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizensRelations))
            )
            .andExpect(status().isBadRequest());

        List<CitizensRelations> citizensRelationsList = citizensRelationsRepository.findAll();
        assertThat(citizensRelationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCitizensRelations() throws Exception {
        // Initialize the database
        citizensRelationsRepository.saveAndFlush(citizensRelations);

        // Get all the citizensRelationsList
        restCitizensRelationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citizensRelations.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getCitizensRelations() throws Exception {
        // Initialize the database
        citizensRelationsRepository.saveAndFlush(citizensRelations);

        // Get the citizensRelations
        restCitizensRelationsMockMvc
            .perform(get(ENTITY_API_URL_ID, citizensRelations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(citizensRelations.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getCitizensRelationsByIdFiltering() throws Exception {
        // Initialize the database
        citizensRelationsRepository.saveAndFlush(citizensRelations);

        Long id = citizensRelations.getId();

        defaultCitizensRelationsShouldBeFound("id.equals=" + id);
        defaultCitizensRelationsShouldNotBeFound("id.notEquals=" + id);

        defaultCitizensRelationsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCitizensRelationsShouldNotBeFound("id.greaterThan=" + id);

        defaultCitizensRelationsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCitizensRelationsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCitizensRelationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRelationsRepository.saveAndFlush(citizensRelations);

        // Get all the citizensRelationsList where name equals to DEFAULT_NAME
        defaultCitizensRelationsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the citizensRelationsList where name equals to UPDATED_NAME
        defaultCitizensRelationsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensRelationsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensRelationsRepository.saveAndFlush(citizensRelations);

        // Get all the citizensRelationsList where name not equals to DEFAULT_NAME
        defaultCitizensRelationsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the citizensRelationsList where name not equals to UPDATED_NAME
        defaultCitizensRelationsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensRelationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        citizensRelationsRepository.saveAndFlush(citizensRelations);

        // Get all the citizensRelationsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCitizensRelationsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the citizensRelationsList where name equals to UPDATED_NAME
        defaultCitizensRelationsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensRelationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensRelationsRepository.saveAndFlush(citizensRelations);

        // Get all the citizensRelationsList where name is not null
        defaultCitizensRelationsShouldBeFound("name.specified=true");

        // Get all the citizensRelationsList where name is null
        defaultCitizensRelationsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensRelationsByNameContainsSomething() throws Exception {
        // Initialize the database
        citizensRelationsRepository.saveAndFlush(citizensRelations);

        // Get all the citizensRelationsList where name contains DEFAULT_NAME
        defaultCitizensRelationsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the citizensRelationsList where name contains UPDATED_NAME
        defaultCitizensRelationsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensRelationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        citizensRelationsRepository.saveAndFlush(citizensRelations);

        // Get all the citizensRelationsList where name does not contain DEFAULT_NAME
        defaultCitizensRelationsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the citizensRelationsList where name does not contain UPDATED_NAME
        defaultCitizensRelationsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensRelationsByCitizenIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRelationsRepository.saveAndFlush(citizensRelations);
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
        citizensRelations.setCitizen(citizen);
        citizensRelationsRepository.saveAndFlush(citizensRelations);
        Long citizenId = citizen.getId();

        // Get all the citizensRelationsList where citizen equals to citizenId
        defaultCitizensRelationsShouldBeFound("citizenId.equals=" + citizenId);

        // Get all the citizensRelationsList where citizen equals to (citizenId + 1)
        defaultCitizensRelationsShouldNotBeFound("citizenId.equals=" + (citizenId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCitizensRelationsShouldBeFound(String filter) throws Exception {
        restCitizensRelationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citizensRelations.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCitizensRelationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCitizensRelationsShouldNotBeFound(String filter) throws Exception {
        restCitizensRelationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCitizensRelationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCitizensRelations() throws Exception {
        // Get the citizensRelations
        restCitizensRelationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCitizensRelations() throws Exception {
        // Initialize the database
        citizensRelationsRepository.saveAndFlush(citizensRelations);

        int databaseSizeBeforeUpdate = citizensRelationsRepository.findAll().size();

        // Update the citizensRelations
        CitizensRelations updatedCitizensRelations = citizensRelationsRepository.findById(citizensRelations.getId()).get();
        // Disconnect from session so that the updates on updatedCitizensRelations are not directly saved in db
        em.detach(updatedCitizensRelations);
        updatedCitizensRelations.name(UPDATED_NAME);

        restCitizensRelationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCitizensRelations.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCitizensRelations))
            )
            .andExpect(status().isOk());

        // Validate the CitizensRelations in the database
        List<CitizensRelations> citizensRelationsList = citizensRelationsRepository.findAll();
        assertThat(citizensRelationsList).hasSize(databaseSizeBeforeUpdate);
        CitizensRelations testCitizensRelations = citizensRelationsList.get(citizensRelationsList.size() - 1);
        assertThat(testCitizensRelations.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCitizensRelations() throws Exception {
        int databaseSizeBeforeUpdate = citizensRelationsRepository.findAll().size();
        citizensRelations.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitizensRelationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citizensRelations.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citizensRelations))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitizensRelations in the database
        List<CitizensRelations> citizensRelationsList = citizensRelationsRepository.findAll();
        assertThat(citizensRelationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCitizensRelations() throws Exception {
        int databaseSizeBeforeUpdate = citizensRelationsRepository.findAll().size();
        citizensRelations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizensRelationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citizensRelations))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitizensRelations in the database
        List<CitizensRelations> citizensRelationsList = citizensRelationsRepository.findAll();
        assertThat(citizensRelationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCitizensRelations() throws Exception {
        int databaseSizeBeforeUpdate = citizensRelationsRepository.findAll().size();
        citizensRelations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizensRelationsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizensRelations))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CitizensRelations in the database
        List<CitizensRelations> citizensRelationsList = citizensRelationsRepository.findAll();
        assertThat(citizensRelationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCitizensRelationsWithPatch() throws Exception {
        // Initialize the database
        citizensRelationsRepository.saveAndFlush(citizensRelations);

        int databaseSizeBeforeUpdate = citizensRelationsRepository.findAll().size();

        // Update the citizensRelations using partial update
        CitizensRelations partialUpdatedCitizensRelations = new CitizensRelations();
        partialUpdatedCitizensRelations.setId(citizensRelations.getId());

        restCitizensRelationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCitizensRelations.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCitizensRelations))
            )
            .andExpect(status().isOk());

        // Validate the CitizensRelations in the database
        List<CitizensRelations> citizensRelationsList = citizensRelationsRepository.findAll();
        assertThat(citizensRelationsList).hasSize(databaseSizeBeforeUpdate);
        CitizensRelations testCitizensRelations = citizensRelationsList.get(citizensRelationsList.size() - 1);
        assertThat(testCitizensRelations.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCitizensRelationsWithPatch() throws Exception {
        // Initialize the database
        citizensRelationsRepository.saveAndFlush(citizensRelations);

        int databaseSizeBeforeUpdate = citizensRelationsRepository.findAll().size();

        // Update the citizensRelations using partial update
        CitizensRelations partialUpdatedCitizensRelations = new CitizensRelations();
        partialUpdatedCitizensRelations.setId(citizensRelations.getId());

        partialUpdatedCitizensRelations.name(UPDATED_NAME);

        restCitizensRelationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCitizensRelations.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCitizensRelations))
            )
            .andExpect(status().isOk());

        // Validate the CitizensRelations in the database
        List<CitizensRelations> citizensRelationsList = citizensRelationsRepository.findAll();
        assertThat(citizensRelationsList).hasSize(databaseSizeBeforeUpdate);
        CitizensRelations testCitizensRelations = citizensRelationsList.get(citizensRelationsList.size() - 1);
        assertThat(testCitizensRelations.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCitizensRelations() throws Exception {
        int databaseSizeBeforeUpdate = citizensRelationsRepository.findAll().size();
        citizensRelations.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitizensRelationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, citizensRelations.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citizensRelations))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitizensRelations in the database
        List<CitizensRelations> citizensRelationsList = citizensRelationsRepository.findAll();
        assertThat(citizensRelationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCitizensRelations() throws Exception {
        int databaseSizeBeforeUpdate = citizensRelationsRepository.findAll().size();
        citizensRelations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizensRelationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citizensRelations))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitizensRelations in the database
        List<CitizensRelations> citizensRelationsList = citizensRelationsRepository.findAll();
        assertThat(citizensRelationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCitizensRelations() throws Exception {
        int databaseSizeBeforeUpdate = citizensRelationsRepository.findAll().size();
        citizensRelations.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizensRelationsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citizensRelations))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CitizensRelations in the database
        List<CitizensRelations> citizensRelationsList = citizensRelationsRepository.findAll();
        assertThat(citizensRelationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCitizensRelations() throws Exception {
        // Initialize the database
        citizensRelationsRepository.saveAndFlush(citizensRelations);

        int databaseSizeBeforeDelete = citizensRelationsRepository.findAll().size();

        // Delete the citizensRelations
        restCitizensRelationsMockMvc
            .perform(delete(ENTITY_API_URL_ID, citizensRelations.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CitizensRelations> citizensRelationsList = citizensRelationsRepository.findAll();
        assertThat(citizensRelationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
