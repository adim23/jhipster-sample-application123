<template>
  <div>
    <h2 id="page-heading" data-cy="FilesHeading">
      <span v-text="$t('jhipsterSampleApplication123App.files.home.title')" id="files-heading">Files</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('jhipsterSampleApplication123App.files.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'FilesCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-files"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('jhipsterSampleApplication123App.files.home.createLabel')"> Create a new Files </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && files && files.length === 0">
      <span v-text="$t('jhipsterSampleApplication123App.files.home.notFound')">No files found</span>
    </div>
    <div class="table-responsive" v-if="files && files.length > 0">
      <table class="table table-striped" aria-describedby="files">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('uuid')">
              <span v-text="$t('jhipsterSampleApplication123App.files.uuid')">Uuid</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'uuid'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('filename')">
              <span v-text="$t('jhipsterSampleApplication123App.files.filename')">Filename</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'filename'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fileType')">
              <span v-text="$t('jhipsterSampleApplication123App.files.fileType')">File Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fileSize')">
              <span v-text="$t('jhipsterSampleApplication123App.files.fileSize')">File Size</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileSize'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createDate')">
              <span v-text="$t('jhipsterSampleApplication123App.files.createDate')">Create Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('filePath')">
              <span v-text="$t('jhipsterSampleApplication123App.files.filePath')">File Path</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'filePath'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('version')">
              <span v-text="$t('jhipsterSampleApplication123App.files.version')">Version</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'version'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mime')">
              <span v-text="$t('jhipsterSampleApplication123App.files.mime')">Mime</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('parent.filename')">
              <span v-text="$t('jhipsterSampleApplication123App.files.parent')">Parent</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'parent.filename'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy.login')">
              <span v-text="$t('jhipsterSampleApplication123App.files.createdBy')">Created By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy.login'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="files in files" :key="files.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FilesView', params: { filesId: files.id } }">{{ files.id }}</router-link>
            </td>
            <td>{{ files.uuid }}</td>
            <td>{{ files.filename }}</td>
            <td v-text="$t('jhipsterSampleApplication123App.FileType.' + files.fileType)">{{ files.fileType }}</td>
            <td>{{ files.fileSize }}</td>
            <td>{{ files.createDate }}</td>
            <td>{{ files.filePath }}</td>
            <td>{{ files.version }}</td>
            <td>{{ files.mime }}</td>
            <td>
              <div v-if="files.parent">
                <router-link :to="{ name: 'FilesView', params: { filesId: files.parent.id } }">{{ files.parent.filename }}</router-link>
              </div>
            </td>
            <td>
              {{ files.createdBy ? files.createdBy.login : '' }}
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'FilesView', params: { filesId: files.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'FilesEdit', params: { filesId: files.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(files)"
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
          id="jhipsterSampleApplication123App.files.delete.question"
          data-cy="filesDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-files-heading" v-text="$t('jhipsterSampleApplication123App.files.delete.question', { id: removeId })">
          Are you sure you want to delete this Files?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-files"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeFiles()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="files && files.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./files.component.ts"></script>
