<template>
  <div>
    <h2 id="page-heading" data-cy="CitizensRelationsHeading">
      <span v-text="$t('jhipsterSampleApplication123App.citizensRelations.home.title')" id="citizens-relations-heading"
        >Citizens Relations</span
      >
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('jhipsterSampleApplication123App.citizensRelations.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CitizensRelationsCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-citizens-relations"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('jhipsterSampleApplication123App.citizensRelations.home.createLabel')"> Create a new Citizens Relations </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && citizensRelations && citizensRelations.length === 0">
      <span v-text="$t('jhipsterSampleApplication123App.citizensRelations.home.notFound')">No citizensRelations found</span>
    </div>
    <div class="table-responsive" v-if="citizensRelations && citizensRelations.length > 0">
      <table class="table table-striped" aria-describedby="citizensRelations">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('jhipsterSampleApplication123App.citizensRelations.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('citizen.id')">
              <span v-text="$t('jhipsterSampleApplication123App.citizensRelations.citizen')">Citizen</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'citizen.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="citizensRelations in citizensRelations" :key="citizensRelations.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CitizensRelationsView', params: { citizensRelationsId: citizensRelations.id } }">{{
                citizensRelations.id
              }}</router-link>
            </td>
            <td>{{ citizensRelations.name }}</td>
            <td>
              <div v-if="citizensRelations.citizen">
                <router-link :to="{ name: 'CitizensView', params: { citizensId: citizensRelations.citizen.id } }">{{
                  citizensRelations.citizen.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'CitizensRelationsView', params: { citizensRelationsId: citizensRelations.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'CitizensRelationsEdit', params: { citizensRelationsId: citizensRelations.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(citizensRelations)"
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
          id="jhipsterSampleApplication123App.citizensRelations.delete.question"
          data-cy="citizensRelationsDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p
          id="jhi-delete-citizensRelations-heading"
          v-text="$t('jhipsterSampleApplication123App.citizensRelations.delete.question', { id: removeId })"
        >
          Are you sure you want to delete this Citizens Relations?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-citizensRelations"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCitizensRelations()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="citizensRelations && citizensRelations.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./citizens-relations.component.ts"></script>
