package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Citizens;
import com.mycompany.myapp.domain.ContactTypes;
import com.mycompany.myapp.domain.SocialContacts;
import com.mycompany.myapp.domain.SocialKinds;
import com.mycompany.myapp.repository.SocialContactsRepository;
import com.mycompany.myapp.service.criteria.SocialContactsCriteria;
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
 * Integration tests for the {@link SocialContactsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocialContactsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FAVORED = false;
    private static final Boolean UPDATED_FAVORED = true;

    private static final String ENTITY_API_URL = "/api/social-contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocialContactsRepository socialContactsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocialContactsMockMvc;

    private SocialContacts socialContacts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialContacts createEntity(EntityManager em) {
        SocialContacts socialContacts = new SocialContacts().name(DEFAULT_NAME).favored(DEFAULT_FAVORED);
        return socialContacts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialContacts createUpdatedEntity(EntityManager em) {
        SocialContacts socialContacts = new SocialContacts().name(UPDATED_NAME).favored(UPDATED_FAVORED);
        return socialContacts;
    }

    @BeforeEach
    public void initTest() {
        socialContacts = createEntity(em);
    }

    @Test
    @Transactional
    void createSocialContacts() throws Exception {
        int databaseSizeBeforeCreate = socialContactsRepository.findAll().size();
        // Create the SocialContacts
        restSocialContactsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialContacts))
            )
            .andExpect(status().isCreated());

        // Validate the SocialContacts in the database
        List<SocialContacts> socialContactsList = socialContactsRepository.findAll();
        assertThat(socialContactsList).hasSize(databaseSizeBeforeCreate + 1);
        SocialContacts testSocialContacts = socialContactsList.get(socialContactsList.size() - 1);
        assertThat(testSocialContacts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSocialContacts.getFavored()).isEqualTo(DEFAULT_FAVORED);
    }

    @Test
    @Transactional
    void createSocialContactsWithExistingId() throws Exception {
        // Create the SocialContacts with an existing ID
        socialContacts.setId(1L);

        int databaseSizeBeforeCreate = socialContactsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialContactsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialContacts))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialContacts in the database
        List<SocialContacts> socialContactsList = socialContactsRepository.findAll();
        assertThat(socialContactsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = socialContactsRepository.findAll().size();
        // set the field null
        socialContacts.setName(null);

        // Create the SocialContacts, which fails.

        restSocialContactsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialContacts))
            )
            .andExpect(status().isBadRequest());

        List<SocialContacts> socialContactsList = socialContactsRepository.findAll();
        assertThat(socialContactsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSocialContacts() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        // Get all the socialContactsList
        restSocialContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialContacts.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].favored").value(hasItem(DEFAULT_FAVORED.booleanValue())));
    }

    @Test
    @Transactional
    void getSocialContacts() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        // Get the socialContacts
        restSocialContactsMockMvc
            .perform(get(ENTITY_API_URL_ID, socialContacts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(socialContacts.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.favored").value(DEFAULT_FAVORED.booleanValue()));
    }

    @Test
    @Transactional
    void getSocialContactsByIdFiltering() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        Long id = socialContacts.getId();

        defaultSocialContactsShouldBeFound("id.equals=" + id);
        defaultSocialContactsShouldNotBeFound("id.notEquals=" + id);

        defaultSocialContactsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSocialContactsShouldNotBeFound("id.greaterThan=" + id);

        defaultSocialContactsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSocialContactsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSocialContactsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        // Get all the socialContactsList where name equals to DEFAULT_NAME
        defaultSocialContactsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the socialContactsList where name equals to UPDATED_NAME
        defaultSocialContactsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSocialContactsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        // Get all the socialContactsList where name not equals to DEFAULT_NAME
        defaultSocialContactsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the socialContactsList where name not equals to UPDATED_NAME
        defaultSocialContactsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSocialContactsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        // Get all the socialContactsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSocialContactsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the socialContactsList where name equals to UPDATED_NAME
        defaultSocialContactsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSocialContactsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        // Get all the socialContactsList where name is not null
        defaultSocialContactsShouldBeFound("name.specified=true");

        // Get all the socialContactsList where name is null
        defaultSocialContactsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllSocialContactsByNameContainsSomething() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        // Get all the socialContactsList where name contains DEFAULT_NAME
        defaultSocialContactsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the socialContactsList where name contains UPDATED_NAME
        defaultSocialContactsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSocialContactsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        // Get all the socialContactsList where name does not contain DEFAULT_NAME
        defaultSocialContactsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the socialContactsList where name does not contain UPDATED_NAME
        defaultSocialContactsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSocialContactsByFavoredIsEqualToSomething() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        // Get all the socialContactsList where favored equals to DEFAULT_FAVORED
        defaultSocialContactsShouldBeFound("favored.equals=" + DEFAULT_FAVORED);

        // Get all the socialContactsList where favored equals to UPDATED_FAVORED
        defaultSocialContactsShouldNotBeFound("favored.equals=" + UPDATED_FAVORED);
    }

    @Test
    @Transactional
    void getAllSocialContactsByFavoredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        // Get all the socialContactsList where favored not equals to DEFAULT_FAVORED
        defaultSocialContactsShouldNotBeFound("favored.notEquals=" + DEFAULT_FAVORED);

        // Get all the socialContactsList where favored not equals to UPDATED_FAVORED
        defaultSocialContactsShouldBeFound("favored.notEquals=" + UPDATED_FAVORED);
    }

    @Test
    @Transactional
    void getAllSocialContactsByFavoredIsInShouldWork() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        // Get all the socialContactsList where favored in DEFAULT_FAVORED or UPDATED_FAVORED
        defaultSocialContactsShouldBeFound("favored.in=" + DEFAULT_FAVORED + "," + UPDATED_FAVORED);

        // Get all the socialContactsList where favored equals to UPDATED_FAVORED
        defaultSocialContactsShouldNotBeFound("favored.in=" + UPDATED_FAVORED);
    }

    @Test
    @Transactional
    void getAllSocialContactsByFavoredIsNullOrNotNull() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        // Get all the socialContactsList where favored is not null
        defaultSocialContactsShouldBeFound("favored.specified=true");

        // Get all the socialContactsList where favored is null
        defaultSocialContactsShouldNotBeFound("favored.specified=false");
    }

    @Test
    @Transactional
    void getAllSocialContactsBySocialIsEqualToSomething() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);
        SocialKinds social;
        if (TestUtil.findAll(em, SocialKinds.class).isEmpty()) {
            social = SocialKindsResourceIT.createEntity(em);
            em.persist(social);
            em.flush();
        } else {
            social = TestUtil.findAll(em, SocialKinds.class).get(0);
        }
        em.persist(social);
        em.flush();
        socialContacts.setSocial(social);
        socialContactsRepository.saveAndFlush(socialContacts);
        Long socialId = social.getId();

        // Get all the socialContactsList where social equals to socialId
        defaultSocialContactsShouldBeFound("socialId.equals=" + socialId);

        // Get all the socialContactsList where social equals to (socialId + 1)
        defaultSocialContactsShouldNotBeFound("socialId.equals=" + (socialId + 1));
    }

    @Test
    @Transactional
    void getAllSocialContactsByKindIsEqualToSomething() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);
        ContactTypes kind;
        if (TestUtil.findAll(em, ContactTypes.class).isEmpty()) {
            kind = ContactTypesResourceIT.createEntity(em);
            em.persist(kind);
            em.flush();
        } else {
            kind = TestUtil.findAll(em, ContactTypes.class).get(0);
        }
        em.persist(kind);
        em.flush();
        socialContacts.setKind(kind);
        socialContactsRepository.saveAndFlush(socialContacts);
        Long kindId = kind.getId();

        // Get all the socialContactsList where kind equals to kindId
        defaultSocialContactsShouldBeFound("kindId.equals=" + kindId);

        // Get all the socialContactsList where kind equals to (kindId + 1)
        defaultSocialContactsShouldNotBeFound("kindId.equals=" + (kindId + 1));
    }

    @Test
    @Transactional
    void getAllSocialContactsByCitizenIsEqualToSomething() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);
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
        socialContacts.setCitizen(citizen);
        socialContactsRepository.saveAndFlush(socialContacts);
        Long citizenId = citizen.getId();

        // Get all the socialContactsList where citizen equals to citizenId
        defaultSocialContactsShouldBeFound("citizenId.equals=" + citizenId);

        // Get all the socialContactsList where citizen equals to (citizenId + 1)
        defaultSocialContactsShouldNotBeFound("citizenId.equals=" + (citizenId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSocialContactsShouldBeFound(String filter) throws Exception {
        restSocialContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialContacts.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].favored").value(hasItem(DEFAULT_FAVORED.booleanValue())));

        // Check, that the count call also returns 1
        restSocialContactsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSocialContactsShouldNotBeFound(String filter) throws Exception {
        restSocialContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSocialContactsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSocialContacts() throws Exception {
        // Get the socialContacts
        restSocialContactsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSocialContacts() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        int databaseSizeBeforeUpdate = socialContactsRepository.findAll().size();

        // Update the socialContacts
        SocialContacts updatedSocialContacts = socialContactsRepository.findById(socialContacts.getId()).get();
        // Disconnect from session so that the updates on updatedSocialContacts are not directly saved in db
        em.detach(updatedSocialContacts);
        updatedSocialContacts.name(UPDATED_NAME).favored(UPDATED_FAVORED);

        restSocialContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSocialContacts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSocialContacts))
            )
            .andExpect(status().isOk());

        // Validate the SocialContacts in the database
        List<SocialContacts> socialContactsList = socialContactsRepository.findAll();
        assertThat(socialContactsList).hasSize(databaseSizeBeforeUpdate);
        SocialContacts testSocialContacts = socialContactsList.get(socialContactsList.size() - 1);
        assertThat(testSocialContacts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSocialContacts.getFavored()).isEqualTo(UPDATED_FAVORED);
    }

    @Test
    @Transactional
    void putNonExistingSocialContacts() throws Exception {
        int databaseSizeBeforeUpdate = socialContactsRepository.findAll().size();
        socialContacts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socialContacts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialContacts))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialContacts in the database
        List<SocialContacts> socialContactsList = socialContactsRepository.findAll();
        assertThat(socialContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocialContacts() throws Exception {
        int databaseSizeBeforeUpdate = socialContactsRepository.findAll().size();
        socialContacts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialContacts))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialContacts in the database
        List<SocialContacts> socialContactsList = socialContactsRepository.findAll();
        assertThat(socialContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocialContacts() throws Exception {
        int databaseSizeBeforeUpdate = socialContactsRepository.findAll().size();
        socialContacts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialContactsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialContacts)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocialContacts in the database
        List<SocialContacts> socialContactsList = socialContactsRepository.findAll();
        assertThat(socialContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocialContactsWithPatch() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        int databaseSizeBeforeUpdate = socialContactsRepository.findAll().size();

        // Update the socialContacts using partial update
        SocialContacts partialUpdatedSocialContacts = new SocialContacts();
        partialUpdatedSocialContacts.setId(socialContacts.getId());

        restSocialContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocialContacts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocialContacts))
            )
            .andExpect(status().isOk());

        // Validate the SocialContacts in the database
        List<SocialContacts> socialContactsList = socialContactsRepository.findAll();
        assertThat(socialContactsList).hasSize(databaseSizeBeforeUpdate);
        SocialContacts testSocialContacts = socialContactsList.get(socialContactsList.size() - 1);
        assertThat(testSocialContacts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSocialContacts.getFavored()).isEqualTo(DEFAULT_FAVORED);
    }

    @Test
    @Transactional
    void fullUpdateSocialContactsWithPatch() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        int databaseSizeBeforeUpdate = socialContactsRepository.findAll().size();

        // Update the socialContacts using partial update
        SocialContacts partialUpdatedSocialContacts = new SocialContacts();
        partialUpdatedSocialContacts.setId(socialContacts.getId());

        partialUpdatedSocialContacts.name(UPDATED_NAME).favored(UPDATED_FAVORED);

        restSocialContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocialContacts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocialContacts))
            )
            .andExpect(status().isOk());

        // Validate the SocialContacts in the database
        List<SocialContacts> socialContactsList = socialContactsRepository.findAll();
        assertThat(socialContactsList).hasSize(databaseSizeBeforeUpdate);
        SocialContacts testSocialContacts = socialContactsList.get(socialContactsList.size() - 1);
        assertThat(testSocialContacts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSocialContacts.getFavored()).isEqualTo(UPDATED_FAVORED);
    }

    @Test
    @Transactional
    void patchNonExistingSocialContacts() throws Exception {
        int databaseSizeBeforeUpdate = socialContactsRepository.findAll().size();
        socialContacts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, socialContacts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socialContacts))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialContacts in the database
        List<SocialContacts> socialContactsList = socialContactsRepository.findAll();
        assertThat(socialContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocialContacts() throws Exception {
        int databaseSizeBeforeUpdate = socialContactsRepository.findAll().size();
        socialContacts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socialContacts))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialContacts in the database
        List<SocialContacts> socialContactsList = socialContactsRepository.findAll();
        assertThat(socialContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocialContacts() throws Exception {
        int databaseSizeBeforeUpdate = socialContactsRepository.findAll().size();
        socialContacts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialContactsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(socialContacts))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocialContacts in the database
        List<SocialContacts> socialContactsList = socialContactsRepository.findAll();
        assertThat(socialContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocialContacts() throws Exception {
        // Initialize the database
        socialContactsRepository.saveAndFlush(socialContacts);

        int databaseSizeBeforeDelete = socialContactsRepository.findAll().size();

        // Delete the socialContacts
        restSocialContactsMockMvc
            .perform(delete(ENTITY_API_URL_ID, socialContacts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocialContacts> socialContactsList = socialContactsRepository.findAll();
        assertThat(socialContactsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
