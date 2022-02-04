<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.origins.home.createOrEditLabel"
          data-cy="OriginsCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.origins.home.createOrEditLabel')"
        >
          Create or edit a Origins
        </h2>
        <div>
          <div class="form-group" v-if="origins.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="origins.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.origins.name')" for="origins-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="origins-name"
              data-cy="name"
              :class="{ valid: !$v.origins.name.$invalid, invalid: $v.origins.name.$invalid }"
              v-model="$v.origins.name.$model"
              required
            />
            <div v-if="$v.origins.name.$anyDirty && $v.origins.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.origins.name.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.origins.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./origins-update.component.ts"></script>
