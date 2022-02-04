package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Files;
import com.mycompany.myapp.domain.Files;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.enumeration.FileType;
import com.mycompany.myapp.repository.FilesRepository;
import com.mycompany.myapp.service.criteria.FilesCriteria;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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
 * Integration tests for the {@link FilesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FilesResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    private static final FileType DEFAULT_FILE_TYPE = FileType.FOLDER;
    private static final FileType UPDATED_FILE_TYPE = FileType.FILE;

    private static final Long DEFAULT_FILE_SIZE = 1L;
    private static final Long UPDATED_FILE_SIZE = 2L;
    private static final Long SMALLER_FILE_SIZE = 1L - 1L;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_MIME = "AAAAAAAAAA";
    private static final String UPDATED_MIME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFilesMockMvc;

    private Files files;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Files createEntity(EntityManager em) {
        Files files = new Files()
            .uuid(DEFAULT_UUID)
            .filename(DEFAULT_FILENAME)
            .fileType(DEFAULT_FILE_TYPE)
            .fileSize(DEFAULT_FILE_SIZE)
            .createDate(DEFAULT_CREATE_DATE)
            .filePath(DEFAULT_FILE_PATH)
            .version(DEFAULT_VERSION)
            .mime(DEFAULT_MIME);
        return files;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Files createUpdatedEntity(EntityManager em) {
        Files files = new Files()
            .uuid(UPDATED_UUID)
            .filename(UPDATED_FILENAME)
            .fileType(UPDATED_FILE_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .createDate(UPDATED_CREATE_DATE)
            .filePath(UPDATED_FILE_PATH)
            .version(UPDATED_VERSION)
            .mime(UPDATED_MIME);
        return files;
    }

    @BeforeEach
    public void initTest() {
        files = createEntity(em);
    }

    @Test
    @Transactional
    void createFiles() throws Exception {
        int databaseSizeBeforeCreate = filesRepository.findAll().size();
        // Create the Files
        restFilesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(files)))
            .andExpect(status().isCreated());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeCreate + 1);
        Files testFiles = filesList.get(filesList.size() - 1);
        assertThat(testFiles.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testFiles.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testFiles.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testFiles.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
        assertThat(testFiles.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testFiles.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
        assertThat(testFiles.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFiles.getMime()).isEqualTo(DEFAULT_MIME);
    }

    @Test
    @Transactional
    void createFilesWithExistingId() throws Exception {
        // Create the Files with an existing ID
        files.setId(1L);

        int databaseSizeBeforeCreate = filesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(files)))
            .andExpect(status().isBadRequest());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList
        restFilesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(files.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].mime").value(hasItem(DEFAULT_MIME)));
    }

    @Test
    @Transactional
    void getFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get the files
        restFilesMockMvc
            .perform(get(ENTITY_API_URL_ID, files.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(files.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE.toString()))
            .andExpect(jsonPath("$.fileSize").value(DEFAULT_FILE_SIZE.intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.mime").value(DEFAULT_MIME));
    }

    @Test
    @Transactional
    void getFilesByIdFiltering() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        Long id = files.getId();

        defaultFilesShouldBeFound("id.equals=" + id);
        defaultFilesShouldNotBeFound("id.notEquals=" + id);

        defaultFilesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFilesShouldNotBeFound("id.greaterThan=" + id);

        defaultFilesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFilesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFilesByUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where uuid equals to DEFAULT_UUID
        defaultFilesShouldBeFound("uuid.equals=" + DEFAULT_UUID);

        // Get all the filesList where uuid equals to UPDATED_UUID
        defaultFilesShouldNotBeFound("uuid.equals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    void getAllFilesByUuidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where uuid not equals to DEFAULT_UUID
        defaultFilesShouldNotBeFound("uuid.notEquals=" + DEFAULT_UUID);

        // Get all the filesList where uuid not equals to UPDATED_UUID
        defaultFilesShouldBeFound("uuid.notEquals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    void getAllFilesByUuidIsInShouldWork() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where uuid in DEFAULT_UUID or UPDATED_UUID
        defaultFilesShouldBeFound("uuid.in=" + DEFAULT_UUID + "," + UPDATED_UUID);

        // Get all the filesList where uuid equals to UPDATED_UUID
        defaultFilesShouldNotBeFound("uuid.in=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    void getAllFilesByUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where uuid is not null
        defaultFilesShouldBeFound("uuid.specified=true");

        // Get all the filesList where uuid is null
        defaultFilesShouldNotBeFound("uuid.specified=false");
    }

    @Test
    @Transactional
    void getAllFilesByFilenameIsEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where filename equals to DEFAULT_FILENAME
        defaultFilesShouldBeFound("filename.equals=" + DEFAULT_FILENAME);

        // Get all the filesList where filename equals to UPDATED_FILENAME
        defaultFilesShouldNotBeFound("filename.equals=" + UPDATED_FILENAME);
    }

    @Test
    @Transactional
    void getAllFilesByFilenameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where filename not equals to DEFAULT_FILENAME
        defaultFilesShouldNotBeFound("filename.notEquals=" + DEFAULT_FILENAME);

        // Get all the filesList where filename not equals to UPDATED_FILENAME
        defaultFilesShouldBeFound("filename.notEquals=" + UPDATED_FILENAME);
    }

    @Test
    @Transactional
    void getAllFilesByFilenameIsInShouldWork() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where filename in DEFAULT_FILENAME or UPDATED_FILENAME
        defaultFilesShouldBeFound("filename.in=" + DEFAULT_FILENAME + "," + UPDATED_FILENAME);

        // Get all the filesList where filename equals to UPDATED_FILENAME
        defaultFilesShouldNotBeFound("filename.in=" + UPDATED_FILENAME);
    }

    @Test
    @Transactional
    void getAllFilesByFilenameIsNullOrNotNull() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where filename is not null
        defaultFilesShouldBeFound("filename.specified=true");

        // Get all the filesList where filename is null
        defaultFilesShouldNotBeFound("filename.specified=false");
    }

    @Test
    @Transactional
    void getAllFilesByFilenameContainsSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where filename contains DEFAULT_FILENAME
        defaultFilesShouldBeFound("filename.contains=" + DEFAULT_FILENAME);

        // Get all the filesList where filename contains UPDATED_FILENAME
        defaultFilesShouldNotBeFound("filename.contains=" + UPDATED_FILENAME);
    }

    @Test
    @Transactional
    void getAllFilesByFilenameNotContainsSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where filename does not contain DEFAULT_FILENAME
        defaultFilesShouldNotBeFound("filename.doesNotContain=" + DEFAULT_FILENAME);

        // Get all the filesList where filename does not contain UPDATED_FILENAME
        defaultFilesShouldBeFound("filename.doesNotContain=" + UPDATED_FILENAME);
    }

    @Test
    @Transactional
    void getAllFilesByFileTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where fileType equals to DEFAULT_FILE_TYPE
        defaultFilesShouldBeFound("fileType.equals=" + DEFAULT_FILE_TYPE);

        // Get all the filesList where fileType equals to UPDATED_FILE_TYPE
        defaultFilesShouldNotBeFound("fileType.equals=" + UPDATED_FILE_TYPE);
    }

    @Test
    @Transactional
    void getAllFilesByFileTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where fileType not equals to DEFAULT_FILE_TYPE
        defaultFilesShouldNotBeFound("fileType.notEquals=" + DEFAULT_FILE_TYPE);

        // Get all the filesList where fileType not equals to UPDATED_FILE_TYPE
        defaultFilesShouldBeFound("fileType.notEquals=" + UPDATED_FILE_TYPE);
    }

    @Test
    @Transactional
    void getAllFilesByFileTypeIsInShouldWork() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where fileType in DEFAULT_FILE_TYPE or UPDATED_FILE_TYPE
        defaultFilesShouldBeFound("fileType.in=" + DEFAULT_FILE_TYPE + "," + UPDATED_FILE_TYPE);

        // Get all the filesList where fileType equals to UPDATED_FILE_TYPE
        defaultFilesShouldNotBeFound("fileType.in=" + UPDATED_FILE_TYPE);
    }

    @Test
    @Transactional
    void getAllFilesByFileTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where fileType is not null
        defaultFilesShouldBeFound("fileType.specified=true");

        // Get all the filesList where fileType is null
        defaultFilesShouldNotBeFound("fileType.specified=false");
    }

    @Test
    @Transactional
    void getAllFilesByFileSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where fileSize equals to DEFAULT_FILE_SIZE
        defaultFilesShouldBeFound("fileSize.equals=" + DEFAULT_FILE_SIZE);

        // Get all the filesList where fileSize equals to UPDATED_FILE_SIZE
        defaultFilesShouldNotBeFound("fileSize.equals=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    void getAllFilesByFileSizeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where fileSize not equals to DEFAULT_FILE_SIZE
        defaultFilesShouldNotBeFound("fileSize.notEquals=" + DEFAULT_FILE_SIZE);

        // Get all the filesList where fileSize not equals to UPDATED_FILE_SIZE
        defaultFilesShouldBeFound("fileSize.notEquals=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    void getAllFilesByFileSizeIsInShouldWork() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where fileSize in DEFAULT_FILE_SIZE or UPDATED_FILE_SIZE
        defaultFilesShouldBeFound("fileSize.in=" + DEFAULT_FILE_SIZE + "," + UPDATED_FILE_SIZE);

        // Get all the filesList where fileSize equals to UPDATED_FILE_SIZE
        defaultFilesShouldNotBeFound("fileSize.in=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    void getAllFilesByFileSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where fileSize is not null
        defaultFilesShouldBeFound("fileSize.specified=true");

        // Get all the filesList where fileSize is null
        defaultFilesShouldNotBeFound("fileSize.specified=false");
    }

    @Test
    @Transactional
    void getAllFilesByFileSizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where fileSize is greater than or equal to DEFAULT_FILE_SIZE
        defaultFilesShouldBeFound("fileSize.greaterThanOrEqual=" + DEFAULT_FILE_SIZE);

        // Get all the filesList where fileSize is greater than or equal to UPDATED_FILE_SIZE
        defaultFilesShouldNotBeFound("fileSize.greaterThanOrEqual=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    void getAllFilesByFileSizeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where fileSize is less than or equal to DEFAULT_FILE_SIZE
        defaultFilesShouldBeFound("fileSize.lessThanOrEqual=" + DEFAULT_FILE_SIZE);

        // Get all the filesList where fileSize is less than or equal to SMALLER_FILE_SIZE
        defaultFilesShouldNotBeFound("fileSize.lessThanOrEqual=" + SMALLER_FILE_SIZE);
    }

    @Test
    @Transactional
    void getAllFilesByFileSizeIsLessThanSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where fileSize is less than DEFAULT_FILE_SIZE
        defaultFilesShouldNotBeFound("fileSize.lessThan=" + DEFAULT_FILE_SIZE);

        // Get all the filesList where fileSize is less than UPDATED_FILE_SIZE
        defaultFilesShouldBeFound("fileSize.lessThan=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    void getAllFilesByFileSizeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where fileSize is greater than DEFAULT_FILE_SIZE
        defaultFilesShouldNotBeFound("fileSize.greaterThan=" + DEFAULT_FILE_SIZE);

        // Get all the filesList where fileSize is greater than SMALLER_FILE_SIZE
        defaultFilesShouldBeFound("fileSize.greaterThan=" + SMALLER_FILE_SIZE);
    }

    @Test
    @Transactional
    void getAllFilesByCreateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where createDate equals to DEFAULT_CREATE_DATE
        defaultFilesShouldBeFound("createDate.equals=" + DEFAULT_CREATE_DATE);

        // Get all the filesList where createDate equals to UPDATED_CREATE_DATE
        defaultFilesShouldNotBeFound("createDate.equals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    void getAllFilesByCreateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where createDate not equals to DEFAULT_CREATE_DATE
        defaultFilesShouldNotBeFound("createDate.notEquals=" + DEFAULT_CREATE_DATE);

        // Get all the filesList where createDate not equals to UPDATED_CREATE_DATE
        defaultFilesShouldBeFound("createDate.notEquals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    void getAllFilesByCreateDateIsInShouldWork() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where createDate in DEFAULT_CREATE_DATE or UPDATED_CREATE_DATE
        defaultFilesShouldBeFound("createDate.in=" + DEFAULT_CREATE_DATE + "," + UPDATED_CREATE_DATE);

        // Get all the filesList where createDate equals to UPDATED_CREATE_DATE
        defaultFilesShouldNotBeFound("createDate.in=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    void getAllFilesByCreateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where createDate is not null
        defaultFilesShouldBeFound("createDate.specified=true");

        // Get all the filesList where createDate is null
        defaultFilesShouldNotBeFound("createDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFilesByCreateDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where createDate is greater than or equal to DEFAULT_CREATE_DATE
        defaultFilesShouldBeFound("createDate.greaterThanOrEqual=" + DEFAULT_CREATE_DATE);

        // Get all the filesList where createDate is greater than or equal to UPDATED_CREATE_DATE
        defaultFilesShouldNotBeFound("createDate.greaterThanOrEqual=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    void getAllFilesByCreateDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where createDate is less than or equal to DEFAULT_CREATE_DATE
        defaultFilesShouldBeFound("createDate.lessThanOrEqual=" + DEFAULT_CREATE_DATE);

        // Get all the filesList where createDate is less than or equal to SMALLER_CREATE_DATE
        defaultFilesShouldNotBeFound("createDate.lessThanOrEqual=" + SMALLER_CREATE_DATE);
    }

    @Test
    @Transactional
    void getAllFilesByCreateDateIsLessThanSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where createDate is less than DEFAULT_CREATE_DATE
        defaultFilesShouldNotBeFound("createDate.lessThan=" + DEFAULT_CREATE_DATE);

        // Get all the filesList where createDate is less than UPDATED_CREATE_DATE
        defaultFilesShouldBeFound("createDate.lessThan=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    void getAllFilesByCreateDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where createDate is greater than DEFAULT_CREATE_DATE
        defaultFilesShouldNotBeFound("createDate.greaterThan=" + DEFAULT_CREATE_DATE);

        // Get all the filesList where createDate is greater than SMALLER_CREATE_DATE
        defaultFilesShouldBeFound("createDate.greaterThan=" + SMALLER_CREATE_DATE);
    }

    @Test
    @Transactional
    void getAllFilesByFilePathIsEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where filePath equals to DEFAULT_FILE_PATH
        defaultFilesShouldBeFound("filePath.equals=" + DEFAULT_FILE_PATH);

        // Get all the filesList where filePath equals to UPDATED_FILE_PATH
        defaultFilesShouldNotBeFound("filePath.equals=" + UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void getAllFilesByFilePathIsNotEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where filePath not equals to DEFAULT_FILE_PATH
        defaultFilesShouldNotBeFound("filePath.notEquals=" + DEFAULT_FILE_PATH);

        // Get all the filesList where filePath not equals to UPDATED_FILE_PATH
        defaultFilesShouldBeFound("filePath.notEquals=" + UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void getAllFilesByFilePathIsInShouldWork() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where filePath in DEFAULT_FILE_PATH or UPDATED_FILE_PATH
        defaultFilesShouldBeFound("filePath.in=" + DEFAULT_FILE_PATH + "," + UPDATED_FILE_PATH);

        // Get all the filesList where filePath equals to UPDATED_FILE_PATH
        defaultFilesShouldNotBeFound("filePath.in=" + UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void getAllFilesByFilePathIsNullOrNotNull() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where filePath is not null
        defaultFilesShouldBeFound("filePath.specified=true");

        // Get all the filesList where filePath is null
        defaultFilesShouldNotBeFound("filePath.specified=false");
    }

    @Test
    @Transactional
    void getAllFilesByFilePathContainsSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where filePath contains DEFAULT_FILE_PATH
        defaultFilesShouldBeFound("filePath.contains=" + DEFAULT_FILE_PATH);

        // Get all the filesList where filePath contains UPDATED_FILE_PATH
        defaultFilesShouldNotBeFound("filePath.contains=" + UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void getAllFilesByFilePathNotContainsSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where filePath does not contain DEFAULT_FILE_PATH
        defaultFilesShouldNotBeFound("filePath.doesNotContain=" + DEFAULT_FILE_PATH);

        // Get all the filesList where filePath does not contain UPDATED_FILE_PATH
        defaultFilesShouldBeFound("filePath.doesNotContain=" + UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void getAllFilesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where version equals to DEFAULT_VERSION
        defaultFilesShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the filesList where version equals to UPDATED_VERSION
        defaultFilesShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllFilesByVersionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where version not equals to DEFAULT_VERSION
        defaultFilesShouldNotBeFound("version.notEquals=" + DEFAULT_VERSION);

        // Get all the filesList where version not equals to UPDATED_VERSION
        defaultFilesShouldBeFound("version.notEquals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllFilesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultFilesShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the filesList where version equals to UPDATED_VERSION
        defaultFilesShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllFilesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where version is not null
        defaultFilesShouldBeFound("version.specified=true");

        // Get all the filesList where version is null
        defaultFilesShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    void getAllFilesByVersionContainsSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where version contains DEFAULT_VERSION
        defaultFilesShouldBeFound("version.contains=" + DEFAULT_VERSION);

        // Get all the filesList where version contains UPDATED_VERSION
        defaultFilesShouldNotBeFound("version.contains=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllFilesByVersionNotContainsSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where version does not contain DEFAULT_VERSION
        defaultFilesShouldNotBeFound("version.doesNotContain=" + DEFAULT_VERSION);

        // Get all the filesList where version does not contain UPDATED_VERSION
        defaultFilesShouldBeFound("version.doesNotContain=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllFilesByMimeIsEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where mime equals to DEFAULT_MIME
        defaultFilesShouldBeFound("mime.equals=" + DEFAULT_MIME);

        // Get all the filesList where mime equals to UPDATED_MIME
        defaultFilesShouldNotBeFound("mime.equals=" + UPDATED_MIME);
    }

    @Test
    @Transactional
    void getAllFilesByMimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where mime not equals to DEFAULT_MIME
        defaultFilesShouldNotBeFound("mime.notEquals=" + DEFAULT_MIME);

        // Get all the filesList where mime not equals to UPDATED_MIME
        defaultFilesShouldBeFound("mime.notEquals=" + UPDATED_MIME);
    }

    @Test
    @Transactional
    void getAllFilesByMimeIsInShouldWork() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where mime in DEFAULT_MIME or UPDATED_MIME
        defaultFilesShouldBeFound("mime.in=" + DEFAULT_MIME + "," + UPDATED_MIME);

        // Get all the filesList where mime equals to UPDATED_MIME
        defaultFilesShouldNotBeFound("mime.in=" + UPDATED_MIME);
    }

    @Test
    @Transactional
    void getAllFilesByMimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where mime is not null
        defaultFilesShouldBeFound("mime.specified=true");

        // Get all the filesList where mime is null
        defaultFilesShouldNotBeFound("mime.specified=false");
    }

    @Test
    @Transactional
    void getAllFilesByMimeContainsSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where mime contains DEFAULT_MIME
        defaultFilesShouldBeFound("mime.contains=" + DEFAULT_MIME);

        // Get all the filesList where mime contains UPDATED_MIME
        defaultFilesShouldNotBeFound("mime.contains=" + UPDATED_MIME);
    }

    @Test
    @Transactional
    void getAllFilesByMimeNotContainsSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList where mime does not contain DEFAULT_MIME
        defaultFilesShouldNotBeFound("mime.doesNotContain=" + DEFAULT_MIME);

        // Get all the filesList where mime does not contain UPDATED_MIME
        defaultFilesShouldBeFound("mime.doesNotContain=" + UPDATED_MIME);
    }

    @Test
    @Transactional
    void getAllFilesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);
        Files parent;
        if (TestUtil.findAll(em, Files.class).isEmpty()) {
            parent = FilesResourceIT.createEntity(em);
            em.persist(parent);
            em.flush();
        } else {
            parent = TestUtil.findAll(em, Files.class).get(0);
        }
        em.persist(parent);
        em.flush();
        files.setParent(parent);
        filesRepository.saveAndFlush(files);
        Long parentId = parent.getId();

        // Get all the filesList where parent equals to parentId
        defaultFilesShouldBeFound("parentId.equals=" + parentId);

        // Get all the filesList where parent equals to (parentId + 1)
        defaultFilesShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    @Test
    @Transactional
    void getAllFilesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);
        User createdBy;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            createdBy = UserResourceIT.createEntity(em);
            em.persist(createdBy);
            em.flush();
        } else {
            createdBy = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(createdBy);
        em.flush();
        files.setCreatedBy(createdBy);
        filesRepository.saveAndFlush(files);
        Long createdById = createdBy.getId();

        // Get all the filesList where createdBy equals to createdById
        defaultFilesShouldBeFound("createdById.equals=" + createdById);

        // Get all the filesList where createdBy equals to (createdById + 1)
        defaultFilesShouldNotBeFound("createdById.equals=" + (createdById + 1));
    }

    @Test
    @Transactional
    void getAllFilesByChildrenIsEqualToSomething() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);
        Files children;
        if (TestUtil.findAll(em, Files.class).isEmpty()) {
            children = FilesResourceIT.createEntity(em);
            em.persist(children);
            em.flush();
        } else {
            children = TestUtil.findAll(em, Files.class).get(0);
        }
        em.persist(children);
        em.flush();
        files.addChildren(children);
        filesRepository.saveAndFlush(files);
        Long childrenId = children.getId();

        // Get all the filesList where children equals to childrenId
        defaultFilesShouldBeFound("childrenId.equals=" + childrenId);

        // Get all the filesList where children equals to (childrenId + 1)
        defaultFilesShouldNotBeFound("childrenId.equals=" + (childrenId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFilesShouldBeFound(String filter) throws Exception {
        restFilesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(files.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].mime").value(hasItem(DEFAULT_MIME)));

        // Check, that the count call also returns 1
        restFilesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFilesShouldNotBeFound(String filter) throws Exception {
        restFilesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFilesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFiles() throws Exception {
        // Get the files
        restFilesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        int databaseSizeBeforeUpdate = filesRepository.findAll().size();

        // Update the files
        Files updatedFiles = filesRepository.findById(files.getId()).get();
        // Disconnect from session so that the updates on updatedFiles are not directly saved in db
        em.detach(updatedFiles);
        updatedFiles
            .uuid(UPDATED_UUID)
            .filename(UPDATED_FILENAME)
            .fileType(UPDATED_FILE_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .createDate(UPDATED_CREATE_DATE)
            .filePath(UPDATED_FILE_PATH)
            .version(UPDATED_VERSION)
            .mime(UPDATED_MIME);

        restFilesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFiles.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFiles))
            )
            .andExpect(status().isOk());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
        Files testFiles = filesList.get(filesList.size() - 1);
        assertThat(testFiles.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testFiles.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testFiles.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testFiles.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testFiles.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testFiles.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testFiles.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFiles.getMime()).isEqualTo(UPDATED_MIME);
    }

    @Test
    @Transactional
    void putNonExistingFiles() throws Exception {
        int databaseSizeBeforeUpdate = filesRepository.findAll().size();
        files.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, files.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(files))
            )
            .andExpect(status().isBadRequest());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFiles() throws Exception {
        int databaseSizeBeforeUpdate = filesRepository.findAll().size();
        files.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(files))
            )
            .andExpect(status().isBadRequest());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFiles() throws Exception {
        int databaseSizeBeforeUpdate = filesRepository.findAll().size();
        files.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(files)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFilesWithPatch() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        int databaseSizeBeforeUpdate = filesRepository.findAll().size();

        // Update the files using partial update
        Files partialUpdatedFiles = new Files();
        partialUpdatedFiles.setId(files.getId());

        partialUpdatedFiles.uuid(UPDATED_UUID).filename(UPDATED_FILENAME).fileSize(UPDATED_FILE_SIZE);

        restFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFiles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFiles))
            )
            .andExpect(status().isOk());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
        Files testFiles = filesList.get(filesList.size() - 1);
        assertThat(testFiles.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testFiles.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testFiles.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testFiles.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testFiles.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testFiles.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
        assertThat(testFiles.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFiles.getMime()).isEqualTo(DEFAULT_MIME);
    }

    @Test
    @Transactional
    void fullUpdateFilesWithPatch() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        int databaseSizeBeforeUpdate = filesRepository.findAll().size();

        // Update the files using partial update
        Files partialUpdatedFiles = new Files();
        partialUpdatedFiles.setId(files.getId());

        partialUpdatedFiles
            .uuid(UPDATED_UUID)
            .filename(UPDATED_FILENAME)
            .fileType(UPDATED_FILE_TYPE)
            .fileSize(UPDATED_FILE_SIZE)
            .createDate(UPDATED_CREATE_DATE)
            .filePath(UPDATED_FILE_PATH)
            .version(UPDATED_VERSION)
            .mime(UPDATED_MIME);

        restFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFiles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFiles))
            )
            .andExpect(status().isOk());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
        Files testFiles = filesList.get(filesList.size() - 1);
        assertThat(testFiles.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testFiles.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testFiles.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testFiles.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testFiles.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testFiles.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testFiles.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFiles.getMime()).isEqualTo(UPDATED_MIME);
    }

    @Test
    @Transactional
    void patchNonExistingFiles() throws Exception {
        int databaseSizeBeforeUpdate = filesRepository.findAll().size();
        files.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, files.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(files))
            )
            .andExpect(status().isBadRequest());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFiles() throws Exception {
        int databaseSizeBeforeUpdate = filesRepository.findAll().size();
        files.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(files))
            )
            .andExpect(status().isBadRequest());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFiles() throws Exception {
        int databaseSizeBeforeUpdate = filesRepository.findAll().size();
        files.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(files)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        int databaseSizeBeforeDelete = filesRepository.findAll().size();

        // Delete the files
        restFilesMockMvc
            .perform(delete(ENTITY_API_URL_ID, files.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
