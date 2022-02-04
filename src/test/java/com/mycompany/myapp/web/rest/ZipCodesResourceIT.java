package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cities;
import com.mycompany.myapp.domain.Countries;
import com.mycompany.myapp.domain.Regions;
import com.mycompany.myapp.domain.ZipCodes;
import com.mycompany.myapp.repository.ZipCodesRepository;
import com.mycompany.myapp.service.criteria.ZipCodesCriteria;
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
 * Integration tests for the {@link ZipCodesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ZipCodesResourceIT {

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_FROM_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FROM_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TO_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TO_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/zip-codes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ZipCodesRepository zipCodesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZipCodesMockMvc;

    private ZipCodes zipCodes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ZipCodes createEntity(EntityManager em) {
        ZipCodes zipCodes = new ZipCodes()
            .street(DEFAULT_STREET)
            .area(DEFAULT_AREA)
            .fromNumber(DEFAULT_FROM_NUMBER)
            .toNumber(DEFAULT_TO_NUMBER);
        return zipCodes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ZipCodes createUpdatedEntity(EntityManager em) {
        ZipCodes zipCodes = new ZipCodes()
            .street(UPDATED_STREET)
            .area(UPDATED_AREA)
            .fromNumber(UPDATED_FROM_NUMBER)
            .toNumber(UPDATED_TO_NUMBER);
        return zipCodes;
    }

    @BeforeEach
    public void initTest() {
        zipCodes = createEntity(em);
    }

    @Test
    @Transactional
    void createZipCodes() throws Exception {
        int databaseSizeBeforeCreate = zipCodesRepository.findAll().size();
        // Create the ZipCodes
        restZipCodesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(zipCodes)))
            .andExpect(status().isCreated());

        // Validate the ZipCodes in the database
        List<ZipCodes> zipCodesList = zipCodesRepository.findAll();
        assertThat(zipCodesList).hasSize(databaseSizeBeforeCreate + 1);
        ZipCodes testZipCodes = zipCodesList.get(zipCodesList.size() - 1);
        assertThat(testZipCodes.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testZipCodes.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testZipCodes.getFromNumber()).isEqualTo(DEFAULT_FROM_NUMBER);
        assertThat(testZipCodes.getToNumber()).isEqualTo(DEFAULT_TO_NUMBER);
    }

    @Test
    @Transactional
    void createZipCodesWithExistingId() throws Exception {
        // Create the ZipCodes with an existing ID
        zipCodes.setId(1L);

        int databaseSizeBeforeCreate = zipCodesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZipCodesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(zipCodes)))
            .andExpect(status().isBadRequest());

        // Validate the ZipCodes in the database
        List<ZipCodes> zipCodesList = zipCodesRepository.findAll();
        assertThat(zipCodesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllZipCodes() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList
        restZipCodesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zipCodes.getId().intValue())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].fromNumber").value(hasItem(DEFAULT_FROM_NUMBER)))
            .andExpect(jsonPath("$.[*].toNumber").value(hasItem(DEFAULT_TO_NUMBER)));
    }

    @Test
    @Transactional
    void getZipCodes() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get the zipCodes
        restZipCodesMockMvc
            .perform(get(ENTITY_API_URL_ID, zipCodes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zipCodes.getId().intValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.fromNumber").value(DEFAULT_FROM_NUMBER))
            .andExpect(jsonPath("$.toNumber").value(DEFAULT_TO_NUMBER));
    }

    @Test
    @Transactional
    void getZipCodesByIdFiltering() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        Long id = zipCodes.getId();

        defaultZipCodesShouldBeFound("id.equals=" + id);
        defaultZipCodesShouldNotBeFound("id.notEquals=" + id);

        defaultZipCodesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultZipCodesShouldNotBeFound("id.greaterThan=" + id);

        defaultZipCodesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultZipCodesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllZipCodesByStreetIsEqualToSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where street equals to DEFAULT_STREET
        defaultZipCodesShouldBeFound("street.equals=" + DEFAULT_STREET);

        // Get all the zipCodesList where street equals to UPDATED_STREET
        defaultZipCodesShouldNotBeFound("street.equals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    void getAllZipCodesByStreetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where street not equals to DEFAULT_STREET
        defaultZipCodesShouldNotBeFound("street.notEquals=" + DEFAULT_STREET);

        // Get all the zipCodesList where street not equals to UPDATED_STREET
        defaultZipCodesShouldBeFound("street.notEquals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    void getAllZipCodesByStreetIsInShouldWork() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where street in DEFAULT_STREET or UPDATED_STREET
        defaultZipCodesShouldBeFound("street.in=" + DEFAULT_STREET + "," + UPDATED_STREET);

        // Get all the zipCodesList where street equals to UPDATED_STREET
        defaultZipCodesShouldNotBeFound("street.in=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    void getAllZipCodesByStreetIsNullOrNotNull() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where street is not null
        defaultZipCodesShouldBeFound("street.specified=true");

        // Get all the zipCodesList where street is null
        defaultZipCodesShouldNotBeFound("street.specified=false");
    }

    @Test
    @Transactional
    void getAllZipCodesByStreetContainsSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where street contains DEFAULT_STREET
        defaultZipCodesShouldBeFound("street.contains=" + DEFAULT_STREET);

        // Get all the zipCodesList where street contains UPDATED_STREET
        defaultZipCodesShouldNotBeFound("street.contains=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    void getAllZipCodesByStreetNotContainsSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where street does not contain DEFAULT_STREET
        defaultZipCodesShouldNotBeFound("street.doesNotContain=" + DEFAULT_STREET);

        // Get all the zipCodesList where street does not contain UPDATED_STREET
        defaultZipCodesShouldBeFound("street.doesNotContain=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    void getAllZipCodesByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where area equals to DEFAULT_AREA
        defaultZipCodesShouldBeFound("area.equals=" + DEFAULT_AREA);

        // Get all the zipCodesList where area equals to UPDATED_AREA
        defaultZipCodesShouldNotBeFound("area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllZipCodesByAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where area not equals to DEFAULT_AREA
        defaultZipCodesShouldNotBeFound("area.notEquals=" + DEFAULT_AREA);

        // Get all the zipCodesList where area not equals to UPDATED_AREA
        defaultZipCodesShouldBeFound("area.notEquals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllZipCodesByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where area in DEFAULT_AREA or UPDATED_AREA
        defaultZipCodesShouldBeFound("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

        // Get all the zipCodesList where area equals to UPDATED_AREA
        defaultZipCodesShouldNotBeFound("area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllZipCodesByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where area is not null
        defaultZipCodesShouldBeFound("area.specified=true");

        // Get all the zipCodesList where area is null
        defaultZipCodesShouldNotBeFound("area.specified=false");
    }

    @Test
    @Transactional
    void getAllZipCodesByAreaContainsSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where area contains DEFAULT_AREA
        defaultZipCodesShouldBeFound("area.contains=" + DEFAULT_AREA);

        // Get all the zipCodesList where area contains UPDATED_AREA
        defaultZipCodesShouldNotBeFound("area.contains=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllZipCodesByAreaNotContainsSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where area does not contain DEFAULT_AREA
        defaultZipCodesShouldNotBeFound("area.doesNotContain=" + DEFAULT_AREA);

        // Get all the zipCodesList where area does not contain UPDATED_AREA
        defaultZipCodesShouldBeFound("area.doesNotContain=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllZipCodesByFromNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where fromNumber equals to DEFAULT_FROM_NUMBER
        defaultZipCodesShouldBeFound("fromNumber.equals=" + DEFAULT_FROM_NUMBER);

        // Get all the zipCodesList where fromNumber equals to UPDATED_FROM_NUMBER
        defaultZipCodesShouldNotBeFound("fromNumber.equals=" + UPDATED_FROM_NUMBER);
    }

    @Test
    @Transactional
    void getAllZipCodesByFromNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where fromNumber not equals to DEFAULT_FROM_NUMBER
        defaultZipCodesShouldNotBeFound("fromNumber.notEquals=" + DEFAULT_FROM_NUMBER);

        // Get all the zipCodesList where fromNumber not equals to UPDATED_FROM_NUMBER
        defaultZipCodesShouldBeFound("fromNumber.notEquals=" + UPDATED_FROM_NUMBER);
    }

    @Test
    @Transactional
    void getAllZipCodesByFromNumberIsInShouldWork() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where fromNumber in DEFAULT_FROM_NUMBER or UPDATED_FROM_NUMBER
        defaultZipCodesShouldBeFound("fromNumber.in=" + DEFAULT_FROM_NUMBER + "," + UPDATED_FROM_NUMBER);

        // Get all the zipCodesList where fromNumber equals to UPDATED_FROM_NUMBER
        defaultZipCodesShouldNotBeFound("fromNumber.in=" + UPDATED_FROM_NUMBER);
    }

    @Test
    @Transactional
    void getAllZipCodesByFromNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where fromNumber is not null
        defaultZipCodesShouldBeFound("fromNumber.specified=true");

        // Get all the zipCodesList where fromNumber is null
        defaultZipCodesShouldNotBeFound("fromNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllZipCodesByFromNumberContainsSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where fromNumber contains DEFAULT_FROM_NUMBER
        defaultZipCodesShouldBeFound("fromNumber.contains=" + DEFAULT_FROM_NUMBER);

        // Get all the zipCodesList where fromNumber contains UPDATED_FROM_NUMBER
        defaultZipCodesShouldNotBeFound("fromNumber.contains=" + UPDATED_FROM_NUMBER);
    }

    @Test
    @Transactional
    void getAllZipCodesByFromNumberNotContainsSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where fromNumber does not contain DEFAULT_FROM_NUMBER
        defaultZipCodesShouldNotBeFound("fromNumber.doesNotContain=" + DEFAULT_FROM_NUMBER);

        // Get all the zipCodesList where fromNumber does not contain UPDATED_FROM_NUMBER
        defaultZipCodesShouldBeFound("fromNumber.doesNotContain=" + UPDATED_FROM_NUMBER);
    }

    @Test
    @Transactional
    void getAllZipCodesByToNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where toNumber equals to DEFAULT_TO_NUMBER
        defaultZipCodesShouldBeFound("toNumber.equals=" + DEFAULT_TO_NUMBER);

        // Get all the zipCodesList where toNumber equals to UPDATED_TO_NUMBER
        defaultZipCodesShouldNotBeFound("toNumber.equals=" + UPDATED_TO_NUMBER);
    }

    @Test
    @Transactional
    void getAllZipCodesByToNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where toNumber not equals to DEFAULT_TO_NUMBER
        defaultZipCodesShouldNotBeFound("toNumber.notEquals=" + DEFAULT_TO_NUMBER);

        // Get all the zipCodesList where toNumber not equals to UPDATED_TO_NUMBER
        defaultZipCodesShouldBeFound("toNumber.notEquals=" + UPDATED_TO_NUMBER);
    }

    @Test
    @Transactional
    void getAllZipCodesByToNumberIsInShouldWork() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where toNumber in DEFAULT_TO_NUMBER or UPDATED_TO_NUMBER
        defaultZipCodesShouldBeFound("toNumber.in=" + DEFAULT_TO_NUMBER + "," + UPDATED_TO_NUMBER);

        // Get all the zipCodesList where toNumber equals to UPDATED_TO_NUMBER
        defaultZipCodesShouldNotBeFound("toNumber.in=" + UPDATED_TO_NUMBER);
    }

    @Test
    @Transactional
    void getAllZipCodesByToNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where toNumber is not null
        defaultZipCodesShouldBeFound("toNumber.specified=true");

        // Get all the zipCodesList where toNumber is null
        defaultZipCodesShouldNotBeFound("toNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllZipCodesByToNumberContainsSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where toNumber contains DEFAULT_TO_NUMBER
        defaultZipCodesShouldBeFound("toNumber.contains=" + DEFAULT_TO_NUMBER);

        // Get all the zipCodesList where toNumber contains UPDATED_TO_NUMBER
        defaultZipCodesShouldNotBeFound("toNumber.contains=" + UPDATED_TO_NUMBER);
    }

    @Test
    @Transactional
    void getAllZipCodesByToNumberNotContainsSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        // Get all the zipCodesList where toNumber does not contain DEFAULT_TO_NUMBER
        defaultZipCodesShouldNotBeFound("toNumber.doesNotContain=" + DEFAULT_TO_NUMBER);

        // Get all the zipCodesList where toNumber does not contain UPDATED_TO_NUMBER
        defaultZipCodesShouldBeFound("toNumber.doesNotContain=" + UPDATED_TO_NUMBER);
    }

    @Test
    @Transactional
    void getAllZipCodesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);
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
        zipCodes.setCountry(country);
        zipCodesRepository.saveAndFlush(zipCodes);
        Long countryId = country.getId();

        // Get all the zipCodesList where country equals to countryId
        defaultZipCodesShouldBeFound("countryId.equals=" + countryId);

        // Get all the zipCodesList where country equals to (countryId + 1)
        defaultZipCodesShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    @Test
    @Transactional
    void getAllZipCodesByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);
        Regions region;
        if (TestUtil.findAll(em, Regions.class).isEmpty()) {
            region = RegionsResourceIT.createEntity(em);
            em.persist(region);
            em.flush();
        } else {
            region = TestUtil.findAll(em, Regions.class).get(0);
        }
        em.persist(region);
        em.flush();
        zipCodes.setRegion(region);
        zipCodesRepository.saveAndFlush(zipCodes);
        Long regionId = region.getId();

        // Get all the zipCodesList where region equals to regionId
        defaultZipCodesShouldBeFound("regionId.equals=" + regionId);

        // Get all the zipCodesList where region equals to (regionId + 1)
        defaultZipCodesShouldNotBeFound("regionId.equals=" + (regionId + 1));
    }

    @Test
    @Transactional
    void getAllZipCodesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);
        Cities city;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            city = CitiesResourceIT.createEntity(em);
            em.persist(city);
            em.flush();
        } else {
            city = TestUtil.findAll(em, Cities.class).get(0);
        }
        em.persist(city);
        em.flush();
        zipCodes.setCity(city);
        zipCodesRepository.saveAndFlush(zipCodes);
        Long cityId = city.getId();

        // Get all the zipCodesList where city equals to cityId
        defaultZipCodesShouldBeFound("cityId.equals=" + cityId);

        // Get all the zipCodesList where city equals to (cityId + 1)
        defaultZipCodesShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultZipCodesShouldBeFound(String filter) throws Exception {
        restZipCodesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zipCodes.getId().intValue())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].fromNumber").value(hasItem(DEFAULT_FROM_NUMBER)))
            .andExpect(jsonPath("$.[*].toNumber").value(hasItem(DEFAULT_TO_NUMBER)));

        // Check, that the count call also returns 1
        restZipCodesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultZipCodesShouldNotBeFound(String filter) throws Exception {
        restZipCodesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restZipCodesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingZipCodes() throws Exception {
        // Get the zipCodes
        restZipCodesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewZipCodes() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        int databaseSizeBeforeUpdate = zipCodesRepository.findAll().size();

        // Update the zipCodes
        ZipCodes updatedZipCodes = zipCodesRepository.findById(zipCodes.getId()).get();
        // Disconnect from session so that the updates on updatedZipCodes are not directly saved in db
        em.detach(updatedZipCodes);
        updatedZipCodes.street(UPDATED_STREET).area(UPDATED_AREA).fromNumber(UPDATED_FROM_NUMBER).toNumber(UPDATED_TO_NUMBER);

        restZipCodesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedZipCodes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedZipCodes))
            )
            .andExpect(status().isOk());

        // Validate the ZipCodes in the database
        List<ZipCodes> zipCodesList = zipCodesRepository.findAll();
        assertThat(zipCodesList).hasSize(databaseSizeBeforeUpdate);
        ZipCodes testZipCodes = zipCodesList.get(zipCodesList.size() - 1);
        assertThat(testZipCodes.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testZipCodes.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testZipCodes.getFromNumber()).isEqualTo(UPDATED_FROM_NUMBER);
        assertThat(testZipCodes.getToNumber()).isEqualTo(UPDATED_TO_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingZipCodes() throws Exception {
        int databaseSizeBeforeUpdate = zipCodesRepository.findAll().size();
        zipCodes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZipCodesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, zipCodes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zipCodes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZipCodes in the database
        List<ZipCodes> zipCodesList = zipCodesRepository.findAll();
        assertThat(zipCodesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchZipCodes() throws Exception {
        int databaseSizeBeforeUpdate = zipCodesRepository.findAll().size();
        zipCodes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZipCodesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(zipCodes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZipCodes in the database
        List<ZipCodes> zipCodesList = zipCodesRepository.findAll();
        assertThat(zipCodesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamZipCodes() throws Exception {
        int databaseSizeBeforeUpdate = zipCodesRepository.findAll().size();
        zipCodes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZipCodesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(zipCodes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ZipCodes in the database
        List<ZipCodes> zipCodesList = zipCodesRepository.findAll();
        assertThat(zipCodesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateZipCodesWithPatch() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        int databaseSizeBeforeUpdate = zipCodesRepository.findAll().size();

        // Update the zipCodes using partial update
        ZipCodes partialUpdatedZipCodes = new ZipCodes();
        partialUpdatedZipCodes.setId(zipCodes.getId());

        partialUpdatedZipCodes.street(UPDATED_STREET).area(UPDATED_AREA).fromNumber(UPDATED_FROM_NUMBER).toNumber(UPDATED_TO_NUMBER);

        restZipCodesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZipCodes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedZipCodes))
            )
            .andExpect(status().isOk());

        // Validate the ZipCodes in the database
        List<ZipCodes> zipCodesList = zipCodesRepository.findAll();
        assertThat(zipCodesList).hasSize(databaseSizeBeforeUpdate);
        ZipCodes testZipCodes = zipCodesList.get(zipCodesList.size() - 1);
        assertThat(testZipCodes.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testZipCodes.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testZipCodes.getFromNumber()).isEqualTo(UPDATED_FROM_NUMBER);
        assertThat(testZipCodes.getToNumber()).isEqualTo(UPDATED_TO_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateZipCodesWithPatch() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        int databaseSizeBeforeUpdate = zipCodesRepository.findAll().size();

        // Update the zipCodes using partial update
        ZipCodes partialUpdatedZipCodes = new ZipCodes();
        partialUpdatedZipCodes.setId(zipCodes.getId());

        partialUpdatedZipCodes.street(UPDATED_STREET).area(UPDATED_AREA).fromNumber(UPDATED_FROM_NUMBER).toNumber(UPDATED_TO_NUMBER);

        restZipCodesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZipCodes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedZipCodes))
            )
            .andExpect(status().isOk());

        // Validate the ZipCodes in the database
        List<ZipCodes> zipCodesList = zipCodesRepository.findAll();
        assertThat(zipCodesList).hasSize(databaseSizeBeforeUpdate);
        ZipCodes testZipCodes = zipCodesList.get(zipCodesList.size() - 1);
        assertThat(testZipCodes.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testZipCodes.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testZipCodes.getFromNumber()).isEqualTo(UPDATED_FROM_NUMBER);
        assertThat(testZipCodes.getToNumber()).isEqualTo(UPDATED_TO_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingZipCodes() throws Exception {
        int databaseSizeBeforeUpdate = zipCodesRepository.findAll().size();
        zipCodes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZipCodesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, zipCodes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(zipCodes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZipCodes in the database
        List<ZipCodes> zipCodesList = zipCodesRepository.findAll();
        assertThat(zipCodesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchZipCodes() throws Exception {
        int databaseSizeBeforeUpdate = zipCodesRepository.findAll().size();
        zipCodes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZipCodesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(zipCodes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZipCodes in the database
        List<ZipCodes> zipCodesList = zipCodesRepository.findAll();
        assertThat(zipCodesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamZipCodes() throws Exception {
        int databaseSizeBeforeUpdate = zipCodesRepository.findAll().size();
        zipCodes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZipCodesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(zipCodes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ZipCodes in the database
        List<ZipCodes> zipCodesList = zipCodesRepository.findAll();
        assertThat(zipCodesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteZipCodes() throws Exception {
        // Initialize the database
        zipCodesRepository.saveAndFlush(zipCodes);

        int databaseSizeBeforeDelete = zipCodesRepository.findAll().size();

        // Delete the zipCodes
        restZipCodesMockMvc
            .perform(delete(ENTITY_API_URL_ID, zipCodes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ZipCodes> zipCodesList = zipCodesRepository.findAll();
        assertThat(zipCodesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
