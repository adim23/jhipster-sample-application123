package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Addresses;
import com.mycompany.myapp.domain.Companies;
import com.mycompany.myapp.domain.CompanyKinds;
import com.mycompany.myapp.domain.Phones;
import com.mycompany.myapp.repository.CompaniesRepository;
import com.mycompany.myapp.service.criteria.CompaniesCriteria;
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
 * Integration tests for the {@link CompaniesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompaniesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompaniesRepository companiesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompaniesMockMvc;

    private Companies companies;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Companies createEntity(EntityManager em) {
        Companies companies = new Companies().name(DEFAULT_NAME);
        return companies;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Companies createUpdatedEntity(EntityManager em) {
        Companies companies = new Companies().name(UPDATED_NAME);
        return companies;
    }

    @BeforeEach
    public void initTest() {
        companies = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanies() throws Exception {
        int databaseSizeBeforeCreate = companiesRepository.findAll().size();
        // Create the Companies
        restCompaniesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companies)))
            .andExpect(status().isCreated());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeCreate + 1);
        Companies testCompanies = companiesList.get(companiesList.size() - 1);
        assertThat(testCompanies.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createCompaniesWithExistingId() throws Exception {
        // Create the Companies with an existing ID
        companies.setId(1L);

        int databaseSizeBeforeCreate = companiesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompaniesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companies)))
            .andExpect(status().isBadRequest());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companiesRepository.findAll().size();
        // set the field null
        companies.setName(null);

        // Create the Companies, which fails.

        restCompaniesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companies)))
            .andExpect(status().isBadRequest());

        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompanies() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList
        restCompaniesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companies.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getCompanies() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get the companies
        restCompaniesMockMvc
            .perform(get(ENTITY_API_URL_ID, companies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companies.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getCompaniesByIdFiltering() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        Long id = companies.getId();

        defaultCompaniesShouldBeFound("id.equals=" + id);
        defaultCompaniesShouldNotBeFound("id.notEquals=" + id);

        defaultCompaniesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompaniesShouldNotBeFound("id.greaterThan=" + id);

        defaultCompaniesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompaniesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCompaniesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where name equals to DEFAULT_NAME
        defaultCompaniesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the companiesList where name equals to UPDATED_NAME
        defaultCompaniesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where name not equals to DEFAULT_NAME
        defaultCompaniesShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the companiesList where name not equals to UPDATED_NAME
        defaultCompaniesShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCompaniesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the companiesList where name equals to UPDATED_NAME
        defaultCompaniesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where name is not null
        defaultCompaniesShouldBeFound("name.specified=true");

        // Get all the companiesList where name is null
        defaultCompaniesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByNameContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where name contains DEFAULT_NAME
        defaultCompaniesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the companiesList where name contains UPDATED_NAME
        defaultCompaniesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where name does not contain DEFAULT_NAME
        defaultCompaniesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the companiesList where name does not contain UPDATED_NAME
        defaultCompaniesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByKindIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);
        CompanyKinds kind;
        if (TestUtil.findAll(em, CompanyKinds.class).isEmpty()) {
            kind = CompanyKindsResourceIT.createEntity(em);
            em.persist(kind);
            em.flush();
        } else {
            kind = TestUtil.findAll(em, CompanyKinds.class).get(0);
        }
        em.persist(kind);
        em.flush();
        companies.setKind(kind);
        companiesRepository.saveAndFlush(companies);
        Long kindId = kind.getId();

        // Get all the companiesList where kind equals to kindId
        defaultCompaniesShouldBeFound("kindId.equals=" + kindId);

        // Get all the companiesList where kind equals to (kindId + 1)
        defaultCompaniesShouldNotBeFound("kindId.equals=" + (kindId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByPhonesIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);
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
        companies.addPhones(phones);
        companiesRepository.saveAndFlush(companies);
        Long phonesId = phones.getId();

        // Get all the companiesList where phones equals to phonesId
        defaultCompaniesShouldBeFound("phonesId.equals=" + phonesId);

        // Get all the companiesList where phones equals to (phonesId + 1)
        defaultCompaniesShouldNotBeFound("phonesId.equals=" + (phonesId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByAddressesIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);
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
        companies.addAddresses(addresses);
        companiesRepository.saveAndFlush(companies);
        Long addressesId = addresses.getId();

        // Get all the companiesList where addresses equals to addressesId
        defaultCompaniesShouldBeFound("addressesId.equals=" + addressesId);

        // Get all the companiesList where addresses equals to (addressesId + 1)
        defaultCompaniesShouldNotBeFound("addressesId.equals=" + (addressesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompaniesShouldBeFound(String filter) throws Exception {
        restCompaniesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companies.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCompaniesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompaniesShouldNotBeFound(String filter) throws Exception {
        restCompaniesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompaniesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCompanies() throws Exception {
        // Get the companies
        restCompaniesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompanies() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();

        // Update the companies
        Companies updatedCompanies = companiesRepository.findById(companies.getId()).get();
        // Disconnect from session so that the updates on updatedCompanies are not directly saved in db
        em.detach(updatedCompanies);
        updatedCompanies.name(UPDATED_NAME);

        restCompaniesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompanies.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompanies))
            )
            .andExpect(status().isOk());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
        Companies testCompanies = companiesList.get(companiesList.size() - 1);
        assertThat(testCompanies.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companies.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companies))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companies))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companies)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompaniesWithPatch() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();

        // Update the companies using partial update
        Companies partialUpdatedCompanies = new Companies();
        partialUpdatedCompanies.setId(companies.getId());

        partialUpdatedCompanies.name(UPDATED_NAME);

        restCompaniesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanies.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanies))
            )
            .andExpect(status().isOk());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
        Companies testCompanies = companiesList.get(companiesList.size() - 1);
        assertThat(testCompanies.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCompaniesWithPatch() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();

        // Update the companies using partial update
        Companies partialUpdatedCompanies = new Companies();
        partialUpdatedCompanies.setId(companies.getId());

        partialUpdatedCompanies.name(UPDATED_NAME);

        restCompaniesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanies.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanies))
            )
            .andExpect(status().isOk());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
        Companies testCompanies = companiesList.get(companiesList.size() - 1);
        assertThat(testCompanies.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companies.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companies))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companies))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(companies))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanies() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        int databaseSizeBeforeDelete = companiesRepository.findAll().size();

        // Delete the companies
        restCompaniesMockMvc
            .perform(delete(ENTITY_API_URL_ID, companies.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
