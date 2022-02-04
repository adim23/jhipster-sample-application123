package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cities;
import com.mycompany.myapp.domain.Countries;
import com.mycompany.myapp.domain.Regions;
import com.mycompany.myapp.repository.CitiesRepository;
import com.mycompany.myapp.service.criteria.CitiesCriteria;
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
 * Integration tests for the {@link CitiesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CitiesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRESIDENT = "AAAAAAAAAA";
    private static final String UPDATED_PRESIDENT = "BBBBBBBBBB";

    private static final String DEFAULT_PRESIDENTS_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PRESIDENTS_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_SECRETARY = "AAAAAAAAAA";
    private static final String UPDATED_SECRETARY = "BBBBBBBBBB";

    private static final String DEFAULT_SECRETARYS_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_SECRETARYS_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_POLICE = "AAAAAAAAAA";
    private static final String UPDATED_POLICE = "BBBBBBBBBB";

    private static final String DEFAULT_POLICES_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_POLICES_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCTOR = "AAAAAAAAAA";
    private static final String UPDATED_DOCTOR = "BBBBBBBBBB";

    private static final String DEFAULT_DOCTORS_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_DOCTORS_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_TEACHER = "AAAAAAAAAA";
    private static final String UPDATED_TEACHER = "BBBBBBBBBB";

    private static final String DEFAULT_TEACHERS_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_TEACHERS_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIEST = "AAAAAAAAAA";
    private static final String UPDATED_PRIEST = "BBBBBBBBBB";

    private static final String DEFAULT_PRIESTS_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PRIESTS_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitiesMockMvc;

    private Cities cities;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cities createEntity(EntityManager em) {
        Cities cities = new Cities()
            .name(DEFAULT_NAME)
            .president(DEFAULT_PRESIDENT)
            .presidentsPhone(DEFAULT_PRESIDENTS_PHONE)
            .secretary(DEFAULT_SECRETARY)
            .secretarysPhone(DEFAULT_SECRETARYS_PHONE)
            .police(DEFAULT_POLICE)
            .policesPhone(DEFAULT_POLICES_PHONE)
            .doctor(DEFAULT_DOCTOR)
            .doctorsPhone(DEFAULT_DOCTORS_PHONE)
            .teacher(DEFAULT_TEACHER)
            .teachersPhone(DEFAULT_TEACHERS_PHONE)
            .priest(DEFAULT_PRIEST)
            .priestsPhone(DEFAULT_PRIESTS_PHONE);
        // Add required entity
        Countries countries;
        if (TestUtil.findAll(em, Countries.class).isEmpty()) {
            countries = CountriesResourceIT.createEntity(em);
            em.persist(countries);
            em.flush();
        } else {
            countries = TestUtil.findAll(em, Countries.class).get(0);
        }
        cities.setCountry(countries);
        // Add required entity
        Regions regions;
        if (TestUtil.findAll(em, Regions.class).isEmpty()) {
            regions = RegionsResourceIT.createEntity(em);
            em.persist(regions);
            em.flush();
        } else {
            regions = TestUtil.findAll(em, Regions.class).get(0);
        }
        cities.setRegion(regions);
        return cities;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cities createUpdatedEntity(EntityManager em) {
        Cities cities = new Cities()
            .name(UPDATED_NAME)
            .president(UPDATED_PRESIDENT)
            .presidentsPhone(UPDATED_PRESIDENTS_PHONE)
            .secretary(UPDATED_SECRETARY)
            .secretarysPhone(UPDATED_SECRETARYS_PHONE)
            .police(UPDATED_POLICE)
            .policesPhone(UPDATED_POLICES_PHONE)
            .doctor(UPDATED_DOCTOR)
            .doctorsPhone(UPDATED_DOCTORS_PHONE)
            .teacher(UPDATED_TEACHER)
            .teachersPhone(UPDATED_TEACHERS_PHONE)
            .priest(UPDATED_PRIEST)
            .priestsPhone(UPDATED_PRIESTS_PHONE);
        // Add required entity
        Countries countries;
        if (TestUtil.findAll(em, Countries.class).isEmpty()) {
            countries = CountriesResourceIT.createUpdatedEntity(em);
            em.persist(countries);
            em.flush();
        } else {
            countries = TestUtil.findAll(em, Countries.class).get(0);
        }
        cities.setCountry(countries);
        // Add required entity
        Regions regions;
        if (TestUtil.findAll(em, Regions.class).isEmpty()) {
            regions = RegionsResourceIT.createUpdatedEntity(em);
            em.persist(regions);
            em.flush();
        } else {
            regions = TestUtil.findAll(em, Regions.class).get(0);
        }
        cities.setRegion(regions);
        return cities;
    }

    @BeforeEach
    public void initTest() {
        cities = createEntity(em);
    }

    @Test
    @Transactional
    void createCities() throws Exception {
        int databaseSizeBeforeCreate = citiesRepository.findAll().size();
        // Create the Cities
        restCitiesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cities)))
            .andExpect(status().isCreated());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeCreate + 1);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCities.getPresident()).isEqualTo(DEFAULT_PRESIDENT);
        assertThat(testCities.getPresidentsPhone()).isEqualTo(DEFAULT_PRESIDENTS_PHONE);
        assertThat(testCities.getSecretary()).isEqualTo(DEFAULT_SECRETARY);
        assertThat(testCities.getSecretarysPhone()).isEqualTo(DEFAULT_SECRETARYS_PHONE);
        assertThat(testCities.getPolice()).isEqualTo(DEFAULT_POLICE);
        assertThat(testCities.getPolicesPhone()).isEqualTo(DEFAULT_POLICES_PHONE);
        assertThat(testCities.getDoctor()).isEqualTo(DEFAULT_DOCTOR);
        assertThat(testCities.getDoctorsPhone()).isEqualTo(DEFAULT_DOCTORS_PHONE);
        assertThat(testCities.getTeacher()).isEqualTo(DEFAULT_TEACHER);
        assertThat(testCities.getTeachersPhone()).isEqualTo(DEFAULT_TEACHERS_PHONE);
        assertThat(testCities.getPriest()).isEqualTo(DEFAULT_PRIEST);
        assertThat(testCities.getPriestsPhone()).isEqualTo(DEFAULT_PRIESTS_PHONE);
    }

    @Test
    @Transactional
    void createCitiesWithExistingId() throws Exception {
        // Create the Cities with an existing ID
        cities.setId(1L);

        int databaseSizeBeforeCreate = citiesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitiesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cities)))
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = citiesRepository.findAll().size();
        // set the field null
        cities.setName(null);

        // Create the Cities, which fails.

        restCitiesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cities)))
            .andExpect(status().isBadRequest());

        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cities.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].president").value(hasItem(DEFAULT_PRESIDENT)))
            .andExpect(jsonPath("$.[*].presidentsPhone").value(hasItem(DEFAULT_PRESIDENTS_PHONE)))
            .andExpect(jsonPath("$.[*].secretary").value(hasItem(DEFAULT_SECRETARY)))
            .andExpect(jsonPath("$.[*].secretarysPhone").value(hasItem(DEFAULT_SECRETARYS_PHONE)))
            .andExpect(jsonPath("$.[*].police").value(hasItem(DEFAULT_POLICE)))
            .andExpect(jsonPath("$.[*].policesPhone").value(hasItem(DEFAULT_POLICES_PHONE)))
            .andExpect(jsonPath("$.[*].doctor").value(hasItem(DEFAULT_DOCTOR)))
            .andExpect(jsonPath("$.[*].doctorsPhone").value(hasItem(DEFAULT_DOCTORS_PHONE)))
            .andExpect(jsonPath("$.[*].teacher").value(hasItem(DEFAULT_TEACHER)))
            .andExpect(jsonPath("$.[*].teachersPhone").value(hasItem(DEFAULT_TEACHERS_PHONE)))
            .andExpect(jsonPath("$.[*].priest").value(hasItem(DEFAULT_PRIEST)))
            .andExpect(jsonPath("$.[*].priestsPhone").value(hasItem(DEFAULT_PRIESTS_PHONE)));
    }

    @Test
    @Transactional
    void getCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get the cities
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL_ID, cities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cities.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.president").value(DEFAULT_PRESIDENT))
            .andExpect(jsonPath("$.presidentsPhone").value(DEFAULT_PRESIDENTS_PHONE))
            .andExpect(jsonPath("$.secretary").value(DEFAULT_SECRETARY))
            .andExpect(jsonPath("$.secretarysPhone").value(DEFAULT_SECRETARYS_PHONE))
            .andExpect(jsonPath("$.police").value(DEFAULT_POLICE))
            .andExpect(jsonPath("$.policesPhone").value(DEFAULT_POLICES_PHONE))
            .andExpect(jsonPath("$.doctor").value(DEFAULT_DOCTOR))
            .andExpect(jsonPath("$.doctorsPhone").value(DEFAULT_DOCTORS_PHONE))
            .andExpect(jsonPath("$.teacher").value(DEFAULT_TEACHER))
            .andExpect(jsonPath("$.teachersPhone").value(DEFAULT_TEACHERS_PHONE))
            .andExpect(jsonPath("$.priest").value(DEFAULT_PRIEST))
            .andExpect(jsonPath("$.priestsPhone").value(DEFAULT_PRIESTS_PHONE));
    }

    @Test
    @Transactional
    void getCitiesByIdFiltering() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        Long id = cities.getId();

        defaultCitiesShouldBeFound("id.equals=" + id);
        defaultCitiesShouldNotBeFound("id.notEquals=" + id);

        defaultCitiesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCitiesShouldNotBeFound("id.greaterThan=" + id);

        defaultCitiesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCitiesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCitiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where name equals to DEFAULT_NAME
        defaultCitiesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the citiesList where name equals to UPDATED_NAME
        defaultCitiesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitiesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where name not equals to DEFAULT_NAME
        defaultCitiesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the citiesList where name not equals to UPDATED_NAME
        defaultCitiesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCitiesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the citiesList where name equals to UPDATED_NAME
        defaultCitiesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where name is not null
        defaultCitiesShouldBeFound("name.specified=true");

        // Get all the citiesList where name is null
        defaultCitiesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByNameContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where name contains DEFAULT_NAME
        defaultCitiesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the citiesList where name contains UPDATED_NAME
        defaultCitiesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitiesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where name does not contain DEFAULT_NAME
        defaultCitiesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the citiesList where name does not contain UPDATED_NAME
        defaultCitiesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitiesByPresidentIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where president equals to DEFAULT_PRESIDENT
        defaultCitiesShouldBeFound("president.equals=" + DEFAULT_PRESIDENT);

        // Get all the citiesList where president equals to UPDATED_PRESIDENT
        defaultCitiesShouldNotBeFound("president.equals=" + UPDATED_PRESIDENT);
    }

    @Test
    @Transactional
    void getAllCitiesByPresidentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where president not equals to DEFAULT_PRESIDENT
        defaultCitiesShouldNotBeFound("president.notEquals=" + DEFAULT_PRESIDENT);

        // Get all the citiesList where president not equals to UPDATED_PRESIDENT
        defaultCitiesShouldBeFound("president.notEquals=" + UPDATED_PRESIDENT);
    }

    @Test
    @Transactional
    void getAllCitiesByPresidentIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where president in DEFAULT_PRESIDENT or UPDATED_PRESIDENT
        defaultCitiesShouldBeFound("president.in=" + DEFAULT_PRESIDENT + "," + UPDATED_PRESIDENT);

        // Get all the citiesList where president equals to UPDATED_PRESIDENT
        defaultCitiesShouldNotBeFound("president.in=" + UPDATED_PRESIDENT);
    }

    @Test
    @Transactional
    void getAllCitiesByPresidentIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where president is not null
        defaultCitiesShouldBeFound("president.specified=true");

        // Get all the citiesList where president is null
        defaultCitiesShouldNotBeFound("president.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByPresidentContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where president contains DEFAULT_PRESIDENT
        defaultCitiesShouldBeFound("president.contains=" + DEFAULT_PRESIDENT);

        // Get all the citiesList where president contains UPDATED_PRESIDENT
        defaultCitiesShouldNotBeFound("president.contains=" + UPDATED_PRESIDENT);
    }

    @Test
    @Transactional
    void getAllCitiesByPresidentNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where president does not contain DEFAULT_PRESIDENT
        defaultCitiesShouldNotBeFound("president.doesNotContain=" + DEFAULT_PRESIDENT);

        // Get all the citiesList where president does not contain UPDATED_PRESIDENT
        defaultCitiesShouldBeFound("president.doesNotContain=" + UPDATED_PRESIDENT);
    }

    @Test
    @Transactional
    void getAllCitiesByPresidentsPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where presidentsPhone equals to DEFAULT_PRESIDENTS_PHONE
        defaultCitiesShouldBeFound("presidentsPhone.equals=" + DEFAULT_PRESIDENTS_PHONE);

        // Get all the citiesList where presidentsPhone equals to UPDATED_PRESIDENTS_PHONE
        defaultCitiesShouldNotBeFound("presidentsPhone.equals=" + UPDATED_PRESIDENTS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByPresidentsPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where presidentsPhone not equals to DEFAULT_PRESIDENTS_PHONE
        defaultCitiesShouldNotBeFound("presidentsPhone.notEquals=" + DEFAULT_PRESIDENTS_PHONE);

        // Get all the citiesList where presidentsPhone not equals to UPDATED_PRESIDENTS_PHONE
        defaultCitiesShouldBeFound("presidentsPhone.notEquals=" + UPDATED_PRESIDENTS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByPresidentsPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where presidentsPhone in DEFAULT_PRESIDENTS_PHONE or UPDATED_PRESIDENTS_PHONE
        defaultCitiesShouldBeFound("presidentsPhone.in=" + DEFAULT_PRESIDENTS_PHONE + "," + UPDATED_PRESIDENTS_PHONE);

        // Get all the citiesList where presidentsPhone equals to UPDATED_PRESIDENTS_PHONE
        defaultCitiesShouldNotBeFound("presidentsPhone.in=" + UPDATED_PRESIDENTS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByPresidentsPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where presidentsPhone is not null
        defaultCitiesShouldBeFound("presidentsPhone.specified=true");

        // Get all the citiesList where presidentsPhone is null
        defaultCitiesShouldNotBeFound("presidentsPhone.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByPresidentsPhoneContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where presidentsPhone contains DEFAULT_PRESIDENTS_PHONE
        defaultCitiesShouldBeFound("presidentsPhone.contains=" + DEFAULT_PRESIDENTS_PHONE);

        // Get all the citiesList where presidentsPhone contains UPDATED_PRESIDENTS_PHONE
        defaultCitiesShouldNotBeFound("presidentsPhone.contains=" + UPDATED_PRESIDENTS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByPresidentsPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where presidentsPhone does not contain DEFAULT_PRESIDENTS_PHONE
        defaultCitiesShouldNotBeFound("presidentsPhone.doesNotContain=" + DEFAULT_PRESIDENTS_PHONE);

        // Get all the citiesList where presidentsPhone does not contain UPDATED_PRESIDENTS_PHONE
        defaultCitiesShouldBeFound("presidentsPhone.doesNotContain=" + UPDATED_PRESIDENTS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesBySecretaryIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where secretary equals to DEFAULT_SECRETARY
        defaultCitiesShouldBeFound("secretary.equals=" + DEFAULT_SECRETARY);

        // Get all the citiesList where secretary equals to UPDATED_SECRETARY
        defaultCitiesShouldNotBeFound("secretary.equals=" + UPDATED_SECRETARY);
    }

    @Test
    @Transactional
    void getAllCitiesBySecretaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where secretary not equals to DEFAULT_SECRETARY
        defaultCitiesShouldNotBeFound("secretary.notEquals=" + DEFAULT_SECRETARY);

        // Get all the citiesList where secretary not equals to UPDATED_SECRETARY
        defaultCitiesShouldBeFound("secretary.notEquals=" + UPDATED_SECRETARY);
    }

    @Test
    @Transactional
    void getAllCitiesBySecretaryIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where secretary in DEFAULT_SECRETARY or UPDATED_SECRETARY
        defaultCitiesShouldBeFound("secretary.in=" + DEFAULT_SECRETARY + "," + UPDATED_SECRETARY);

        // Get all the citiesList where secretary equals to UPDATED_SECRETARY
        defaultCitiesShouldNotBeFound("secretary.in=" + UPDATED_SECRETARY);
    }

    @Test
    @Transactional
    void getAllCitiesBySecretaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where secretary is not null
        defaultCitiesShouldBeFound("secretary.specified=true");

        // Get all the citiesList where secretary is null
        defaultCitiesShouldNotBeFound("secretary.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesBySecretaryContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where secretary contains DEFAULT_SECRETARY
        defaultCitiesShouldBeFound("secretary.contains=" + DEFAULT_SECRETARY);

        // Get all the citiesList where secretary contains UPDATED_SECRETARY
        defaultCitiesShouldNotBeFound("secretary.contains=" + UPDATED_SECRETARY);
    }

    @Test
    @Transactional
    void getAllCitiesBySecretaryNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where secretary does not contain DEFAULT_SECRETARY
        defaultCitiesShouldNotBeFound("secretary.doesNotContain=" + DEFAULT_SECRETARY);

        // Get all the citiesList where secretary does not contain UPDATED_SECRETARY
        defaultCitiesShouldBeFound("secretary.doesNotContain=" + UPDATED_SECRETARY);
    }

    @Test
    @Transactional
    void getAllCitiesBySecretarysPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where secretarysPhone equals to DEFAULT_SECRETARYS_PHONE
        defaultCitiesShouldBeFound("secretarysPhone.equals=" + DEFAULT_SECRETARYS_PHONE);

        // Get all the citiesList where secretarysPhone equals to UPDATED_SECRETARYS_PHONE
        defaultCitiesShouldNotBeFound("secretarysPhone.equals=" + UPDATED_SECRETARYS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesBySecretarysPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where secretarysPhone not equals to DEFAULT_SECRETARYS_PHONE
        defaultCitiesShouldNotBeFound("secretarysPhone.notEquals=" + DEFAULT_SECRETARYS_PHONE);

        // Get all the citiesList where secretarysPhone not equals to UPDATED_SECRETARYS_PHONE
        defaultCitiesShouldBeFound("secretarysPhone.notEquals=" + UPDATED_SECRETARYS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesBySecretarysPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where secretarysPhone in DEFAULT_SECRETARYS_PHONE or UPDATED_SECRETARYS_PHONE
        defaultCitiesShouldBeFound("secretarysPhone.in=" + DEFAULT_SECRETARYS_PHONE + "," + UPDATED_SECRETARYS_PHONE);

        // Get all the citiesList where secretarysPhone equals to UPDATED_SECRETARYS_PHONE
        defaultCitiesShouldNotBeFound("secretarysPhone.in=" + UPDATED_SECRETARYS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesBySecretarysPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where secretarysPhone is not null
        defaultCitiesShouldBeFound("secretarysPhone.specified=true");

        // Get all the citiesList where secretarysPhone is null
        defaultCitiesShouldNotBeFound("secretarysPhone.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesBySecretarysPhoneContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where secretarysPhone contains DEFAULT_SECRETARYS_PHONE
        defaultCitiesShouldBeFound("secretarysPhone.contains=" + DEFAULT_SECRETARYS_PHONE);

        // Get all the citiesList where secretarysPhone contains UPDATED_SECRETARYS_PHONE
        defaultCitiesShouldNotBeFound("secretarysPhone.contains=" + UPDATED_SECRETARYS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesBySecretarysPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where secretarysPhone does not contain DEFAULT_SECRETARYS_PHONE
        defaultCitiesShouldNotBeFound("secretarysPhone.doesNotContain=" + DEFAULT_SECRETARYS_PHONE);

        // Get all the citiesList where secretarysPhone does not contain UPDATED_SECRETARYS_PHONE
        defaultCitiesShouldBeFound("secretarysPhone.doesNotContain=" + UPDATED_SECRETARYS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByPoliceIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where police equals to DEFAULT_POLICE
        defaultCitiesShouldBeFound("police.equals=" + DEFAULT_POLICE);

        // Get all the citiesList where police equals to UPDATED_POLICE
        defaultCitiesShouldNotBeFound("police.equals=" + UPDATED_POLICE);
    }

    @Test
    @Transactional
    void getAllCitiesByPoliceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where police not equals to DEFAULT_POLICE
        defaultCitiesShouldNotBeFound("police.notEquals=" + DEFAULT_POLICE);

        // Get all the citiesList where police not equals to UPDATED_POLICE
        defaultCitiesShouldBeFound("police.notEquals=" + UPDATED_POLICE);
    }

    @Test
    @Transactional
    void getAllCitiesByPoliceIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where police in DEFAULT_POLICE or UPDATED_POLICE
        defaultCitiesShouldBeFound("police.in=" + DEFAULT_POLICE + "," + UPDATED_POLICE);

        // Get all the citiesList where police equals to UPDATED_POLICE
        defaultCitiesShouldNotBeFound("police.in=" + UPDATED_POLICE);
    }

    @Test
    @Transactional
    void getAllCitiesByPoliceIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where police is not null
        defaultCitiesShouldBeFound("police.specified=true");

        // Get all the citiesList where police is null
        defaultCitiesShouldNotBeFound("police.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByPoliceContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where police contains DEFAULT_POLICE
        defaultCitiesShouldBeFound("police.contains=" + DEFAULT_POLICE);

        // Get all the citiesList where police contains UPDATED_POLICE
        defaultCitiesShouldNotBeFound("police.contains=" + UPDATED_POLICE);
    }

    @Test
    @Transactional
    void getAllCitiesByPoliceNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where police does not contain DEFAULT_POLICE
        defaultCitiesShouldNotBeFound("police.doesNotContain=" + DEFAULT_POLICE);

        // Get all the citiesList where police does not contain UPDATED_POLICE
        defaultCitiesShouldBeFound("police.doesNotContain=" + UPDATED_POLICE);
    }

    @Test
    @Transactional
    void getAllCitiesByPolicesPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where policesPhone equals to DEFAULT_POLICES_PHONE
        defaultCitiesShouldBeFound("policesPhone.equals=" + DEFAULT_POLICES_PHONE);

        // Get all the citiesList where policesPhone equals to UPDATED_POLICES_PHONE
        defaultCitiesShouldNotBeFound("policesPhone.equals=" + UPDATED_POLICES_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByPolicesPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where policesPhone not equals to DEFAULT_POLICES_PHONE
        defaultCitiesShouldNotBeFound("policesPhone.notEquals=" + DEFAULT_POLICES_PHONE);

        // Get all the citiesList where policesPhone not equals to UPDATED_POLICES_PHONE
        defaultCitiesShouldBeFound("policesPhone.notEquals=" + UPDATED_POLICES_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByPolicesPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where policesPhone in DEFAULT_POLICES_PHONE or UPDATED_POLICES_PHONE
        defaultCitiesShouldBeFound("policesPhone.in=" + DEFAULT_POLICES_PHONE + "," + UPDATED_POLICES_PHONE);

        // Get all the citiesList where policesPhone equals to UPDATED_POLICES_PHONE
        defaultCitiesShouldNotBeFound("policesPhone.in=" + UPDATED_POLICES_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByPolicesPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where policesPhone is not null
        defaultCitiesShouldBeFound("policesPhone.specified=true");

        // Get all the citiesList where policesPhone is null
        defaultCitiesShouldNotBeFound("policesPhone.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByPolicesPhoneContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where policesPhone contains DEFAULT_POLICES_PHONE
        defaultCitiesShouldBeFound("policesPhone.contains=" + DEFAULT_POLICES_PHONE);

        // Get all the citiesList where policesPhone contains UPDATED_POLICES_PHONE
        defaultCitiesShouldNotBeFound("policesPhone.contains=" + UPDATED_POLICES_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByPolicesPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where policesPhone does not contain DEFAULT_POLICES_PHONE
        defaultCitiesShouldNotBeFound("policesPhone.doesNotContain=" + DEFAULT_POLICES_PHONE);

        // Get all the citiesList where policesPhone does not contain UPDATED_POLICES_PHONE
        defaultCitiesShouldBeFound("policesPhone.doesNotContain=" + UPDATED_POLICES_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByDoctorIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where doctor equals to DEFAULT_DOCTOR
        defaultCitiesShouldBeFound("doctor.equals=" + DEFAULT_DOCTOR);

        // Get all the citiesList where doctor equals to UPDATED_DOCTOR
        defaultCitiesShouldNotBeFound("doctor.equals=" + UPDATED_DOCTOR);
    }

    @Test
    @Transactional
    void getAllCitiesByDoctorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where doctor not equals to DEFAULT_DOCTOR
        defaultCitiesShouldNotBeFound("doctor.notEquals=" + DEFAULT_DOCTOR);

        // Get all the citiesList where doctor not equals to UPDATED_DOCTOR
        defaultCitiesShouldBeFound("doctor.notEquals=" + UPDATED_DOCTOR);
    }

    @Test
    @Transactional
    void getAllCitiesByDoctorIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where doctor in DEFAULT_DOCTOR or UPDATED_DOCTOR
        defaultCitiesShouldBeFound("doctor.in=" + DEFAULT_DOCTOR + "," + UPDATED_DOCTOR);

        // Get all the citiesList where doctor equals to UPDATED_DOCTOR
        defaultCitiesShouldNotBeFound("doctor.in=" + UPDATED_DOCTOR);
    }

    @Test
    @Transactional
    void getAllCitiesByDoctorIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where doctor is not null
        defaultCitiesShouldBeFound("doctor.specified=true");

        // Get all the citiesList where doctor is null
        defaultCitiesShouldNotBeFound("doctor.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByDoctorContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where doctor contains DEFAULT_DOCTOR
        defaultCitiesShouldBeFound("doctor.contains=" + DEFAULT_DOCTOR);

        // Get all the citiesList where doctor contains UPDATED_DOCTOR
        defaultCitiesShouldNotBeFound("doctor.contains=" + UPDATED_DOCTOR);
    }

    @Test
    @Transactional
    void getAllCitiesByDoctorNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where doctor does not contain DEFAULT_DOCTOR
        defaultCitiesShouldNotBeFound("doctor.doesNotContain=" + DEFAULT_DOCTOR);

        // Get all the citiesList where doctor does not contain UPDATED_DOCTOR
        defaultCitiesShouldBeFound("doctor.doesNotContain=" + UPDATED_DOCTOR);
    }

    @Test
    @Transactional
    void getAllCitiesByDoctorsPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where doctorsPhone equals to DEFAULT_DOCTORS_PHONE
        defaultCitiesShouldBeFound("doctorsPhone.equals=" + DEFAULT_DOCTORS_PHONE);

        // Get all the citiesList where doctorsPhone equals to UPDATED_DOCTORS_PHONE
        defaultCitiesShouldNotBeFound("doctorsPhone.equals=" + UPDATED_DOCTORS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByDoctorsPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where doctorsPhone not equals to DEFAULT_DOCTORS_PHONE
        defaultCitiesShouldNotBeFound("doctorsPhone.notEquals=" + DEFAULT_DOCTORS_PHONE);

        // Get all the citiesList where doctorsPhone not equals to UPDATED_DOCTORS_PHONE
        defaultCitiesShouldBeFound("doctorsPhone.notEquals=" + UPDATED_DOCTORS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByDoctorsPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where doctorsPhone in DEFAULT_DOCTORS_PHONE or UPDATED_DOCTORS_PHONE
        defaultCitiesShouldBeFound("doctorsPhone.in=" + DEFAULT_DOCTORS_PHONE + "," + UPDATED_DOCTORS_PHONE);

        // Get all the citiesList where doctorsPhone equals to UPDATED_DOCTORS_PHONE
        defaultCitiesShouldNotBeFound("doctorsPhone.in=" + UPDATED_DOCTORS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByDoctorsPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where doctorsPhone is not null
        defaultCitiesShouldBeFound("doctorsPhone.specified=true");

        // Get all the citiesList where doctorsPhone is null
        defaultCitiesShouldNotBeFound("doctorsPhone.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByDoctorsPhoneContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where doctorsPhone contains DEFAULT_DOCTORS_PHONE
        defaultCitiesShouldBeFound("doctorsPhone.contains=" + DEFAULT_DOCTORS_PHONE);

        // Get all the citiesList where doctorsPhone contains UPDATED_DOCTORS_PHONE
        defaultCitiesShouldNotBeFound("doctorsPhone.contains=" + UPDATED_DOCTORS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByDoctorsPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where doctorsPhone does not contain DEFAULT_DOCTORS_PHONE
        defaultCitiesShouldNotBeFound("doctorsPhone.doesNotContain=" + DEFAULT_DOCTORS_PHONE);

        // Get all the citiesList where doctorsPhone does not contain UPDATED_DOCTORS_PHONE
        defaultCitiesShouldBeFound("doctorsPhone.doesNotContain=" + UPDATED_DOCTORS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByTeacherIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where teacher equals to DEFAULT_TEACHER
        defaultCitiesShouldBeFound("teacher.equals=" + DEFAULT_TEACHER);

        // Get all the citiesList where teacher equals to UPDATED_TEACHER
        defaultCitiesShouldNotBeFound("teacher.equals=" + UPDATED_TEACHER);
    }

    @Test
    @Transactional
    void getAllCitiesByTeacherIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where teacher not equals to DEFAULT_TEACHER
        defaultCitiesShouldNotBeFound("teacher.notEquals=" + DEFAULT_TEACHER);

        // Get all the citiesList where teacher not equals to UPDATED_TEACHER
        defaultCitiesShouldBeFound("teacher.notEquals=" + UPDATED_TEACHER);
    }

    @Test
    @Transactional
    void getAllCitiesByTeacherIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where teacher in DEFAULT_TEACHER or UPDATED_TEACHER
        defaultCitiesShouldBeFound("teacher.in=" + DEFAULT_TEACHER + "," + UPDATED_TEACHER);

        // Get all the citiesList where teacher equals to UPDATED_TEACHER
        defaultCitiesShouldNotBeFound("teacher.in=" + UPDATED_TEACHER);
    }

    @Test
    @Transactional
    void getAllCitiesByTeacherIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where teacher is not null
        defaultCitiesShouldBeFound("teacher.specified=true");

        // Get all the citiesList where teacher is null
        defaultCitiesShouldNotBeFound("teacher.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByTeacherContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where teacher contains DEFAULT_TEACHER
        defaultCitiesShouldBeFound("teacher.contains=" + DEFAULT_TEACHER);

        // Get all the citiesList where teacher contains UPDATED_TEACHER
        defaultCitiesShouldNotBeFound("teacher.contains=" + UPDATED_TEACHER);
    }

    @Test
    @Transactional
    void getAllCitiesByTeacherNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where teacher does not contain DEFAULT_TEACHER
        defaultCitiesShouldNotBeFound("teacher.doesNotContain=" + DEFAULT_TEACHER);

        // Get all the citiesList where teacher does not contain UPDATED_TEACHER
        defaultCitiesShouldBeFound("teacher.doesNotContain=" + UPDATED_TEACHER);
    }

    @Test
    @Transactional
    void getAllCitiesByTeachersPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where teachersPhone equals to DEFAULT_TEACHERS_PHONE
        defaultCitiesShouldBeFound("teachersPhone.equals=" + DEFAULT_TEACHERS_PHONE);

        // Get all the citiesList where teachersPhone equals to UPDATED_TEACHERS_PHONE
        defaultCitiesShouldNotBeFound("teachersPhone.equals=" + UPDATED_TEACHERS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByTeachersPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where teachersPhone not equals to DEFAULT_TEACHERS_PHONE
        defaultCitiesShouldNotBeFound("teachersPhone.notEquals=" + DEFAULT_TEACHERS_PHONE);

        // Get all the citiesList where teachersPhone not equals to UPDATED_TEACHERS_PHONE
        defaultCitiesShouldBeFound("teachersPhone.notEquals=" + UPDATED_TEACHERS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByTeachersPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where teachersPhone in DEFAULT_TEACHERS_PHONE or UPDATED_TEACHERS_PHONE
        defaultCitiesShouldBeFound("teachersPhone.in=" + DEFAULT_TEACHERS_PHONE + "," + UPDATED_TEACHERS_PHONE);

        // Get all the citiesList where teachersPhone equals to UPDATED_TEACHERS_PHONE
        defaultCitiesShouldNotBeFound("teachersPhone.in=" + UPDATED_TEACHERS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByTeachersPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where teachersPhone is not null
        defaultCitiesShouldBeFound("teachersPhone.specified=true");

        // Get all the citiesList where teachersPhone is null
        defaultCitiesShouldNotBeFound("teachersPhone.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByTeachersPhoneContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where teachersPhone contains DEFAULT_TEACHERS_PHONE
        defaultCitiesShouldBeFound("teachersPhone.contains=" + DEFAULT_TEACHERS_PHONE);

        // Get all the citiesList where teachersPhone contains UPDATED_TEACHERS_PHONE
        defaultCitiesShouldNotBeFound("teachersPhone.contains=" + UPDATED_TEACHERS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByTeachersPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where teachersPhone does not contain DEFAULT_TEACHERS_PHONE
        defaultCitiesShouldNotBeFound("teachersPhone.doesNotContain=" + DEFAULT_TEACHERS_PHONE);

        // Get all the citiesList where teachersPhone does not contain UPDATED_TEACHERS_PHONE
        defaultCitiesShouldBeFound("teachersPhone.doesNotContain=" + UPDATED_TEACHERS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByPriestIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where priest equals to DEFAULT_PRIEST
        defaultCitiesShouldBeFound("priest.equals=" + DEFAULT_PRIEST);

        // Get all the citiesList where priest equals to UPDATED_PRIEST
        defaultCitiesShouldNotBeFound("priest.equals=" + UPDATED_PRIEST);
    }

    @Test
    @Transactional
    void getAllCitiesByPriestIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where priest not equals to DEFAULT_PRIEST
        defaultCitiesShouldNotBeFound("priest.notEquals=" + DEFAULT_PRIEST);

        // Get all the citiesList where priest not equals to UPDATED_PRIEST
        defaultCitiesShouldBeFound("priest.notEquals=" + UPDATED_PRIEST);
    }

    @Test
    @Transactional
    void getAllCitiesByPriestIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where priest in DEFAULT_PRIEST or UPDATED_PRIEST
        defaultCitiesShouldBeFound("priest.in=" + DEFAULT_PRIEST + "," + UPDATED_PRIEST);

        // Get all the citiesList where priest equals to UPDATED_PRIEST
        defaultCitiesShouldNotBeFound("priest.in=" + UPDATED_PRIEST);
    }

    @Test
    @Transactional
    void getAllCitiesByPriestIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where priest is not null
        defaultCitiesShouldBeFound("priest.specified=true");

        // Get all the citiesList where priest is null
        defaultCitiesShouldNotBeFound("priest.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByPriestContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where priest contains DEFAULT_PRIEST
        defaultCitiesShouldBeFound("priest.contains=" + DEFAULT_PRIEST);

        // Get all the citiesList where priest contains UPDATED_PRIEST
        defaultCitiesShouldNotBeFound("priest.contains=" + UPDATED_PRIEST);
    }

    @Test
    @Transactional
    void getAllCitiesByPriestNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where priest does not contain DEFAULT_PRIEST
        defaultCitiesShouldNotBeFound("priest.doesNotContain=" + DEFAULT_PRIEST);

        // Get all the citiesList where priest does not contain UPDATED_PRIEST
        defaultCitiesShouldBeFound("priest.doesNotContain=" + UPDATED_PRIEST);
    }

    @Test
    @Transactional
    void getAllCitiesByPriestsPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where priestsPhone equals to DEFAULT_PRIESTS_PHONE
        defaultCitiesShouldBeFound("priestsPhone.equals=" + DEFAULT_PRIESTS_PHONE);

        // Get all the citiesList where priestsPhone equals to UPDATED_PRIESTS_PHONE
        defaultCitiesShouldNotBeFound("priestsPhone.equals=" + UPDATED_PRIESTS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByPriestsPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where priestsPhone not equals to DEFAULT_PRIESTS_PHONE
        defaultCitiesShouldNotBeFound("priestsPhone.notEquals=" + DEFAULT_PRIESTS_PHONE);

        // Get all the citiesList where priestsPhone not equals to UPDATED_PRIESTS_PHONE
        defaultCitiesShouldBeFound("priestsPhone.notEquals=" + UPDATED_PRIESTS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByPriestsPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where priestsPhone in DEFAULT_PRIESTS_PHONE or UPDATED_PRIESTS_PHONE
        defaultCitiesShouldBeFound("priestsPhone.in=" + DEFAULT_PRIESTS_PHONE + "," + UPDATED_PRIESTS_PHONE);

        // Get all the citiesList where priestsPhone equals to UPDATED_PRIESTS_PHONE
        defaultCitiesShouldNotBeFound("priestsPhone.in=" + UPDATED_PRIESTS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByPriestsPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where priestsPhone is not null
        defaultCitiesShouldBeFound("priestsPhone.specified=true");

        // Get all the citiesList where priestsPhone is null
        defaultCitiesShouldNotBeFound("priestsPhone.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByPriestsPhoneContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where priestsPhone contains DEFAULT_PRIESTS_PHONE
        defaultCitiesShouldBeFound("priestsPhone.contains=" + DEFAULT_PRIESTS_PHONE);

        // Get all the citiesList where priestsPhone contains UPDATED_PRIESTS_PHONE
        defaultCitiesShouldNotBeFound("priestsPhone.contains=" + UPDATED_PRIESTS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByPriestsPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where priestsPhone does not contain DEFAULT_PRIESTS_PHONE
        defaultCitiesShouldNotBeFound("priestsPhone.doesNotContain=" + DEFAULT_PRIESTS_PHONE);

        // Get all the citiesList where priestsPhone does not contain UPDATED_PRIESTS_PHONE
        defaultCitiesShouldBeFound("priestsPhone.doesNotContain=" + UPDATED_PRIESTS_PHONE);
    }

    @Test
    @Transactional
    void getAllCitiesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
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
        cities.setCountry(country);
        citiesRepository.saveAndFlush(cities);
        Long countryId = country.getId();

        // Get all the citiesList where country equals to countryId
        defaultCitiesShouldBeFound("countryId.equals=" + countryId);

        // Get all the citiesList where country equals to (countryId + 1)
        defaultCitiesShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
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
        cities.setRegion(region);
        citiesRepository.saveAndFlush(cities);
        Long regionId = region.getId();

        // Get all the citiesList where region equals to regionId
        defaultCitiesShouldBeFound("regionId.equals=" + regionId);

        // Get all the citiesList where region equals to (regionId + 1)
        defaultCitiesShouldNotBeFound("regionId.equals=" + (regionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCitiesShouldBeFound(String filter) throws Exception {
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cities.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].president").value(hasItem(DEFAULT_PRESIDENT)))
            .andExpect(jsonPath("$.[*].presidentsPhone").value(hasItem(DEFAULT_PRESIDENTS_PHONE)))
            .andExpect(jsonPath("$.[*].secretary").value(hasItem(DEFAULT_SECRETARY)))
            .andExpect(jsonPath("$.[*].secretarysPhone").value(hasItem(DEFAULT_SECRETARYS_PHONE)))
            .andExpect(jsonPath("$.[*].police").value(hasItem(DEFAULT_POLICE)))
            .andExpect(jsonPath("$.[*].policesPhone").value(hasItem(DEFAULT_POLICES_PHONE)))
            .andExpect(jsonPath("$.[*].doctor").value(hasItem(DEFAULT_DOCTOR)))
            .andExpect(jsonPath("$.[*].doctorsPhone").value(hasItem(DEFAULT_DOCTORS_PHONE)))
            .andExpect(jsonPath("$.[*].teacher").value(hasItem(DEFAULT_TEACHER)))
            .andExpect(jsonPath("$.[*].teachersPhone").value(hasItem(DEFAULT_TEACHERS_PHONE)))
            .andExpect(jsonPath("$.[*].priest").value(hasItem(DEFAULT_PRIEST)))
            .andExpect(jsonPath("$.[*].priestsPhone").value(hasItem(DEFAULT_PRIESTS_PHONE)));

        // Check, that the count call also returns 1
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCitiesShouldNotBeFound(String filter) throws Exception {
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCities() throws Exception {
        // Get the cities
        restCitiesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Update the cities
        Cities updatedCities = citiesRepository.findById(cities.getId()).get();
        // Disconnect from session so that the updates on updatedCities are not directly saved in db
        em.detach(updatedCities);
        updatedCities
            .name(UPDATED_NAME)
            .president(UPDATED_PRESIDENT)
            .presidentsPhone(UPDATED_PRESIDENTS_PHONE)
            .secretary(UPDATED_SECRETARY)
            .secretarysPhone(UPDATED_SECRETARYS_PHONE)
            .police(UPDATED_POLICE)
            .policesPhone(UPDATED_POLICES_PHONE)
            .doctor(UPDATED_DOCTOR)
            .doctorsPhone(UPDATED_DOCTORS_PHONE)
            .teacher(UPDATED_TEACHER)
            .teachersPhone(UPDATED_TEACHERS_PHONE)
            .priest(UPDATED_PRIEST)
            .priestsPhone(UPDATED_PRIESTS_PHONE);

        restCitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCities.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCities))
            )
            .andExpect(status().isOk());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCities.getPresident()).isEqualTo(UPDATED_PRESIDENT);
        assertThat(testCities.getPresidentsPhone()).isEqualTo(UPDATED_PRESIDENTS_PHONE);
        assertThat(testCities.getSecretary()).isEqualTo(UPDATED_SECRETARY);
        assertThat(testCities.getSecretarysPhone()).isEqualTo(UPDATED_SECRETARYS_PHONE);
        assertThat(testCities.getPolice()).isEqualTo(UPDATED_POLICE);
        assertThat(testCities.getPolicesPhone()).isEqualTo(UPDATED_POLICES_PHONE);
        assertThat(testCities.getDoctor()).isEqualTo(UPDATED_DOCTOR);
        assertThat(testCities.getDoctorsPhone()).isEqualTo(UPDATED_DOCTORS_PHONE);
        assertThat(testCities.getTeacher()).isEqualTo(UPDATED_TEACHER);
        assertThat(testCities.getTeachersPhone()).isEqualTo(UPDATED_TEACHERS_PHONE);
        assertThat(testCities.getPriest()).isEqualTo(UPDATED_PRIEST);
        assertThat(testCities.getPriestsPhone()).isEqualTo(UPDATED_PRIESTS_PHONE);
    }

    @Test
    @Transactional
    void putNonExistingCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cities.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cities))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cities))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cities)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCitiesWithPatch() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Update the cities using partial update
        Cities partialUpdatedCities = new Cities();
        partialUpdatedCities.setId(cities.getId());

        partialUpdatedCities
            .president(UPDATED_PRESIDENT)
            .presidentsPhone(UPDATED_PRESIDENTS_PHONE)
            .secretarysPhone(UPDATED_SECRETARYS_PHONE)
            .policesPhone(UPDATED_POLICES_PHONE)
            .doctor(UPDATED_DOCTOR)
            .teachersPhone(UPDATED_TEACHERS_PHONE)
            .priest(UPDATED_PRIEST);

        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCities.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCities))
            )
            .andExpect(status().isOk());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCities.getPresident()).isEqualTo(UPDATED_PRESIDENT);
        assertThat(testCities.getPresidentsPhone()).isEqualTo(UPDATED_PRESIDENTS_PHONE);
        assertThat(testCities.getSecretary()).isEqualTo(DEFAULT_SECRETARY);
        assertThat(testCities.getSecretarysPhone()).isEqualTo(UPDATED_SECRETARYS_PHONE);
        assertThat(testCities.getPolice()).isEqualTo(DEFAULT_POLICE);
        assertThat(testCities.getPolicesPhone()).isEqualTo(UPDATED_POLICES_PHONE);
        assertThat(testCities.getDoctor()).isEqualTo(UPDATED_DOCTOR);
        assertThat(testCities.getDoctorsPhone()).isEqualTo(DEFAULT_DOCTORS_PHONE);
        assertThat(testCities.getTeacher()).isEqualTo(DEFAULT_TEACHER);
        assertThat(testCities.getTeachersPhone()).isEqualTo(UPDATED_TEACHERS_PHONE);
        assertThat(testCities.getPriest()).isEqualTo(UPDATED_PRIEST);
        assertThat(testCities.getPriestsPhone()).isEqualTo(DEFAULT_PRIESTS_PHONE);
    }

    @Test
    @Transactional
    void fullUpdateCitiesWithPatch() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Update the cities using partial update
        Cities partialUpdatedCities = new Cities();
        partialUpdatedCities.setId(cities.getId());

        partialUpdatedCities
            .name(UPDATED_NAME)
            .president(UPDATED_PRESIDENT)
            .presidentsPhone(UPDATED_PRESIDENTS_PHONE)
            .secretary(UPDATED_SECRETARY)
            .secretarysPhone(UPDATED_SECRETARYS_PHONE)
            .police(UPDATED_POLICE)
            .policesPhone(UPDATED_POLICES_PHONE)
            .doctor(UPDATED_DOCTOR)
            .doctorsPhone(UPDATED_DOCTORS_PHONE)
            .teacher(UPDATED_TEACHER)
            .teachersPhone(UPDATED_TEACHERS_PHONE)
            .priest(UPDATED_PRIEST)
            .priestsPhone(UPDATED_PRIESTS_PHONE);

        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCities.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCities))
            )
            .andExpect(status().isOk());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCities.getPresident()).isEqualTo(UPDATED_PRESIDENT);
        assertThat(testCities.getPresidentsPhone()).isEqualTo(UPDATED_PRESIDENTS_PHONE);
        assertThat(testCities.getSecretary()).isEqualTo(UPDATED_SECRETARY);
        assertThat(testCities.getSecretarysPhone()).isEqualTo(UPDATED_SECRETARYS_PHONE);
        assertThat(testCities.getPolice()).isEqualTo(UPDATED_POLICE);
        assertThat(testCities.getPolicesPhone()).isEqualTo(UPDATED_POLICES_PHONE);
        assertThat(testCities.getDoctor()).isEqualTo(UPDATED_DOCTOR);
        assertThat(testCities.getDoctorsPhone()).isEqualTo(UPDATED_DOCTORS_PHONE);
        assertThat(testCities.getTeacher()).isEqualTo(UPDATED_TEACHER);
        assertThat(testCities.getTeachersPhone()).isEqualTo(UPDATED_TEACHERS_PHONE);
        assertThat(testCities.getPriest()).isEqualTo(UPDATED_PRIEST);
        assertThat(testCities.getPriestsPhone()).isEqualTo(UPDATED_PRIESTS_PHONE);
    }

    @Test
    @Transactional
    void patchNonExistingCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cities.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cities))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cities))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cities)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeDelete = citiesRepository.findAll().size();

        // Delete the cities
        restCitiesMockMvc
            .perform(delete(ENTITY_API_URL_ID, cities.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
