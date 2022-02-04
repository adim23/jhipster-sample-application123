<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.regions.home.createOrEditLabel"
          data-cy="RegionsCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.regions.home.createOrEditLabel')"
        >
          Create or edit a Regions
        </h2>
        <div>
          <div class="form-group" v-if="regions.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="regions.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.regions.name')" for="regions-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="regions-name"
              data-cy="name"
              :class="{ valid: !$v.regions.name.$invalid, invalid: $v.regions.name.$invalid }"
              v-model="$v.regions.name.$model"
              required
            />
            <div v-if="$v.regions.name.$anyDirty && $v.regions.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.regions.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.regions.country')" for="regions-country"
              >Country</label
            >
            <select class="form-control" id="regions-country" data-cy="country" name="country" v-model="regions.country" required>
              <option v-if="!regions.country" v-bind:value="null" selected></option>
              <option
                v-bind:value="regions.country && countriesOption.id === regions.country.id ? regions.country : countriesOption"
                v-for="countriesOption in countries"
                :key="countriesOption.id"
              >
                {{ countriesOption.name }}
              </option>
            </select>
          </div>
          <div v-if="$v.regions.country.$anyDirty && $v.regions.country.$invalid">
            <small class="form-text text-danger" v-if="!$v.regions.country.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
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
            :disabled="$v.regions.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./regions-update.component.ts"></script>
