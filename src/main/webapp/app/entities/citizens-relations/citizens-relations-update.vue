<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.citizensRelations.home.createOrEditLabel"
          data-cy="CitizensRelationsCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.citizensRelations.home.createOrEditLabel')"
        >
          Create or edit a CitizensRelations
        </h2>
        <div>
          <div class="form-group" v-if="citizensRelations.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="citizensRelations.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.citizensRelations.name')"
              for="citizens-relations-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="citizens-relations-name"
              data-cy="name"
              :class="{ valid: !$v.citizensRelations.name.$invalid, invalid: $v.citizensRelations.name.$invalid }"
              v-model="$v.citizensRelations.name.$model"
              required
            />
            <div v-if="$v.citizensRelations.name.$anyDirty && $v.citizensRelations.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.citizensRelations.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.citizensRelations.citizen')"
              for="citizens-relations-citizen"
              >Citizen</label
            >
            <select
              class="form-control"
              id="citizens-relations-citizen"
              data-cy="citizen"
              name="citizen"
              v-model="citizensRelations.citizen"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  citizensRelations.citizen && citizensOption.id === citizensRelations.citizen.id
                    ? citizensRelations.citizen
                    : citizensOption
                "
                v-for="citizensOption in citizens"
                :key="citizensOption.id"
              >
                {{ citizensOption.id }}
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
            :disabled="$v.citizensRelations.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./citizens-relations-update.component.ts"></script>
