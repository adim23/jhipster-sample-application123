package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Citizens;
import com.mycompany.myapp.domain.CitizensMeetings;
import com.mycompany.myapp.repository.CitizensMeetingsRepository;
import com.mycompany.myapp.service.criteria.CitizensMeetingsCriteria;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link CitizensMeetingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CitizensMeetingsResourceIT {

    private static final LocalDate DEFAULT_MEET_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MEET_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_MEET_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_AGENDA = "AAAAAAAAAA";
    private static final String UPDATED_AGENDA = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;
    private static final Float SMALLER_AMOUNT = 1F - 1F;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;
    private static final Integer SMALLER_QUANTITY = 1 - 1;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FLAG = false;
    private static final Boolean UPDATED_FLAG = true;

    private static final String ENTITY_API_URL = "/api/citizens-meetings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CitizensMeetingsRepository citizensMeetingsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitizensMeetingsMockMvc;

    private CitizensMeetings citizensMeetings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CitizensMeetings createEntity(EntityManager em) {
        CitizensMeetings citizensMeetings = new CitizensMeetings()
            .meetDate(DEFAULT_MEET_DATE)
            .agenda(DEFAULT_AGENDA)
            .comments(DEFAULT_COMMENTS)
            .amount(DEFAULT_AMOUNT)
            .quantity(DEFAULT_QUANTITY)
            .status(DEFAULT_STATUS)
            .flag(DEFAULT_FLAG);
        return citizensMeetings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CitizensMeetings createUpdatedEntity(EntityManager em) {
        CitizensMeetings citizensMeetings = new CitizensMeetings()
            .meetDate(UPDATED_MEET_DATE)
            .agenda(UPDATED_AGENDA)
            .comments(UPDATED_COMMENTS)
            .amount(UPDATED_AMOUNT)
            .quantity(UPDATED_QUANTITY)
            .status(UPDATED_STATUS)
            .flag(UPDATED_FLAG);
        return citizensMeetings;
    }

    @BeforeEach
    public void initTest() {
        citizensMeetings = createEntity(em);
    }

    @Test
    @Transactional
    void createCitizensMeetings() throws Exception {
        int databaseSizeBeforeCreate = citizensMeetingsRepository.findAll().size();
        // Create the CitizensMeetings
        restCitizensMeetingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizensMeetings))
            )
            .andExpect(status().isCreated());

        // Validate the CitizensMeetings in the database
        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeCreate + 1);
        CitizensMeetings testCitizensMeetings = citizensMeetingsList.get(citizensMeetingsList.size() - 1);
        assertThat(testCitizensMeetings.getMeetDate()).isEqualTo(DEFAULT_MEET_DATE);
        assertThat(testCitizensMeetings.getAgenda()).isEqualTo(DEFAULT_AGENDA);
        assertThat(testCitizensMeetings.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testCitizensMeetings.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCitizensMeetings.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testCitizensMeetings.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCitizensMeetings.getFlag()).isEqualTo(DEFAULT_FLAG);
    }

    @Test
    @Transactional
    void createCitizensMeetingsWithExistingId() throws Exception {
        // Create the CitizensMeetings with an existing ID
        citizensMeetings.setId(1L);

        int databaseSizeBeforeCreate = citizensMeetingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitizensMeetingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizensMeetings))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitizensMeetings in the database
        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMeetDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = citizensMeetingsRepository.findAll().size();
        // set the field null
        citizensMeetings.setMeetDate(null);

        // Create the CitizensMeetings, which fails.

        restCitizensMeetingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizensMeetings))
            )
            .andExpect(status().isBadRequest());

        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAgendaIsRequired() throws Exception {
        int databaseSizeBeforeTest = citizensMeetingsRepository.findAll().size();
        // set the field null
        citizensMeetings.setAgenda(null);

        // Create the CitizensMeetings, which fails.

        restCitizensMeetingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizensMeetings))
            )
            .andExpect(status().isBadRequest());

        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = citizensMeetingsRepository.findAll().size();
        // set the field null
        citizensMeetings.setStatus(null);

        // Create the CitizensMeetings, which fails.

        restCitizensMeetingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizensMeetings))
            )
            .andExpect(status().isBadRequest());

        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = citizensMeetingsRepository.findAll().size();
        // set the field null
        citizensMeetings.setFlag(null);

        // Create the CitizensMeetings, which fails.

        restCitizensMeetingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizensMeetings))
            )
            .andExpect(status().isBadRequest());

        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCitizensMeetings() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList
        restCitizensMeetingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citizensMeetings.getId().intValue())))
            .andExpect(jsonPath("$.[*].meetDate").value(hasItem(DEFAULT_MEET_DATE.toString())))
            .andExpect(jsonPath("$.[*].agenda").value(hasItem(DEFAULT_AGENDA)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.booleanValue())));
    }

    @Test
    @Transactional
    void getCitizensMeetings() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get the citizensMeetings
        restCitizensMeetingsMockMvc
            .perform(get(ENTITY_API_URL_ID, citizensMeetings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(citizensMeetings.getId().intValue()))
            .andExpect(jsonPath("$.meetDate").value(DEFAULT_MEET_DATE.toString()))
            .andExpect(jsonPath("$.agenda").value(DEFAULT_AGENDA))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.flag").value(DEFAULT_FLAG.booleanValue()));
    }

    @Test
    @Transactional
    void getCitizensMeetingsByIdFiltering() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        Long id = citizensMeetings.getId();

        defaultCitizensMeetingsShouldBeFound("id.equals=" + id);
        defaultCitizensMeetingsShouldNotBeFound("id.notEquals=" + id);

        defaultCitizensMeetingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCitizensMeetingsShouldNotBeFound("id.greaterThan=" + id);

        defaultCitizensMeetingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCitizensMeetingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByMeetDateIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where meetDate equals to DEFAULT_MEET_DATE
        defaultCitizensMeetingsShouldBeFound("meetDate.equals=" + DEFAULT_MEET_DATE);

        // Get all the citizensMeetingsList where meetDate equals to UPDATED_MEET_DATE
        defaultCitizensMeetingsShouldNotBeFound("meetDate.equals=" + UPDATED_MEET_DATE);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByMeetDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where meetDate not equals to DEFAULT_MEET_DATE
        defaultCitizensMeetingsShouldNotBeFound("meetDate.notEquals=" + DEFAULT_MEET_DATE);

        // Get all the citizensMeetingsList where meetDate not equals to UPDATED_MEET_DATE
        defaultCitizensMeetingsShouldBeFound("meetDate.notEquals=" + UPDATED_MEET_DATE);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByMeetDateIsInShouldWork() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where meetDate in DEFAULT_MEET_DATE or UPDATED_MEET_DATE
        defaultCitizensMeetingsShouldBeFound("meetDate.in=" + DEFAULT_MEET_DATE + "," + UPDATED_MEET_DATE);

        // Get all the citizensMeetingsList where meetDate equals to UPDATED_MEET_DATE
        defaultCitizensMeetingsShouldNotBeFound("meetDate.in=" + UPDATED_MEET_DATE);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByMeetDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where meetDate is not null
        defaultCitizensMeetingsShouldBeFound("meetDate.specified=true");

        // Get all the citizensMeetingsList where meetDate is null
        defaultCitizensMeetingsShouldNotBeFound("meetDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByMeetDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where meetDate is greater than or equal to DEFAULT_MEET_DATE
        defaultCitizensMeetingsShouldBeFound("meetDate.greaterThanOrEqual=" + DEFAULT_MEET_DATE);

        // Get all the citizensMeetingsList where meetDate is greater than or equal to UPDATED_MEET_DATE
        defaultCitizensMeetingsShouldNotBeFound("meetDate.greaterThanOrEqual=" + UPDATED_MEET_DATE);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByMeetDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where meetDate is less than or equal to DEFAULT_MEET_DATE
        defaultCitizensMeetingsShouldBeFound("meetDate.lessThanOrEqual=" + DEFAULT_MEET_DATE);

        // Get all the citizensMeetingsList where meetDate is less than or equal to SMALLER_MEET_DATE
        defaultCitizensMeetingsShouldNotBeFound("meetDate.lessThanOrEqual=" + SMALLER_MEET_DATE);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByMeetDateIsLessThanSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where meetDate is less than DEFAULT_MEET_DATE
        defaultCitizensMeetingsShouldNotBeFound("meetDate.lessThan=" + DEFAULT_MEET_DATE);

        // Get all the citizensMeetingsList where meetDate is less than UPDATED_MEET_DATE
        defaultCitizensMeetingsShouldBeFound("meetDate.lessThan=" + UPDATED_MEET_DATE);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByMeetDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where meetDate is greater than DEFAULT_MEET_DATE
        defaultCitizensMeetingsShouldNotBeFound("meetDate.greaterThan=" + DEFAULT_MEET_DATE);

        // Get all the citizensMeetingsList where meetDate is greater than SMALLER_MEET_DATE
        defaultCitizensMeetingsShouldBeFound("meetDate.greaterThan=" + SMALLER_MEET_DATE);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByAgendaIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where agenda equals to DEFAULT_AGENDA
        defaultCitizensMeetingsShouldBeFound("agenda.equals=" + DEFAULT_AGENDA);

        // Get all the citizensMeetingsList where agenda equals to UPDATED_AGENDA
        defaultCitizensMeetingsShouldNotBeFound("agenda.equals=" + UPDATED_AGENDA);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByAgendaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where agenda not equals to DEFAULT_AGENDA
        defaultCitizensMeetingsShouldNotBeFound("agenda.notEquals=" + DEFAULT_AGENDA);

        // Get all the citizensMeetingsList where agenda not equals to UPDATED_AGENDA
        defaultCitizensMeetingsShouldBeFound("agenda.notEquals=" + UPDATED_AGENDA);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByAgendaIsInShouldWork() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where agenda in DEFAULT_AGENDA or UPDATED_AGENDA
        defaultCitizensMeetingsShouldBeFound("agenda.in=" + DEFAULT_AGENDA + "," + UPDATED_AGENDA);

        // Get all the citizensMeetingsList where agenda equals to UPDATED_AGENDA
        defaultCitizensMeetingsShouldNotBeFound("agenda.in=" + UPDATED_AGENDA);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByAgendaIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where agenda is not null
        defaultCitizensMeetingsShouldBeFound("agenda.specified=true");

        // Get all the citizensMeetingsList where agenda is null
        defaultCitizensMeetingsShouldNotBeFound("agenda.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByAgendaContainsSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where agenda contains DEFAULT_AGENDA
        defaultCitizensMeetingsShouldBeFound("agenda.contains=" + DEFAULT_AGENDA);

        // Get all the citizensMeetingsList where agenda contains UPDATED_AGENDA
        defaultCitizensMeetingsShouldNotBeFound("agenda.contains=" + UPDATED_AGENDA);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByAgendaNotContainsSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where agenda does not contain DEFAULT_AGENDA
        defaultCitizensMeetingsShouldNotBeFound("agenda.doesNotContain=" + DEFAULT_AGENDA);

        // Get all the citizensMeetingsList where agenda does not contain UPDATED_AGENDA
        defaultCitizensMeetingsShouldBeFound("agenda.doesNotContain=" + UPDATED_AGENDA);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where amount equals to DEFAULT_AMOUNT
        defaultCitizensMeetingsShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the citizensMeetingsList where amount equals to UPDATED_AMOUNT
        defaultCitizensMeetingsShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where amount not equals to DEFAULT_AMOUNT
        defaultCitizensMeetingsShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the citizensMeetingsList where amount not equals to UPDATED_AMOUNT
        defaultCitizensMeetingsShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultCitizensMeetingsShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the citizensMeetingsList where amount equals to UPDATED_AMOUNT
        defaultCitizensMeetingsShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where amount is not null
        defaultCitizensMeetingsShouldBeFound("amount.specified=true");

        // Get all the citizensMeetingsList where amount is null
        defaultCitizensMeetingsShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultCitizensMeetingsShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the citizensMeetingsList where amount is greater than or equal to UPDATED_AMOUNT
        defaultCitizensMeetingsShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where amount is less than or equal to DEFAULT_AMOUNT
        defaultCitizensMeetingsShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the citizensMeetingsList where amount is less than or equal to SMALLER_AMOUNT
        defaultCitizensMeetingsShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where amount is less than DEFAULT_AMOUNT
        defaultCitizensMeetingsShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the citizensMeetingsList where amount is less than UPDATED_AMOUNT
        defaultCitizensMeetingsShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where amount is greater than DEFAULT_AMOUNT
        defaultCitizensMeetingsShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the citizensMeetingsList where amount is greater than SMALLER_AMOUNT
        defaultCitizensMeetingsShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where quantity equals to DEFAULT_QUANTITY
        defaultCitizensMeetingsShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the citizensMeetingsList where quantity equals to UPDATED_QUANTITY
        defaultCitizensMeetingsShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where quantity not equals to DEFAULT_QUANTITY
        defaultCitizensMeetingsShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the citizensMeetingsList where quantity not equals to UPDATED_QUANTITY
        defaultCitizensMeetingsShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultCitizensMeetingsShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the citizensMeetingsList where quantity equals to UPDATED_QUANTITY
        defaultCitizensMeetingsShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where quantity is not null
        defaultCitizensMeetingsShouldBeFound("quantity.specified=true");

        // Get all the citizensMeetingsList where quantity is null
        defaultCitizensMeetingsShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultCitizensMeetingsShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the citizensMeetingsList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultCitizensMeetingsShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultCitizensMeetingsShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the citizensMeetingsList where quantity is less than or equal to SMALLER_QUANTITY
        defaultCitizensMeetingsShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where quantity is less than DEFAULT_QUANTITY
        defaultCitizensMeetingsShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the citizensMeetingsList where quantity is less than UPDATED_QUANTITY
        defaultCitizensMeetingsShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where quantity is greater than DEFAULT_QUANTITY
        defaultCitizensMeetingsShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the citizensMeetingsList where quantity is greater than SMALLER_QUANTITY
        defaultCitizensMeetingsShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where status equals to DEFAULT_STATUS
        defaultCitizensMeetingsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the citizensMeetingsList where status equals to UPDATED_STATUS
        defaultCitizensMeetingsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where status not equals to DEFAULT_STATUS
        defaultCitizensMeetingsShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the citizensMeetingsList where status not equals to UPDATED_STATUS
        defaultCitizensMeetingsShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCitizensMeetingsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the citizensMeetingsList where status equals to UPDATED_STATUS
        defaultCitizensMeetingsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where status is not null
        defaultCitizensMeetingsShouldBeFound("status.specified=true");

        // Get all the citizensMeetingsList where status is null
        defaultCitizensMeetingsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByStatusContainsSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where status contains DEFAULT_STATUS
        defaultCitizensMeetingsShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the citizensMeetingsList where status contains UPDATED_STATUS
        defaultCitizensMeetingsShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where status does not contain DEFAULT_STATUS
        defaultCitizensMeetingsShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the citizensMeetingsList where status does not contain UPDATED_STATUS
        defaultCitizensMeetingsShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where flag equals to DEFAULT_FLAG
        defaultCitizensMeetingsShouldBeFound("flag.equals=" + DEFAULT_FLAG);

        // Get all the citizensMeetingsList where flag equals to UPDATED_FLAG
        defaultCitizensMeetingsShouldNotBeFound("flag.equals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where flag not equals to DEFAULT_FLAG
        defaultCitizensMeetingsShouldNotBeFound("flag.notEquals=" + DEFAULT_FLAG);

        // Get all the citizensMeetingsList where flag not equals to UPDATED_FLAG
        defaultCitizensMeetingsShouldBeFound("flag.notEquals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByFlagIsInShouldWork() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where flag in DEFAULT_FLAG or UPDATED_FLAG
        defaultCitizensMeetingsShouldBeFound("flag.in=" + DEFAULT_FLAG + "," + UPDATED_FLAG);

        // Get all the citizensMeetingsList where flag equals to UPDATED_FLAG
        defaultCitizensMeetingsShouldNotBeFound("flag.in=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        // Get all the citizensMeetingsList where flag is not null
        defaultCitizensMeetingsShouldBeFound("flag.specified=true");

        // Get all the citizensMeetingsList where flag is null
        defaultCitizensMeetingsShouldNotBeFound("flag.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensMeetingsByCitizenIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);
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
        citizensMeetings.setCitizen(citizen);
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);
        Long citizenId = citizen.getId();

        // Get all the citizensMeetingsList where citizen equals to citizenId
        defaultCitizensMeetingsShouldBeFound("citizenId.equals=" + citizenId);

        // Get all the citizensMeetingsList where citizen equals to (citizenId + 1)
        defaultCitizensMeetingsShouldNotBeFound("citizenId.equals=" + (citizenId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCitizensMeetingsShouldBeFound(String filter) throws Exception {
        restCitizensMeetingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citizensMeetings.getId().intValue())))
            .andExpect(jsonPath("$.[*].meetDate").value(hasItem(DEFAULT_MEET_DATE.toString())))
            .andExpect(jsonPath("$.[*].agenda").value(hasItem(DEFAULT_AGENDA)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.booleanValue())));

        // Check, that the count call also returns 1
        restCitizensMeetingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCitizensMeetingsShouldNotBeFound(String filter) throws Exception {
        restCitizensMeetingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCitizensMeetingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCitizensMeetings() throws Exception {
        // Get the citizensMeetings
        restCitizensMeetingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCitizensMeetings() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        int databaseSizeBeforeUpdate = citizensMeetingsRepository.findAll().size();

        // Update the citizensMeetings
        CitizensMeetings updatedCitizensMeetings = citizensMeetingsRepository.findById(citizensMeetings.getId()).get();
        // Disconnect from session so that the updates on updatedCitizensMeetings are not directly saved in db
        em.detach(updatedCitizensMeetings);
        updatedCitizensMeetings
            .meetDate(UPDATED_MEET_DATE)
            .agenda(UPDATED_AGENDA)
            .comments(UPDATED_COMMENTS)
            .amount(UPDATED_AMOUNT)
            .quantity(UPDATED_QUANTITY)
            .status(UPDATED_STATUS)
            .flag(UPDATED_FLAG);

        restCitizensMeetingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCitizensMeetings.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCitizensMeetings))
            )
            .andExpect(status().isOk());

        // Validate the CitizensMeetings in the database
        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeUpdate);
        CitizensMeetings testCitizensMeetings = citizensMeetingsList.get(citizensMeetingsList.size() - 1);
        assertThat(testCitizensMeetings.getMeetDate()).isEqualTo(UPDATED_MEET_DATE);
        assertThat(testCitizensMeetings.getAgenda()).isEqualTo(UPDATED_AGENDA);
        assertThat(testCitizensMeetings.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testCitizensMeetings.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCitizensMeetings.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCitizensMeetings.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCitizensMeetings.getFlag()).isEqualTo(UPDATED_FLAG);
    }

    @Test
    @Transactional
    void putNonExistingCitizensMeetings() throws Exception {
        int databaseSizeBeforeUpdate = citizensMeetingsRepository.findAll().size();
        citizensMeetings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitizensMeetingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citizensMeetings.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citizensMeetings))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitizensMeetings in the database
        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCitizensMeetings() throws Exception {
        int databaseSizeBeforeUpdate = citizensMeetingsRepository.findAll().size();
        citizensMeetings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizensMeetingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citizensMeetings))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitizensMeetings in the database
        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCitizensMeetings() throws Exception {
        int databaseSizeBeforeUpdate = citizensMeetingsRepository.findAll().size();
        citizensMeetings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizensMeetingsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizensMeetings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CitizensMeetings in the database
        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCitizensMeetingsWithPatch() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        int databaseSizeBeforeUpdate = citizensMeetingsRepository.findAll().size();

        // Update the citizensMeetings using partial update
        CitizensMeetings partialUpdatedCitizensMeetings = new CitizensMeetings();
        partialUpdatedCitizensMeetings.setId(citizensMeetings.getId());

        partialUpdatedCitizensMeetings
            .meetDate(UPDATED_MEET_DATE)
            .comments(UPDATED_COMMENTS)
            .amount(UPDATED_AMOUNT)
            .quantity(UPDATED_QUANTITY)
            .status(UPDATED_STATUS)
            .flag(UPDATED_FLAG);

        restCitizensMeetingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCitizensMeetings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCitizensMeetings))
            )
            .andExpect(status().isOk());

        // Validate the CitizensMeetings in the database
        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeUpdate);
        CitizensMeetings testCitizensMeetings = citizensMeetingsList.get(citizensMeetingsList.size() - 1);
        assertThat(testCitizensMeetings.getMeetDate()).isEqualTo(UPDATED_MEET_DATE);
        assertThat(testCitizensMeetings.getAgenda()).isEqualTo(DEFAULT_AGENDA);
        assertThat(testCitizensMeetings.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testCitizensMeetings.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCitizensMeetings.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCitizensMeetings.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCitizensMeetings.getFlag()).isEqualTo(UPDATED_FLAG);
    }

    @Test
    @Transactional
    void fullUpdateCitizensMeetingsWithPatch() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        int databaseSizeBeforeUpdate = citizensMeetingsRepository.findAll().size();

        // Update the citizensMeetings using partial update
        CitizensMeetings partialUpdatedCitizensMeetings = new CitizensMeetings();
        partialUpdatedCitizensMeetings.setId(citizensMeetings.getId());

        partialUpdatedCitizensMeetings
            .meetDate(UPDATED_MEET_DATE)
            .agenda(UPDATED_AGENDA)
            .comments(UPDATED_COMMENTS)
            .amount(UPDATED_AMOUNT)
            .quantity(UPDATED_QUANTITY)
            .status(UPDATED_STATUS)
            .flag(UPDATED_FLAG);

        restCitizensMeetingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCitizensMeetings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCitizensMeetings))
            )
            .andExpect(status().isOk());

        // Validate the CitizensMeetings in the database
        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeUpdate);
        CitizensMeetings testCitizensMeetings = citizensMeetingsList.get(citizensMeetingsList.size() - 1);
        assertThat(testCitizensMeetings.getMeetDate()).isEqualTo(UPDATED_MEET_DATE);
        assertThat(testCitizensMeetings.getAgenda()).isEqualTo(UPDATED_AGENDA);
        assertThat(testCitizensMeetings.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testCitizensMeetings.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCitizensMeetings.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCitizensMeetings.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCitizensMeetings.getFlag()).isEqualTo(UPDATED_FLAG);
    }

    @Test
    @Transactional
    void patchNonExistingCitizensMeetings() throws Exception {
        int databaseSizeBeforeUpdate = citizensMeetingsRepository.findAll().size();
        citizensMeetings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitizensMeetingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, citizensMeetings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citizensMeetings))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitizensMeetings in the database
        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCitizensMeetings() throws Exception {
        int databaseSizeBeforeUpdate = citizensMeetingsRepository.findAll().size();
        citizensMeetings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizensMeetingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citizensMeetings))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitizensMeetings in the database
        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCitizensMeetings() throws Exception {
        int databaseSizeBeforeUpdate = citizensMeetingsRepository.findAll().size();
        citizensMeetings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizensMeetingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citizensMeetings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CitizensMeetings in the database
        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCitizensMeetings() throws Exception {
        // Initialize the database
        citizensMeetingsRepository.saveAndFlush(citizensMeetings);

        int databaseSizeBeforeDelete = citizensMeetingsRepository.findAll().size();

        // Delete the citizensMeetings
        restCitizensMeetingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, citizensMeetings.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CitizensMeetings> citizensMeetingsList = citizensMeetingsRepository.findAll();
        assertThat(citizensMeetingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
