package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Jobs;
import com.mycompany.myapp.repository.JobsRepository;
import com.mycompany.myapp.service.criteria.JobsCriteria;
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
 * Integration tests for the {@link JobsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/jobs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobsRepository jobsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobsMockMvc;

    private Jobs jobs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jobs createEntity(EntityManager em) {
        Jobs jobs = new Jobs().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return jobs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jobs createUpdatedEntity(EntityManager em) {
        Jobs jobs = new Jobs().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return jobs;
    }

    @BeforeEach
    public void initTest() {
        jobs = createEntity(em);
    }

    @Test
    @Transactional
    void createJobs() throws Exception {
        int databaseSizeBeforeCreate = jobsRepository.findAll().size();
        // Create the Jobs
        restJobsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobs)))
            .andExpect(status().isCreated());

        // Validate the Jobs in the database
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeCreate + 1);
        Jobs testJobs = jobsList.get(jobsList.size() - 1);
        assertThat(testJobs.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobs.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createJobsWithExistingId() throws Exception {
        // Create the Jobs with an existing ID
        jobs.setId(1L);

        int databaseSizeBeforeCreate = jobsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobs)))
            .andExpect(status().isBadRequest());

        // Validate the Jobs in the database
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobsRepository.findAll().size();
        // set the field null
        jobs.setName(null);

        // Create the Jobs, which fails.

        restJobsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobs)))
            .andExpect(status().isBadRequest());

        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllJobs() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get all the jobsList
        restJobsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobs.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getJobs() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get the jobs
        restJobsMockMvc
            .perform(get(ENTITY_API_URL_ID, jobs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobs.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getJobsByIdFiltering() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        Long id = jobs.getId();

        defaultJobsShouldBeFound("id.equals=" + id);
        defaultJobsShouldNotBeFound("id.notEquals=" + id);

        defaultJobsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultJobsShouldNotBeFound("id.greaterThan=" + id);

        defaultJobsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultJobsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllJobsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get all the jobsList where name equals to DEFAULT_NAME
        defaultJobsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the jobsList where name equals to UPDATED_NAME
        defaultJobsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllJobsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get all the jobsList where name not equals to DEFAULT_NAME
        defaultJobsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the jobsList where name not equals to UPDATED_NAME
        defaultJobsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllJobsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get all the jobsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultJobsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the jobsList where name equals to UPDATED_NAME
        defaultJobsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllJobsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get all the jobsList where name is not null
        defaultJobsShouldBeFound("name.specified=true");

        // Get all the jobsList where name is null
        defaultJobsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllJobsByNameContainsSomething() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get all the jobsList where name contains DEFAULT_NAME
        defaultJobsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the jobsList where name contains UPDATED_NAME
        defaultJobsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllJobsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get all the jobsList where name does not contain DEFAULT_NAME
        defaultJobsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the jobsList where name does not contain UPDATED_NAME
        defaultJobsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllJobsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get all the jobsList where description equals to DEFAULT_DESCRIPTION
        defaultJobsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the jobsList where description equals to UPDATED_DESCRIPTION
        defaultJobsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllJobsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get all the jobsList where description not equals to DEFAULT_DESCRIPTION
        defaultJobsShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the jobsList where description not equals to UPDATED_DESCRIPTION
        defaultJobsShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllJobsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get all the jobsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultJobsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the jobsList where description equals to UPDATED_DESCRIPTION
        defaultJobsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllJobsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get all the jobsList where description is not null
        defaultJobsShouldBeFound("description.specified=true");

        // Get all the jobsList where description is null
        defaultJobsShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllJobsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get all the jobsList where description contains DEFAULT_DESCRIPTION
        defaultJobsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the jobsList where description contains UPDATED_DESCRIPTION
        defaultJobsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllJobsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        // Get all the jobsList where description does not contain DEFAULT_DESCRIPTION
        defaultJobsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the jobsList where description does not contain UPDATED_DESCRIPTION
        defaultJobsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultJobsShouldBeFound(String filter) throws Exception {
        restJobsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobs.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restJobsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultJobsShouldNotBeFound(String filter) throws Exception {
        restJobsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJobsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingJobs() throws Exception {
        // Get the jobs
        restJobsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewJobs() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        int databaseSizeBeforeUpdate = jobsRepository.findAll().size();

        // Update the jobs
        Jobs updatedJobs = jobsRepository.findById(jobs.getId()).get();
        // Disconnect from session so that the updates on updatedJobs are not directly saved in db
        em.detach(updatedJobs);
        updatedJobs.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restJobsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobs.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJobs))
            )
            .andExpect(status().isOk());

        // Validate the Jobs in the database
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeUpdate);
        Jobs testJobs = jobsList.get(jobsList.size() - 1);
        assertThat(testJobs.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobs.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingJobs() throws Exception {
        int databaseSizeBeforeUpdate = jobsRepository.findAll().size();
        jobs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobs.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jobs in the database
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobs() throws Exception {
        int databaseSizeBeforeUpdate = jobsRepository.findAll().size();
        jobs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jobs in the database
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobs() throws Exception {
        int databaseSizeBeforeUpdate = jobsRepository.findAll().size();
        jobs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobs)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jobs in the database
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobsWithPatch() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        int databaseSizeBeforeUpdate = jobsRepository.findAll().size();

        // Update the jobs using partial update
        Jobs partialUpdatedJobs = new Jobs();
        partialUpdatedJobs.setId(jobs.getId());

        partialUpdatedJobs.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restJobsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobs))
            )
            .andExpect(status().isOk());

        // Validate the Jobs in the database
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeUpdate);
        Jobs testJobs = jobsList.get(jobsList.size() - 1);
        assertThat(testJobs.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobs.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateJobsWithPatch() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        int databaseSizeBeforeUpdate = jobsRepository.findAll().size();

        // Update the jobs using partial update
        Jobs partialUpdatedJobs = new Jobs();
        partialUpdatedJobs.setId(jobs.getId());

        partialUpdatedJobs.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restJobsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobs))
            )
            .andExpect(status().isOk());

        // Validate the Jobs in the database
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeUpdate);
        Jobs testJobs = jobsList.get(jobsList.size() - 1);
        assertThat(testJobs.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobs.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingJobs() throws Exception {
        int databaseSizeBeforeUpdate = jobsRepository.findAll().size();
        jobs.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jobs in the database
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobs() throws Exception {
        int databaseSizeBeforeUpdate = jobsRepository.findAll().size();
        jobs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobs))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jobs in the database
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobs() throws Exception {
        int databaseSizeBeforeUpdate = jobsRepository.findAll().size();
        jobs.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jobs)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jobs in the database
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobs() throws Exception {
        // Initialize the database
        jobsRepository.saveAndFlush(jobs);

        int databaseSizeBeforeDelete = jobsRepository.findAll().size();

        // Delete the jobs
        restJobsMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Jobs> jobsList = jobsRepository.findAll();
        assertThat(jobsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
