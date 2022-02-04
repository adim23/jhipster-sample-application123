<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="citizens">
        <h2 class="jh-entity-heading" data-cy="citizensDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.citizens.detail.title')">Citizens</span> {{ citizens.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.title')">Title</span>
          </dt>
          <dd>
            <span>{{ citizens.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.lastname')">Lastname</span>
          </dt>
          <dd>
            <span>{{ citizens.lastname }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.firstname')">Firstname</span>
          </dt>
          <dd>
            <span>{{ citizens.firstname }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.fathersName')">Fathers Name</span>
          </dt>
          <dd>
            <span>{{ citizens.fathersName }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.comments')">Comments</span>
          </dt>
          <dd>
            <span>{{ citizens.comments }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.birthDate')">Birth Date</span>
          </dt>
          <dd>
            <span>{{ citizens.birthDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.giortazi')">Giortazi</span>
          </dt>
          <dd>
            <span>{{ citizens.giortazi }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.male')">Male</span>
          </dt>
          <dd>
            <span>{{ citizens.male }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.meLetter')">Me Letter</span>
          </dt>
          <dd>
            <span>{{ citizens.meLetter }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.meLabel')">Me Label</span>
          </dt>
          <dd>
            <span>{{ citizens.meLabel }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.image')">Image</span>
          </dt>
          <dd>
            <div v-if="citizens.image">
              <a v-on:click="openFile(citizens.imageContentType, citizens.image)">
                <img
                  v-bind:src="'data:' + citizens.imageContentType + ';base64,' + citizens.image"
                  style="max-width: 100%"
                  alt="citizens image"
                />
              </a>
              {{ citizens.imageContentType }}, {{ byteSize(citizens.image) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.folder')">Folder</span>
          </dt>
          <dd>
            <div v-if="citizens.folder">
              <router-link :to="{ name: 'CitizenFoldersView', params: { citizenFoldersId: citizens.folder.id } }">{{
                citizens.folder.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.company')">Company</span>
          </dt>
          <dd>
            <div v-if="citizens.company">
              <router-link :to="{ name: 'CompaniesView', params: { companiesId: citizens.company.id } }">{{
                citizens.company.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.maritalStatus')">Marital Status</span>
          </dt>
          <dd>
            <div v-if="citizens.maritalStatus">
              <router-link :to="{ name: 'MaritalStatusView', params: { maritalStatusId: citizens.maritalStatus.id } }">{{
                citizens.maritalStatus.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.team')">Team</span>
          </dt>
          <dd>
            <div v-if="citizens.team">
              <router-link :to="{ name: 'TeamsView', params: { teamsId: citizens.team.id } }">{{ citizens.team.name }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.code')">Code</span>
          </dt>
          <dd>
            <div v-if="citizens.code">
              <router-link :to="{ name: 'CodesView', params: { codesId: citizens.code.id } }">{{ citizens.code.name }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.origin')">Origin</span>
          </dt>
          <dd>
            <div v-if="citizens.origin">
              <router-link :to="{ name: 'OriginsView', params: { originsId: citizens.origin.id } }">{{ citizens.origin.name }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.job')">Job</span>
          </dt>
          <dd>
            <div v-if="citizens.job">
              <router-link :to="{ name: 'JobsView', params: { jobsId: citizens.job.id } }">{{ citizens.job.name }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="citizens.id" :to="{ name: 'CitizensEdit', params: { citizensId: citizens.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./citizens-details.component.ts"></script>
