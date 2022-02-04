<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.socialContacts.home.createOrEditLabel"
          data-cy="SocialContactsCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.socialContacts.home.createOrEditLabel')"
        >
          Create or edit a SocialContacts
        </h2>
        <div>
          <div class="form-group" v-if="socialContacts.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="socialContacts.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.socialContacts.name')" for="social-contacts-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="social-contacts-name"
              data-cy="name"
              :class="{ valid: !$v.socialContacts.name.$invalid, invalid: $v.socialContacts.name.$invalid }"
              v-model="$v.socialContacts.name.$model"
              required
            />
            <div v-if="$v.socialContacts.name.$anyDirty && $v.socialContacts.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.socialContacts.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.socialContacts.favored')"
              for="social-contacts-favored"
              >Favored</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="favored"
              id="social-contacts-favored"
              data-cy="favored"
              :class="{ valid: !$v.socialContacts.favored.$invalid, invalid: $v.socialContacts.favored.$invalid }"
              v-model="$v.socialContacts.favored.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.socialContacts.social')"
              for="social-contacts-social"
              >Social</label
            >
            <select class="form-control" id="social-contacts-social" data-cy="social" name="social" v-model="socialContacts.social">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  socialContacts.social && socialKindsOption.id === socialContacts.social.id ? socialContacts.social : socialKindsOption
                "
                v-for="socialKindsOption in socialKinds"
                :key="socialKindsOption.id"
              >
                {{ socialKindsOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.socialContacts.kind')" for="social-contacts-kind"
              >Kind</label
            >
            <select class="form-control" id="social-contacts-kind" data-cy="kind" name="kind" v-model="socialContacts.kind">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  socialContacts.kind && contactTypesOption.id === socialContacts.kind.id ? socialContacts.kind : contactTypesOption
                "
                v-for="contactTypesOption in contactTypes"
                :key="contactTypesOption.id"
              >
                {{ contactTypesOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.socialContacts.citizen')"
              for="social-contacts-citizen"
              >Citizen</label
            >
            <select class="form-control" id="social-contacts-citizen" data-cy="citizen" name="citizen" v-model="socialContacts.citizen">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  socialContacts.citizen && citizensOption.id === socialContacts.citizen.id ? socialContacts.citizen : citizensOption
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
            :disabled="$v.socialContacts.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./social-contacts-update.component.ts"></script>
