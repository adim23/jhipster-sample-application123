<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.companyKinds.home.createOrEditLabel"
          data-cy="CompanyKindsCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.companyKinds.home.createOrEditLabel')"
        >
          Create or edit a CompanyKinds
        </h2>
        <div>
          <div class="form-group" v-if="companyKinds.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="companyKinds.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.companyKinds.name')" for="company-kinds-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="company-kinds-name"
              data-cy="name"
              :class="{ valid: !$v.companyKinds.name.$invalid, invalid: $v.companyKinds.name.$invalid }"
              v-model="$v.companyKinds.name.$model"
              required
            />
            <div v-if="$v.companyKinds.name.$anyDirty && $v.companyKinds.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.companyKinds.name.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.companyKinds.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./company-kinds-update.component.ts"></script>
