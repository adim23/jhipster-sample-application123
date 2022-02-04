<template>
  <div>
    <h2 id="page-heading" data-cy="SocialKindsHeading">
      <span v-text="$t('jhipsterSampleApplication123App.socialKinds.home.title')" id="social-kinds-heading">Social Kinds</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('jhipsterSampleApplication123App.socialKinds.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'SocialKindsCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-social-kinds"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('jhipsterSampleApplication123App.socialKinds.home.createLabel')"> Create a new Social Kinds </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && socialKinds && socialKinds.length === 0">
      <span v-text="$t('jhipsterSampleApplication123App.socialKinds.home.notFound')">No socialKinds found</span>
    </div>
    <div class="table-responsive" v-if="socialKinds && socialKinds.length > 0">
      <table class="table table-striped" aria-describedby="socialKinds">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('code')">
              <span v-text="$t('jhipsterSampleApplication123App.socialKinds.code')">Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('jhipsterSampleApplication123App.socialKinds.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('call')">
              <span v-text="$t('jhipsterSampleApplication123App.socialKinds.call')">Call</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'call'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="socialKinds in socialKinds" :key="socialKinds.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SocialKindsView', params: { socialKindsId: socialKinds.id } }">{{ socialKinds.id }}</router-link>
            </td>
            <td>{{ socialKinds.code }}</td>
            <td>{{ socialKinds.name }}</td>
            <td>{{ socialKinds.call }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SocialKindsView', params: { socialKindsId: socialKinds.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SocialKindsEdit', params: { socialKindsId: socialKinds.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(socialKinds)"
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
          id="jhipsterSampleApplication123App.socialKinds.delete.question"
          data-cy="socialKindsDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-socialKinds-heading" v-text="$t('jhipsterSampleApplication123App.socialKinds.delete.question', { id: removeId })">
          Are you sure you want to delete this Social Kinds?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-socialKinds"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeSocialKinds()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="socialKinds && socialKinds.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./social-kinds.component.ts"></script>
