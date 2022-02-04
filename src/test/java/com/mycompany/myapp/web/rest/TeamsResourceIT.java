package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Teams;
import com.mycompany.myapp.repository.TeamsRepository;
import com.mycompany.myapp.service.criteria.TeamsCriteria;
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
 * Integration tests for the {@link TeamsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TeamsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/teams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TeamsRepository teamsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeamsMockMvc;

    private Teams teams;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Teams createEntity(EntityManager em) {
        Teams teams = new Teams().name(DEFAULT_NAME);
        return teams;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Teams createUpdatedEntity(EntityManager em) {
        Teams teams = new Teams().name(UPDATED_NAME);
        return teams;
    }

    @BeforeEach
    public void initTest() {
        teams = createEntity(em);
    }

    @Test
    @Transactional
    void createTeams() throws Exception {
        int databaseSizeBeforeCreate = teamsRepository.findAll().size();
        // Create the Teams
        restTeamsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teams)))
            .andExpect(status().isCreated());

        // Validate the Teams in the database
        List<Teams> teamsList = teamsRepository.findAll();
        assertThat(teamsList).hasSize(databaseSizeBeforeCreate + 1);
        Teams testTeams = teamsList.get(teamsList.size() - 1);
        assertThat(testTeams.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createTeamsWithExistingId() throws Exception {
        // Create the Teams with an existing ID
        teams.setId(1L);

        int databaseSizeBeforeCreate = teamsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teams)))
            .andExpect(status().isBadRequest());

        // Validate the Teams in the database
        List<Teams> teamsList = teamsRepository.findAll();
        assertThat(teamsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamsRepository.findAll().size();
        // set the field null
        teams.setName(null);

        // Create the Teams, which fails.

        restTeamsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teams)))
            .andExpect(status().isBadRequest());

        List<Teams> teamsList = teamsRepository.findAll();
        assertThat(teamsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTeams() throws Exception {
        // Initialize the database
        teamsRepository.saveAndFlush(teams);

        // Get all the teamsList
        restTeamsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teams.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getTeams() throws Exception {
        // Initialize the database
        teamsRepository.saveAndFlush(teams);

        // Get the teams
        restTeamsMockMvc
            .perform(get(ENTITY_API_URL_ID, teams.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teams.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getTeamsByIdFiltering() throws Exception {
        // Initialize the database
        teamsRepository.saveAndFlush(teams);

        Long id = teams.getId();

        defaultTeamsShouldBeFound("id.equals=" + id);
        defaultTeamsShouldNotBeFound("id.notEquals=" + id);

        defaultTeamsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTeamsShouldNotBeFound("id.greaterThan=" + id);

        defaultTeamsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTeamsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTeamsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        teamsRepository.saveAndFlush(teams);

        // Get all the teamsList where name equals to DEFAULT_NAME
        defaultTeamsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the teamsList where name equals to UPDATED_NAME
        defaultTeamsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTeamsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teamsRepository.saveAndFlush(teams);

        // Get all the teamsList where name not equals to DEFAULT_NAME
        defaultTeamsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the teamsList where name not equals to UPDATED_NAME
        defaultTeamsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTeamsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        teamsRepository.saveAndFlush(teams);

        // Get all the teamsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTeamsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the teamsList where name equals to UPDATED_NAME
        defaultTeamsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTeamsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamsRepository.saveAndFlush(teams);

        // Get all the teamsList where name is not null
        defaultTeamsShouldBeFound("name.specified=true");

        // Get all the teamsList where name is null
        defaultTeamsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamsByNameContainsSomething() throws Exception {
        // Initialize the database
        teamsRepository.saveAndFlush(teams);

        // Get all the teamsList where name contains DEFAULT_NAME
        defaultTeamsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the teamsList where name contains UPDATED_NAME
        defaultTeamsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTeamsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        teamsRepository.saveAndFlush(teams);

        // Get all the teamsList where name does not contain DEFAULT_NAME
        defaultTeamsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the teamsList where name does not contain UPDATED_NAME
        defaultTeamsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTeamsShouldBeFound(String filter) throws Exception {
        restTeamsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teams.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restTeamsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTeamsShouldNotBeFound(String filter) throws Exception {
        restTeamsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTeamsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTeams() throws Exception {
        // Get the teams
        restTeamsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTeams() throws Exception {
        // Initialize the database
        teamsRepository.saveAndFlush(teams);

        int databaseSizeBeforeUpdate = teamsRepository.findAll().size();

        // Update the teams
        Teams updatedTeams = teamsRepository.findById(teams.getId()).get();
        // Disconnect from session so that the updates on updatedTeams are not directly saved in db
        em.detach(updatedTeams);
        updatedTeams.name(UPDATED_NAME);

        restTeamsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTeams.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTeams))
            )
            .andExpect(status().isOk());

        // Validate the Teams in the database
        List<Teams> teamsList = teamsRepository.findAll();
        assertThat(teamsList).hasSize(databaseSizeBeforeUpdate);
        Teams testTeams = teamsList.get(teamsList.size() - 1);
        assertThat(testTeams.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingTeams() throws Exception {
        int databaseSizeBeforeUpdate = teamsRepository.findAll().size();
        teams.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teams.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teams))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teams in the database
        List<Teams> teamsList = teamsRepository.findAll();
        assertThat(teamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTeams() throws Exception {
        int databaseSizeBeforeUpdate = teamsRepository.findAll().size();
        teams.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teams))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teams in the database
        List<Teams> teamsList = teamsRepository.findAll();
        assertThat(teamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTeams() throws Exception {
        int databaseSizeBeforeUpdate = teamsRepository.findAll().size();
        teams.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teams)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Teams in the database
        List<Teams> teamsList = teamsRepository.findAll();
        assertThat(teamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTeamsWithPatch() throws Exception {
        // Initialize the database
        teamsRepository.saveAndFlush(teams);

        int databaseSizeBeforeUpdate = teamsRepository.findAll().size();

        // Update the teams using partial update
        Teams partialUpdatedTeams = new Teams();
        partialUpdatedTeams.setId(teams.getId());

        partialUpdatedTeams.name(UPDATED_NAME);

        restTeamsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeams.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeams))
            )
            .andExpect(status().isOk());

        // Validate the Teams in the database
        List<Teams> teamsList = teamsRepository.findAll();
        assertThat(teamsList).hasSize(databaseSizeBeforeUpdate);
        Teams testTeams = teamsList.get(teamsList.size() - 1);
        assertThat(testTeams.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateTeamsWithPatch() throws Exception {
        // Initialize the database
        teamsRepository.saveAndFlush(teams);

        int databaseSizeBeforeUpdate = teamsRepository.findAll().size();

        // Update the teams using partial update
        Teams partialUpdatedTeams = new Teams();
        partialUpdatedTeams.setId(teams.getId());

        partialUpdatedTeams.name(UPDATED_NAME);

        restTeamsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeams.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeams))
            )
            .andExpect(status().isOk());

        // Validate the Teams in the database
        List<Teams> teamsList = teamsRepository.findAll();
        assertThat(teamsList).hasSize(databaseSizeBeforeUpdate);
        Teams testTeams = teamsList.get(teamsList.size() - 1);
        assertThat(testTeams.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingTeams() throws Exception {
        int databaseSizeBeforeUpdate = teamsRepository.findAll().size();
        teams.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teams.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teams))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teams in the database
        List<Teams> teamsList = teamsRepository.findAll();
        assertThat(teamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTeams() throws Exception {
        int databaseSizeBeforeUpdate = teamsRepository.findAll().size();
        teams.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teams))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teams in the database
        List<Teams> teamsList = teamsRepository.findAll();
        assertThat(teamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTeams() throws Exception {
        int databaseSizeBeforeUpdate = teamsRepository.findAll().size();
        teams.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(teams)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Teams in the database
        List<Teams> teamsList = teamsRepository.findAll();
        assertThat(teamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTeams() throws Exception {
        // Initialize the database
        teamsRepository.saveAndFlush(teams);

        int databaseSizeBeforeDelete = teamsRepository.findAll().size();

        // Delete the teams
        restTeamsMockMvc
            .perform(delete(ENTITY_API_URL_ID, teams.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Teams> teamsList = teamsRepository.findAll();
        assertThat(teamsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
