<template>
  <div>
    <h2 id="page-heading" data-cy="PhonesHeading">
      <span v-text="$t('jhipsterSampleApplication123App.phones.home.title')" id="phones-heading">Phones</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('jhipsterSampleApplication123App.phones.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PhonesCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-phones"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('jhipsterSampleApplication123App.phones.home.createLabel')"> Create a new Phones </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && phones && phones.length === 0">
      <span v-text="$t('jhipsterSampleApplication123App.phones.home.notFound')">No phones found</span>
    </div>
    <div class="table-responsive" v-if="phones && phones.length > 0">
      <table class="table table-striped" aria-describedby="phones">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('phone')">
              <span v-text="$t('jhipsterSampleApplication123App.phones.phone')">Phone</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phone'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('jhipsterSampleApplication123App.phones.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('favourite')">
              <span v-text="$t('jhipsterSampleApplication123App.phones.favourite')">Favourite</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'favourite'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('kind.name')">
              <span v-text="$t('jhipsterSampleApplication123App.phones.kind')">Kind</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'kind.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('company.name')">
              <span v-text="$t('jhipsterSampleApplication123App.phones.company')">Company</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'company.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('citizen.id')">
              <span v-text="$t('jhipsterSampleApplication123App.phones.citizen')">Citizen</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'citizen.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="phones in phones" :key="phones.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PhonesView', params: { phonesId: phones.id } }">{{ phones.id }}</router-link>
            </td>
            <td>{{ phones.phone }}</td>
            <td>{{ phones.description }}</td>
            <td>{{ phones.favourite }}</td>
            <td>
              <div v-if="phones.kind">
                <router-link :to="{ name: 'PhoneTypesView', params: { phoneTypesId: phones.kind.id } }">{{ phones.kind.name }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="phones.company">
                <router-link :to="{ name: 'CompaniesView', params: { companiesId: phones.company.id } }">{{
                  phones.company.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="phones.citizen">
                <router-link :to="{ name: 'CitizensView', params: { citizensId: phones.citizen.id } }">{{ phones.citizen.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PhonesView', params: { phonesId: phones.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PhonesEdit', params: { phonesId: phones.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(phones)"
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
          id="jhipsterSampleApplication123App.phones.delete.question"
          data-cy="phonesDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-phones-heading" v-text="$t('jhipsterSampleApplication123App.phones.delete.question', { id: removeId })">
          Are you sure you want to delete this Phones?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-phones"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePhones()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="phones && phones.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./phones.component.ts"></script>
