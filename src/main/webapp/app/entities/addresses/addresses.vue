<template>
  <div>
    <h2 id="page-heading" data-cy="AddressesHeading">
      <span v-text="$t('jhipsterSampleApplication123App.addresses.home.title')" id="addresses-heading">Addresses</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('jhipsterSampleApplication123App.addresses.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AddressesCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-addresses"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('jhipsterSampleApplication123App.addresses.home.createLabel')"> Create a new Addresses </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && addresses && addresses.length === 0">
      <span v-text="$t('jhipsterSampleApplication123App.addresses.home.notFound')">No addresses found</span>
    </div>
    <div class="table-responsive" v-if="addresses && addresses.length > 0">
      <table class="table table-striped" aria-describedby="addresses">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('address')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.address')">Address</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'address'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('addressNo')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.addressNo')">Address No</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'addressNo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('zipCode')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.zipCode')">Zip Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'zipCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('prosfLetter')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.prosfLetter')">Prosf Letter</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'prosfLetter'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nameLetter')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.nameLetter')">Name Letter</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nameLetter'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('letterClose')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.letterClose')">Letter Close</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'letterClose'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('firstLabel')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.firstLabel')">First Label</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'firstLabel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('secondLabel')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.secondLabel')">Second Label</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'secondLabel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('thirdLabel')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.thirdLabel')">Third Label</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'thirdLabel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fourthLabel')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.fourthLabel')">Fourth Label</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fourthLabel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fifthLabel')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.fifthLabel')">Fifth Label</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fifthLabel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('favourite')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.favourite')">Favourite</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'favourite'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('country.name')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.country')">Country</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'country.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('kind.name')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.kind')">Kind</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'kind.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('region.name')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.region')">Region</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'region.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('company.name')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.company')">Company</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'company.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('citizen.id')">
              <span v-text="$t('jhipsterSampleApplication123App.addresses.citizen')">Citizen</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'citizen.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="addresses in addresses" :key="addresses.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AddressesView', params: { addressesId: addresses.id } }">{{ addresses.id }}</router-link>
            </td>
            <td>{{ addresses.address }}</td>
            <td>{{ addresses.addressNo }}</td>
            <td>{{ addresses.zipCode }}</td>
            <td>{{ addresses.prosfLetter }}</td>
            <td>{{ addresses.nameLetter }}</td>
            <td>{{ addresses.letterClose }}</td>
            <td>{{ addresses.firstLabel }}</td>
            <td>{{ addresses.secondLabel }}</td>
            <td>{{ addresses.thirdLabel }}</td>
            <td>{{ addresses.fourthLabel }}</td>
            <td>{{ addresses.fifthLabel }}</td>
            <td>{{ addresses.favourite }}</td>
            <td>
              <div v-if="addresses.country">
                <router-link :to="{ name: 'CountriesView', params: { countriesId: addresses.country.id } }">{{
                  addresses.country.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="addresses.kind">
                <router-link :to="{ name: 'ContactTypesView', params: { contactTypesId: addresses.kind.id } }">{{
                  addresses.kind.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="addresses.region">
                <router-link :to="{ name: 'RegionsView', params: { regionsId: addresses.region.id } }">{{
                  addresses.region.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="addresses.company">
                <router-link :to="{ name: 'CompaniesView', params: { companiesId: addresses.company.id } }">{{
                  addresses.company.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="addresses.citizen">
                <router-link :to="{ name: 'CitizensView', params: { citizensId: addresses.citizen.id } }">{{
                  addresses.citizen.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AddressesView', params: { addressesId: addresses.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AddressesEdit', params: { addressesId: addresses.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(addresses)"
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
          id="jhipsterSampleApplication123App.addresses.delete.question"
          data-cy="addressesDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-addresses-heading" v-text="$t('jhipsterSampleApplication123App.addresses.delete.question', { id: removeId })">
          Are you sure you want to delete this Addresses?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-addresses"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAddresses()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="addresses && addresses.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./addresses.component.ts"></script>
