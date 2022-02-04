package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Citizens;
import com.mycompany.myapp.domain.Companies;
import com.mycompany.myapp.domain.PhoneTypes;
import com.mycompany.myapp.domain.Phones;
import com.mycompany.myapp.repository.PhonesRepository;
import com.mycompany.myapp.service.criteria.PhonesCriteria;
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
 * Integration tests for the {@link PhonesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhonesResourceIT {

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FAVOURITE = false;
    private static final Boolean UPDATED_FAVOURITE = true;

    private static final String ENTITY_API_URL = "/api/phones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PhonesRepository phonesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhonesMockMvc;

    private Phones phones;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phones createEntity(EntityManager em) {
        Phones phones = new Phones().phone(DEFAULT_PHONE).description(DEFAULT_DESCRIPTION).favourite(DEFAULT_FAVOURITE);
        return phones;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phones createUpdatedEntity(EntityManager em) {
        Phones phones = new Phones().phone(UPDATED_PHONE).description(UPDATED_DESCRIPTION).favourite(UPDATED_FAVOURITE);
        return phones;
    }

    @BeforeEach
    public void initTest() {
        phones = createEntity(em);
    }

    @Test
    @Transactional
    void createPhones() throws Exception {
        int databaseSizeBeforeCreate = phonesRepository.findAll().size();
        // Create the Phones
        restPhonesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phones)))
            .andExpect(status().isCreated());

        // Validate the Phones in the database
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeCreate + 1);
        Phones testPhones = phonesList.get(phonesList.size() - 1);
        assertThat(testPhones.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPhones.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPhones.getFavourite()).isEqualTo(DEFAULT_FAVOURITE);
    }

    @Test
    @Transactional
    void createPhonesWithExistingId() throws Exception {
        // Create the Phones with an existing ID
        phones.setId(1L);

        int databaseSizeBeforeCreate = phonesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhonesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phones)))
            .andExpect(status().isBadRequest());

        // Validate the Phones in the database
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = phonesRepository.findAll().size();
        // set the field null
        phones.setPhone(null);

        // Create the Phones, which fails.

        restPhonesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phones)))
            .andExpect(status().isBadRequest());

        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPhones() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList
        restPhonesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phones.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].favourite").value(hasItem(DEFAULT_FAVOURITE.booleanValue())));
    }

    @Test
    @Transactional
    void getPhones() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get the phones
        restPhonesMockMvc
            .perform(get(ENTITY_API_URL_ID, phones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phones.getId().intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.favourite").value(DEFAULT_FAVOURITE.booleanValue()));
    }

    @Test
    @Transactional
    void getPhonesByIdFiltering() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        Long id = phones.getId();

        defaultPhonesShouldBeFound("id.equals=" + id);
        defaultPhonesShouldNotBeFound("id.notEquals=" + id);

        defaultPhonesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPhonesShouldNotBeFound("id.greaterThan=" + id);

        defaultPhonesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPhonesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPhonesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where phone equals to DEFAULT_PHONE
        defaultPhonesShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the phonesList where phone equals to UPDATED_PHONE
        defaultPhonesShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllPhonesByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where phone not equals to DEFAULT_PHONE
        defaultPhonesShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the phonesList where phone not equals to UPDATED_PHONE
        defaultPhonesShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllPhonesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultPhonesShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the phonesList where phone equals to UPDATED_PHONE
        defaultPhonesShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllPhonesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where phone is not null
        defaultPhonesShouldBeFound("phone.specified=true");

        // Get all the phonesList where phone is null
        defaultPhonesShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllPhonesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where phone contains DEFAULT_PHONE
        defaultPhonesShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the phonesList where phone contains UPDATED_PHONE
        defaultPhonesShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllPhonesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where phone does not contain DEFAULT_PHONE
        defaultPhonesShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the phonesList where phone does not contain UPDATED_PHONE
        defaultPhonesShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllPhonesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where description equals to DEFAULT_DESCRIPTION
        defaultPhonesShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the phonesList where description equals to UPDATED_DESCRIPTION
        defaultPhonesShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPhonesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where description not equals to DEFAULT_DESCRIPTION
        defaultPhonesShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the phonesList where description not equals to UPDATED_DESCRIPTION
        defaultPhonesShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPhonesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPhonesShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the phonesList where description equals to UPDATED_DESCRIPTION
        defaultPhonesShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPhonesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where description is not null
        defaultPhonesShouldBeFound("description.specified=true");

        // Get all the phonesList where description is null
        defaultPhonesShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllPhonesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where description contains DEFAULT_DESCRIPTION
        defaultPhonesShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the phonesList where description contains UPDATED_DESCRIPTION
        defaultPhonesShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPhonesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where description does not contain DEFAULT_DESCRIPTION
        defaultPhonesShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the phonesList where description does not contain UPDATED_DESCRIPTION
        defaultPhonesShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPhonesByFavouriteIsEqualToSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where favourite equals to DEFAULT_FAVOURITE
        defaultPhonesShouldBeFound("favourite.equals=" + DEFAULT_FAVOURITE);

        // Get all the phonesList where favourite equals to UPDATED_FAVOURITE
        defaultPhonesShouldNotBeFound("favourite.equals=" + UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void getAllPhonesByFavouriteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where favourite not equals to DEFAULT_FAVOURITE
        defaultPhonesShouldNotBeFound("favourite.notEquals=" + DEFAULT_FAVOURITE);

        // Get all the phonesList where favourite not equals to UPDATED_FAVOURITE
        defaultPhonesShouldBeFound("favourite.notEquals=" + UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void getAllPhonesByFavouriteIsInShouldWork() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where favourite in DEFAULT_FAVOURITE or UPDATED_FAVOURITE
        defaultPhonesShouldBeFound("favourite.in=" + DEFAULT_FAVOURITE + "," + UPDATED_FAVOURITE);

        // Get all the phonesList where favourite equals to UPDATED_FAVOURITE
        defaultPhonesShouldNotBeFound("favourite.in=" + UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void getAllPhonesByFavouriteIsNullOrNotNull() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        // Get all the phonesList where favourite is not null
        defaultPhonesShouldBeFound("favourite.specified=true");

        // Get all the phonesList where favourite is null
        defaultPhonesShouldNotBeFound("favourite.specified=false");
    }

    @Test
    @Transactional
    void getAllPhonesByKindIsEqualToSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);
        PhoneTypes kind;
        if (TestUtil.findAll(em, PhoneTypes.class).isEmpty()) {
            kind = PhoneTypesResourceIT.createEntity(em);
            em.persist(kind);
            em.flush();
        } else {
            kind = TestUtil.findAll(em, PhoneTypes.class).get(0);
        }
        em.persist(kind);
        em.flush();
        phones.setKind(kind);
        phonesRepository.saveAndFlush(phones);
        Long kindId = kind.getId();

        // Get all the phonesList where kind equals to kindId
        defaultPhonesShouldBeFound("kindId.equals=" + kindId);

        // Get all the phonesList where kind equals to (kindId + 1)
        defaultPhonesShouldNotBeFound("kindId.equals=" + (kindId + 1));
    }

    @Test
    @Transactional
    void getAllPhonesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);
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
        phones.setCompany(company);
        phonesRepository.saveAndFlush(phones);
        Long companyId = company.getId();

        // Get all the phonesList where company equals to companyId
        defaultPhonesShouldBeFound("companyId.equals=" + companyId);

        // Get all the phonesList where company equals to (companyId + 1)
        defaultPhonesShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    @Test
    @Transactional
    void getAllPhonesByCitizenIsEqualToSomething() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);
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
        phones.setCitizen(citizen);
        phonesRepository.saveAndFlush(phones);
        Long citizenId = citizen.getId();

        // Get all the phonesList where citizen equals to citizenId
        defaultPhonesShouldBeFound("citizenId.equals=" + citizenId);

        // Get all the phonesList where citizen equals to (citizenId + 1)
        defaultPhonesShouldNotBeFound("citizenId.equals=" + (citizenId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPhonesShouldBeFound(String filter) throws Exception {
        restPhonesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phones.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].favourite").value(hasItem(DEFAULT_FAVOURITE.booleanValue())));

        // Check, that the count call also returns 1
        restPhonesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPhonesShouldNotBeFound(String filter) throws Exception {
        restPhonesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPhonesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPhones() throws Exception {
        // Get the phones
        restPhonesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPhones() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        int databaseSizeBeforeUpdate = phonesRepository.findAll().size();

        // Update the phones
        Phones updatedPhones = phonesRepository.findById(phones.getId()).get();
        // Disconnect from session so that the updates on updatedPhones are not directly saved in db
        em.detach(updatedPhones);
        updatedPhones.phone(UPDATED_PHONE).description(UPDATED_DESCRIPTION).favourite(UPDATED_FAVOURITE);

        restPhonesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPhones.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPhones))
            )
            .andExpect(status().isOk());

        // Validate the Phones in the database
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeUpdate);
        Phones testPhones = phonesList.get(phonesList.size() - 1);
        assertThat(testPhones.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPhones.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPhones.getFavourite()).isEqualTo(UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void putNonExistingPhones() throws Exception {
        int databaseSizeBeforeUpdate = phonesRepository.findAll().size();
        phones.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhonesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phones.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phones))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phones in the database
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPhones() throws Exception {
        int databaseSizeBeforeUpdate = phonesRepository.findAll().size();
        phones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhonesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phones))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phones in the database
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPhones() throws Exception {
        int databaseSizeBeforeUpdate = phonesRepository.findAll().size();
        phones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhonesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phones)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Phones in the database
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePhonesWithPatch() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        int databaseSizeBeforeUpdate = phonesRepository.findAll().size();

        // Update the phones using partial update
        Phones partialUpdatedPhones = new Phones();
        partialUpdatedPhones.setId(phones.getId());

        partialUpdatedPhones.phone(UPDATED_PHONE);

        restPhonesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhones.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhones))
            )
            .andExpect(status().isOk());

        // Validate the Phones in the database
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeUpdate);
        Phones testPhones = phonesList.get(phonesList.size() - 1);
        assertThat(testPhones.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPhones.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPhones.getFavourite()).isEqualTo(DEFAULT_FAVOURITE);
    }

    @Test
    @Transactional
    void fullUpdatePhonesWithPatch() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        int databaseSizeBeforeUpdate = phonesRepository.findAll().size();

        // Update the phones using partial update
        Phones partialUpdatedPhones = new Phones();
        partialUpdatedPhones.setId(phones.getId());

        partialUpdatedPhones.phone(UPDATED_PHONE).description(UPDATED_DESCRIPTION).favourite(UPDATED_FAVOURITE);

        restPhonesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhones.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhones))
            )
            .andExpect(status().isOk());

        // Validate the Phones in the database
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeUpdate);
        Phones testPhones = phonesList.get(phonesList.size() - 1);
        assertThat(testPhones.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPhones.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPhones.getFavourite()).isEqualTo(UPDATED_FAVOURITE);
    }

    @Test
    @Transactional
    void patchNonExistingPhones() throws Exception {
        int databaseSizeBeforeUpdate = phonesRepository.findAll().size();
        phones.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhonesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, phones.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phones))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phones in the database
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPhones() throws Exception {
        int databaseSizeBeforeUpdate = phonesRepository.findAll().size();
        phones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhonesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phones))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phones in the database
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPhones() throws Exception {
        int databaseSizeBeforeUpdate = phonesRepository.findAll().size();
        phones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhonesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(phones)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Phones in the database
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePhones() throws Exception {
        // Initialize the database
        phonesRepository.saveAndFlush(phones);

        int databaseSizeBeforeDelete = phonesRepository.findAll().size();

        // Delete the phones
        restPhonesMockMvc
            .perform(delete(ENTITY_API_URL_ID, phones.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Phones> phonesList = phonesRepository.findAll();
        assertThat(phonesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
