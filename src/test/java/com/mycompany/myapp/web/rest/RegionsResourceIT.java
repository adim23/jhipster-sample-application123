package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Countries;
import com.mycompany.myapp.domain.Regions;
import com.mycompany.myapp.repository.RegionsRepository;
import com.mycompany.myapp.service.criteria.RegionsCriteria;
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
 * Integration tests for the {@link RegionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/regions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegionsRepository regionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegionsMockMvc;

    private Regions regions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regions createEntity(EntityManager em) {
        Regions regions = new Regions().name(DEFAULT_NAME);
        // Add required entity
        Countries countries;
        if (TestUtil.findAll(em, Countries.class).isEmpty()) {
            countries = CountriesResourceIT.createEntity(em);
            em.persist(countries);
            em.flush();
        } else {
            countries = TestUtil.findAll(em, Countries.class).get(0);
        }
        regions.setCountry(countries);
        return regions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regions createUpdatedEntity(EntityManager em) {
        Regions regions = new Regions().name(UPDATED_NAME);
        // Add required entity
        Countries countries;
        if (TestUtil.findAll(em, Countries.class).isEmpty()) {
            countries = CountriesResourceIT.createUpdatedEntity(em);
            em.persist(countries);
            em.flush();
        } else {
            countries = TestUtil.findAll(em, Countries.class).get(0);
        }
        regions.setCountry(countries);
        return regions;
    }

    @BeforeEach
    public void initTest() {
        regions = createEntity(em);
    }

    @Test
    @Transactional
    void createRegions() throws Exception {
        int databaseSizeBeforeCreate = regionsRepository.findAll().size();
        // Create the Regions
        restRegionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regions)))
            .andExpect(status().isCreated());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeCreate + 1);
        Regions testRegions = regionsList.get(regionsList.size() - 1);
        assertThat(testRegions.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createRegionsWithExistingId() throws Exception {
        // Create the Regions with an existing ID
        regions.setId(1L);

        int databaseSizeBeforeCreate = regionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regions)))
            .andExpect(status().isBadRequest());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionsRepository.findAll().size();
        // set the field null
        regions.setName(null);

        // Create the Regions, which fails.

        restRegionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regions)))
            .andExpect(status().isBadRequest());

        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRegions() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList
        restRegionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getRegions() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get the regions
        restRegionsMockMvc
            .perform(get(ENTITY_API_URL_ID, regions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getRegionsByIdFiltering() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        Long id = regions.getId();

        defaultRegionsShouldBeFound("id.equals=" + id);
        defaultRegionsShouldNotBeFound("id.notEquals=" + id);

        defaultRegionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRegionsShouldNotBeFound("id.greaterThan=" + id);

        defaultRegionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRegionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRegionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where name equals to DEFAULT_NAME
        defaultRegionsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the regionsList where name equals to UPDATED_NAME
        defaultRegionsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRegionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where name not equals to DEFAULT_NAME
        defaultRegionsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the regionsList where name not equals to UPDATED_NAME
        defaultRegionsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRegionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRegionsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the regionsList where name equals to UPDATED_NAME
        defaultRegionsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRegionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where name is not null
        defaultRegionsShouldBeFound("name.specified=true");

        // Get all the regionsList where name is null
        defaultRegionsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllRegionsByNameContainsSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where name contains DEFAULT_NAME
        defaultRegionsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the regionsList where name contains UPDATED_NAME
        defaultRegionsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRegionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList where name does not contain DEFAULT_NAME
        defaultRegionsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the regionsList where name does not contain UPDATED_NAME
        defaultRegionsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRegionsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);
        Countries country;
        if (TestUtil.findAll(em, Countries.class).isEmpty()) {
            country = CountriesResourceIT.createEntity(em);
            em.persist(country);
            em.flush();
        } else {
            country = TestUtil.findAll(em, Countries.class).get(0);
        }
        em.persist(country);
        em.flush();
        regions.setCountry(country);
        regionsRepository.saveAndFlush(regions);
        Long countryId = country.getId();

        // Get all the regionsList where country equals to countryId
        defaultRegionsShouldBeFound("countryId.equals=" + countryId);

        // Get all the regionsList where country equals to (countryId + 1)
        defaultRegionsShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegionsShouldBeFound(String filter) throws Exception {
        restRegionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restRegionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegionsShouldNotBeFound(String filter) throws Exception {
        restRegionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRegions() throws Exception {
        // Get the regions
        restRegionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRegions() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        int databaseSizeBeforeUpdate = regionsRepository.findAll().size();

        // Update the regions
        Regions updatedRegions = regionsRepository.findById(regions.getId()).get();
        // Disconnect from session so that the updates on updatedRegions are not directly saved in db
        em.detach(updatedRegions);
        updatedRegions.name(UPDATED_NAME);

        restRegionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRegions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRegions))
            )
            .andExpect(status().isOk());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeUpdate);
        Regions testRegions = regionsList.get(regionsList.size() - 1);
        assertThat(testRegions.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingRegions() throws Exception {
        int databaseSizeBeforeUpdate = regionsRepository.findAll().size();
        regions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegions() throws Exception {
        int databaseSizeBeforeUpdate = regionsRepository.findAll().size();
        regions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegions() throws Exception {
        int databaseSizeBeforeUpdate = regionsRepository.findAll().size();
        regions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regions)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegionsWithPatch() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        int databaseSizeBeforeUpdate = regionsRepository.findAll().size();

        // Update the regions using partial update
        Regions partialUpdatedRegions = new Regions();
        partialUpdatedRegions.setId(regions.getId());

        restRegionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegions))
            )
            .andExpect(status().isOk());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeUpdate);
        Regions testRegions = regionsList.get(regionsList.size() - 1);
        assertThat(testRegions.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateRegionsWithPatch() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        int databaseSizeBeforeUpdate = regionsRepository.findAll().size();

        // Update the regions using partial update
        Regions partialUpdatedRegions = new Regions();
        partialUpdatedRegions.setId(regions.getId());

        partialUpdatedRegions.name(UPDATED_NAME);

        restRegionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegions))
            )
            .andExpect(status().isOk());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeUpdate);
        Regions testRegions = regionsList.get(regionsList.size() - 1);
        assertThat(testRegions.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingRegions() throws Exception {
        int databaseSizeBeforeUpdate = regionsRepository.findAll().size();
        regions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, regions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegions() throws Exception {
        int databaseSizeBeforeUpdate = regionsRepository.findAll().size();
        regions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegions() throws Exception {
        int databaseSizeBeforeUpdate = regionsRepository.findAll().size();
        regions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegionsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(regions)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegions() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        int databaseSizeBeforeDelete = regionsRepository.findAll().size();

        // Delete the regions
        restRegionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, regions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
