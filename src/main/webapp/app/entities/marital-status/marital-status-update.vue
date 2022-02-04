<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.maritalStatus.home.createOrEditLabel"
          data-cy="MaritalStatusCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.maritalStatus.home.createOrEditLabel')"
        >
          Create or edit a MaritalStatus
        </h2>
        <div>
          <div class="form-group" v-if="maritalStatus.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="maritalStatus.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.maritalStatus.name')" for="marital-status-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="marital-status-name"
              data-cy="name"
              :class="{ valid: !$v.maritalStatus.name.$invalid, invalid: $v.maritalStatus.name.$invalid }"
              v-model="$v.maritalStatus.name.$model"
              required
            />
            <div v-if="$v.maritalStatus.name.$anyDirty && $v.maritalStatus.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.maritalStatus.name.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.maritalStatus.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./marital-status-update.component.ts"></script>
