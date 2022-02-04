package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Countries;
import com.mycompany.myapp.repository.CountriesRepository;
import com.mycompany.myapp.service.criteria.CountriesCriteria;
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
 * Integration tests for the {@link CountriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CountriesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ISO = "AAAAAAAAAA";
    private static final String UPDATED_ISO = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_LANG = "AAAAAAAAAA";
    private static final String UPDATED_LANG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/countries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountriesMockMvc;

    private Countries countries;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Countries createEntity(EntityManager em) {
        Countries countries = new Countries().name(DEFAULT_NAME).iso(DEFAULT_ISO).language(DEFAULT_LANGUAGE).lang(DEFAULT_LANG);
        return countries;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Countries createUpdatedEntity(EntityManager em) {
        Countries countries = new Countries().name(UPDATED_NAME).iso(UPDATED_ISO).language(UPDATED_LANGUAGE).lang(UPDATED_LANG);
        return countries;
    }

    @BeforeEach
    public void initTest() {
        countries = createEntity(em);
    }

    @Test
    @Transactional
    void createCountries() throws Exception {
        int databaseSizeBeforeCreate = countriesRepository.findAll().size();
        // Create the Countries
        restCountriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countries)))
            .andExpect(status().isCreated());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeCreate + 1);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCountries.getIso()).isEqualTo(DEFAULT_ISO);
        assertThat(testCountries.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testCountries.getLang()).isEqualTo(DEFAULT_LANG);
    }

    @Test
    @Transactional
    void createCountriesWithExistingId() throws Exception {
        // Create the Countries with an existing ID
        countries.setId(1L);

        int databaseSizeBeforeCreate = countriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countries)))
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setName(null);

        // Create the Countries, which fails.

        restCountriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countries)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList
        restCountriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countries.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].iso").value(hasItem(DEFAULT_ISO)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG)));
    }

    @Test
    @Transactional
    void getCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get the countries
        restCountriesMockMvc
            .perform(get(ENTITY_API_URL_ID, countries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(countries.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.iso").value(DEFAULT_ISO))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.lang").value(DEFAULT_LANG));
    }

    @Test
    @Transactional
    void getCountriesByIdFiltering() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        Long id = countries.getId();

        defaultCountriesShouldBeFound("id.equals=" + id);
        defaultCountriesShouldNotBeFound("id.notEquals=" + id);

        defaultCountriesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCountriesShouldNotBeFound("id.greaterThan=" + id);

        defaultCountriesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCountriesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCountriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where name equals to DEFAULT_NAME
        defaultCountriesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the countriesList where name equals to UPDATED_NAME
        defaultCountriesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where name not equals to DEFAULT_NAME
        defaultCountriesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the countriesList where name not equals to UPDATED_NAME
        defaultCountriesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCountriesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the countriesList where name equals to UPDATED_NAME
        defaultCountriesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where name is not null
        defaultCountriesShouldBeFound("name.specified=true");

        // Get all the countriesList where name is null
        defaultCountriesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByNameContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where name contains DEFAULT_NAME
        defaultCountriesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the countriesList where name contains UPDATED_NAME
        defaultCountriesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where name does not contain DEFAULT_NAME
        defaultCountriesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the countriesList where name does not contain UPDATED_NAME
        defaultCountriesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByIsoIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where iso equals to DEFAULT_ISO
        defaultCountriesShouldBeFound("iso.equals=" + DEFAULT_ISO);

        // Get all the countriesList where iso equals to UPDATED_ISO
        defaultCountriesShouldNotBeFound("iso.equals=" + UPDATED_ISO);
    }

    @Test
    @Transactional
    void getAllCountriesByIsoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where iso not equals to DEFAULT_ISO
        defaultCountriesShouldNotBeFound("iso.notEquals=" + DEFAULT_ISO);

        // Get all the countriesList where iso not equals to UPDATED_ISO
        defaultCountriesShouldBeFound("iso.notEquals=" + UPDATED_ISO);
    }

    @Test
    @Transactional
    void getAllCountriesByIsoIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where iso in DEFAULT_ISO or UPDATED_ISO
        defaultCountriesShouldBeFound("iso.in=" + DEFAULT_ISO + "," + UPDATED_ISO);

        // Get all the countriesList where iso equals to UPDATED_ISO
        defaultCountriesShouldNotBeFound("iso.in=" + UPDATED_ISO);
    }

    @Test
    @Transactional
    void getAllCountriesByIsoIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where iso is not null
        defaultCountriesShouldBeFound("iso.specified=true");

        // Get all the countriesList where iso is null
        defaultCountriesShouldNotBeFound("iso.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByIsoContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where iso contains DEFAULT_ISO
        defaultCountriesShouldBeFound("iso.contains=" + DEFAULT_ISO);

        // Get all the countriesList where iso contains UPDATED_ISO
        defaultCountriesShouldNotBeFound("iso.contains=" + UPDATED_ISO);
    }

    @Test
    @Transactional
    void getAllCountriesByIsoNotContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where iso does not contain DEFAULT_ISO
        defaultCountriesShouldNotBeFound("iso.doesNotContain=" + DEFAULT_ISO);

        // Get all the countriesList where iso does not contain UPDATED_ISO
        defaultCountriesShouldBeFound("iso.doesNotContain=" + UPDATED_ISO);
    }

    @Test
    @Transactional
    void getAllCountriesByLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where language equals to DEFAULT_LANGUAGE
        defaultCountriesShouldBeFound("language.equals=" + DEFAULT_LANGUAGE);

        // Get all the countriesList where language equals to UPDATED_LANGUAGE
        defaultCountriesShouldNotBeFound("language.equals=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllCountriesByLanguageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where language not equals to DEFAULT_LANGUAGE
        defaultCountriesShouldNotBeFound("language.notEquals=" + DEFAULT_LANGUAGE);

        // Get all the countriesList where language not equals to UPDATED_LANGUAGE
        defaultCountriesShouldBeFound("language.notEquals=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllCountriesByLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where language in DEFAULT_LANGUAGE or UPDATED_LANGUAGE
        defaultCountriesShouldBeFound("language.in=" + DEFAULT_LANGUAGE + "," + UPDATED_LANGUAGE);

        // Get all the countriesList where language equals to UPDATED_LANGUAGE
        defaultCountriesShouldNotBeFound("language.in=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllCountriesByLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where language is not null
        defaultCountriesShouldBeFound("language.specified=true");

        // Get all the countriesList where language is null
        defaultCountriesShouldNotBeFound("language.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByLanguageContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where language contains DEFAULT_LANGUAGE
        defaultCountriesShouldBeFound("language.contains=" + DEFAULT_LANGUAGE);

        // Get all the countriesList where language contains UPDATED_LANGUAGE
        defaultCountriesShouldNotBeFound("language.contains=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllCountriesByLanguageNotContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where language does not contain DEFAULT_LANGUAGE
        defaultCountriesShouldNotBeFound("language.doesNotContain=" + DEFAULT_LANGUAGE);

        // Get all the countriesList where language does not contain UPDATED_LANGUAGE
        defaultCountriesShouldBeFound("language.doesNotContain=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllCountriesByLangIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where lang equals to DEFAULT_LANG
        defaultCountriesShouldBeFound("lang.equals=" + DEFAULT_LANG);

        // Get all the countriesList where lang equals to UPDATED_LANG
        defaultCountriesShouldNotBeFound("lang.equals=" + UPDATED_LANG);
    }

    @Test
    @Transactional
    void getAllCountriesByLangIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where lang not equals to DEFAULT_LANG
        defaultCountriesShouldNotBeFound("lang.notEquals=" + DEFAULT_LANG);

        // Get all the countriesList where lang not equals to UPDATED_LANG
        defaultCountriesShouldBeFound("lang.notEquals=" + UPDATED_LANG);
    }

    @Test
    @Transactional
    void getAllCountriesByLangIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where lang in DEFAULT_LANG or UPDATED_LANG
        defaultCountriesShouldBeFound("lang.in=" + DEFAULT_LANG + "," + UPDATED_LANG);

        // Get all the countriesList where lang equals to UPDATED_LANG
        defaultCountriesShouldNotBeFound("lang.in=" + UPDATED_LANG);
    }

    @Test
    @Transactional
    void getAllCountriesByLangIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where lang is not null
        defaultCountriesShouldBeFound("lang.specified=true");

        // Get all the countriesList where lang is null
        defaultCountriesShouldNotBeFound("lang.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByLangContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where lang contains DEFAULT_LANG
        defaultCountriesShouldBeFound("lang.contains=" + DEFAULT_LANG);

        // Get all the countriesList where lang contains UPDATED_LANG
        defaultCountriesShouldNotBeFound("lang.contains=" + UPDATED_LANG);
    }

    @Test
    @Transactional
    void getAllCountriesByLangNotContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where lang does not contain DEFAULT_LANG
        defaultCountriesShouldNotBeFound("lang.doesNotContain=" + DEFAULT_LANG);

        // Get all the countriesList where lang does not contain UPDATED_LANG
        defaultCountriesShouldBeFound("lang.doesNotContain=" + UPDATED_LANG);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCountriesShouldBeFound(String filter) throws Exception {
        restCountriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countries.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].iso").value(hasItem(DEFAULT_ISO)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG)));

        // Check, that the count call also returns 1
        restCountriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCountriesShouldNotBeFound(String filter) throws Exception {
        restCountriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCountriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCountries() throws Exception {
        // Get the countries
        restCountriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();

        // Update the countries
        Countries updatedCountries = countriesRepository.findById(countries.getId()).get();
        // Disconnect from session so that the updates on updatedCountries are not directly saved in db
        em.detach(updatedCountries);
        updatedCountries.name(UPDATED_NAME).iso(UPDATED_ISO).language(UPDATED_LANGUAGE).lang(UPDATED_LANG);

        restCountriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCountries.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCountries))
            )
            .andExpect(status().isOk());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountries.getIso()).isEqualTo(UPDATED_ISO);
        assertThat(testCountries.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testCountries.getLang()).isEqualTo(UPDATED_LANG);
    }

    @Test
    @Transactional
    void putNonExistingCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countries.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countries))
            )
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countries))
            )
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countries)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCountriesWithPatch() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();

        // Update the countries using partial update
        Countries partialUpdatedCountries = new Countries();
        partialUpdatedCountries.setId(countries.getId());

        partialUpdatedCountries.name(UPDATED_NAME).iso(UPDATED_ISO).language(UPDATED_LANGUAGE);

        restCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountries.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCountries))
            )
            .andExpect(status().isOk());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountries.getIso()).isEqualTo(UPDATED_ISO);
        assertThat(testCountries.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testCountries.getLang()).isEqualTo(DEFAULT_LANG);
    }

    @Test
    @Transactional
    void fullUpdateCountriesWithPatch() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();

        // Update the countries using partial update
        Countries partialUpdatedCountries = new Countries();
        partialUpdatedCountries.setId(countries.getId());

        partialUpdatedCountries.name(UPDATED_NAME).iso(UPDATED_ISO).language(UPDATED_LANGUAGE).lang(UPDATED_LANG);

        restCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountries.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCountries))
            )
            .andExpect(status().isOk());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountries.getIso()).isEqualTo(UPDATED_ISO);
        assertThat(testCountries.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testCountries.getLang()).isEqualTo(UPDATED_LANG);
    }

    @Test
    @Transactional
    void patchNonExistingCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, countries.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(countries))
            )
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(countries))
            )
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(countries))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        int databaseSizeBeforeDelete = countriesRepository.findAll().size();

        // Delete the countries
        restCountriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, countries.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
