package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Addresses;
import com.mycompany.myapp.domain.CitizenFolders;
import com.mycompany.myapp.domain.Citizens;
import com.mycompany.myapp.domain.CitizensRelations;
import com.mycompany.myapp.domain.Codes;
import com.mycompany.myapp.domain.Companies;
import com.mycompany.myapp.domain.Emails;
import com.mycompany.myapp.domain.Jobs;
import com.mycompany.myapp.domain.MaritalStatus;
import com.mycompany.myapp.domain.Origins;
import com.mycompany.myapp.domain.Phones;
import com.mycompany.myapp.domain.SocialContacts;
import com.mycompany.myapp.domain.Teams;
import com.mycompany.myapp.repository.CitizensRepository;
import com.mycompany.myapp.service.criteria.CitizensCriteria;
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
 * Integration tests for the {@link CitizensResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CitizensResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_FATHERS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHERS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTH_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_GIORTAZI = "AAAAAAAAAA";
    private static final String UPDATED_GIORTAZI = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MALE = false;
    private static final Boolean UPDATED_MALE = true;

    private static final Integer DEFAULT_ME_LETTER = 1;
    private static final Integer UPDATED_ME_LETTER = 2;
    private static final Integer SMALLER_ME_LETTER = 1 - 1;

    private static final Integer DEFAULT_ME_LABEL = 1;
    private static final Integer UPDATED_ME_LABEL = 2;
    private static final Integer SMALLER_ME_LABEL = 1 - 1;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/citizens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CitizensRepository citizensRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitizensMockMvc;

    private Citizens citizens;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Citizens createEntity(EntityManager em) {
        Citizens citizens = new Citizens()
            .title(DEFAULT_TITLE)
            .lastname(DEFAULT_LASTNAME)
            .firstname(DEFAULT_FIRSTNAME)
            .fathersName(DEFAULT_FATHERS_NAME)
            .comments(DEFAULT_COMMENTS)
            .birthDate(DEFAULT_BIRTH_DATE)
            .giortazi(DEFAULT_GIORTAZI)
            .male(DEFAULT_MALE)
            .meLetter(DEFAULT_ME_LETTER)
            .meLabel(DEFAULT_ME_LABEL)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return citizens;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Citizens createUpdatedEntity(EntityManager em) {
        Citizens citizens = new Citizens()
            .title(UPDATED_TITLE)
            .lastname(UPDATED_LASTNAME)
            .firstname(UPDATED_FIRSTNAME)
            .fathersName(UPDATED_FATHERS_NAME)
            .comments(UPDATED_COMMENTS)
            .birthDate(UPDATED_BIRTH_DATE)
            .giortazi(UPDATED_GIORTAZI)
            .male(UPDATED_MALE)
            .meLetter(UPDATED_ME_LETTER)
            .meLabel(UPDATED_ME_LABEL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return citizens;
    }

    @BeforeEach
    public void initTest() {
        citizens = createEntity(em);
    }

    @Test
    @Transactional
    void createCitizens() throws Exception {
        int databaseSizeBeforeCreate = citizensRepository.findAll().size();
        // Create the Citizens
        restCitizensMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizens)))
            .andExpect(status().isCreated());

        // Validate the Citizens in the database
        List<Citizens> citizensList = citizensRepository.findAll();
        assertThat(citizensList).hasSize(databaseSizeBeforeCreate + 1);
        Citizens testCitizens = citizensList.get(citizensList.size() - 1);
        assertThat(testCitizens.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCitizens.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testCitizens.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testCitizens.getFathersName()).isEqualTo(DEFAULT_FATHERS_NAME);
        assertThat(testCitizens.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testCitizens.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testCitizens.getGiortazi()).isEqualTo(DEFAULT_GIORTAZI);
        assertThat(testCitizens.getMale()).isEqualTo(DEFAULT_MALE);
        assertThat(testCitizens.getMeLetter()).isEqualTo(DEFAULT_ME_LETTER);
        assertThat(testCitizens.getMeLabel()).isEqualTo(DEFAULT_ME_LABEL);
        assertThat(testCitizens.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCitizens.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createCitizensWithExistingId() throws Exception {
        // Create the Citizens with an existing ID
        citizens.setId(1L);

        int databaseSizeBeforeCreate = citizensRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitizensMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizens)))
            .andExpect(status().isBadRequest());

        // Validate the Citizens in the database
        List<Citizens> citizensList = citizensRepository.findAll();
        assertThat(citizensList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCitizens() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList
        restCitizensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citizens.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].fathersName").value(hasItem(DEFAULT_FATHERS_NAME)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].giortazi").value(hasItem(DEFAULT_GIORTAZI)))
            .andExpect(jsonPath("$.[*].male").value(hasItem(DEFAULT_MALE.booleanValue())))
            .andExpect(jsonPath("$.[*].meLetter").value(hasItem(DEFAULT_ME_LETTER)))
            .andExpect(jsonPath("$.[*].meLabel").value(hasItem(DEFAULT_ME_LABEL)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getCitizens() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get the citizens
        restCitizensMockMvc
            .perform(get(ENTITY_API_URL_ID, citizens.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(citizens.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.fathersName").value(DEFAULT_FATHERS_NAME))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.giortazi").value(DEFAULT_GIORTAZI))
            .andExpect(jsonPath("$.male").value(DEFAULT_MALE.booleanValue()))
            .andExpect(jsonPath("$.meLetter").value(DEFAULT_ME_LETTER))
            .andExpect(jsonPath("$.meLabel").value(DEFAULT_ME_LABEL))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getCitizensByIdFiltering() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        Long id = citizens.getId();

        defaultCitizensShouldBeFound("id.equals=" + id);
        defaultCitizensShouldNotBeFound("id.notEquals=" + id);

        defaultCitizensShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCitizensShouldNotBeFound("id.greaterThan=" + id);

        defaultCitizensShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCitizensShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCitizensByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where title equals to DEFAULT_TITLE
        defaultCitizensShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the citizensList where title equals to UPDATED_TITLE
        defaultCitizensShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCitizensByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where title not equals to DEFAULT_TITLE
        defaultCitizensShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the citizensList where title not equals to UPDATED_TITLE
        defaultCitizensShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCitizensByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultCitizensShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the citizensList where title equals to UPDATED_TITLE
        defaultCitizensShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCitizensByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where title is not null
        defaultCitizensShouldBeFound("title.specified=true");

        // Get all the citizensList where title is null
        defaultCitizensShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByTitleContainsSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where title contains DEFAULT_TITLE
        defaultCitizensShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the citizensList where title contains UPDATED_TITLE
        defaultCitizensShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCitizensByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where title does not contain DEFAULT_TITLE
        defaultCitizensShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the citizensList where title does not contain UPDATED_TITLE
        defaultCitizensShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCitizensByLastnameIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where lastname equals to DEFAULT_LASTNAME
        defaultCitizensShouldBeFound("lastname.equals=" + DEFAULT_LASTNAME);

        // Get all the citizensList where lastname equals to UPDATED_LASTNAME
        defaultCitizensShouldNotBeFound("lastname.equals=" + UPDATED_LASTNAME);
    }

    @Test
    @Transactional
    void getAllCitizensByLastnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where lastname not equals to DEFAULT_LASTNAME
        defaultCitizensShouldNotBeFound("lastname.notEquals=" + DEFAULT_LASTNAME);

        // Get all the citizensList where lastname not equals to UPDATED_LASTNAME
        defaultCitizensShouldBeFound("lastname.notEquals=" + UPDATED_LASTNAME);
    }

    @Test
    @Transactional
    void getAllCitizensByLastnameIsInShouldWork() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where lastname in DEFAULT_LASTNAME or UPDATED_LASTNAME
        defaultCitizensShouldBeFound("lastname.in=" + DEFAULT_LASTNAME + "," + UPDATED_LASTNAME);

        // Get all the citizensList where lastname equals to UPDATED_LASTNAME
        defaultCitizensShouldNotBeFound("lastname.in=" + UPDATED_LASTNAME);
    }

    @Test
    @Transactional
    void getAllCitizensByLastnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where lastname is not null
        defaultCitizensShouldBeFound("lastname.specified=true");

        // Get all the citizensList where lastname is null
        defaultCitizensShouldNotBeFound("lastname.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByLastnameContainsSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where lastname contains DEFAULT_LASTNAME
        defaultCitizensShouldBeFound("lastname.contains=" + DEFAULT_LASTNAME);

        // Get all the citizensList where lastname contains UPDATED_LASTNAME
        defaultCitizensShouldNotBeFound("lastname.contains=" + UPDATED_LASTNAME);
    }

    @Test
    @Transactional
    void getAllCitizensByLastnameNotContainsSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where lastname does not contain DEFAULT_LASTNAME
        defaultCitizensShouldNotBeFound("lastname.doesNotContain=" + DEFAULT_LASTNAME);

        // Get all the citizensList where lastname does not contain UPDATED_LASTNAME
        defaultCitizensShouldBeFound("lastname.doesNotContain=" + UPDATED_LASTNAME);
    }

    @Test
    @Transactional
    void getAllCitizensByFirstnameIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where firstname equals to DEFAULT_FIRSTNAME
        defaultCitizensShouldBeFound("firstname.equals=" + DEFAULT_FIRSTNAME);

        // Get all the citizensList where firstname equals to UPDATED_FIRSTNAME
        defaultCitizensShouldNotBeFound("firstname.equals=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    void getAllCitizensByFirstnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where firstname not equals to DEFAULT_FIRSTNAME
        defaultCitizensShouldNotBeFound("firstname.notEquals=" + DEFAULT_FIRSTNAME);

        // Get all the citizensList where firstname not equals to UPDATED_FIRSTNAME
        defaultCitizensShouldBeFound("firstname.notEquals=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    void getAllCitizensByFirstnameIsInShouldWork() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where firstname in DEFAULT_FIRSTNAME or UPDATED_FIRSTNAME
        defaultCitizensShouldBeFound("firstname.in=" + DEFAULT_FIRSTNAME + "," + UPDATED_FIRSTNAME);

        // Get all the citizensList where firstname equals to UPDATED_FIRSTNAME
        defaultCitizensShouldNotBeFound("firstname.in=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    void getAllCitizensByFirstnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where firstname is not null
        defaultCitizensShouldBeFound("firstname.specified=true");

        // Get all the citizensList where firstname is null
        defaultCitizensShouldNotBeFound("firstname.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByFirstnameContainsSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where firstname contains DEFAULT_FIRSTNAME
        defaultCitizensShouldBeFound("firstname.contains=" + DEFAULT_FIRSTNAME);

        // Get all the citizensList where firstname contains UPDATED_FIRSTNAME
        defaultCitizensShouldNotBeFound("firstname.contains=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    void getAllCitizensByFirstnameNotContainsSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where firstname does not contain DEFAULT_FIRSTNAME
        defaultCitizensShouldNotBeFound("firstname.doesNotContain=" + DEFAULT_FIRSTNAME);

        // Get all the citizensList where firstname does not contain UPDATED_FIRSTNAME
        defaultCitizensShouldBeFound("firstname.doesNotContain=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    void getAllCitizensByFathersNameIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where fathersName equals to DEFAULT_FATHERS_NAME
        defaultCitizensShouldBeFound("fathersName.equals=" + DEFAULT_FATHERS_NAME);

        // Get all the citizensList where fathersName equals to UPDATED_FATHERS_NAME
        defaultCitizensShouldNotBeFound("fathersName.equals=" + UPDATED_FATHERS_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByFathersNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where fathersName not equals to DEFAULT_FATHERS_NAME
        defaultCitizensShouldNotBeFound("fathersName.notEquals=" + DEFAULT_FATHERS_NAME);

        // Get all the citizensList where fathersName not equals to UPDATED_FATHERS_NAME
        defaultCitizensShouldBeFound("fathersName.notEquals=" + UPDATED_FATHERS_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByFathersNameIsInShouldWork() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where fathersName in DEFAULT_FATHERS_NAME or UPDATED_FATHERS_NAME
        defaultCitizensShouldBeFound("fathersName.in=" + DEFAULT_FATHERS_NAME + "," + UPDATED_FATHERS_NAME);

        // Get all the citizensList where fathersName equals to UPDATED_FATHERS_NAME
        defaultCitizensShouldNotBeFound("fathersName.in=" + UPDATED_FATHERS_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByFathersNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where fathersName is not null
        defaultCitizensShouldBeFound("fathersName.specified=true");

        // Get all the citizensList where fathersName is null
        defaultCitizensShouldNotBeFound("fathersName.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByFathersNameContainsSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where fathersName contains DEFAULT_FATHERS_NAME
        defaultCitizensShouldBeFound("fathersName.contains=" + DEFAULT_FATHERS_NAME);

        // Get all the citizensList where fathersName contains UPDATED_FATHERS_NAME
        defaultCitizensShouldNotBeFound("fathersName.contains=" + UPDATED_FATHERS_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByFathersNameNotContainsSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where fathersName does not contain DEFAULT_FATHERS_NAME
        defaultCitizensShouldNotBeFound("fathersName.doesNotContain=" + DEFAULT_FATHERS_NAME);

        // Get all the citizensList where fathersName does not contain UPDATED_FATHERS_NAME
        defaultCitizensShouldBeFound("fathersName.doesNotContain=" + UPDATED_FATHERS_NAME);
    }

    @Test
    @Transactional
    void getAllCitizensByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultCitizensShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the citizensList where birthDate equals to UPDATED_BIRTH_DATE
        defaultCitizensShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllCitizensByBirthDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where birthDate not equals to DEFAULT_BIRTH_DATE
        defaultCitizensShouldNotBeFound("birthDate.notEquals=" + DEFAULT_BIRTH_DATE);

        // Get all the citizensList where birthDate not equals to UPDATED_BIRTH_DATE
        defaultCitizensShouldBeFound("birthDate.notEquals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllCitizensByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultCitizensShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the citizensList where birthDate equals to UPDATED_BIRTH_DATE
        defaultCitizensShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllCitizensByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where birthDate is not null
        defaultCitizensShouldBeFound("birthDate.specified=true");

        // Get all the citizensList where birthDate is null
        defaultCitizensShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByBirthDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where birthDate is greater than or equal to DEFAULT_BIRTH_DATE
        defaultCitizensShouldBeFound("birthDate.greaterThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the citizensList where birthDate is greater than or equal to UPDATED_BIRTH_DATE
        defaultCitizensShouldNotBeFound("birthDate.greaterThanOrEqual=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllCitizensByBirthDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where birthDate is less than or equal to DEFAULT_BIRTH_DATE
        defaultCitizensShouldBeFound("birthDate.lessThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the citizensList where birthDate is less than or equal to SMALLER_BIRTH_DATE
        defaultCitizensShouldNotBeFound("birthDate.lessThanOrEqual=" + SMALLER_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllCitizensByBirthDateIsLessThanSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where birthDate is less than DEFAULT_BIRTH_DATE
        defaultCitizensShouldNotBeFound("birthDate.lessThan=" + DEFAULT_BIRTH_DATE);

        // Get all the citizensList where birthDate is less than UPDATED_BIRTH_DATE
        defaultCitizensShouldBeFound("birthDate.lessThan=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllCitizensByBirthDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where birthDate is greater than DEFAULT_BIRTH_DATE
        defaultCitizensShouldNotBeFound("birthDate.greaterThan=" + DEFAULT_BIRTH_DATE);

        // Get all the citizensList where birthDate is greater than SMALLER_BIRTH_DATE
        defaultCitizensShouldBeFound("birthDate.greaterThan=" + SMALLER_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllCitizensByGiortaziIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where giortazi equals to DEFAULT_GIORTAZI
        defaultCitizensShouldBeFound("giortazi.equals=" + DEFAULT_GIORTAZI);

        // Get all the citizensList where giortazi equals to UPDATED_GIORTAZI
        defaultCitizensShouldNotBeFound("giortazi.equals=" + UPDATED_GIORTAZI);
    }

    @Test
    @Transactional
    void getAllCitizensByGiortaziIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where giortazi not equals to DEFAULT_GIORTAZI
        defaultCitizensShouldNotBeFound("giortazi.notEquals=" + DEFAULT_GIORTAZI);

        // Get all the citizensList where giortazi not equals to UPDATED_GIORTAZI
        defaultCitizensShouldBeFound("giortazi.notEquals=" + UPDATED_GIORTAZI);
    }

    @Test
    @Transactional
    void getAllCitizensByGiortaziIsInShouldWork() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where giortazi in DEFAULT_GIORTAZI or UPDATED_GIORTAZI
        defaultCitizensShouldBeFound("giortazi.in=" + DEFAULT_GIORTAZI + "," + UPDATED_GIORTAZI);

        // Get all the citizensList where giortazi equals to UPDATED_GIORTAZI
        defaultCitizensShouldNotBeFound("giortazi.in=" + UPDATED_GIORTAZI);
    }

    @Test
    @Transactional
    void getAllCitizensByGiortaziIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where giortazi is not null
        defaultCitizensShouldBeFound("giortazi.specified=true");

        // Get all the citizensList where giortazi is null
        defaultCitizensShouldNotBeFound("giortazi.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByGiortaziContainsSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where giortazi contains DEFAULT_GIORTAZI
        defaultCitizensShouldBeFound("giortazi.contains=" + DEFAULT_GIORTAZI);

        // Get all the citizensList where giortazi contains UPDATED_GIORTAZI
        defaultCitizensShouldNotBeFound("giortazi.contains=" + UPDATED_GIORTAZI);
    }

    @Test
    @Transactional
    void getAllCitizensByGiortaziNotContainsSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where giortazi does not contain DEFAULT_GIORTAZI
        defaultCitizensShouldNotBeFound("giortazi.doesNotContain=" + DEFAULT_GIORTAZI);

        // Get all the citizensList where giortazi does not contain UPDATED_GIORTAZI
        defaultCitizensShouldBeFound("giortazi.doesNotContain=" + UPDATED_GIORTAZI);
    }

    @Test
    @Transactional
    void getAllCitizensByMaleIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where male equals to DEFAULT_MALE
        defaultCitizensShouldBeFound("male.equals=" + DEFAULT_MALE);

        // Get all the citizensList where male equals to UPDATED_MALE
        defaultCitizensShouldNotBeFound("male.equals=" + UPDATED_MALE);
    }

    @Test
    @Transactional
    void getAllCitizensByMaleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where male not equals to DEFAULT_MALE
        defaultCitizensShouldNotBeFound("male.notEquals=" + DEFAULT_MALE);

        // Get all the citizensList where male not equals to UPDATED_MALE
        defaultCitizensShouldBeFound("male.notEquals=" + UPDATED_MALE);
    }

    @Test
    @Transactional
    void getAllCitizensByMaleIsInShouldWork() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where male in DEFAULT_MALE or UPDATED_MALE
        defaultCitizensShouldBeFound("male.in=" + DEFAULT_MALE + "," + UPDATED_MALE);

        // Get all the citizensList where male equals to UPDATED_MALE
        defaultCitizensShouldNotBeFound("male.in=" + UPDATED_MALE);
    }

    @Test
    @Transactional
    void getAllCitizensByMaleIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where male is not null
        defaultCitizensShouldBeFound("male.specified=true");

        // Get all the citizensList where male is null
        defaultCitizensShouldNotBeFound("male.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByMeLetterIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLetter equals to DEFAULT_ME_LETTER
        defaultCitizensShouldBeFound("meLetter.equals=" + DEFAULT_ME_LETTER);

        // Get all the citizensList where meLetter equals to UPDATED_ME_LETTER
        defaultCitizensShouldNotBeFound("meLetter.equals=" + UPDATED_ME_LETTER);
    }

    @Test
    @Transactional
    void getAllCitizensByMeLetterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLetter not equals to DEFAULT_ME_LETTER
        defaultCitizensShouldNotBeFound("meLetter.notEquals=" + DEFAULT_ME_LETTER);

        // Get all the citizensList where meLetter not equals to UPDATED_ME_LETTER
        defaultCitizensShouldBeFound("meLetter.notEquals=" + UPDATED_ME_LETTER);
    }

    @Test
    @Transactional
    void getAllCitizensByMeLetterIsInShouldWork() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLetter in DEFAULT_ME_LETTER or UPDATED_ME_LETTER
        defaultCitizensShouldBeFound("meLetter.in=" + DEFAULT_ME_LETTER + "," + UPDATED_ME_LETTER);

        // Get all the citizensList where meLetter equals to UPDATED_ME_LETTER
        defaultCitizensShouldNotBeFound("meLetter.in=" + UPDATED_ME_LETTER);
    }

    @Test
    @Transactional
    void getAllCitizensByMeLetterIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLetter is not null
        defaultCitizensShouldBeFound("meLetter.specified=true");

        // Get all the citizensList where meLetter is null
        defaultCitizensShouldNotBeFound("meLetter.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByMeLetterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLetter is greater than or equal to DEFAULT_ME_LETTER
        defaultCitizensShouldBeFound("meLetter.greaterThanOrEqual=" + DEFAULT_ME_LETTER);

        // Get all the citizensList where meLetter is greater than or equal to UPDATED_ME_LETTER
        defaultCitizensShouldNotBeFound("meLetter.greaterThanOrEqual=" + UPDATED_ME_LETTER);
    }

    @Test
    @Transactional
    void getAllCitizensByMeLetterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLetter is less than or equal to DEFAULT_ME_LETTER
        defaultCitizensShouldBeFound("meLetter.lessThanOrEqual=" + DEFAULT_ME_LETTER);

        // Get all the citizensList where meLetter is less than or equal to SMALLER_ME_LETTER
        defaultCitizensShouldNotBeFound("meLetter.lessThanOrEqual=" + SMALLER_ME_LETTER);
    }

    @Test
    @Transactional
    void getAllCitizensByMeLetterIsLessThanSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLetter is less than DEFAULT_ME_LETTER
        defaultCitizensShouldNotBeFound("meLetter.lessThan=" + DEFAULT_ME_LETTER);

        // Get all the citizensList where meLetter is less than UPDATED_ME_LETTER
        defaultCitizensShouldBeFound("meLetter.lessThan=" + UPDATED_ME_LETTER);
    }

    @Test
    @Transactional
    void getAllCitizensByMeLetterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLetter is greater than DEFAULT_ME_LETTER
        defaultCitizensShouldNotBeFound("meLetter.greaterThan=" + DEFAULT_ME_LETTER);

        // Get all the citizensList where meLetter is greater than SMALLER_ME_LETTER
        defaultCitizensShouldBeFound("meLetter.greaterThan=" + SMALLER_ME_LETTER);
    }

    @Test
    @Transactional
    void getAllCitizensByMeLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLabel equals to DEFAULT_ME_LABEL
        defaultCitizensShouldBeFound("meLabel.equals=" + DEFAULT_ME_LABEL);

        // Get all the citizensList where meLabel equals to UPDATED_ME_LABEL
        defaultCitizensShouldNotBeFound("meLabel.equals=" + UPDATED_ME_LABEL);
    }

    @Test
    @Transactional
    void getAllCitizensByMeLabelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLabel not equals to DEFAULT_ME_LABEL
        defaultCitizensShouldNotBeFound("meLabel.notEquals=" + DEFAULT_ME_LABEL);

        // Get all the citizensList where meLabel not equals to UPDATED_ME_LABEL
        defaultCitizensShouldBeFound("meLabel.notEquals=" + UPDATED_ME_LABEL);
    }

    @Test
    @Transactional
    void getAllCitizensByMeLabelIsInShouldWork() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLabel in DEFAULT_ME_LABEL or UPDATED_ME_LABEL
        defaultCitizensShouldBeFound("meLabel.in=" + DEFAULT_ME_LABEL + "," + UPDATED_ME_LABEL);

        // Get all the citizensList where meLabel equals to UPDATED_ME_LABEL
        defaultCitizensShouldNotBeFound("meLabel.in=" + UPDATED_ME_LABEL);
    }

    @Test
    @Transactional
    void getAllCitizensByMeLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLabel is not null
        defaultCitizensShouldBeFound("meLabel.specified=true");

        // Get all the citizensList where meLabel is null
        defaultCitizensShouldNotBeFound("meLabel.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizensByMeLabelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLabel is greater than or equal to DEFAULT_ME_LABEL
        defaultCitizensShouldBeFound("meLabel.greaterThanOrEqual=" + DEFAULT_ME_LABEL);

        // Get all the citizensList where meLabel is greater than or equal to UPDATED_ME_LABEL
        defaultCitizensShouldNotBeFound("meLabel.greaterThanOrEqual=" + UPDATED_ME_LABEL);
    }

    @Test
    @Transactional
    void getAllCitizensByMeLabelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLabel is less than or equal to DEFAULT_ME_LABEL
        defaultCitizensShouldBeFound("meLabel.lessThanOrEqual=" + DEFAULT_ME_LABEL);

        // Get all the citizensList where meLabel is less than or equal to SMALLER_ME_LABEL
        defaultCitizensShouldNotBeFound("meLabel.lessThanOrEqual=" + SMALLER_ME_LABEL);
    }

    @Test
    @Transactional
    void getAllCitizensByMeLabelIsLessThanSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLabel is less than DEFAULT_ME_LABEL
        defaultCitizensShouldNotBeFound("meLabel.lessThan=" + DEFAULT_ME_LABEL);

        // Get all the citizensList where meLabel is less than UPDATED_ME_LABEL
        defaultCitizensShouldBeFound("meLabel.lessThan=" + UPDATED_ME_LABEL);
    }

    @Test
    @Transactional
    void getAllCitizensByMeLabelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        // Get all the citizensList where meLabel is greater than DEFAULT_ME_LABEL
        defaultCitizensShouldNotBeFound("meLabel.greaterThan=" + DEFAULT_ME_LABEL);

        // Get all the citizensList where meLabel is greater than SMALLER_ME_LABEL
        defaultCitizensShouldBeFound("meLabel.greaterThan=" + SMALLER_ME_LABEL);
    }

    @Test
    @Transactional
    void getAllCitizensByFolderIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);
        CitizenFolders folder;
        if (TestUtil.findAll(em, CitizenFolders.class).isEmpty()) {
            folder = CitizenFoldersResourceIT.createEntity(em);
            em.persist(folder);
            em.flush();
        } else {
            folder = TestUtil.findAll(em, CitizenFolders.class).get(0);
        }
        em.persist(folder);
        em.flush();
        citizens.setFolder(folder);
        citizensRepository.saveAndFlush(citizens);
        Long folderId = folder.getId();

        // Get all the citizensList where folder equals to folderId
        defaultCitizensShouldBeFound("folderId.equals=" + folderId);

        // Get all the citizensList where folder equals to (folderId + 1)
        defaultCitizensShouldNotBeFound("folderId.equals=" + (folderId + 1));
    }

    @Test
    @Transactional
    void getAllCitizensByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);
        Companies company;
        if (TestUtil.findAll(em, Companies.class).isEmpty()) {
            company = CompaniesResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Companies.class).get(0);
        }
        em.persist(company);
        em.flush();
        citizens.setCompany(company);
        citizensRepository.saveAndFlush(citizens);
        Long companyId = company.getId();

        // Get all the citizensList where company equals to companyId
        defaultCitizensShouldBeFound("companyId.equals=" + companyId);

        // Get all the citizensList where company equals to (companyId + 1)
        defaultCitizensShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    @Test
    @Transactional
    void getAllCitizensByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);
        MaritalStatus maritalStatus;
        if (TestUtil.findAll(em, MaritalStatus.class).isEmpty()) {
            maritalStatus = MaritalStatusResourceIT.createEntity(em);
            em.persist(maritalStatus);
            em.flush();
        } else {
            maritalStatus = TestUtil.findAll(em, MaritalStatus.class).get(0);
        }
        em.persist(maritalStatus);
        em.flush();
        citizens.setMaritalStatus(maritalStatus);
        citizensRepository.saveAndFlush(citizens);
        Long maritalStatusId = maritalStatus.getId();

        // Get all the citizensList where maritalStatus equals to maritalStatusId
        defaultCitizensShouldBeFound("maritalStatusId.equals=" + maritalStatusId);

        // Get all the citizensList where maritalStatus equals to (maritalStatusId + 1)
        defaultCitizensShouldNotBeFound("maritalStatusId.equals=" + (maritalStatusId + 1));
    }

    @Test
    @Transactional
    void getAllCitizensByTeamIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);
        Teams team;
        if (TestUtil.findAll(em, Teams.class).isEmpty()) {
            team = TeamsResourceIT.createEntity(em);
            em.persist(team);
            em.flush();
        } else {
            team = TestUtil.findAll(em, Teams.class).get(0);
        }
        em.persist(team);
        em.flush();
        citizens.setTeam(team);
        citizensRepository.saveAndFlush(citizens);
        Long teamId = team.getId();

        // Get all the citizensList where team equals to teamId
        defaultCitizensShouldBeFound("teamId.equals=" + teamId);

        // Get all the citizensList where team equals to (teamId + 1)
        defaultCitizensShouldNotBeFound("teamId.equals=" + (teamId + 1));
    }

    @Test
    @Transactional
    void getAllCitizensByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);
        Codes code;
        if (TestUtil.findAll(em, Codes.class).isEmpty()) {
            code = CodesResourceIT.createEntity(em);
            em.persist(code);
            em.flush();
        } else {
            code = TestUtil.findAll(em, Codes.class).get(0);
        }
        em.persist(code);
        em.flush();
        citizens.setCode(code);
        citizensRepository.saveAndFlush(citizens);
        Long codeId = code.getId();

        // Get all the citizensList where code equals to codeId
        defaultCitizensShouldBeFound("codeId.equals=" + codeId);

        // Get all the citizensList where code equals to (codeId + 1)
        defaultCitizensShouldNotBeFound("codeId.equals=" + (codeId + 1));
    }

    @Test
    @Transactional
    void getAllCitizensByOriginIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);
        Origins origin;
        if (TestUtil.findAll(em, Origins.class).isEmpty()) {
            origin = OriginsResourceIT.createEntity(em);
            em.persist(origin);
            em.flush();
        } else {
            origin = TestUtil.findAll(em, Origins.class).get(0);
        }
        em.persist(origin);
        em.flush();
        citizens.setOrigin(origin);
        citizensRepository.saveAndFlush(citizens);
        Long originId = origin.getId();

        // Get all the citizensList where origin equals to originId
        defaultCitizensShouldBeFound("originId.equals=" + originId);

        // Get all the citizensList where origin equals to (originId + 1)
        defaultCitizensShouldNotBeFound("originId.equals=" + (originId + 1));
    }

    @Test
    @Transactional
    void getAllCitizensByJobIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);
        Jobs job;
        if (TestUtil.findAll(em, Jobs.class).isEmpty()) {
            job = JobsResourceIT.createEntity(em);
            em.persist(job);
            em.flush();
        } else {
            job = TestUtil.findAll(em, Jobs.class).get(0);
        }
        em.persist(job);
        em.flush();
        citizens.setJob(job);
        citizensRepository.saveAndFlush(citizens);
        Long jobId = job.getId();

        // Get all the citizensList where job equals to jobId
        defaultCitizensShouldBeFound("jobId.equals=" + jobId);

        // Get all the citizensList where job equals to (jobId + 1)
        defaultCitizensShouldNotBeFound("jobId.equals=" + (jobId + 1));
    }

    @Test
    @Transactional
    void getAllCitizensByPhonesIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);
        Phones phones;
        if (TestUtil.findAll(em, Phones.class).isEmpty()) {
            phones = PhonesResourceIT.createEntity(em);
            em.persist(phones);
            em.flush();
        } else {
            phones = TestUtil.findAll(em, Phones.class).get(0);
        }
        em.persist(phones);
        em.flush();
        citizens.addPhones(phones);
        citizensRepository.saveAndFlush(citizens);
        Long phonesId = phones.getId();

        // Get all the citizensList where phones equals to phonesId
        defaultCitizensShouldBeFound("phonesId.equals=" + phonesId);

        // Get all the citizensList where phones equals to (phonesId + 1)
        defaultCitizensShouldNotBeFound("phonesId.equals=" + (phonesId + 1));
    }

    @Test
    @Transactional
    void getAllCitizensByAddressesIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);
        Addresses addresses;
        if (TestUtil.findAll(em, Addresses.class).isEmpty()) {
            addresses = AddressesResourceIT.createEntity(em);
            em.persist(addresses);
            em.flush();
        } else {
            addresses = TestUtil.findAll(em, Addresses.class).get(0);
        }
        em.persist(addresses);
        em.flush();
        citizens.addAddresses(addresses);
        citizensRepository.saveAndFlush(citizens);
        Long addressesId = addresses.getId();

        // Get all the citizensList where addresses equals to addressesId
        defaultCitizensShouldBeFound("addressesId.equals=" + addressesId);

        // Get all the citizensList where addresses equals to (addressesId + 1)
        defaultCitizensShouldNotBeFound("addressesId.equals=" + (addressesId + 1));
    }

    @Test
    @Transactional
    void getAllCitizensBySocialIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);
        SocialContacts social;
        if (TestUtil.findAll(em, SocialContacts.class).isEmpty()) {
            social = SocialContactsResourceIT.createEntity(em);
            em.persist(social);
            em.flush();
        } else {
            social = TestUtil.findAll(em, SocialContacts.class).get(0);
        }
        em.persist(social);
        em.flush();
        citizens.addSocial(social);
        citizensRepository.saveAndFlush(citizens);
        Long socialId = social.getId();

        // Get all the citizensList where social equals to socialId
        defaultCitizensShouldBeFound("socialId.equals=" + socialId);

        // Get all the citizensList where social equals to (socialId + 1)
        defaultCitizensShouldNotBeFound("socialId.equals=" + (socialId + 1));
    }

    @Test
    @Transactional
    void getAllCitizensByEmailsIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);
        Emails emails;
        if (TestUtil.findAll(em, Emails.class).isEmpty()) {
            emails = EmailsResourceIT.createEntity(em);
            em.persist(emails);
            em.flush();
        } else {
            emails = TestUtil.findAll(em, Emails.class).get(0);
        }
        em.persist(emails);
        em.flush();
        citizens.addEmails(emails);
        citizensRepository.saveAndFlush(citizens);
        Long emailsId = emails.getId();

        // Get all the citizensList where emails equals to emailsId
        defaultCitizensShouldBeFound("emailsId.equals=" + emailsId);

        // Get all the citizensList where emails equals to (emailsId + 1)
        defaultCitizensShouldNotBeFound("emailsId.equals=" + (emailsId + 1));
    }

    @Test
    @Transactional
    void getAllCitizensByRelationsIsEqualToSomething() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);
        CitizensRelations relations;
        if (TestUtil.findAll(em, CitizensRelations.class).isEmpty()) {
            relations = CitizensRelationsResourceIT.createEntity(em);
            em.persist(relations);
            em.flush();
        } else {
            relations = TestUtil.findAll(em, CitizensRelations.class).get(0);
        }
        em.persist(relations);
        em.flush();
        citizens.addRelations(relations);
        citizensRepository.saveAndFlush(citizens);
        Long relationsId = relations.getId();

        // Get all the citizensList where relations equals to relationsId
        defaultCitizensShouldBeFound("relationsId.equals=" + relationsId);

        // Get all the citizensList where relations equals to (relationsId + 1)
        defaultCitizensShouldNotBeFound("relationsId.equals=" + (relationsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCitizensShouldBeFound(String filter) throws Exception {
        restCitizensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citizens.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].fathersName").value(hasItem(DEFAULT_FATHERS_NAME)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].giortazi").value(hasItem(DEFAULT_GIORTAZI)))
            .andExpect(jsonPath("$.[*].male").value(hasItem(DEFAULT_MALE.booleanValue())))
            .andExpect(jsonPath("$.[*].meLetter").value(hasItem(DEFAULT_ME_LETTER)))
            .andExpect(jsonPath("$.[*].meLabel").value(hasItem(DEFAULT_ME_LABEL)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));

        // Check, that the count call also returns 1
        restCitizensMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCitizensShouldNotBeFound(String filter) throws Exception {
        restCitizensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCitizensMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCitizens() throws Exception {
        // Get the citizens
        restCitizensMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCitizens() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        int databaseSizeBeforeUpdate = citizensRepository.findAll().size();

        // Update the citizens
        Citizens updatedCitizens = citizensRepository.findById(citizens.getId()).get();
        // Disconnect from session so that the updates on updatedCitizens are not directly saved in db
        em.detach(updatedCitizens);
        updatedCitizens
            .title(UPDATED_TITLE)
            .lastname(UPDATED_LASTNAME)
            .firstname(UPDATED_FIRSTNAME)
            .fathersName(UPDATED_FATHERS_NAME)
            .comments(UPDATED_COMMENTS)
            .birthDate(UPDATED_BIRTH_DATE)
            .giortazi(UPDATED_GIORTAZI)
            .male(UPDATED_MALE)
            .meLetter(UPDATED_ME_LETTER)
            .meLabel(UPDATED_ME_LABEL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restCitizensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCitizens.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCitizens))
            )
            .andExpect(status().isOk());

        // Validate the Citizens in the database
        List<Citizens> citizensList = citizensRepository.findAll();
        assertThat(citizensList).hasSize(databaseSizeBeforeUpdate);
        Citizens testCitizens = citizensList.get(citizensList.size() - 1);
        assertThat(testCitizens.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCitizens.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testCitizens.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testCitizens.getFathersName()).isEqualTo(UPDATED_FATHERS_NAME);
        assertThat(testCitizens.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testCitizens.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testCitizens.getGiortazi()).isEqualTo(UPDATED_GIORTAZI);
        assertThat(testCitizens.getMale()).isEqualTo(UPDATED_MALE);
        assertThat(testCitizens.getMeLetter()).isEqualTo(UPDATED_ME_LETTER);
        assertThat(testCitizens.getMeLabel()).isEqualTo(UPDATED_ME_LABEL);
        assertThat(testCitizens.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCitizens.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingCitizens() throws Exception {
        int databaseSizeBeforeUpdate = citizensRepository.findAll().size();
        citizens.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitizensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citizens.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citizens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Citizens in the database
        List<Citizens> citizensList = citizensRepository.findAll();
        assertThat(citizensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCitizens() throws Exception {
        int databaseSizeBeforeUpdate = citizensRepository.findAll().size();
        citizens.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citizens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Citizens in the database
        List<Citizens> citizensList = citizensRepository.findAll();
        assertThat(citizensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCitizens() throws Exception {
        int databaseSizeBeforeUpdate = citizensRepository.findAll().size();
        citizens.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizensMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizens)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Citizens in the database
        List<Citizens> citizensList = citizensRepository.findAll();
        assertThat(citizensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCitizensWithPatch() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        int databaseSizeBeforeUpdate = citizensRepository.findAll().size();

        // Update the citizens using partial update
        Citizens partialUpdatedCitizens = new Citizens();
        partialUpdatedCitizens.setId(citizens.getId());

        partialUpdatedCitizens.fathersName(UPDATED_FATHERS_NAME).birthDate(UPDATED_BIRTH_DATE).male(UPDATED_MALE).meLabel(UPDATED_ME_LABEL);

        restCitizensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCitizens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCitizens))
            )
            .andExpect(status().isOk());

        // Validate the Citizens in the database
        List<Citizens> citizensList = citizensRepository.findAll();
        assertThat(citizensList).hasSize(databaseSizeBeforeUpdate);
        Citizens testCitizens = citizensList.get(citizensList.size() - 1);
        assertThat(testCitizens.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCitizens.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testCitizens.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testCitizens.getFathersName()).isEqualTo(UPDATED_FATHERS_NAME);
        assertThat(testCitizens.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testCitizens.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testCitizens.getGiortazi()).isEqualTo(DEFAULT_GIORTAZI);
        assertThat(testCitizens.getMale()).isEqualTo(UPDATED_MALE);
        assertThat(testCitizens.getMeLetter()).isEqualTo(DEFAULT_ME_LETTER);
        assertThat(testCitizens.getMeLabel()).isEqualTo(UPDATED_ME_LABEL);
        assertThat(testCitizens.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCitizens.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCitizensWithPatch() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        int databaseSizeBeforeUpdate = citizensRepository.findAll().size();

        // Update the citizens using partial update
        Citizens partialUpdatedCitizens = new Citizens();
        partialUpdatedCitizens.setId(citizens.getId());

        partialUpdatedCitizens
            .title(UPDATED_TITLE)
            .lastname(UPDATED_LASTNAME)
            .firstname(UPDATED_FIRSTNAME)
            .fathersName(UPDATED_FATHERS_NAME)
            .comments(UPDATED_COMMENTS)
            .birthDate(UPDATED_BIRTH_DATE)
            .giortazi(UPDATED_GIORTAZI)
            .male(UPDATED_MALE)
            .meLetter(UPDATED_ME_LETTER)
            .meLabel(UPDATED_ME_LABEL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restCitizensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCitizens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCitizens))
            )
            .andExpect(status().isOk());

        // Validate the Citizens in the database
        List<Citizens> citizensList = citizensRepository.findAll();
        assertThat(citizensList).hasSize(databaseSizeBeforeUpdate);
        Citizens testCitizens = citizensList.get(citizensList.size() - 1);
        assertThat(testCitizens.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCitizens.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testCitizens.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testCitizens.getFathersName()).isEqualTo(UPDATED_FATHERS_NAME);
        assertThat(testCitizens.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testCitizens.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testCitizens.getGiortazi()).isEqualTo(UPDATED_GIORTAZI);
        assertThat(testCitizens.getMale()).isEqualTo(UPDATED_MALE);
        assertThat(testCitizens.getMeLetter()).isEqualTo(UPDATED_ME_LETTER);
        assertThat(testCitizens.getMeLabel()).isEqualTo(UPDATED_ME_LABEL);
        assertThat(testCitizens.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCitizens.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCitizens() throws Exception {
        int databaseSizeBeforeUpdate = citizensRepository.findAll().size();
        citizens.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitizensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, citizens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citizens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Citizens in the database
        List<Citizens> citizensList = citizensRepository.findAll();
        assertThat(citizensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCitizens() throws Exception {
        int databaseSizeBeforeUpdate = citizensRepository.findAll().size();
        citizens.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citizens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Citizens in the database
        List<Citizens> citizensList = citizensRepository.findAll();
        assertThat(citizensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCitizens() throws Exception {
        int databaseSizeBeforeUpdate = citizensRepository.findAll().size();
        citizens.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizensMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(citizens)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Citizens in the database
        List<Citizens> citizensList = citizensRepository.findAll();
        assertThat(citizensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCitizens() throws Exception {
        // Initialize the database
        citizensRepository.saveAndFlush(citizens);

        int databaseSizeBeforeDelete = citizensRepository.findAll().size();

        // Delete the citizens
        restCitizensMockMvc
            .perform(delete(ENTITY_API_URL_ID, citizens.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Citizens> citizensList = citizensRepository.findAll();
        assertThat(citizensList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
