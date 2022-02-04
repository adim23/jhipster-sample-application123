<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.emails.home.createOrEditLabel"
          data-cy="EmailsCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.emails.home.createOrEditLabel')"
        >
          Create or edit a Emails
        </h2>
        <div>
          <div class="form-group" v-if="emails.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="emails.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.emails.email')" for="emails-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="emails-email"
              data-cy="email"
              :class="{ valid: !$v.emails.email.$invalid, invalid: $v.emails.email.$invalid }"
              v-model="$v.emails.email.$model"
              required
            />
            <div v-if="$v.emails.email.$anyDirty && $v.emails.email.$invalid">
              <small class="form-text text-danger" v-if="!$v.emails.email.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.emails.description')" for="emails-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="emails-description"
              data-cy="description"
              :class="{ valid: !$v.emails.description.$invalid, invalid: $v.emails.description.$invalid }"
              v-model="$v.emails.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.emails.favourite')" for="emails-favourite"
              >Favourite</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="favourite"
              id="emails-favourite"
              data-cy="favourite"
              :class="{ valid: !$v.emails.favourite.$invalid, invalid: $v.emails.favourite.$invalid }"
              v-model="$v.emails.favourite.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.emails.kind')" for="emails-kind">Kind</label>
            <select class="form-control" id="emails-kind" data-cy="kind" name="kind" v-model="emails.kind">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="emails.kind && contactTypesOption.id === emails.kind.id ? emails.kind : contactTypesOption"
                v-for="contactTypesOption in contactTypes"
                :key="contactTypesOption.id"
              >
                {{ contactTypesOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.emails.citizen')" for="emails-citizen"
              >Citizen</label
            >
            <select class="form-control" id="emails-citizen" data-cy="citizen" name="citizen" v-model="emails.citizen">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="emails.citizen && citizensOption.id === emails.citizen.id ? emails.citizen : citizensOption"
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
            :disabled="$v.emails.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./emails-update.component.ts"></script>
