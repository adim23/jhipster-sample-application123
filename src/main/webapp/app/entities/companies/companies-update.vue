<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.companies.home.createOrEditLabel"
          data-cy="CompaniesCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.companies.home.createOrEditLabel')"
        >
          Create or edit a Companies
        </h2>
        <div>
          <div class="form-group" v-if="companies.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="companies.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.companies.name')" for="companies-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="companies-name"
              data-cy="name"
              :class="{ valid: !$v.companies.name.$invalid, invalid: $v.companies.name.$invalid }"
              v-model="$v.companies.name.$model"
              required
            />
            <div v-if="$v.companies.name.$anyDirty && $v.companies.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.companies.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.companies.kind')" for="companies-kind"
              >Kind</label
            >
            <select class="form-control" id="companies-kind" data-cy="kind" name="kind" v-model="companies.kind">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="companies.kind && companyKindsOption.id === companies.kind.id ? companies.kind : companyKindsOption"
                v-for="companyKindsOption in companyKinds"
                :key="companyKindsOption.id"
              >
                {{ companyKindsOption.name }}
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
            :disabled="$v.companies.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./companies-update.component.ts"></script>
