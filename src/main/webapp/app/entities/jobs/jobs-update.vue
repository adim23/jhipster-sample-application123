<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.jobs.home.createOrEditLabel"
          data-cy="JobsCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.jobs.home.createOrEditLabel')"
        >
          Create or edit a Jobs
        </h2>
        <div>
          <div class="form-group" v-if="jobs.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="jobs.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.jobs.name')" for="jobs-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="jobs-name"
              data-cy="name"
              :class="{ valid: !$v.jobs.name.$invalid, invalid: $v.jobs.name.$invalid }"
              v-model="$v.jobs.name.$model"
              required
            />
            <div v-if="$v.jobs.name.$anyDirty && $v.jobs.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.jobs.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.jobs.description')" for="jobs-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="jobs-description"
              data-cy="description"
              :class="{ valid: !$v.jobs.description.$invalid, invalid: $v.jobs.description.$invalid }"
              v-model="$v.jobs.description.$model"
            />
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
            :disabled="$v.jobs.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./jobs-update.component.ts"></script>
