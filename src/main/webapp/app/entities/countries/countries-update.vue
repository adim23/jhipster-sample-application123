<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.countries.home.createOrEditLabel"
          data-cy="CountriesCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.countries.home.createOrEditLabel')"
        >
          Create or edit a Countries
        </h2>
        <div>
          <div class="form-group" v-if="countries.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="countries.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.countries.name')" for="countries-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="countries-name"
              data-cy="name"
              :class="{ valid: !$v.countries.name.$invalid, invalid: $v.countries.name.$invalid }"
              v-model="$v.countries.name.$model"
              required
            />
            <div v-if="$v.countries.name.$anyDirty && $v.countries.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.countries.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.countries.iso')" for="countries-iso">Iso</label>
            <input
              type="text"
              class="form-control"
              name="iso"
              id="countries-iso"
              data-cy="iso"
              :class="{ valid: !$v.countries.iso.$invalid, invalid: $v.countries.iso.$invalid }"
              v-model="$v.countries.iso.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.countries.language')" for="countries-language"
              >Language</label
            >
            <input
              type="text"
              class="form-control"
              name="language"
              id="countries-language"
              data-cy="language"
              :class="{ valid: !$v.countries.language.$invalid, invalid: $v.countries.language.$invalid }"
              v-model="$v.countries.language.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.countries.lang')" for="countries-lang"
              >Lang</label
            >
            <input
              type="text"
              class="form-control"
              name="lang"
              id="countries-lang"
              data-cy="lang"
              :class="{ valid: !$v.countries.lang.$invalid, invalid: $v.countries.lang.$invalid }"
              v-model="$v.countries.lang.$model"
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
            :disabled="$v.countries.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./countries-update.component.ts"></script>
