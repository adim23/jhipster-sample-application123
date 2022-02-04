package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Department;
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.Job;
import com.mycompany.myapp.repository.EmployeeRepository;
import com.mycompany.myapp.service.criteria.EmployeeCriteria;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "KOb8Z3moc-e@piGuB.UHW";
    private static final String UPDATED_EMAIL = "RDoecK-Whb4cxI@xj-kJ.3WL.nax.CxR.kX.8EF.3v";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_HIRE_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HIRE_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ZonedDateTime DEFAULT_ZONED_HIRE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ZONED_HIRE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ZONED_HIRE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final LocalDate DEFAULT_HIRE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HIRE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_HIRE_DATE = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_SALARY = 0L;
    private static final Long UPDATED_SALARY = 1L;
    private static final Long SMALLER_SALARY = 0L - 1L;

    private static final Long DEFAULT_COMMISSION_PCT = 0L;
    private static final Long UPDATED_COMMISSION_PCT = 1L;
    private static final Long SMALLER_COMMISSION_PCT = 0L - 1L;

    private static final Duration DEFAULT_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_DURATION = Duration.ofHours(12);
    private static final Duration SMALLER_DURATION = Duration.ofHours(5);

    private static final byte[] DEFAULT_PICT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CV = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CV = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CV_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CV_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .fullName(DEFAULT_FULL_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .hireDateTime(DEFAULT_HIRE_DATE_TIME)
            .zonedHireDateTime(DEFAULT_ZONED_HIRE_DATE_TIME)
            .hireDate(DEFAULT_HIRE_DATE)
            .salary(DEFAULT_SALARY)
            .commissionPct(DEFAULT_COMMISSION_PCT)
            .duration(DEFAULT_DURATION)
            .pict(DEFAULT_PICT)
            .pictContentType(DEFAULT_PICT_CONTENT_TYPE)
            .comments(DEFAULT_COMMENTS)
            .cv(DEFAULT_CV)
            .cvContentType(DEFAULT_CV_CONTENT_TYPE)
            .active(DEFAULT_ACTIVE);
        return employee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDateTime(UPDATED_HIRE_DATE_TIME)
            .zonedHireDateTime(UPDATED_ZONED_HIRE_DATE_TIME)
            .hireDate(UPDATED_HIRE_DATE)
            .salary(UPDATED_SALARY)
            .commissionPct(UPDATED_COMMISSION_PCT)
            .duration(UPDATED_DURATION)
            .pict(UPDATED_PICT)
            .pictContentType(UPDATED_PICT_CONTENT_TYPE)
            .comments(UPDATED_COMMENTS)
            .cv(UPDATED_CV)
            .cvContentType(UPDATED_CV_CONTENT_TYPE)
            .active(UPDATED_ACTIVE);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();
        // Create the Employee
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEmployee.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testEmployee.getHireDateTime()).isEqualTo(DEFAULT_HIRE_DATE_TIME);
        assertThat(testEmployee.getZonedHireDateTime()).isEqualTo(DEFAULT_ZONED_HIRE_DATE_TIME);
        assertThat(testEmployee.getHireDate()).isEqualTo(DEFAULT_HIRE_DATE);
        assertThat(testEmployee.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testEmployee.getCommissionPct()).isEqualTo(DEFAULT_COMMISSION_PCT);
        assertThat(testEmployee.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testEmployee.getPict()).isEqualTo(DEFAULT_PICT);
        assertThat(testEmployee.getPictContentType()).isEqualTo(DEFAULT_PICT_CONTENT_TYPE);
        assertThat(testEmployee.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testEmployee.getCv()).isEqualTo(DEFAULT_CV);
        assertThat(testEmployee.getCvContentType()).isEqualTo(DEFAULT_CV_CONTENT_TYPE);
        assertThat(testEmployee.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void createEmployeeWithExistingId() throws Exception {
        // Create the Employee with an existing ID
        employee.setId(1L);

        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setFirstName(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setLastName(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setFullName(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setEmail(null);

        // Create the Employee, which fails.

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].hireDateTime").value(hasItem(DEFAULT_HIRE_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].zonedHireDateTime").value(hasItem(sameInstant(DEFAULT_ZONED_HIRE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].hireDate").value(hasItem(DEFAULT_HIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].commissionPct").value(hasItem(DEFAULT_COMMISSION_PCT.intValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].pictContentType").value(hasItem(DEFAULT_PICT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pict").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICT))))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].cvContentType").value(hasItem(DEFAULT_CV_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cv").value(hasItem(Base64Utils.encodeToString(DEFAULT_CV))))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.hireDateTime").value(DEFAULT_HIRE_DATE_TIME.toString()))
            .andExpect(jsonPath("$.zonedHireDateTime").value(sameInstant(DEFAULT_ZONED_HIRE_DATE_TIME)))
            .andExpect(jsonPath("$.hireDate").value(DEFAULT_HIRE_DATE.toString()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.intValue()))
            .andExpect(jsonPath("$.commissionPct").value(DEFAULT_COMMISSION_PCT.intValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.pictContentType").value(DEFAULT_PICT_CONTENT_TYPE))
            .andExpect(jsonPath("$.pict").value(Base64Utils.encodeToString(DEFAULT_PICT)))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.cvContentType").value(DEFAULT_CV_CONTENT_TYPE))
            .andExpect(jsonPath("$.cv").value(Base64Utils.encodeToString(DEFAULT_CV)))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        Long id = employee.getId();

        defaultEmployeeShouldBeFound("id.equals=" + id);
        defaultEmployeeShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName equals to DEFAULT_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName equals to UPDATED_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName not equals to DEFAULT_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName not equals to UPDATED_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the employeeList where firstName equals to UPDATED_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName is not null
        defaultEmployeeShouldBeFound("firstName.specified=true");

        // Get all the employeeList where firstName is null
        defaultEmployeeShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName contains DEFAULT_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName contains UPDATED_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName does not contain DEFAULT_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName does not contain UPDATED_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName equals to DEFAULT_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName equals to UPDATED_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName not equals to DEFAULT_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName not equals to UPDATED_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the employeeList where lastName equals to UPDATED_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName is not null
        defaultEmployeeShouldBeFound("lastName.specified=true");

        // Get all the employeeList where lastName is null
        defaultEmployeeShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName contains DEFAULT_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName contains UPDATED_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName does not contain DEFAULT_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName does not contain UPDATED_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName equals to DEFAULT_FULL_NAME
        defaultEmployeeShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the employeeList where fullName equals to UPDATED_FULL_NAME
        defaultEmployeeShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFullNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName not equals to DEFAULT_FULL_NAME
        defaultEmployeeShouldNotBeFound("fullName.notEquals=" + DEFAULT_FULL_NAME);

        // Get all the employeeList where fullName not equals to UPDATED_FULL_NAME
        defaultEmployeeShouldBeFound("fullName.notEquals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultEmployeeShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the employeeList where fullName equals to UPDATED_FULL_NAME
        defaultEmployeeShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName is not null
        defaultEmployeeShouldBeFound("fullName.specified=true");

        // Get all the employeeList where fullName is null
        defaultEmployeeShouldNotBeFound("fullName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByFullNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName contains DEFAULT_FULL_NAME
        defaultEmployeeShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the employeeList where fullName contains UPDATED_FULL_NAME
        defaultEmployeeShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName does not contain DEFAULT_FULL_NAME
        defaultEmployeeShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the employeeList where fullName does not contain UPDATED_FULL_NAME
        defaultEmployeeShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email equals to DEFAULT_EMAIL
        defaultEmployeeShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the employeeList where email equals to UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email not equals to DEFAULT_EMAIL
        defaultEmployeeShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the employeeList where email not equals to UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the employeeList where email equals to UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email is not null
        defaultEmployeeShouldBeFound("email.specified=true");

        // Get all the employeeList where email is null
        defaultEmployeeShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email contains DEFAULT_EMAIL
        defaultEmployeeShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the employeeList where email contains UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email does not contain DEFAULT_EMAIL
        defaultEmployeeShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the employeeList where email does not contain UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultEmployeeShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the employeeList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultEmployeeShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultEmployeeShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the employeeList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultEmployeeShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultEmployeeShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the employeeList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultEmployeeShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phoneNumber is not null
        defaultEmployeeShouldBeFound("phoneNumber.specified=true");

        // Get all the employeeList where phoneNumber is null
        defaultEmployeeShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultEmployeeShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the employeeList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultEmployeeShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultEmployeeShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the employeeList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultEmployeeShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hireDateTime equals to DEFAULT_HIRE_DATE_TIME
        defaultEmployeeShouldBeFound("hireDateTime.equals=" + DEFAULT_HIRE_DATE_TIME);

        // Get all the employeeList where hireDateTime equals to UPDATED_HIRE_DATE_TIME
        defaultEmployeeShouldNotBeFound("hireDateTime.equals=" + UPDATED_HIRE_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireDateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hireDateTime not equals to DEFAULT_HIRE_DATE_TIME
        defaultEmployeeShouldNotBeFound("hireDateTime.notEquals=" + DEFAULT_HIRE_DATE_TIME);

        // Get all the employeeList where hireDateTime not equals to UPDATED_HIRE_DATE_TIME
        defaultEmployeeShouldBeFound("hireDateTime.notEquals=" + UPDATED_HIRE_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hireDateTime in DEFAULT_HIRE_DATE_TIME or UPDATED_HIRE_DATE_TIME
        defaultEmployeeShouldBeFound("hireDateTime.in=" + DEFAULT_HIRE_DATE_TIME + "," + UPDATED_HIRE_DATE_TIME);

        // Get all the employeeList where hireDateTime equals to UPDATED_HIRE_DATE_TIME
        defaultEmployeeShouldNotBeFound("hireDateTime.in=" + UPDATED_HIRE_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hireDateTime is not null
        defaultEmployeeShouldBeFound("hireDateTime.specified=true");

        // Get all the employeeList where hireDateTime is null
        defaultEmployeeShouldNotBeFound("hireDateTime.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByZonedHireDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where zonedHireDateTime equals to DEFAULT_ZONED_HIRE_DATE_TIME
        defaultEmployeeShouldBeFound("zonedHireDateTime.equals=" + DEFAULT_ZONED_HIRE_DATE_TIME);

        // Get all the employeeList where zonedHireDateTime equals to UPDATED_ZONED_HIRE_DATE_TIME
        defaultEmployeeShouldNotBeFound("zonedHireDateTime.equals=" + UPDATED_ZONED_HIRE_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllEmployeesByZonedHireDateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where zonedHireDateTime not equals to DEFAULT_ZONED_HIRE_DATE_TIME
        defaultEmployeeShouldNotBeFound("zonedHireDateTime.notEquals=" + DEFAULT_ZONED_HIRE_DATE_TIME);

        // Get all the employeeList where zonedHireDateTime not equals to UPDATED_ZONED_HIRE_DATE_TIME
        defaultEmployeeShouldBeFound("zonedHireDateTime.notEquals=" + UPDATED_ZONED_HIRE_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllEmployeesByZonedHireDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where zonedHireDateTime in DEFAULT_ZONED_HIRE_DATE_TIME or UPDATED_ZONED_HIRE_DATE_TIME
        defaultEmployeeShouldBeFound("zonedHireDateTime.in=" + DEFAULT_ZONED_HIRE_DATE_TIME + "," + UPDATED_ZONED_HIRE_DATE_TIME);

        // Get all the employeeList where zonedHireDateTime equals to UPDATED_ZONED_HIRE_DATE_TIME
        defaultEmployeeShouldNotBeFound("zonedHireDateTime.in=" + UPDATED_ZONED_HIRE_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllEmployeesByZonedHireDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where zonedHireDateTime is not null
        defaultEmployeeShouldBeFound("zonedHireDateTime.specified=true");

        // Get all the employeeList where zonedHireDateTime is null
        defaultEmployeeShouldNotBeFound("zonedHireDateTime.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByZonedHireDateTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where zonedHireDateTime is greater than or equal to DEFAULT_ZONED_HIRE_DATE_TIME
        defaultEmployeeShouldBeFound("zonedHireDateTime.greaterThanOrEqual=" + DEFAULT_ZONED_HIRE_DATE_TIME);

        // Get all the employeeList where zonedHireDateTime is greater than or equal to UPDATED_ZONED_HIRE_DATE_TIME
        defaultEmployeeShouldNotBeFound("zonedHireDateTime.greaterThanOrEqual=" + UPDATED_ZONED_HIRE_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllEmployeesByZonedHireDateTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where zonedHireDateTime is less than or equal to DEFAULT_ZONED_HIRE_DATE_TIME
        defaultEmployeeShouldBeFound("zonedHireDateTime.lessThanOrEqual=" + DEFAULT_ZONED_HIRE_DATE_TIME);

        // Get all the employeeList where zonedHireDateTime is less than or equal to SMALLER_ZONED_HIRE_DATE_TIME
        defaultEmployeeShouldNotBeFound("zonedHireDateTime.lessThanOrEqual=" + SMALLER_ZONED_HIRE_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllEmployeesByZonedHireDateTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where zonedHireDateTime is less than DEFAULT_ZONED_HIRE_DATE_TIME
        defaultEmployeeShouldNotBeFound("zonedHireDateTime.lessThan=" + DEFAULT_ZONED_HIRE_DATE_TIME);

        // Get all the employeeList where zonedHireDateTime is less than UPDATED_ZONED_HIRE_DATE_TIME
        defaultEmployeeShouldBeFound("zonedHireDateTime.lessThan=" + UPDATED_ZONED_HIRE_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllEmployeesByZonedHireDateTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where zonedHireDateTime is greater than DEFAULT_ZONED_HIRE_DATE_TIME
        defaultEmployeeShouldNotBeFound("zonedHireDateTime.greaterThan=" + DEFAULT_ZONED_HIRE_DATE_TIME);

        // Get all the employeeList where zonedHireDateTime is greater than SMALLER_ZONED_HIRE_DATE_TIME
        defaultEmployeeShouldBeFound("zonedHireDateTime.greaterThan=" + SMALLER_ZONED_HIRE_DATE_TIME);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hireDate equals to DEFAULT_HIRE_DATE
        defaultEmployeeShouldBeFound("hireDate.equals=" + DEFAULT_HIRE_DATE);

        // Get all the employeeList where hireDate equals to UPDATED_HIRE_DATE
        defaultEmployeeShouldNotBeFound("hireDate.equals=" + UPDATED_HIRE_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hireDate not equals to DEFAULT_HIRE_DATE
        defaultEmployeeShouldNotBeFound("hireDate.notEquals=" + DEFAULT_HIRE_DATE);

        // Get all the employeeList where hireDate not equals to UPDATED_HIRE_DATE
        defaultEmployeeShouldBeFound("hireDate.notEquals=" + UPDATED_HIRE_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hireDate in DEFAULT_HIRE_DATE or UPDATED_HIRE_DATE
        defaultEmployeeShouldBeFound("hireDate.in=" + DEFAULT_HIRE_DATE + "," + UPDATED_HIRE_DATE);

        // Get all the employeeList where hireDate equals to UPDATED_HIRE_DATE
        defaultEmployeeShouldNotBeFound("hireDate.in=" + UPDATED_HIRE_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hireDate is not null
        defaultEmployeeShouldBeFound("hireDate.specified=true");

        // Get all the employeeList where hireDate is null
        defaultEmployeeShouldNotBeFound("hireDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByHireDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hireDate is greater than or equal to DEFAULT_HIRE_DATE
        defaultEmployeeShouldBeFound("hireDate.greaterThanOrEqual=" + DEFAULT_HIRE_DATE);

        // Get all the employeeList where hireDate is greater than or equal to UPDATED_HIRE_DATE
        defaultEmployeeShouldNotBeFound("hireDate.greaterThanOrEqual=" + UPDATED_HIRE_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hireDate is less than or equal to DEFAULT_HIRE_DATE
        defaultEmployeeShouldBeFound("hireDate.lessThanOrEqual=" + DEFAULT_HIRE_DATE);

        // Get all the employeeList where hireDate is less than or equal to SMALLER_HIRE_DATE
        defaultEmployeeShouldNotBeFound("hireDate.lessThanOrEqual=" + SMALLER_HIRE_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hireDate is less than DEFAULT_HIRE_DATE
        defaultEmployeeShouldNotBeFound("hireDate.lessThan=" + DEFAULT_HIRE_DATE);

        // Get all the employeeList where hireDate is less than UPDATED_HIRE_DATE
        defaultEmployeeShouldBeFound("hireDate.lessThan=" + UPDATED_HIRE_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hireDate is greater than DEFAULT_HIRE_DATE
        defaultEmployeeShouldNotBeFound("hireDate.greaterThan=" + DEFAULT_HIRE_DATE);

        // Get all the employeeList where hireDate is greater than SMALLER_HIRE_DATE
        defaultEmployeeShouldBeFound("hireDate.greaterThan=" + SMALLER_HIRE_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary equals to DEFAULT_SALARY
        defaultEmployeeShouldBeFound("salary.equals=" + DEFAULT_SALARY);

        // Get all the employeeList where salary equals to UPDATED_SALARY
        defaultEmployeeShouldNotBeFound("salary.equals=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary not equals to DEFAULT_SALARY
        defaultEmployeeShouldNotBeFound("salary.notEquals=" + DEFAULT_SALARY);

        // Get all the employeeList where salary not equals to UPDATED_SALARY
        defaultEmployeeShouldBeFound("salary.notEquals=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary in DEFAULT_SALARY or UPDATED_SALARY
        defaultEmployeeShouldBeFound("salary.in=" + DEFAULT_SALARY + "," + UPDATED_SALARY);

        // Get all the employeeList where salary equals to UPDATED_SALARY
        defaultEmployeeShouldNotBeFound("salary.in=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary is not null
        defaultEmployeeShouldBeFound("salary.specified=true");

        // Get all the employeeList where salary is null
        defaultEmployeeShouldNotBeFound("salary.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary is greater than or equal to DEFAULT_SALARY
        defaultEmployeeShouldBeFound("salary.greaterThanOrEqual=" + DEFAULT_SALARY);

        // Get all the employeeList where salary is greater than or equal to UPDATED_SALARY
        defaultEmployeeShouldNotBeFound("salary.greaterThanOrEqual=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary is less than or equal to DEFAULT_SALARY
        defaultEmployeeShouldBeFound("salary.lessThanOrEqual=" + DEFAULT_SALARY);

        // Get all the employeeList where salary is less than or equal to SMALLER_SALARY
        defaultEmployeeShouldNotBeFound("salary.lessThanOrEqual=" + SMALLER_SALARY);
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary is less than DEFAULT_SALARY
        defaultEmployeeShouldNotBeFound("salary.lessThan=" + DEFAULT_SALARY);

        // Get all the employeeList where salary is less than UPDATED_SALARY
        defaultEmployeeShouldBeFound("salary.lessThan=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary is greater than DEFAULT_SALARY
        defaultEmployeeShouldNotBeFound("salary.greaterThan=" + DEFAULT_SALARY);

        // Get all the employeeList where salary is greater than SMALLER_SALARY
        defaultEmployeeShouldBeFound("salary.greaterThan=" + SMALLER_SALARY);
    }

    @Test
    @Transactional
    void getAllEmployeesByCommissionPctIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where commissionPct equals to DEFAULT_COMMISSION_PCT
        defaultEmployeeShouldBeFound("commissionPct.equals=" + DEFAULT_COMMISSION_PCT);

        // Get all the employeeList where commissionPct equals to UPDATED_COMMISSION_PCT
        defaultEmployeeShouldNotBeFound("commissionPct.equals=" + UPDATED_COMMISSION_PCT);
    }

    @Test
    @Transactional
    void getAllEmployeesByCommissionPctIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where commissionPct not equals to DEFAULT_COMMISSION_PCT
        defaultEmployeeShouldNotBeFound("commissionPct.notEquals=" + DEFAULT_COMMISSION_PCT);

        // Get all the employeeList where commissionPct not equals to UPDATED_COMMISSION_PCT
        defaultEmployeeShouldBeFound("commissionPct.notEquals=" + UPDATED_COMMISSION_PCT);
    }

    @Test
    @Transactional
    void getAllEmployeesByCommissionPctIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where commissionPct in DEFAULT_COMMISSION_PCT or UPDATED_COMMISSION_PCT
        defaultEmployeeShouldBeFound("commissionPct.in=" + DEFAULT_COMMISSION_PCT + "," + UPDATED_COMMISSION_PCT);

        // Get all the employeeList where commissionPct equals to UPDATED_COMMISSION_PCT
        defaultEmployeeShouldNotBeFound("commissionPct.in=" + UPDATED_COMMISSION_PCT);
    }

    @Test
    @Transactional
    void getAllEmployeesByCommissionPctIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where commissionPct is not null
        defaultEmployeeShouldBeFound("commissionPct.specified=true");

        // Get all the employeeList where commissionPct is null
        defaultEmployeeShouldNotBeFound("commissionPct.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByCommissionPctIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where commissionPct is greater than or equal to DEFAULT_COMMISSION_PCT
        defaultEmployeeShouldBeFound("commissionPct.greaterThanOrEqual=" + DEFAULT_COMMISSION_PCT);

        // Get all the employeeList where commissionPct is greater than or equal to (DEFAULT_COMMISSION_PCT + 1)
        defaultEmployeeShouldNotBeFound("commissionPct.greaterThanOrEqual=" + (DEFAULT_COMMISSION_PCT + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByCommissionPctIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where commissionPct is less than or equal to DEFAULT_COMMISSION_PCT
        defaultEmployeeShouldBeFound("commissionPct.lessThanOrEqual=" + DEFAULT_COMMISSION_PCT);

        // Get all the employeeList where commissionPct is less than or equal to SMALLER_COMMISSION_PCT
        defaultEmployeeShouldNotBeFound("commissionPct.lessThanOrEqual=" + SMALLER_COMMISSION_PCT);
    }

    @Test
    @Transactional
    void getAllEmployeesByCommissionPctIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where commissionPct is less than DEFAULT_COMMISSION_PCT
        defaultEmployeeShouldNotBeFound("commissionPct.lessThan=" + DEFAULT_COMMISSION_PCT);

        // Get all the employeeList where commissionPct is less than (DEFAULT_COMMISSION_PCT + 1)
        defaultEmployeeShouldBeFound("commissionPct.lessThan=" + (DEFAULT_COMMISSION_PCT + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByCommissionPctIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where commissionPct is greater than DEFAULT_COMMISSION_PCT
        defaultEmployeeShouldNotBeFound("commissionPct.greaterThan=" + DEFAULT_COMMISSION_PCT);

        // Get all the employeeList where commissionPct is greater than SMALLER_COMMISSION_PCT
        defaultEmployeeShouldBeFound("commissionPct.greaterThan=" + SMALLER_COMMISSION_PCT);
    }

    @Test
    @Transactional
    void getAllEmployeesByDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where duration equals to DEFAULT_DURATION
        defaultEmployeeShouldBeFound("duration.equals=" + DEFAULT_DURATION);

        // Get all the employeeList where duration equals to UPDATED_DURATION
        defaultEmployeeShouldNotBeFound("duration.equals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where duration not equals to DEFAULT_DURATION
        defaultEmployeeShouldNotBeFound("duration.notEquals=" + DEFAULT_DURATION);

        // Get all the employeeList where duration not equals to UPDATED_DURATION
        defaultEmployeeShouldBeFound("duration.notEquals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByDurationIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where duration in DEFAULT_DURATION or UPDATED_DURATION
        defaultEmployeeShouldBeFound("duration.in=" + DEFAULT_DURATION + "," + UPDATED_DURATION);

        // Get all the employeeList where duration equals to UPDATED_DURATION
        defaultEmployeeShouldNotBeFound("duration.in=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where duration is not null
        defaultEmployeeShouldBeFound("duration.specified=true");

        // Get all the employeeList where duration is null
        defaultEmployeeShouldNotBeFound("duration.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where duration is greater than or equal to DEFAULT_DURATION
        defaultEmployeeShouldBeFound("duration.greaterThanOrEqual=" + DEFAULT_DURATION);

        // Get all the employeeList where duration is greater than or equal to UPDATED_DURATION
        defaultEmployeeShouldNotBeFound("duration.greaterThanOrEqual=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where duration is less than or equal to DEFAULT_DURATION
        defaultEmployeeShouldBeFound("duration.lessThanOrEqual=" + DEFAULT_DURATION);

        // Get all the employeeList where duration is less than or equal to SMALLER_DURATION
        defaultEmployeeShouldNotBeFound("duration.lessThanOrEqual=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where duration is less than DEFAULT_DURATION
        defaultEmployeeShouldNotBeFound("duration.lessThan=" + DEFAULT_DURATION);

        // Get all the employeeList where duration is less than UPDATED_DURATION
        defaultEmployeeShouldBeFound("duration.lessThan=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where duration is greater than DEFAULT_DURATION
        defaultEmployeeShouldNotBeFound("duration.greaterThan=" + DEFAULT_DURATION);

        // Get all the employeeList where duration is greater than SMALLER_DURATION
        defaultEmployeeShouldBeFound("duration.greaterThan=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active equals to DEFAULT_ACTIVE
        defaultEmployeeShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the employeeList where active equals to UPDATED_ACTIVE
        defaultEmployeeShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active not equals to DEFAULT_ACTIVE
        defaultEmployeeShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the employeeList where active not equals to UPDATED_ACTIVE
        defaultEmployeeShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultEmployeeShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the employeeList where active equals to UPDATED_ACTIVE
        defaultEmployeeShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where active is not null
        defaultEmployeeShouldBeFound("active.specified=true");

        // Get all the employeeList where active is null
        defaultEmployeeShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByJobIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Job job;
        if (TestUtil.findAll(em, Job.class).isEmpty()) {
            job = JobResourceIT.createEntity(em);
            em.persist(job);
            em.flush();
        } else {
            job = TestUtil.findAll(em, Job.class).get(0);
        }
        em.persist(job);
        em.flush();
        employee.addJob(job);
        employeeRepository.saveAndFlush(employee);
        Long jobId = job.getId();

        // Get all the employeeList where job equals to jobId
        defaultEmployeeShouldBeFound("jobId.equals=" + jobId);

        // Get all the employeeList where job equals to (jobId + 1)
        defaultEmployeeShouldNotBeFound("jobId.equals=" + (jobId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Employee manager;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            manager = EmployeeResourceIT.createEntity(em);
            em.persist(manager);
            em.flush();
        } else {
            manager = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(manager);
        em.flush();
        employee.setManager(manager);
        employeeRepository.saveAndFlush(employee);
        Long managerId = manager.getId();

        // Get all the employeeList where manager equals to managerId
        defaultEmployeeShouldBeFound("managerId.equals=" + managerId);

        // Get all the employeeList where manager equals to (managerId + 1)
        defaultEmployeeShouldNotBeFound("managerId.equals=" + (managerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            department = DepartmentResourceIT.createEntity(em);
            em.persist(department);
            em.flush();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        em.persist(department);
        em.flush();
        employee.setDepartment(department);
        employeeRepository.saveAndFlush(employee);
        Long departmentId = department.getId();

        // Get all the employeeList where department equals to departmentId
        defaultEmployeeShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the employeeList where department equals to (departmentId + 1)
        defaultEmployeeShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeesIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Employee employees;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employees = EmployeeResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employees);
        em.flush();
        employee.addEmployees(employees);
        employeeRepository.saveAndFlush(employee);
        Long employeesId = employees.getId();

        // Get all the employeeList where employees equals to employeesId
        defaultEmployeeShouldBeFound("employeesId.equals=" + employeesId);

        // Get all the employeeList where employees equals to (employeesId + 1)
        defaultEmployeeShouldNotBeFound("employeesId.equals=" + (employeesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeShouldBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].hireDateTime").value(hasItem(DEFAULT_HIRE_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].zonedHireDateTime").value(hasItem(sameInstant(DEFAULT_ZONED_HIRE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].hireDate").value(hasItem(DEFAULT_HIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].commissionPct").value(hasItem(DEFAULT_COMMISSION_PCT.intValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].pictContentType").value(hasItem(DEFAULT_PICT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pict").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICT))))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].cvContentType").value(hasItem(DEFAULT_CV_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cv").value(hasItem(Base64Utils.encodeToString(DEFAULT_CV))))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeShouldNotBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDateTime(UPDATED_HIRE_DATE_TIME)
            .zonedHireDateTime(UPDATED_ZONED_HIRE_DATE_TIME)
            .hireDate(UPDATED_HIRE_DATE)
            .salary(UPDATED_SALARY)
            .commissionPct(UPDATED_COMMISSION_PCT)
            .duration(UPDATED_DURATION)
            .pict(UPDATED_PICT)
            .pictContentType(UPDATED_PICT_CONTENT_TYPE)
            .comments(UPDATED_COMMENTS)
            .cv(UPDATED_CV)
            .cvContentType(UPDATED_CV_CONTENT_TYPE)
            .active(UPDATED_ACTIVE);

        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmployee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployee.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testEmployee.getHireDateTime()).isEqualTo(UPDATED_HIRE_DATE_TIME);
        assertThat(testEmployee.getZonedHireDateTime()).isEqualTo(UPDATED_ZONED_HIRE_DATE_TIME);
        assertThat(testEmployee.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
        assertThat(testEmployee.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testEmployee.getCommissionPct()).isEqualTo(UPDATED_COMMISSION_PCT);
        assertThat(testEmployee.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testEmployee.getPict()).isEqualTo(UPDATED_PICT);
        assertThat(testEmployee.getPictContentType()).isEqualTo(UPDATED_PICT_CONTENT_TYPE);
        assertThat(testEmployee.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testEmployee.getCv()).isEqualTo(UPDATED_CV);
        assertThat(testEmployee.getCvContentType()).isEqualTo(UPDATED_CV_CONTENT_TYPE);
        assertThat(testEmployee.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .firstName(UPDATED_FIRST_NAME)
            .fullName(UPDATED_FULL_NAME)
            .hireDateTime(UPDATED_HIRE_DATE_TIME)
            .hireDate(UPDATED_HIRE_DATE)
            .pict(UPDATED_PICT)
            .pictContentType(UPDATED_PICT_CONTENT_TYPE)
            .active(UPDATED_ACTIVE);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEmployee.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testEmployee.getHireDateTime()).isEqualTo(UPDATED_HIRE_DATE_TIME);
        assertThat(testEmployee.getZonedHireDateTime()).isEqualTo(DEFAULT_ZONED_HIRE_DATE_TIME);
        assertThat(testEmployee.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
        assertThat(testEmployee.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testEmployee.getCommissionPct()).isEqualTo(DEFAULT_COMMISSION_PCT);
        assertThat(testEmployee.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testEmployee.getPict()).isEqualTo(UPDATED_PICT);
        assertThat(testEmployee.getPictContentType()).isEqualTo(UPDATED_PICT_CONTENT_TYPE);
        assertThat(testEmployee.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testEmployee.getCv()).isEqualTo(DEFAULT_CV);
        assertThat(testEmployee.getCvContentType()).isEqualTo(DEFAULT_CV_CONTENT_TYPE);
        assertThat(testEmployee.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDateTime(UPDATED_HIRE_DATE_TIME)
            .zonedHireDateTime(UPDATED_ZONED_HIRE_DATE_TIME)
            .hireDate(UPDATED_HIRE_DATE)
            .salary(UPDATED_SALARY)
            .commissionPct(UPDATED_COMMISSION_PCT)
            .duration(UPDATED_DURATION)
            .pict(UPDATED_PICT)
            .pictContentType(UPDATED_PICT_CONTENT_TYPE)
            .comments(UPDATED_COMMENTS)
            .cv(UPDATED_CV)
            .cvContentType(UPDATED_CV_CONTENT_TYPE)
            .active(UPDATED_ACTIVE);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployee.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testEmployee.getHireDateTime()).isEqualTo(UPDATED_HIRE_DATE_TIME);
        assertThat(testEmployee.getZonedHireDateTime()).isEqualTo(UPDATED_ZONED_HIRE_DATE_TIME);
        assertThat(testEmployee.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
        assertThat(testEmployee.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testEmployee.getCommissionPct()).isEqualTo(UPDATED_COMMISSION_PCT);
        assertThat(testEmployee.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testEmployee.getPict()).isEqualTo(UPDATED_PICT);
        assertThat(testEmployee.getPictContentType()).isEqualTo(UPDATED_PICT_CONTENT_TYPE);
        assertThat(testEmployee.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testEmployee.getCv()).isEqualTo(UPDATED_CV);
        assertThat(testEmployee.getCvContentType()).isEqualTo(UPDATED_CV_CONTENT_TYPE);
        assertThat(testEmployee.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
