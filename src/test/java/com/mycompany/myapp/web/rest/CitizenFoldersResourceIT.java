package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CitizenFolders;
import com.mycompany.myapp.repository.CitizenFoldersRepository;
import com.mycompany.myapp.service.criteria.CitizenFoldersCriteria;
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
 * Integration tests for the {@link CitizenFoldersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CitizenFoldersResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SPECIAL = false;
    private static final Boolean UPDATED_SPECIAL = true;

    private static final String ENTITY_API_URL = "/api/citizen-folders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CitizenFoldersRepository citizenFoldersRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitizenFoldersMockMvc;

    private CitizenFolders citizenFolders;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CitizenFolders createEntity(EntityManager em) {
        CitizenFolders citizenFolders = new CitizenFolders().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION).special(DEFAULT_SPECIAL);
        return citizenFolders;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CitizenFolders createUpdatedEntity(EntityManager em) {
        CitizenFolders citizenFolders = new CitizenFolders().name(UPDATED_NAME).description(UPDATED_DESCRIPTION).special(UPDATED_SPECIAL);
        return citizenFolders;
    }

    @BeforeEach
    public void initTest() {
        citizenFolders = createEntity(em);
    }

    @Test
    @Transactional
    void createCitizenFolders() throws Exception {
        int databaseSizeBeforeCreate = citizenFoldersRepository.findAll().size();
        // Create the CitizenFolders
        restCitizenFoldersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizenFolders))
            )
            .andExpect(status().isCreated());

        // Validate the CitizenFolders in the database
        List<CitizenFolders> citizenFoldersList = citizenFoldersRepository.findAll();
        assertThat(citizenFoldersList).hasSize(databaseSizeBeforeCreate + 1);
        CitizenFolders testCitizenFolders = citizenFoldersList.get(citizenFoldersList.size() - 1);
        assertThat(testCitizenFolders.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCitizenFolders.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCitizenFolders.getSpecial()).isEqualTo(DEFAULT_SPECIAL);
    }

    @Test
    @Transactional
    void createCitizenFoldersWithExistingId() throws Exception {
        // Create the CitizenFolders with an existing ID
        citizenFolders.setId(1L);

        int databaseSizeBeforeCreate = citizenFoldersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitizenFoldersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizenFolders))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitizenFolders in the database
        List<CitizenFolders> citizenFoldersList = citizenFoldersRepository.findAll();
        assertThat(citizenFoldersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = citizenFoldersRepository.findAll().size();
        // set the field null
        citizenFolders.setName(null);

        // Create the CitizenFolders, which fails.

        restCitizenFoldersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizenFolders))
            )
            .andExpect(status().isBadRequest());

        List<CitizenFolders> citizenFoldersList = citizenFoldersRepository.findAll();
        assertThat(citizenFoldersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpecialIsRequired() throws Exception {
        int databaseSizeBeforeTest = citizenFoldersRepository.findAll().size();
        // set the field null
        citizenFolders.setSpecial(null);

        // Create the CitizenFolders, which fails.

        restCitizenFoldersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizenFolders))
            )
            .andExpect(status().isBadRequest());

        List<CitizenFolders> citizenFoldersList = citizenFoldersRepository.findAll();
        assertThat(citizenFoldersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCitizenFolders() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList
        restCitizenFoldersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citizenFolders.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].special").value(hasItem(DEFAULT_SPECIAL.booleanValue())));
    }

    @Test
    @Transactional
    void getCitizenFolders() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get the citizenFolders
        restCitizenFoldersMockMvc
            .perform(get(ENTITY_API_URL_ID, citizenFolders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(citizenFolders.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.special").value(DEFAULT_SPECIAL.booleanValue()));
    }

    @Test
    @Transactional
    void getCitizenFoldersByIdFiltering() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        Long id = citizenFolders.getId();

        defaultCitizenFoldersShouldBeFound("id.equals=" + id);
        defaultCitizenFoldersShouldNotBeFound("id.notEquals=" + id);

        defaultCitizenFoldersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCitizenFoldersShouldNotBeFound("id.greaterThan=" + id);

        defaultCitizenFoldersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCitizenFoldersShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCitizenFoldersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where name equals to DEFAULT_NAME
        defaultCitizenFoldersShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the citizenFoldersList where name equals to UPDATED_NAME
        defaultCitizenFoldersShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitizenFoldersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where name not equals to DEFAULT_NAME
        defaultCitizenFoldersShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the citizenFoldersList where name not equals to UPDATED_NAME
        defaultCitizenFoldersShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitizenFoldersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCitizenFoldersShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the citizenFoldersList where name equals to UPDATED_NAME
        defaultCitizenFoldersShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitizenFoldersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where name is not null
        defaultCitizenFoldersShouldBeFound("name.specified=true");

        // Get all the citizenFoldersList where name is null
        defaultCitizenFoldersShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizenFoldersByNameContainsSomething() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where name contains DEFAULT_NAME
        defaultCitizenFoldersShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the citizenFoldersList where name contains UPDATED_NAME
        defaultCitizenFoldersShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitizenFoldersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where name does not contain DEFAULT_NAME
        defaultCitizenFoldersShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the citizenFoldersList where name does not contain UPDATED_NAME
        defaultCitizenFoldersShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCitizenFoldersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where description equals to DEFAULT_DESCRIPTION
        defaultCitizenFoldersShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the citizenFoldersList where description equals to UPDATED_DESCRIPTION
        defaultCitizenFoldersShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCitizenFoldersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where description not equals to DEFAULT_DESCRIPTION
        defaultCitizenFoldersShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the citizenFoldersList where description not equals to UPDATED_DESCRIPTION
        defaultCitizenFoldersShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCitizenFoldersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCitizenFoldersShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the citizenFoldersList where description equals to UPDATED_DESCRIPTION
        defaultCitizenFoldersShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCitizenFoldersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where description is not null
        defaultCitizenFoldersShouldBeFound("description.specified=true");

        // Get all the citizenFoldersList where description is null
        defaultCitizenFoldersShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllCitizenFoldersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where description contains DEFAULT_DESCRIPTION
        defaultCitizenFoldersShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the citizenFoldersList where description contains UPDATED_DESCRIPTION
        defaultCitizenFoldersShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCitizenFoldersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where description does not contain DEFAULT_DESCRIPTION
        defaultCitizenFoldersShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the citizenFoldersList where description does not contain UPDATED_DESCRIPTION
        defaultCitizenFoldersShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCitizenFoldersBySpecialIsEqualToSomething() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where special equals to DEFAULT_SPECIAL
        defaultCitizenFoldersShouldBeFound("special.equals=" + DEFAULT_SPECIAL);

        // Get all the citizenFoldersList where special equals to UPDATED_SPECIAL
        defaultCitizenFoldersShouldNotBeFound("special.equals=" + UPDATED_SPECIAL);
    }

    @Test
    @Transactional
    void getAllCitizenFoldersBySpecialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where special not equals to DEFAULT_SPECIAL
        defaultCitizenFoldersShouldNotBeFound("special.notEquals=" + DEFAULT_SPECIAL);

        // Get all the citizenFoldersList where special not equals to UPDATED_SPECIAL
        defaultCitizenFoldersShouldBeFound("special.notEquals=" + UPDATED_SPECIAL);
    }

    @Test
    @Transactional
    void getAllCitizenFoldersBySpecialIsInShouldWork() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where special in DEFAULT_SPECIAL or UPDATED_SPECIAL
        defaultCitizenFoldersShouldBeFound("special.in=" + DEFAULT_SPECIAL + "," + UPDATED_SPECIAL);

        // Get all the citizenFoldersList where special equals to UPDATED_SPECIAL
        defaultCitizenFoldersShouldNotBeFound("special.in=" + UPDATED_SPECIAL);
    }

    @Test
    @Transactional
    void getAllCitizenFoldersBySpecialIsNullOrNotNull() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        // Get all the citizenFoldersList where special is not null
        defaultCitizenFoldersShouldBeFound("special.specified=true");

        // Get all the citizenFoldersList where special is null
        defaultCitizenFoldersShouldNotBeFound("special.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCitizenFoldersShouldBeFound(String filter) throws Exception {
        restCitizenFoldersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citizenFolders.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].special").value(hasItem(DEFAULT_SPECIAL.booleanValue())));

        // Check, that the count call also returns 1
        restCitizenFoldersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCitizenFoldersShouldNotBeFound(String filter) throws Exception {
        restCitizenFoldersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCitizenFoldersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCitizenFolders() throws Exception {
        // Get the citizenFolders
        restCitizenFoldersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCitizenFolders() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        int databaseSizeBeforeUpdate = citizenFoldersRepository.findAll().size();

        // Update the citizenFolders
        CitizenFolders updatedCitizenFolders = citizenFoldersRepository.findById(citizenFolders.getId()).get();
        // Disconnect from session so that the updates on updatedCitizenFolders are not directly saved in db
        em.detach(updatedCitizenFolders);
        updatedCitizenFolders.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).special(UPDATED_SPECIAL);

        restCitizenFoldersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCitizenFolders.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCitizenFolders))
            )
            .andExpect(status().isOk());

        // Validate the CitizenFolders in the database
        List<CitizenFolders> citizenFoldersList = citizenFoldersRepository.findAll();
        assertThat(citizenFoldersList).hasSize(databaseSizeBeforeUpdate);
        CitizenFolders testCitizenFolders = citizenFoldersList.get(citizenFoldersList.size() - 1);
        assertThat(testCitizenFolders.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCitizenFolders.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCitizenFolders.getSpecial()).isEqualTo(UPDATED_SPECIAL);
    }

    @Test
    @Transactional
    void putNonExistingCitizenFolders() throws Exception {
        int databaseSizeBeforeUpdate = citizenFoldersRepository.findAll().size();
        citizenFolders.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitizenFoldersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citizenFolders.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citizenFolders))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitizenFolders in the database
        List<CitizenFolders> citizenFoldersList = citizenFoldersRepository.findAll();
        assertThat(citizenFoldersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCitizenFolders() throws Exception {
        int databaseSizeBeforeUpdate = citizenFoldersRepository.findAll().size();
        citizenFolders.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizenFoldersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citizenFolders))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitizenFolders in the database
        List<CitizenFolders> citizenFoldersList = citizenFoldersRepository.findAll();
        assertThat(citizenFoldersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCitizenFolders() throws Exception {
        int databaseSizeBeforeUpdate = citizenFoldersRepository.findAll().size();
        citizenFolders.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizenFoldersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citizenFolders)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CitizenFolders in the database
        List<CitizenFolders> citizenFoldersList = citizenFoldersRepository.findAll();
        assertThat(citizenFoldersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCitizenFoldersWithPatch() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        int databaseSizeBeforeUpdate = citizenFoldersRepository.findAll().size();

        // Update the citizenFolders using partial update
        CitizenFolders partialUpdatedCitizenFolders = new CitizenFolders();
        partialUpdatedCitizenFolders.setId(citizenFolders.getId());

        partialUpdatedCitizenFolders.name(UPDATED_NAME).special(UPDATED_SPECIAL);

        restCitizenFoldersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCitizenFolders.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCitizenFolders))
            )
            .andExpect(status().isOk());

        // Validate the CitizenFolders in the database
        List<CitizenFolders> citizenFoldersList = citizenFoldersRepository.findAll();
        assertThat(citizenFoldersList).hasSize(databaseSizeBeforeUpdate);
        CitizenFolders testCitizenFolders = citizenFoldersList.get(citizenFoldersList.size() - 1);
        assertThat(testCitizenFolders.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCitizenFolders.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCitizenFolders.getSpecial()).isEqualTo(UPDATED_SPECIAL);
    }

    @Test
    @Transactional
    void fullUpdateCitizenFoldersWithPatch() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        int databaseSizeBeforeUpdate = citizenFoldersRepository.findAll().size();

        // Update the citizenFolders using partial update
        CitizenFolders partialUpdatedCitizenFolders = new CitizenFolders();
        partialUpdatedCitizenFolders.setId(citizenFolders.getId());

        partialUpdatedCitizenFolders.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).special(UPDATED_SPECIAL);

        restCitizenFoldersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCitizenFolders.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCitizenFolders))
            )
            .andExpect(status().isOk());

        // Validate the CitizenFolders in the database
        List<CitizenFolders> citizenFoldersList = citizenFoldersRepository.findAll();
        assertThat(citizenFoldersList).hasSize(databaseSizeBeforeUpdate);
        CitizenFolders testCitizenFolders = citizenFoldersList.get(citizenFoldersList.size() - 1);
        assertThat(testCitizenFolders.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCitizenFolders.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCitizenFolders.getSpecial()).isEqualTo(UPDATED_SPECIAL);
    }

    @Test
    @Transactional
    void patchNonExistingCitizenFolders() throws Exception {
        int databaseSizeBeforeUpdate = citizenFoldersRepository.findAll().size();
        citizenFolders.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitizenFoldersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, citizenFolders.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citizenFolders))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitizenFolders in the database
        List<CitizenFolders> citizenFoldersList = citizenFoldersRepository.findAll();
        assertThat(citizenFoldersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCitizenFolders() throws Exception {
        int databaseSizeBeforeUpdate = citizenFoldersRepository.findAll().size();
        citizenFolders.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizenFoldersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citizenFolders))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitizenFolders in the database
        List<CitizenFolders> citizenFoldersList = citizenFoldersRepository.findAll();
        assertThat(citizenFoldersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCitizenFolders() throws Exception {
        int databaseSizeBeforeUpdate = citizenFoldersRepository.findAll().size();
        citizenFolders.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitizenFoldersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(citizenFolders))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CitizenFolders in the database
        List<CitizenFolders> citizenFoldersList = citizenFoldersRepository.findAll();
        assertThat(citizenFoldersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCitizenFolders() throws Exception {
        // Initialize the database
        citizenFoldersRepository.saveAndFlush(citizenFolders);

        int databaseSizeBeforeDelete = citizenFoldersRepository.findAll().size();

        // Delete the citizenFolders
        restCitizenFoldersMockMvc
            .perform(delete(ENTITY_API_URL_ID, citizenFolders.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CitizenFolders> citizenFoldersList = citizenFoldersRepository.findAll();
        assertThat(citizenFoldersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
