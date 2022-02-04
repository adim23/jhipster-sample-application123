package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Origins;
import com.mycompany.myapp.repository.OriginsRepository;
import com.mycompany.myapp.service.criteria.OriginsCriteria;
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
 * Integration tests for the {@link OriginsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OriginsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/origins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OriginsRepository originsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOriginsMockMvc;

    private Origins origins;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Origins createEntity(EntityManager em) {
        Origins origins = new Origins().name(DEFAULT_NAME);
        return origins;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Origins createUpdatedEntity(EntityManager em) {
        Origins origins = new Origins().name(UPDATED_NAME);
        return origins;
    }

    @BeforeEach
    public void initTest() {
        origins = createEntity(em);
    }

    @Test
    @Transactional
    void createOrigins() throws Exception {
        int databaseSizeBeforeCreate = originsRepository.findAll().size();
        // Create the Origins
        restOriginsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(origins)))
            .andExpect(status().isCreated());

        // Validate the Origins in the database
        List<Origins> originsList = originsRepository.findAll();
        assertThat(originsList).hasSize(databaseSizeBeforeCreate + 1);
        Origins testOrigins = originsList.get(originsList.size() - 1);
        assertThat(testOrigins.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createOriginsWithExistingId() throws Exception {
        // Create the Origins with an existing ID
        origins.setId(1L);

        int databaseSizeBeforeCreate = originsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOriginsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(origins)))
            .andExpect(status().isBadRequest());

        // Validate the Origins in the database
        List<Origins> originsList = originsRepository.findAll();
        assertThat(originsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = originsRepository.findAll().size();
        // set the field null
        origins.setName(null);

        // Create the Origins, which fails.

        restOriginsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(origins)))
            .andExpect(status().isBadRequest());

        List<Origins> originsList = originsRepository.findAll();
        assertThat(originsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrigins() throws Exception {
        // Initialize the database
        originsRepository.saveAndFlush(origins);

        // Get all the originsList
        restOriginsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(origins.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getOrigins() throws Exception {
        // Initialize the database
        originsRepository.saveAndFlush(origins);

        // Get the origins
        restOriginsMockMvc
            .perform(get(ENTITY_API_URL_ID, origins.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(origins.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getOriginsByIdFiltering() throws Exception {
        // Initialize the database
        originsRepository.saveAndFlush(origins);

        Long id = origins.getId();

        defaultOriginsShouldBeFound("id.equals=" + id);
        defaultOriginsShouldNotBeFound("id.notEquals=" + id);

        defaultOriginsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOriginsShouldNotBeFound("id.greaterThan=" + id);

        defaultOriginsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOriginsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOriginsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        originsRepository.saveAndFlush(origins);

        // Get all the originsList where name equals to DEFAULT_NAME
        defaultOriginsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the originsList where name equals to UPDATED_NAME
        defaultOriginsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOriginsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        originsRepository.saveAndFlush(origins);

        // Get all the originsList where name not equals to DEFAULT_NAME
        defaultOriginsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the originsList where name not equals to UPDATED_NAME
        defaultOriginsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOriginsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        originsRepository.saveAndFlush(origins);

        // Get all the originsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOriginsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the originsList where name equals to UPDATED_NAME
        defaultOriginsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOriginsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        originsRepository.saveAndFlush(origins);

        // Get all the originsList where name is not null
        defaultOriginsShouldBeFound("name.specified=true");

        // Get all the originsList where name is null
        defaultOriginsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOriginsByNameContainsSomething() throws Exception {
        // Initialize the database
        originsRepository.saveAndFlush(origins);

        // Get all the originsList where name contains DEFAULT_NAME
        defaultOriginsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the originsList where name contains UPDATED_NAME
        defaultOriginsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOriginsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        originsRepository.saveAndFlush(origins);

        // Get all the originsList where name does not contain DEFAULT_NAME
        defaultOriginsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the originsList where name does not contain UPDATED_NAME
        defaultOriginsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOriginsShouldBeFound(String filter) throws Exception {
        restOriginsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(origins.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restOriginsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOriginsShouldNotBeFound(String filter) throws Exception {
        restOriginsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOriginsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrigins() throws Exception {
        // Get the origins
        restOriginsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrigins() throws Exception {
        // Initialize the database
        originsRepository.saveAndFlush(origins);

        int databaseSizeBeforeUpdate = originsRepository.findAll().size();

        // Update the origins
        Origins updatedOrigins = originsRepository.findById(origins.getId()).get();
        // Disconnect from session so that the updates on updatedOrigins are not directly saved in db
        em.detach(updatedOrigins);
        updatedOrigins.name(UPDATED_NAME);

        restOriginsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrigins.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrigins))
            )
            .andExpect(status().isOk());

        // Validate the Origins in the database
        List<Origins> originsList = originsRepository.findAll();
        assertThat(originsList).hasSize(databaseSizeBeforeUpdate);
        Origins testOrigins = originsList.get(originsList.size() - 1);
        assertThat(testOrigins.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingOrigins() throws Exception {
        int databaseSizeBeforeUpdate = originsRepository.findAll().size();
        origins.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOriginsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, origins.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(origins))
            )
            .andExpect(status().isBadRequest());

        // Validate the Origins in the database
        List<Origins> originsList = originsRepository.findAll();
        assertThat(originsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrigins() throws Exception {
        int databaseSizeBeforeUpdate = originsRepository.findAll().size();
        origins.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOriginsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(origins))
            )
            .andExpect(status().isBadRequest());

        // Validate the Origins in the database
        List<Origins> originsList = originsRepository.findAll();
        assertThat(originsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrigins() throws Exception {
        int databaseSizeBeforeUpdate = originsRepository.findAll().size();
        origins.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOriginsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(origins)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Origins in the database
        List<Origins> originsList = originsRepository.findAll();
        assertThat(originsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOriginsWithPatch() throws Exception {
        // Initialize the database
        originsRepository.saveAndFlush(origins);

        int databaseSizeBeforeUpdate = originsRepository.findAll().size();

        // Update the origins using partial update
        Origins partialUpdatedOrigins = new Origins();
        partialUpdatedOrigins.setId(origins.getId());

        partialUpdatedOrigins.name(UPDATED_NAME);

        restOriginsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrigins.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrigins))
            )
            .andExpect(status().isOk());

        // Validate the Origins in the database
        List<Origins> originsList = originsRepository.findAll();
        assertThat(originsList).hasSize(databaseSizeBeforeUpdate);
        Origins testOrigins = originsList.get(originsList.size() - 1);
        assertThat(testOrigins.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateOriginsWithPatch() throws Exception {
        // Initialize the database
        originsRepository.saveAndFlush(origins);

        int databaseSizeBeforeUpdate = originsRepository.findAll().size();

        // Update the origins using partial update
        Origins partialUpdatedOrigins = new Origins();
        partialUpdatedOrigins.setId(origins.getId());

        partialUpdatedOrigins.name(UPDATED_NAME);

        restOriginsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrigins.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrigins))
            )
            .andExpect(status().isOk());

        // Validate the Origins in the database
        List<Origins> originsList = originsRepository.findAll();
        assertThat(originsList).hasSize(databaseSizeBeforeUpdate);
        Origins testOrigins = originsList.get(originsList.size() - 1);
        assertThat(testOrigins.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingOrigins() throws Exception {
        int databaseSizeBeforeUpdate = originsRepository.findAll().size();
        origins.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOriginsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, origins.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(origins))
            )
            .andExpect(status().isBadRequest());

        // Validate the Origins in the database
        List<Origins> originsList = originsRepository.findAll();
        assertThat(originsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrigins() throws Exception {
        int databaseSizeBeforeUpdate = originsRepository.findAll().size();
        origins.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOriginsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(origins))
            )
            .andExpect(status().isBadRequest());

        // Validate the Origins in the database
        List<Origins> originsList = originsRepository.findAll();
        assertThat(originsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrigins() throws Exception {
        int databaseSizeBeforeUpdate = originsRepository.findAll().size();
        origins.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOriginsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(origins)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Origins in the database
        List<Origins> originsList = originsRepository.findAll();
        assertThat(originsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrigins() throws Exception {
        // Initialize the database
        originsRepository.saveAndFlush(origins);

        int databaseSizeBeforeDelete = originsRepository.findAll().size();

        // Delete the origins
        restOriginsMockMvc
            .perform(delete(ENTITY_API_URL_ID, origins.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Origins> originsList = originsRepository.findAll();
        assertThat(originsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
