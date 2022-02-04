<template>
  <div>
    <h2 id="page-heading" data-cy="SocialContactsHeading">
      <span v-text="$t('jhipsterSampleApplication123App.socialContacts.home.title')" id="social-contacts-heading">Social Contacts</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('jhipsterSampleApplication123App.socialContacts.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'SocialContactsCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-social-contacts"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('jhipsterSampleApplication123App.socialContacts.home.createLabel')"> Create a new Social Contacts </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && socialContacts && socialContacts.length === 0">
      <span v-text="$t('jhipsterSampleApplication123App.socialContacts.home.notFound')">No socialContacts found</span>
    </div>
    <div class="table-responsive" v-if="socialContacts && socialContacts.length > 0">
      <table class="table table-striped" aria-describedby="socialContacts">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('jhipsterSampleApplication123App.socialContacts.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('favored')">
              <span v-text="$t('jhipsterSampleApplication123App.socialContacts.favored')">Favored</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'favored'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('social.name')">
              <span v-text="$t('jhipsterSampleApplication123App.socialContacts.social')">Social</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'social.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('kind.name')">
              <span v-text="$t('jhipsterSampleApplication123App.socialContacts.kind')">Kind</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'kind.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('citizen.id')">
              <span v-text="$t('jhipsterSampleApplication123App.socialContacts.citizen')">Citizen</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'citizen.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="socialContacts in socialContacts" :key="socialContacts.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SocialContactsView', params: { socialContactsId: socialContacts.id } }">{{
                socialContacts.id
              }}</router-link>
            </td>
            <td>{{ socialContacts.name }}</td>
            <td>{{ socialContacts.favored }}</td>
            <td>
              <div v-if="socialContacts.social">
                <router-link :to="{ name: 'SocialKindsView', params: { socialKindsId: socialContacts.social.id } }">{{
                  socialContacts.social.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="socialContacts.kind">
                <router-link :to="{ name: 'ContactTypesView', params: { contactTypesId: socialContacts.kind.id } }">{{
                  socialContacts.kind.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="socialContacts.citizen">
                <router-link :to="{ name: 'CitizensView', params: { citizensId: socialContacts.citizen.id } }">{{
                  socialContacts.citizen.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'SocialContactsView', params: { socialContactsId: socialContacts.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'SocialContactsEdit', params: { socialContactsId: socialContacts.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(socialContacts)"
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
          id="jhipsterSampleApplication123App.socialContacts.delete.question"
          data-cy="socialContactsDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p
          id="jhi-delete-socialContacts-heading"
          v-text="$t('jhipsterSampleApplication123App.socialContacts.delete.question', { id: removeId })"
        >
          Are you sure you want to delete this Social Contacts?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-socialContacts"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeSocialContacts()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="socialContacts && socialContacts.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./social-contacts.component.ts"></script>
