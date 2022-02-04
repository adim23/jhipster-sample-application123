<template>
  <div>
    <h2 id="page-heading" data-cy="CitizensHeading">
      <span v-text="$t('jhipsterSampleApplication123App.citizens.home.title')" id="citizens-heading">Citizens</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('jhipsterSampleApplication123App.citizens.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CitizensCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-citizens"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('jhipsterSampleApplication123App.citizens.home.createLabel')"> Create a new Citizens </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && citizens && citizens.length === 0">
      <span v-text="$t('jhipsterSampleApplication123App.citizens.home.notFound')">No citizens found</span>
    </div>
    <div class="table-responsive" v-if="citizens && citizens.length > 0">
      <table class="table table-striped" aria-describedby="citizens">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.title')">Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastname')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.lastname')">Lastname</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastname'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('firstname')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.firstname')">Firstname</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'firstname'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fathersName')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.fathersName')">Fathers Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fathersName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('comments')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.comments')">Comments</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'comments'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('birthDate')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.birthDate')">Birth Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'birthDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('giortazi')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.giortazi')">Giortazi</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'giortazi'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('male')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.male')">Male</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'male'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('meLetter')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.meLetter')">Me Letter</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'meLetter'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('meLabel')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.meLabel')">Me Label</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'meLabel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('image')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.image')">Image</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'image'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('folder.name')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.folder')">Folder</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'folder.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('company.name')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.company')">Company</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'company.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('maritalStatus.name')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.maritalStatus')">Marital Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'maritalStatus.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('team.name')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.team')">Team</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'team.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('code.name')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.code')">Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('origin.name')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.origin')">Origin</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'origin.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('job.name')">
              <span v-text="$t('jhipsterSampleApplication123App.citizens.job')">Job</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'job.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="citizens in citizens" :key="citizens.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CitizensView', params: { citizensId: citizens.id } }">{{ citizens.id }}</router-link>
            </td>
            <td>{{ citizens.title }}</td>
            <td>{{ citizens.lastname }}</td>
            <td>{{ citizens.firstname }}</td>
            <td>{{ citizens.fathersName }}</td>
            <td>{{ citizens.comments }}</td>
            <td>{{ citizens.birthDate }}</td>
            <td>{{ citizens.giortazi }}</td>
            <td>{{ citizens.male }}</td>
            <td>{{ citizens.meLetter }}</td>
            <td>{{ citizens.meLabel }}</td>
            <td>
              <a v-if="citizens.image" v-on:click="openFile(citizens.imageContentType, citizens.image)">
                <img
                  v-bind:src="'data:' + citizens.imageContentType + ';base64,' + citizens.image"
                  style="max-height: 30px"
                  alt="citizens image"
                />
              </a>
              <span v-if="citizens.image">{{ citizens.imageContentType }}, {{ byteSize(citizens.image) }}</span>
            </td>
            <td>
              <div v-if="citizens.folder">
                <router-link :to="{ name: 'CitizenFoldersView', params: { citizenFoldersId: citizens.folder.id } }">{{
                  citizens.folder.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="citizens.company">
                <router-link :to="{ name: 'CompaniesView', params: { companiesId: citizens.company.id } }">{{
                  citizens.company.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="citizens.maritalStatus">
                <router-link :to="{ name: 'MaritalStatusView', params: { maritalStatusId: citizens.maritalStatus.id } }">{{
                  citizens.maritalStatus.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="citizens.team">
                <router-link :to="{ name: 'TeamsView', params: { teamsId: citizens.team.id } }">{{ citizens.team.name }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="citizens.code">
                <router-link :to="{ name: 'CodesView', params: { codesId: citizens.code.id } }">{{ citizens.code.name }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="citizens.origin">
                <router-link :to="{ name: 'OriginsView', params: { originsId: citizens.origin.id } }">{{
                  citizens.origin.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="citizens.job">
                <router-link :to="{ name: 'JobsView', params: { jobsId: citizens.job.id } }">{{ citizens.job.name }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CitizensView', params: { citizensId: citizens.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CitizensEdit', params: { citizensId: citizens.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(citizens)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="jhipsterSampleApplication123App.citizens.delete.question"
          data-cy="citizensDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-citizens-heading" v-text="$t('jhipsterSampleApplication123App.citizens.delete.question', { id: removeId })">
          Are you sure you want to delete this Citizens?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-citizens"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCitizens()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="citizens && citizens.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./citizens.component.ts"></script>
