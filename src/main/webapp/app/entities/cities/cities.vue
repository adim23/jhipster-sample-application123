<template>
  <div>
    <h2 id="page-heading" data-cy="CitiesHeading">
      <span v-text="$t('jhipsterSampleApplication123App.cities.home.title')" id="cities-heading">Cities</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('jhipsterSampleApplication123App.cities.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CitiesCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-cities"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('jhipsterSampleApplication123App.cities.home.createLabel')"> Create a new Cities </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && cities && cities.length === 0">
      <span v-text="$t('jhipsterSampleApplication123App.cities.home.notFound')">No cities found</span>
    </div>
    <div class="table-responsive" v-if="cities && cities.length > 0">
      <table class="table table-striped" aria-describedby="cities">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('jhipsterSampleApplication123App.cities.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('president')">
              <span v-text="$t('jhipsterSampleApplication123App.cities.president')">President</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'president'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('presidentsPhone')">
              <span v-text="$t('jhipsterSampleApplication123App.cities.presidentsPhone')">Presidents Phone</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'presidentsPhone'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('secretary')">
              <span v-text="$t('jhipsterSampleApplication123App.cities.secretary')">Secretary</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'secretary'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('secretarysPhone')">
              <span v-text="$t('jhipsterSampleApplication123App.cities.secretarysPhone')">Secretarys Phone</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'secretarysPhone'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('police')">
              <span v-text="$t('jhipsterSampleApplication123App.cities.police')">Police</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'police'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('policesPhone')">
              <span v-text="$t('jhipsterSampleApplication123App.cities.policesPhone')">Polices Phone</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'policesPhone'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('doctor')">
              <span v-text="$t('jhipsterSampleApplication123App.cities.doctor')">Doctor</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'doctor'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('doctorsPhone')">
              <span v-text="$t('jhipsterSampleApplication123App.cities.doctorsPhone')">Doctors Phone</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'doctorsPhone'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('teacher')">
              <span v-text="$t('jhipsterSampleApplication123App.cities.teacher')">Teacher</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'teacher'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('teachersPhone')">
              <span v-text="$t('jhipsterSampleApplication123App.cities.teachersPhone')">Teachers Phone</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'teachersPhone'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('priest')">
              <span v-text="$t('jhipsterSampleApplication123App.cities.priest')">Priest</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'priest'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('priestsPhone')">
              <span v-text="$t('jhipsterSampleApplication123App.cities.priestsPhone')">Priests Phone</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'priestsPhone'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('country.name')">
              <span v-text="$t('jhipsterSampleApplication123App.cities.country')">Country</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'country.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('region.name')">
              <span v-text="$t('jhipsterSampleApplication123App.cities.region')">Region</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'region.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cities in cities" :key="cities.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CitiesView', params: { citiesId: cities.id } }">{{ cities.id }}</router-link>
            </td>
            <td>{{ cities.name }}</td>
            <td>{{ cities.president }}</td>
            <td>{{ cities.presidentsPhone }}</td>
            <td>{{ cities.secretary }}</td>
            <td>{{ cities.secretarysPhone }}</td>
            <td>{{ cities.police }}</td>
            <td>{{ cities.policesPhone }}</td>
            <td>{{ cities.doctor }}</td>
            <td>{{ cities.doctorsPhone }}</td>
            <td>{{ cities.teacher }}</td>
            <td>{{ cities.teachersPhone }}</td>
            <td>{{ cities.priest }}</td>
            <td>{{ cities.priestsPhone }}</td>
            <td>
              <div v-if="cities.country">
                <router-link :to="{ name: 'CountriesView', params: { countriesId: cities.country.id } }">{{
                  cities.country.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="cities.region">
                <router-link :to="{ name: 'RegionsView', params: { regionsId: cities.region.id } }">{{ cities.region.name }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CitiesView', params: { citiesId: cities.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CitiesEdit', params: { citiesId: cities.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(cities)"
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
          id="jhipsterSampleApplication123App.cities.delete.question"
          data-cy="citiesDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-cities-heading" v-text="$t('jhipsterSampleApplication123App.cities.delete.question', { id: removeId })">
          Are you sure you want to delete this Cities?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-cities"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCities()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="cities && cities.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cities.component.ts"></script>
