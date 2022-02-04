<template>
  <div>
    <h2 id="page-heading" data-cy="CompaniesHeading">
      <span v-text="$t('jhipsterSampleApplication123App.companies.home.title')" id="companies-heading">Companies</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('jhipsterSampleApplication123App.companies.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CompaniesCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-companies"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('jhipsterSampleApplication123App.companies.home.createLabel')"> Create a new Companies </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && companies && companies.length === 0">
      <span v-text="$t('jhipsterSampleApplication123App.companies.home.notFound')">No companies found</span>
    </div>
    <div class="table-responsive" v-if="companies && companies.length > 0">
      <table class="table table-striped" aria-describedby="companies">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('jhipsterSampleApplication123App.companies.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('kind.name')">
              <span v-text="$t('jhipsterSampleApplication123App.companies.kind')">Kind</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'kind.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="companies in companies" :key="companies.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CompaniesView', params: { companiesId: companies.id } }">{{ companies.id }}</router-link>
            </td>
            <td>{{ companies.name }}</td>
            <td>
              <div v-if="companies.kind">
                <router-link :to="{ name: 'CompanyKindsView', params: { companyKindsId: companies.kind.id } }">{{
                  companies.kind.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CompaniesView', params: { companiesId: companies.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CompaniesEdit', params: { companiesId: companies.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(companies)"
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
          id="jhipsterSampleApplication123App.companies.delete.question"
          data-cy="companiesDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-companies-heading" v-text="$t('jhipsterSampleApplication123App.companies.delete.question', { id: removeId })">
          Are you sure you want to delete this Companies?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-companies"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCompanies()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="companies && companies.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./companies.component.ts"></script>
