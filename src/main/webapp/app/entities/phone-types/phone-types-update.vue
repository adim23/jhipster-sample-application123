<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.phoneTypes.home.createOrEditLabel"
          data-cy="PhoneTypesCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.phoneTypes.home.createOrEditLabel')"
        >
          Create or edit a PhoneTypes
        </h2>
        <div>
          <div class="form-group" v-if="phoneTypes.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="phoneTypes.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.phoneTypes.name')" for="phone-types-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="phone-types-name"
              data-cy="name"
              :class="{ valid: !$v.phoneTypes.name.$invalid, invalid: $v.phoneTypes.name.$invalid }"
              v-model="$v.phoneTypes.name.$model"
              required
            />
            <div v-if="$v.phoneTypes.name.$anyDirty && $v.phoneTypes.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.phoneTypes.name.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.phoneTypes.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./phone-types-update.component.ts"></script>
