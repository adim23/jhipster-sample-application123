package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Job;
import com.mycompany.myapp.domain.Task;
import com.mycompany.myapp.domain.Task;
import com.mycompany.myapp.repository.TaskRepository;
import com.mycompany.myapp.service.criteria.TaskCriteria;
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

/**
 * Integration tests for the {@link TaskResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaskResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_PERCENT_COMPLETED = 0L;
    private static final Long UPDATED_PERCENT_COMPLETED = 1L;
    private static final Long SMALLER_PERCENT_COMPLETED = 0L - 1L;

    private static final String ENTITY_API_URL = "/api/tasks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskMockMvc;

    private Task task;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createEntity(EntityManager em) {
        Task task = new Task()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .percentCompleted(DEFAULT_PERCENT_COMPLETED);
        return task;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createUpdatedEntity(EntityManager em) {
        Task task = new Task()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .percentCompleted(UPDATED_PERCENT_COMPLETED);
        return task;
    }

    @BeforeEach
    public void initTest() {
        task = createEntity(em);
    }

    @Test
    @Transactional
    void createTask() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();
        // Create the Task
        restTaskMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isCreated());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate + 1);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTask.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTask.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTask.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTask.getPercentCompleted()).isEqualTo(DEFAULT_PERCENT_COMPLETED);
    }

    @Test
    @Transactional
    void createTaskWithExistingId() throws Exception {
        // Create the Task with an existing ID
        task.setId(1L);

        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskRepository.findAll().size();
        // set the field null
        task.setTitle(null);

        // Create the Task, which fails.

        restTaskMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isBadRequest());

        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskRepository.findAll().size();
        // set the field null
        task.setStartDate(null);

        // Create the Task, which fails.

        restTaskMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isBadRequest());

        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTasks() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList
        restTaskMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].percentCompleted").value(hasItem(DEFAULT_PERCENT_COMPLETED.intValue())));
    }

    @Test
    @Transactional
    void getTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get the task
        restTaskMockMvc
            .perform(get(ENTITY_API_URL_ID, task.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(task.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.percentCompleted").value(DEFAULT_PERCENT_COMPLETED.intValue()));
    }

    @Test
    @Transactional
    void getTasksByIdFiltering() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        Long id = task.getId();

        defaultTaskShouldBeFound("id.equals=" + id);
        defaultTaskShouldNotBeFound("id.notEquals=" + id);

        defaultTaskShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTaskShouldNotBeFound("id.greaterThan=" + id);

        defaultTaskShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTaskShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTasksByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where title equals to DEFAULT_TITLE
        defaultTaskShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the taskList where title equals to UPDATED_TITLE
        defaultTaskShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTasksByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where title not equals to DEFAULT_TITLE
        defaultTaskShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the taskList where title not equals to UPDATED_TITLE
        defaultTaskShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTasksByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultTaskShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the taskList where title equals to UPDATED_TITLE
        defaultTaskShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTasksByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where title is not null
        defaultTaskShouldBeFound("title.specified=true");

        // Get all the taskList where title is null
        defaultTaskShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllTasksByTitleContainsSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where title contains DEFAULT_TITLE
        defaultTaskShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the taskList where title contains UPDATED_TITLE
        defaultTaskShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTasksByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where title does not contain DEFAULT_TITLE
        defaultTaskShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the taskList where title does not contain UPDATED_TITLE
        defaultTaskShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllTasksByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where description equals to DEFAULT_DESCRIPTION
        defaultTaskShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the taskList where description equals to UPDATED_DESCRIPTION
        defaultTaskShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTasksByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where description not equals to DEFAULT_DESCRIPTION
        defaultTaskShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the taskList where description not equals to UPDATED_DESCRIPTION
        defaultTaskShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTasksByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTaskShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the taskList where description equals to UPDATED_DESCRIPTION
        defaultTaskShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTasksByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where description is not null
        defaultTaskShouldBeFound("description.specified=true");

        // Get all the taskList where description is null
        defaultTaskShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllTasksByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where description contains DEFAULT_DESCRIPTION
        defaultTaskShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the taskList where description contains UPDATED_DESCRIPTION
        defaultTaskShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTasksByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where description does not contain DEFAULT_DESCRIPTION
        defaultTaskShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the taskList where description does not contain UPDATED_DESCRIPTION
        defaultTaskShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTasksByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where startDate equals to DEFAULT_START_DATE
        defaultTaskShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the taskList where startDate equals to UPDATED_START_DATE
        defaultTaskShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTasksByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where startDate not equals to DEFAULT_START_DATE
        defaultTaskShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the taskList where startDate not equals to UPDATED_START_DATE
        defaultTaskShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTasksByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultTaskShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the taskList where startDate equals to UPDATED_START_DATE
        defaultTaskShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTasksByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where startDate is not null
        defaultTaskShouldBeFound("startDate.specified=true");

        // Get all the taskList where startDate is null
        defaultTaskShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTasksByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultTaskShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the taskList where startDate is greater than or equal to UPDATED_START_DATE
        defaultTaskShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTasksByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where startDate is less than or equal to DEFAULT_START_DATE
        defaultTaskShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the taskList where startDate is less than or equal to SMALLER_START_DATE
        defaultTaskShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllTasksByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where startDate is less than DEFAULT_START_DATE
        defaultTaskShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the taskList where startDate is less than UPDATED_START_DATE
        defaultTaskShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTasksByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where startDate is greater than DEFAULT_START_DATE
        defaultTaskShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the taskList where startDate is greater than SMALLER_START_DATE
        defaultTaskShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllTasksByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where endDate equals to DEFAULT_END_DATE
        defaultTaskShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the taskList where endDate equals to UPDATED_END_DATE
        defaultTaskShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllTasksByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where endDate not equals to DEFAULT_END_DATE
        defaultTaskShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the taskList where endDate not equals to UPDATED_END_DATE
        defaultTaskShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllTasksByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultTaskShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the taskList where endDate equals to UPDATED_END_DATE
        defaultTaskShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllTasksByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where endDate is not null
        defaultTaskShouldBeFound("endDate.specified=true");

        // Get all the taskList where endDate is null
        defaultTaskShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTasksByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultTaskShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the taskList where endDate is greater than or equal to UPDATED_END_DATE
        defaultTaskShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllTasksByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where endDate is less than or equal to DEFAULT_END_DATE
        defaultTaskShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the taskList where endDate is less than or equal to SMALLER_END_DATE
        defaultTaskShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    void getAllTasksByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where endDate is less than DEFAULT_END_DATE
        defaultTaskShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the taskList where endDate is less than UPDATED_END_DATE
        defaultTaskShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllTasksByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where endDate is greater than DEFAULT_END_DATE
        defaultTaskShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the taskList where endDate is greater than SMALLER_END_DATE
        defaultTaskShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    void getAllTasksByPercentCompletedIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where percentCompleted equals to DEFAULT_PERCENT_COMPLETED
        defaultTaskShouldBeFound("percentCompleted.equals=" + DEFAULT_PERCENT_COMPLETED);

        // Get all the taskList where percentCompleted equals to UPDATED_PERCENT_COMPLETED
        defaultTaskShouldNotBeFound("percentCompleted.equals=" + UPDATED_PERCENT_COMPLETED);
    }

    @Test
    @Transactional
    void getAllTasksByPercentCompletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where percentCompleted not equals to DEFAULT_PERCENT_COMPLETED
        defaultTaskShouldNotBeFound("percentCompleted.notEquals=" + DEFAULT_PERCENT_COMPLETED);

        // Get all the taskList where percentCompleted not equals to UPDATED_PERCENT_COMPLETED
        defaultTaskShouldBeFound("percentCompleted.notEquals=" + UPDATED_PERCENT_COMPLETED);
    }

    @Test
    @Transactional
    void getAllTasksByPercentCompletedIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where percentCompleted in DEFAULT_PERCENT_COMPLETED or UPDATED_PERCENT_COMPLETED
        defaultTaskShouldBeFound("percentCompleted.in=" + DEFAULT_PERCENT_COMPLETED + "," + UPDATED_PERCENT_COMPLETED);

        // Get all the taskList where percentCompleted equals to UPDATED_PERCENT_COMPLETED
        defaultTaskShouldNotBeFound("percentCompleted.in=" + UPDATED_PERCENT_COMPLETED);
    }

    @Test
    @Transactional
    void getAllTasksByPercentCompletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where percentCompleted is not null
        defaultTaskShouldBeFound("percentCompleted.specified=true");

        // Get all the taskList where percentCompleted is null
        defaultTaskShouldNotBeFound("percentCompleted.specified=false");
    }

    @Test
    @Transactional
    void getAllTasksByPercentCompletedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where percentCompleted is greater than or equal to DEFAULT_PERCENT_COMPLETED
        defaultTaskShouldBeFound("percentCompleted.greaterThanOrEqual=" + DEFAULT_PERCENT_COMPLETED);

        // Get all the taskList where percentCompleted is greater than or equal to (DEFAULT_PERCENT_COMPLETED + 1)
        defaultTaskShouldNotBeFound("percentCompleted.greaterThanOrEqual=" + (DEFAULT_PERCENT_COMPLETED + 1));
    }

    @Test
    @Transactional
    void getAllTasksByPercentCompletedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where percentCompleted is less than or equal to DEFAULT_PERCENT_COMPLETED
        defaultTaskShouldBeFound("percentCompleted.lessThanOrEqual=" + DEFAULT_PERCENT_COMPLETED);

        // Get all the taskList where percentCompleted is less than or equal to SMALLER_PERCENT_COMPLETED
        defaultTaskShouldNotBeFound("percentCompleted.lessThanOrEqual=" + SMALLER_PERCENT_COMPLETED);
    }

    @Test
    @Transactional
    void getAllTasksByPercentCompletedIsLessThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where percentCompleted is less than DEFAULT_PERCENT_COMPLETED
        defaultTaskShouldNotBeFound("percentCompleted.lessThan=" + DEFAULT_PERCENT_COMPLETED);

        // Get all the taskList where percentCompleted is less than (DEFAULT_PERCENT_COMPLETED + 1)
        defaultTaskShouldBeFound("percentCompleted.lessThan=" + (DEFAULT_PERCENT_COMPLETED + 1));
    }

    @Test
    @Transactional
    void getAllTasksByPercentCompletedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where percentCompleted is greater than DEFAULT_PERCENT_COMPLETED
        defaultTaskShouldNotBeFound("percentCompleted.greaterThan=" + DEFAULT_PERCENT_COMPLETED);

        // Get all the taskList where percentCompleted is greater than SMALLER_PERCENT_COMPLETED
        defaultTaskShouldBeFound("percentCompleted.greaterThan=" + SMALLER_PERCENT_COMPLETED);
    }

    @Test
    @Transactional
    void getAllTasksByDependsOnIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);
        Task dependsOn;
        if (TestUtil.findAll(em, Task.class).isEmpty()) {
            dependsOn = TaskResourceIT.createEntity(em);
            em.persist(dependsOn);
            em.flush();
        } else {
            dependsOn = TestUtil.findAll(em, Task.class).get(0);
        }
        em.persist(dependsOn);
        em.flush();
        task.setDependsOn(dependsOn);
        taskRepository.saveAndFlush(task);
        Long dependsOnId = dependsOn.getId();

        // Get all the taskList where dependsOn equals to dependsOnId
        defaultTaskShouldBeFound("dependsOnId.equals=" + dependsOnId);

        // Get all the taskList where dependsOn equals to (dependsOnId + 1)
        defaultTaskShouldNotBeFound("dependsOnId.equals=" + (dependsOnId + 1));
    }

    @Test
    @Transactional
    void getAllTasksByDependentsIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);
        Task dependents;
        if (TestUtil.findAll(em, Task.class).isEmpty()) {
            dependents = TaskResourceIT.createEntity(em);
            em.persist(dependents);
            em.flush();
        } else {
            dependents = TestUtil.findAll(em, Task.class).get(0);
        }
        em.persist(dependents);
        em.flush();
        task.addDependents(dependents);
        taskRepository.saveAndFlush(task);
        Long dependentsId = dependents.getId();

        // Get all the taskList where dependents equals to dependentsId
        defaultTaskShouldBeFound("dependentsId.equals=" + dependentsId);

        // Get all the taskList where dependents equals to (dependentsId + 1)
        defaultTaskShouldNotBeFound("dependentsId.equals=" + (dependentsId + 1));
    }

    @Test
    @Transactional
    void getAllTasksByJobIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);
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
        task.addJob(job);
        taskRepository.saveAndFlush(task);
        Long jobId = job.getId();

        // Get all the taskList where job equals to jobId
        defaultTaskShouldBeFound("jobId.equals=" + jobId);

        // Get all the taskList where job equals to (jobId + 1)
        defaultTaskShouldNotBeFound("jobId.equals=" + (jobId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTaskShouldBeFound(String filter) throws Exception {
        restTaskMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].percentCompleted").value(hasItem(DEFAULT_PERCENT_COMPLETED.intValue())));

        // Check, that the count call also returns 1
        restTaskMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTaskShouldNotBeFound(String filter) throws Exception {
        restTaskMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTaskMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTask() throws Exception {
        // Get the task
        restTaskMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task
        Task updatedTask = taskRepository.findById(task.getId()).get();
        // Disconnect from session so that the updates on updatedTask are not directly saved in db
        em.detach(updatedTask);
        updatedTask
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .percentCompleted(UPDATED_PERCENT_COMPLETED);

        restTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTask.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTask))
            )
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTask.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTask.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTask.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTask.getPercentCompleted()).isEqualTo(UPDATED_PERCENT_COMPLETED);
    }

    @Test
    @Transactional
    void putNonExistingTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, task.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(task))
            )
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(task))
            )
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaskWithPatch() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task using partial update
        Task partialUpdatedTask = new Task();
        partialUpdatedTask.setId(task.getId());

        partialUpdatedTask.description(UPDATED_DESCRIPTION).endDate(UPDATED_END_DATE);

        restTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTask.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTask))
            )
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTask.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTask.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTask.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTask.getPercentCompleted()).isEqualTo(DEFAULT_PERCENT_COMPLETED);
    }

    @Test
    @Transactional
    void fullUpdateTaskWithPatch() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task using partial update
        Task partialUpdatedTask = new Task();
        partialUpdatedTask.setId(task.getId());

        partialUpdatedTask
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .percentCompleted(UPDATED_PERCENT_COMPLETED);

        restTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTask.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTask))
            )
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTask.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTask.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTask.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTask.getPercentCompleted()).isEqualTo(UPDATED_PERCENT_COMPLETED);
    }

    @Test
    @Transactional
    void patchNonExistingTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, task.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(task))
            )
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(task))
            )
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeDelete = taskRepository.findAll().size();

        // Delete the task
        restTaskMockMvc
            .perform(delete(ENTITY_API_URL_ID, task.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
