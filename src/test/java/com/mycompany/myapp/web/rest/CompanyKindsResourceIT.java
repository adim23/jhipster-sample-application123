package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CompanyKinds;
import com.mycompany.myapp.repository.CompanyKindsRepository;
import com.mycompany.myapp.service.criteria.CompanyKindsCriteria;
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
 * Integration tests for the {@link CompanyKindsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyKindsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/company-kinds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyKindsRepository companyKindsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyKindsMockMvc;

    private CompanyKinds companyKinds;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyKinds createEntity(EntityManager em) {
        CompanyKinds companyKinds = new CompanyKinds().name(DEFAULT_NAME);
        return companyKinds;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyKinds createUpdatedEntity(EntityManager em) {
        CompanyKinds companyKinds = new CompanyKinds().name(UPDATED_NAME);
        return companyKinds;
    }

    @BeforeEach
    public void initTest() {
        companyKinds = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanyKinds() throws Exception {
        int databaseSizeBeforeCreate = companyKindsRepository.findAll().size();
        // Create the CompanyKinds
        restCompanyKindsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyKinds)))
            .andExpect(status().isCreated());

        // Validate the CompanyKinds in the database
        List<CompanyKinds> companyKindsList = companyKindsRepository.findAll();
        assertThat(companyKindsList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyKinds testCompanyKinds = companyKindsList.get(companyKindsList.size() - 1);
        assertThat(testCompanyKinds.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createCompanyKindsWithExistingId() throws Exception {
        // Create the CompanyKinds with an existing ID
        companyKinds.setId(1L);

        int databaseSizeBeforeCreate = companyKindsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyKindsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyKinds)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyKinds in the database
        List<CompanyKinds> companyKindsList = companyKindsRepository.findAll();
        assertThat(companyKindsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyKindsRepository.findAll().size();
        // set the field null
        companyKinds.setName(null);

        // Create the CompanyKinds, which fails.

        restCompanyKindsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyKinds)))
            .andExpect(status().isBadRequest());

        List<CompanyKinds> companyKindsList = companyKindsRepository.findAll();
        assertThat(companyKindsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompanyKinds() throws Exception {
        // Initialize the database
        companyKindsRepository.saveAndFlush(companyKinds);

        // Get all the companyKindsList
        restCompanyKindsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyKinds.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getCompanyKinds() throws Exception {
        // Initialize the database
        companyKindsRepository.saveAndFlush(companyKinds);

        // Get the companyKinds
        restCompanyKindsMockMvc
            .perform(get(ENTITY_API_URL_ID, companyKinds.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyKinds.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getCompanyKindsByIdFiltering() throws Exception {
        // Initialize the database
        companyKindsRepository.saveAndFlush(companyKinds);

        Long id = companyKinds.getId();

        defaultCompanyKindsShouldBeFound("id.equals=" + id);
        defaultCompanyKindsShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyKindsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyKindsShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyKindsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyKindsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCompanyKindsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyKindsRepository.saveAndFlush(companyKinds);

        // Get all the companyKindsList where name equals to DEFAULT_NAME
        defaultCompanyKindsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the companyKindsList where name equals to UPDATED_NAME
        defaultCompanyKindsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompanyKindsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyKindsRepository.saveAndFlush(companyKinds);

        // Get all the companyKindsList where name not equals to DEFAULT_NAME
        defaultCompanyKindsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the companyKindsList where name not equals to UPDATED_NAME
        defaultCompanyKindsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompanyKindsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyKindsRepository.saveAndFlush(companyKinds);

        // Get all the companyKindsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCompanyKindsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the companyKindsList where name equals to UPDATED_NAME
        defaultCompanyKindsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompanyKindsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyKindsRepository.saveAndFlush(companyKinds);

        // Get all the companyKindsList where name is not null
        defaultCompanyKindsShouldBeFound("name.specified=true");

        // Get all the companyKindsList where name is null
        defaultCompanyKindsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyKindsByNameContainsSomething() throws Exception {
        // Initialize the database
        companyKindsRepository.saveAndFlush(companyKinds);

        // Get all the companyKindsList where name contains DEFAULT_NAME
        defaultCompanyKindsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the companyKindsList where name contains UPDATED_NAME
        defaultCompanyKindsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompanyKindsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyKindsRepository.saveAndFlush(companyKinds);

        // Get all the companyKindsList where name does not contain DEFAULT_NAME
        defaultCompanyKindsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the companyKindsList where name does not contain UPDATED_NAME
        defaultCompanyKindsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyKindsShouldBeFound(String filter) throws Exception {
        restCompanyKindsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyKinds.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCompanyKindsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyKindsShouldNotBeFound(String filter) throws Exception {
        restCompanyKindsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyKindsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCompanyKinds() throws Exception {
        // Get the companyKinds
        restCompanyKindsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompanyKinds() throws Exception {
        // Initialize the database
        companyKindsRepository.saveAndFlush(companyKinds);

        int databaseSizeBeforeUpdate = companyKindsRepository.findAll().size();

        // Update the companyKinds
        CompanyKinds updatedCompanyKinds = companyKindsRepository.findById(companyKinds.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyKinds are not directly saved in db
        em.detach(updatedCompanyKinds);
        updatedCompanyKinds.name(UPDATED_NAME);

        restCompanyKindsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompanyKinds.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompanyKinds))
            )
            .andExpect(status().isOk());

        // Validate the CompanyKinds in the database
        List<CompanyKinds> companyKindsList = companyKindsRepository.findAll();
        assertThat(companyKindsList).hasSize(databaseSizeBeforeUpdate);
        CompanyKinds testCompanyKinds = companyKindsList.get(companyKindsList.size() - 1);
        assertThat(testCompanyKinds.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCompanyKinds() throws Exception {
        int databaseSizeBeforeUpdate = companyKindsRepository.findAll().size();
        companyKinds.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyKindsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyKinds.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyKinds))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyKinds in the database
        List<CompanyKinds> companyKindsList = companyKindsRepository.findAll();
        assertThat(companyKindsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanyKinds() throws Exception {
        int databaseSizeBeforeUpdate = companyKindsRepository.findAll().size();
        companyKinds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyKindsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyKinds))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyKinds in the database
        List<CompanyKinds> companyKindsList = companyKindsRepository.findAll();
        assertThat(companyKindsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanyKinds() throws Exception {
        int databaseSizeBeforeUpdate = companyKindsRepository.findAll().size();
        companyKinds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyKindsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyKinds)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyKinds in the database
        List<CompanyKinds> companyKindsList = companyKindsRepository.findAll();
        assertThat(companyKindsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyKindsWithPatch() throws Exception {
        // Initialize the database
        companyKindsRepository.saveAndFlush(companyKinds);

        int databaseSizeBeforeUpdate = companyKindsRepository.findAll().size();

        // Update the companyKinds using partial update
        CompanyKinds partialUpdatedCompanyKinds = new CompanyKinds();
        partialUpdatedCompanyKinds.setId(companyKinds.getId());

        restCompanyKindsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyKinds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyKinds))
            )
            .andExpect(status().isOk());

        // Validate the CompanyKinds in the database
        List<CompanyKinds> companyKindsList = companyKindsRepository.findAll();
        assertThat(companyKindsList).hasSize(databaseSizeBeforeUpdate);
        CompanyKinds testCompanyKinds = companyKindsList.get(companyKindsList.size() - 1);
        assertThat(testCompanyKinds.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCompanyKindsWithPatch() throws Exception {
        // Initialize the database
        companyKindsRepository.saveAndFlush(companyKinds);

        int databaseSizeBeforeUpdate = companyKindsRepository.findAll().size();

        // Update the companyKinds using partial update
        CompanyKinds partialUpdatedCompanyKinds = new CompanyKinds();
        partialUpdatedCompanyKinds.setId(companyKinds.getId());

        partialUpdatedCompanyKinds.name(UPDATED_NAME);

        restCompanyKindsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyKinds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyKinds))
            )
            .andExpect(status().isOk());

        // Validate the CompanyKinds in the database
        List<CompanyKinds> companyKindsList = companyKindsRepository.findAll();
        assertThat(companyKindsList).hasSize(databaseSizeBeforeUpdate);
        CompanyKinds testCompanyKinds = companyKindsList.get(companyKindsList.size() - 1);
        assertThat(testCompanyKinds.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCompanyKinds() throws Exception {
        int databaseSizeBeforeUpdate = companyKindsRepository.findAll().size();
        companyKinds.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyKindsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyKinds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyKinds))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyKinds in the database
        List<CompanyKinds> companyKindsList = companyKindsRepository.findAll();
        assertThat(companyKindsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanyKinds() throws Exception {
        int databaseSizeBeforeUpdate = companyKindsRepository.findAll().size();
        companyKinds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyKindsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyKinds))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyKinds in the database
        List<CompanyKinds> companyKindsList = companyKindsRepository.findAll();
        assertThat(companyKindsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanyKinds() throws Exception {
        int databaseSizeBeforeUpdate = companyKindsRepository.findAll().size();
        companyKinds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyKindsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(companyKinds))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyKinds in the database
        List<CompanyKinds> companyKindsList = companyKindsRepository.findAll();
        assertThat(companyKindsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanyKinds() throws Exception {
        // Initialize the database
        companyKindsRepository.saveAndFlush(companyKinds);

        int databaseSizeBeforeDelete = companyKindsRepository.findAll().size();

        // Delete the companyKinds
        restCompanyKindsMockMvc
            .perform(delete(ENTITY_API_URL_ID, companyKinds.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyKinds> companyKindsList = companyKindsRepository.findAll();
        assertThat(companyKindsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
