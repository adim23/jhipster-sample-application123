<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.citizenFolders.home.createOrEditLabel"
          data-cy="CitizenFoldersCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.citizenFolders.home.createOrEditLabel')"
        >
          Create or edit a CitizenFolders
        </h2>
        <div>
          <div class="form-group" v-if="citizenFolders.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="citizenFolders.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizenFolders.name')" for="citizen-folders-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="citizen-folders-name"
              data-cy="name"
              :class="{ valid: !$v.citizenFolders.name.$invalid, invalid: $v.citizenFolders.name.$invalid }"
              v-model="$v.citizenFolders.name.$model"
              required
            />
            <div v-if="$v.citizenFolders.name.$anyDirty && $v.citizenFolders.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.citizenFolders.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.citizenFolders.description')"
              for="citizen-folders-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="citizen-folders-description"
              data-cy="description"
              :class="{ valid: !$v.citizenFolders.description.$invalid, invalid: $v.citizenFolders.description.$invalid }"
              v-model="$v.citizenFolders.description.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.citizenFolders.special')"
              for="citizen-folders-special"
              >Special</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="special"
              id="citizen-folders-special"
              data-cy="special"
              :class="{ valid: !$v.citizenFolders.special.$invalid, invalid: $v.citizenFolders.special.$invalid }"
              v-model="$v.citizenFolders.special.$model"
              required
            />
            <div v-if="$v.citizenFolders.special.$anyDirty && $v.citizenFolders.special.$invalid">
              <small class="form-text text-danger" v-if="!$v.citizenFolders.special.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
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
            :disabled="$v.citizenFolders.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./citizen-folders-update.component.ts"></script>
