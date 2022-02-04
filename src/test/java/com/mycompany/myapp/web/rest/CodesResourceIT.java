package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Codes;
import com.mycompany.myapp.repository.CodesRepository;
import com.mycompany.myapp.service.criteria.CodesCriteria;
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
 * Integration tests for the {@link CodesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CodesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/codes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CodesRepository codesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCodesMockMvc;

    private Codes codes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Codes createEntity(EntityManager em) {
        Codes codes = new Codes().name(DEFAULT_NAME);
        return codes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Codes createUpdatedEntity(EntityManager em) {
        Codes codes = new Codes().name(UPDATED_NAME);
        return codes;
    }

    @BeforeEach
    public void initTest() {
        codes = createEntity(em);
    }

    @Test
    @Transactional
    void createCodes() throws Exception {
        int databaseSizeBeforeCreate = codesRepository.findAll().size();
        // Create the Codes
        restCodesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codes)))
            .andExpect(status().isCreated());

        // Validate the Codes in the database
        List<Codes> codesList = codesRepository.findAll();
        assertThat(codesList).hasSize(databaseSizeBeforeCreate + 1);
        Codes testCodes = codesList.get(codesList.size() - 1);
        assertThat(testCodes.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createCodesWithExistingId() throws Exception {
        // Create the Codes with an existing ID
        codes.setId(1L);

        int databaseSizeBeforeCreate = codesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codes)))
            .andExpect(status().isBadRequest());

        // Validate the Codes in the database
        List<Codes> codesList = codesRepository.findAll();
        assertThat(codesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = codesRepository.findAll().size();
        // set the field null
        codes.setName(null);

        // Create the Codes, which fails.

        restCodesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codes)))
            .andExpect(status().isBadRequest());

        List<Codes> codesList = codesRepository.findAll();
        assertThat(codesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCodes() throws Exception {
        // Initialize the database
        codesRepository.saveAndFlush(codes);

        // Get all the codesList
        restCodesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getCodes() throws Exception {
        // Initialize the database
        codesRepository.saveAndFlush(codes);

        // Get the codes
        restCodesMockMvc
            .perform(get(ENTITY_API_URL_ID, codes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(codes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getCodesByIdFiltering() throws Exception {
        // Initialize the database
        codesRepository.saveAndFlush(codes);

        Long id = codes.getId();

        defaultCodesShouldBeFound("id.equals=" + id);
        defaultCodesShouldNotBeFound("id.notEquals=" + id);

        defaultCodesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCodesShouldNotBeFound("id.greaterThan=" + id);

        defaultCodesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCodesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCodesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        codesRepository.saveAndFlush(codes);

        // Get all the codesList where name equals to DEFAULT_NAME
        defaultCodesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the codesList where name equals to UPDATED_NAME
        defaultCodesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCodesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        codesRepository.saveAndFlush(codes);

        // Get all the codesList where name not equals to DEFAULT_NAME
        defaultCodesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the codesList where name not equals to UPDATED_NAME
        defaultCodesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCodesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        codesRepository.saveAndFlush(codes);

        // Get all the codesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCodesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the codesList where name equals to UPDATED_NAME
        defaultCodesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCodesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        codesRepository.saveAndFlush(codes);

        // Get all the codesList where name is not null
        defaultCodesShouldBeFound("name.specified=true");

        // Get all the codesList where name is null
        defaultCodesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCodesByNameContainsSomething() throws Exception {
        // Initialize the database
        codesRepository.saveAndFlush(codes);

        // Get all the codesList where name contains DEFAULT_NAME
        defaultCodesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the codesList where name contains UPDATED_NAME
        defaultCodesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCodesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        codesRepository.saveAndFlush(codes);

        // Get all the codesList where name does not contain DEFAULT_NAME
        defaultCodesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the codesList where name does not contain UPDATED_NAME
        defaultCodesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCodesShouldBeFound(String filter) throws Exception {
        restCodesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCodesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCodesShouldNotBeFound(String filter) throws Exception {
        restCodesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCodesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCodes() throws Exception {
        // Get the codes
        restCodesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCodes() throws Exception {
        // Initialize the database
        codesRepository.saveAndFlush(codes);

        int databaseSizeBeforeUpdate = codesRepository.findAll().size();

        // Update the codes
        Codes updatedCodes = codesRepository.findById(codes.getId()).get();
        // Disconnect from session so that the updates on updatedCodes are not directly saved in db
        em.detach(updatedCodes);
        updatedCodes.name(UPDATED_NAME);

        restCodesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCodes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCodes))
            )
            .andExpect(status().isOk());

        // Validate the Codes in the database
        List<Codes> codesList = codesRepository.findAll();
        assertThat(codesList).hasSize(databaseSizeBeforeUpdate);
        Codes testCodes = codesList.get(codesList.size() - 1);
        assertThat(testCodes.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCodes() throws Exception {
        int databaseSizeBeforeUpdate = codesRepository.findAll().size();
        codes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, codes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(codes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Codes in the database
        List<Codes> codesList = codesRepository.findAll();
        assertThat(codesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCodes() throws Exception {
        int databaseSizeBeforeUpdate = codesRepository.findAll().size();
        codes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(codes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Codes in the database
        List<Codes> codesList = codesRepository.findAll();
        assertThat(codesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCodes() throws Exception {
        int databaseSizeBeforeUpdate = codesRepository.findAll().size();
        codes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Codes in the database
        List<Codes> codesList = codesRepository.findAll();
        assertThat(codesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCodesWithPatch() throws Exception {
        // Initialize the database
        codesRepository.saveAndFlush(codes);

        int databaseSizeBeforeUpdate = codesRepository.findAll().size();

        // Update the codes using partial update
        Codes partialUpdatedCodes = new Codes();
        partialUpdatedCodes.setId(codes.getId());

        restCodesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCodes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCodes))
            )
            .andExpect(status().isOk());

        // Validate the Codes in the database
        List<Codes> codesList = codesRepository.findAll();
        assertThat(codesList).hasSize(databaseSizeBeforeUpdate);
        Codes testCodes = codesList.get(codesList.size() - 1);
        assertThat(testCodes.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCodesWithPatch() throws Exception {
        // Initialize the database
        codesRepository.saveAndFlush(codes);

        int databaseSizeBeforeUpdate = codesRepository.findAll().size();

        // Update the codes using partial update
        Codes partialUpdatedCodes = new Codes();
        partialUpdatedCodes.setId(codes.getId());

        partialUpdatedCodes.name(UPDATED_NAME);

        restCodesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCodes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCodes))
            )
            .andExpect(status().isOk());

        // Validate the Codes in the database
        List<Codes> codesList = codesRepository.findAll();
        assertThat(codesList).hasSize(databaseSizeBeforeUpdate);
        Codes testCodes = codesList.get(codesList.size() - 1);
        assertThat(testCodes.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCodes() throws Exception {
        int databaseSizeBeforeUpdate = codesRepository.findAll().size();
        codes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, codes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(codes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Codes in the database
        List<Codes> codesList = codesRepository.findAll();
        assertThat(codesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCodes() throws Exception {
        int databaseSizeBeforeUpdate = codesRepository.findAll().size();
        codes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(codes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Codes in the database
        List<Codes> codesList = codesRepository.findAll();
        assertThat(codesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCodes() throws Exception {
        int databaseSizeBeforeUpdate = codesRepository.findAll().size();
        codes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(codes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Codes in the database
        List<Codes> codesList = codesRepository.findAll();
        assertThat(codesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCodes() throws Exception {
        // Initialize the database
        codesRepository.saveAndFlush(codes);

        int databaseSizeBeforeDelete = codesRepository.findAll().size();

        // Delete the codes
        restCodesMockMvc
            .perform(delete(ENTITY_API_URL_ID, codes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Codes> codesList = codesRepository.findAll();
        assertThat(codesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
