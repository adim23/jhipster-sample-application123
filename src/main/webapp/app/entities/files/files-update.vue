<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.files.home.createOrEditLabel"
          data-cy="FilesCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.files.home.createOrEditLabel')"
        >
          Create or edit a Files
        </h2>
        <div>
          <div class="form-group" v-if="files.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="files.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.files.uuid')" for="files-uuid">Uuid</label>
            <input
              type="text"
              class="form-control"
              name="uuid"
              id="files-uuid"
              data-cy="uuid"
              :class="{ valid: !$v.files.uuid.$invalid, invalid: $v.files.uuid.$invalid }"
              v-model="$v.files.uuid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.files.filename')" for="files-filename"
              >Filename</label
            >
            <input
              type="text"
              class="form-control"
              name="filename"
              id="files-filename"
              data-cy="filename"
              :class="{ valid: !$v.files.filename.$invalid, invalid: $v.files.filename.$invalid }"
              v-model="$v.files.filename.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.files.fileType')" for="files-fileType"
              >File Type</label
            >
            <select
              class="form-control"
              name="fileType"
              :class="{ valid: !$v.files.fileType.$invalid, invalid: $v.files.fileType.$invalid }"
              v-model="$v.files.fileType.$model"
              id="files-fileType"
              data-cy="fileType"
            >
              <option
                v-for="fileType in fileTypeValues"
                :key="fileType"
                v-bind:value="fileType"
                v-bind:label="$t('jhipsterSampleApplication123App.FileType.' + fileType)"
              >
                {{ fileType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.files.fileSize')" for="files-fileSize"
              >File Size</label
            >
            <input
              type="number"
              class="form-control"
              name="fileSize"
              id="files-fileSize"
              data-cy="fileSize"
              :class="{ valid: !$v.files.fileSize.$invalid, invalid: $v.files.fileSize.$invalid }"
              v-model.number="$v.files.fileSize.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.files.createDate')" for="files-createDate"
              >Create Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="files-createDate"
                  v-model="$v.files.createDate.$model"
                  name="createDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="files-createDate"
                data-cy="createDate"
                type="text"
                class="form-control"
                name="createDate"
                :class="{ valid: !$v.files.createDate.$invalid, invalid: $v.files.createDate.$invalid }"
                v-model="$v.files.createDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.files.filePath')" for="files-filePath"
              >File Path</label
            >
            <input
              type="text"
              class="form-control"
              name="filePath"
              id="files-filePath"
              data-cy="filePath"
              :class="{ valid: !$v.files.filePath.$invalid, invalid: $v.files.filePath.$invalid }"
              v-model="$v.files.filePath.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.files.version')" for="files-version"
              >Version</label
            >
            <input
              type="text"
              class="form-control"
              name="version"
              id="files-version"
              data-cy="version"
              :class="{ valid: !$v.files.version.$invalid, invalid: $v.files.version.$invalid }"
              v-model="$v.files.version.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.files.mime')" for="files-mime">Mime</label>
            <input
              type="text"
              class="form-control"
              name="mime"
              id="files-mime"
              data-cy="mime"
              :class="{ valid: !$v.files.mime.$invalid, invalid: $v.files.mime.$invalid }"
              v-model="$v.files.mime.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.files.parent')" for="files-parent">Parent</label>
            <select class="form-control" id="files-parent" data-cy="parent" name="parent" v-model="files.parent">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="files.parent && filesOption.id === files.parent.id ? files.parent : filesOption"
                v-for="filesOption in files"
                :key="filesOption.id"
              >
                {{ filesOption.filename }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.files.createdBy')" for="files-createdBy"
              >Created By</label
            >
            <select class="form-control" id="files-createdBy" data-cy="createdBy" name="createdBy" v-model="files.createdBy">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="files.createdBy && userOption.id === files.createdBy.id ? files.createdBy : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.login }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.files.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./files-update.component.ts"></script>
