package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.SocialKinds;
import com.mycompany.myapp.repository.SocialKindsRepository;
import com.mycompany.myapp.service.criteria.SocialKindsCriteria;
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
 * Integration tests for the {@link SocialKindsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocialKindsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CALL = "AAAAAAAAAA";
    private static final String UPDATED_CALL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/social-kinds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocialKindsRepository socialKindsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocialKindsMockMvc;

    private SocialKinds socialKinds;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialKinds createEntity(EntityManager em) {
        SocialKinds socialKinds = new SocialKinds().code(DEFAULT_CODE).name(DEFAULT_NAME).call(DEFAULT_CALL);
        return socialKinds;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialKinds createUpdatedEntity(EntityManager em) {
        SocialKinds socialKinds = new SocialKinds().code(UPDATED_CODE).name(UPDATED_NAME).call(UPDATED_CALL);
        return socialKinds;
    }

    @BeforeEach
    public void initTest() {
        socialKinds = createEntity(em);
    }

    @Test
    @Transactional
    void createSocialKinds() throws Exception {
        int databaseSizeBeforeCreate = socialKindsRepository.findAll().size();
        // Create the SocialKinds
        restSocialKindsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialKinds)))
            .andExpect(status().isCreated());

        // Validate the SocialKinds in the database
        List<SocialKinds> socialKindsList = socialKindsRepository.findAll();
        assertThat(socialKindsList).hasSize(databaseSizeBeforeCreate + 1);
        SocialKinds testSocialKinds = socialKindsList.get(socialKindsList.size() - 1);
        assertThat(testSocialKinds.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSocialKinds.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSocialKinds.getCall()).isEqualTo(DEFAULT_CALL);
    }

    @Test
    @Transactional
    void createSocialKindsWithExistingId() throws Exception {
        // Create the SocialKinds with an existing ID
        socialKinds.setId(1L);

        int databaseSizeBeforeCreate = socialKindsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialKindsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialKinds)))
            .andExpect(status().isBadRequest());

        // Validate the SocialKinds in the database
        List<SocialKinds> socialKindsList = socialKindsRepository.findAll();
        assertThat(socialKindsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = socialKindsRepository.findAll().size();
        // set the field null
        socialKinds.setCode(null);

        // Create the SocialKinds, which fails.

        restSocialKindsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialKinds)))
            .andExpect(status().isBadRequest());

        List<SocialKinds> socialKindsList = socialKindsRepository.findAll();
        assertThat(socialKindsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = socialKindsRepository.findAll().size();
        // set the field null
        socialKinds.setName(null);

        // Create the SocialKinds, which fails.

        restSocialKindsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialKinds)))
            .andExpect(status().isBadRequest());

        List<SocialKinds> socialKindsList = socialKindsRepository.findAll();
        assertThat(socialKindsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCallIsRequired() throws Exception {
        int databaseSizeBeforeTest = socialKindsRepository.findAll().size();
        // set the field null
        socialKinds.setCall(null);

        // Create the SocialKinds, which fails.

        restSocialKindsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialKinds)))
            .andExpect(status().isBadRequest());

        List<SocialKinds> socialKindsList = socialKindsRepository.findAll();
        assertThat(socialKindsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSocialKinds() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList
        restSocialKindsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialKinds.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].call").value(hasItem(DEFAULT_CALL)));
    }

    @Test
    @Transactional
    void getSocialKinds() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get the socialKinds
        restSocialKindsMockMvc
            .perform(get(ENTITY_API_URL_ID, socialKinds.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(socialKinds.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.call").value(DEFAULT_CALL));
    }

    @Test
    @Transactional
    void getSocialKindsByIdFiltering() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        Long id = socialKinds.getId();

        defaultSocialKindsShouldBeFound("id.equals=" + id);
        defaultSocialKindsShouldNotBeFound("id.notEquals=" + id);

        defaultSocialKindsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSocialKindsShouldNotBeFound("id.greaterThan=" + id);

        defaultSocialKindsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSocialKindsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSocialKindsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where code equals to DEFAULT_CODE
        defaultSocialKindsShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the socialKindsList where code equals to UPDATED_CODE
        defaultSocialKindsShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSocialKindsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where code not equals to DEFAULT_CODE
        defaultSocialKindsShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the socialKindsList where code not equals to UPDATED_CODE
        defaultSocialKindsShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSocialKindsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where code in DEFAULT_CODE or UPDATED_CODE
        defaultSocialKindsShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the socialKindsList where code equals to UPDATED_CODE
        defaultSocialKindsShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSocialKindsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where code is not null
        defaultSocialKindsShouldBeFound("code.specified=true");

        // Get all the socialKindsList where code is null
        defaultSocialKindsShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllSocialKindsByCodeContainsSomething() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where code contains DEFAULT_CODE
        defaultSocialKindsShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the socialKindsList where code contains UPDATED_CODE
        defaultSocialKindsShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSocialKindsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where code does not contain DEFAULT_CODE
        defaultSocialKindsShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the socialKindsList where code does not contain UPDATED_CODE
        defaultSocialKindsShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSocialKindsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where name equals to DEFAULT_NAME
        defaultSocialKindsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the socialKindsList where name equals to UPDATED_NAME
        defaultSocialKindsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSocialKindsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where name not equals to DEFAULT_NAME
        defaultSocialKindsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the socialKindsList where name not equals to UPDATED_NAME
        defaultSocialKindsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSocialKindsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSocialKindsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the socialKindsList where name equals to UPDATED_NAME
        defaultSocialKindsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSocialKindsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where name is not null
        defaultSocialKindsShouldBeFound("name.specified=true");

        // Get all the socialKindsList where name is null
        defaultSocialKindsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllSocialKindsByNameContainsSomething() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where name contains DEFAULT_NAME
        defaultSocialKindsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the socialKindsList where name contains UPDATED_NAME
        defaultSocialKindsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSocialKindsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where name does not contain DEFAULT_NAME
        defaultSocialKindsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the socialKindsList where name does not contain UPDATED_NAME
        defaultSocialKindsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSocialKindsByCallIsEqualToSomething() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where call equals to DEFAULT_CALL
        defaultSocialKindsShouldBeFound("call.equals=" + DEFAULT_CALL);

        // Get all the socialKindsList where call equals to UPDATED_CALL
        defaultSocialKindsShouldNotBeFound("call.equals=" + UPDATED_CALL);
    }

    @Test
    @Transactional
    void getAllSocialKindsByCallIsNotEqualToSomething() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where call not equals to DEFAULT_CALL
        defaultSocialKindsShouldNotBeFound("call.notEquals=" + DEFAULT_CALL);

        // Get all the socialKindsList where call not equals to UPDATED_CALL
        defaultSocialKindsShouldBeFound("call.notEquals=" + UPDATED_CALL);
    }

    @Test
    @Transactional
    void getAllSocialKindsByCallIsInShouldWork() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where call in DEFAULT_CALL or UPDATED_CALL
        defaultSocialKindsShouldBeFound("call.in=" + DEFAULT_CALL + "," + UPDATED_CALL);

        // Get all the socialKindsList where call equals to UPDATED_CALL
        defaultSocialKindsShouldNotBeFound("call.in=" + UPDATED_CALL);
    }

    @Test
    @Transactional
    void getAllSocialKindsByCallIsNullOrNotNull() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where call is not null
        defaultSocialKindsShouldBeFound("call.specified=true");

        // Get all the socialKindsList where call is null
        defaultSocialKindsShouldNotBeFound("call.specified=false");
    }

    @Test
    @Transactional
    void getAllSocialKindsByCallContainsSomething() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where call contains DEFAULT_CALL
        defaultSocialKindsShouldBeFound("call.contains=" + DEFAULT_CALL);

        // Get all the socialKindsList where call contains UPDATED_CALL
        defaultSocialKindsShouldNotBeFound("call.contains=" + UPDATED_CALL);
    }

    @Test
    @Transactional
    void getAllSocialKindsByCallNotContainsSomething() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        // Get all the socialKindsList where call does not contain DEFAULT_CALL
        defaultSocialKindsShouldNotBeFound("call.doesNotContain=" + DEFAULT_CALL);

        // Get all the socialKindsList where call does not contain UPDATED_CALL
        defaultSocialKindsShouldBeFound("call.doesNotContain=" + UPDATED_CALL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSocialKindsShouldBeFound(String filter) throws Exception {
        restSocialKindsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialKinds.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].call").value(hasItem(DEFAULT_CALL)));

        // Check, that the count call also returns 1
        restSocialKindsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSocialKindsShouldNotBeFound(String filter) throws Exception {
        restSocialKindsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSocialKindsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSocialKinds() throws Exception {
        // Get the socialKinds
        restSocialKindsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSocialKinds() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        int databaseSizeBeforeUpdate = socialKindsRepository.findAll().size();

        // Update the socialKinds
        SocialKinds updatedSocialKinds = socialKindsRepository.findById(socialKinds.getId()).get();
        // Disconnect from session so that the updates on updatedSocialKinds are not directly saved in db
        em.detach(updatedSocialKinds);
        updatedSocialKinds.code(UPDATED_CODE).name(UPDATED_NAME).call(UPDATED_CALL);

        restSocialKindsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSocialKinds.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSocialKinds))
            )
            .andExpect(status().isOk());

        // Validate the SocialKinds in the database
        List<SocialKinds> socialKindsList = socialKindsRepository.findAll();
        assertThat(socialKindsList).hasSize(databaseSizeBeforeUpdate);
        SocialKinds testSocialKinds = socialKindsList.get(socialKindsList.size() - 1);
        assertThat(testSocialKinds.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSocialKinds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSocialKinds.getCall()).isEqualTo(UPDATED_CALL);
    }

    @Test
    @Transactional
    void putNonExistingSocialKinds() throws Exception {
        int databaseSizeBeforeUpdate = socialKindsRepository.findAll().size();
        socialKinds.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialKindsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socialKinds.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialKinds))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialKinds in the database
        List<SocialKinds> socialKindsList = socialKindsRepository.findAll();
        assertThat(socialKindsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocialKinds() throws Exception {
        int databaseSizeBeforeUpdate = socialKindsRepository.findAll().size();
        socialKinds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialKindsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialKinds))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialKinds in the database
        List<SocialKinds> socialKindsList = socialKindsRepository.findAll();
        assertThat(socialKindsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocialKinds() throws Exception {
        int databaseSizeBeforeUpdate = socialKindsRepository.findAll().size();
        socialKinds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialKindsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialKinds)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocialKinds in the database
        List<SocialKinds> socialKindsList = socialKindsRepository.findAll();
        assertThat(socialKindsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocialKindsWithPatch() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        int databaseSizeBeforeUpdate = socialKindsRepository.findAll().size();

        // Update the socialKinds using partial update
        SocialKinds partialUpdatedSocialKinds = new SocialKinds();
        partialUpdatedSocialKinds.setId(socialKinds.getId());

        partialUpdatedSocialKinds.code(UPDATED_CODE).name(UPDATED_NAME);

        restSocialKindsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocialKinds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocialKinds))
            )
            .andExpect(status().isOk());

        // Validate the SocialKinds in the database
        List<SocialKinds> socialKindsList = socialKindsRepository.findAll();
        assertThat(socialKindsList).hasSize(databaseSizeBeforeUpdate);
        SocialKinds testSocialKinds = socialKindsList.get(socialKindsList.size() - 1);
        assertThat(testSocialKinds.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSocialKinds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSocialKinds.getCall()).isEqualTo(DEFAULT_CALL);
    }

    @Test
    @Transactional
    void fullUpdateSocialKindsWithPatch() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        int databaseSizeBeforeUpdate = socialKindsRepository.findAll().size();

        // Update the socialKinds using partial update
        SocialKinds partialUpdatedSocialKinds = new SocialKinds();
        partialUpdatedSocialKinds.setId(socialKinds.getId());

        partialUpdatedSocialKinds.code(UPDATED_CODE).name(UPDATED_NAME).call(UPDATED_CALL);

        restSocialKindsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocialKinds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocialKinds))
            )
            .andExpect(status().isOk());

        // Validate the SocialKinds in the database
        List<SocialKinds> socialKindsList = socialKindsRepository.findAll();
        assertThat(socialKindsList).hasSize(databaseSizeBeforeUpdate);
        SocialKinds testSocialKinds = socialKindsList.get(socialKindsList.size() - 1);
        assertThat(testSocialKinds.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSocialKinds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSocialKinds.getCall()).isEqualTo(UPDATED_CALL);
    }

    @Test
    @Transactional
    void patchNonExistingSocialKinds() throws Exception {
        int databaseSizeBeforeUpdate = socialKindsRepository.findAll().size();
        socialKinds.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialKindsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, socialKinds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socialKinds))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialKinds in the database
        List<SocialKinds> socialKindsList = socialKindsRepository.findAll();
        assertThat(socialKindsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocialKinds() throws Exception {
        int databaseSizeBeforeUpdate = socialKindsRepository.findAll().size();
        socialKinds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialKindsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socialKinds))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialKinds in the database
        List<SocialKinds> socialKindsList = socialKindsRepository.findAll();
        assertThat(socialKindsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocialKinds() throws Exception {
        int databaseSizeBeforeUpdate = socialKindsRepository.findAll().size();
        socialKinds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialKindsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(socialKinds))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocialKinds in the database
        List<SocialKinds> socialKindsList = socialKindsRepository.findAll();
        assertThat(socialKindsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocialKinds() throws Exception {
        // Initialize the database
        socialKindsRepository.saveAndFlush(socialKinds);

        int databaseSizeBeforeDelete = socialKindsRepository.findAll().size();

        // Delete the socialKinds
        restSocialKindsMockMvc
            .perform(delete(ENTITY_API_URL_ID, socialKinds.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocialKinds> socialKindsList = socialKindsRepository.findAll();
        assertThat(socialKindsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
