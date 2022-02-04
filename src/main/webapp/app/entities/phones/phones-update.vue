<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.phones.home.createOrEditLabel"
          data-cy="PhonesCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.phones.home.createOrEditLabel')"
        >
          Create or edit a Phones
        </h2>
        <div>
          <div class="form-group" v-if="phones.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="phones.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.phones.phone')" for="phones-phone">Phone</label>
            <input
              type="text"
              class="form-control"
              name="phone"
              id="phones-phone"
              data-cy="phone"
              :class="{ valid: !$v.phones.phone.$invalid, invalid: $v.phones.phone.$invalid }"
              v-model="$v.phones.phone.$model"
              required
            />
            <div v-if="$v.phones.phone.$anyDirty && $v.phones.phone.$invalid">
              <small class="form-text text-danger" v-if="!$v.phones.phone.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.phones.description')" for="phones-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="phones-description"
              data-cy="description"
              :class="{ valid: !$v.phones.description.$invalid, invalid: $v.phones.description.$invalid }"
              v-model="$v.phones.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.phones.favourite')" for="phones-favourite"
              >Favourite</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="favourite"
              id="phones-favourite"
              data-cy="favourite"
              :class="{ valid: !$v.phones.favourite.$invalid, invalid: $v.phones.favourite.$invalid }"
              v-model="$v.phones.favourite.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.phones.kind')" for="phones-kind">Kind</label>
            <select class="form-control" id="phones-kind" data-cy="kind" name="kind" v-model="phones.kind">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="phones.kind && phoneTypesOption.id === phones.kind.id ? phones.kind : phoneTypesOption"
                v-for="phoneTypesOption in phoneTypes"
                :key="phoneTypesOption.id"
              >
                {{ phoneTypesOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.phones.company')" for="phones-company"
              >Company</label
            >
            <select class="form-control" id="phones-company" data-cy="company" name="company" v-model="phones.company">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="phones.company && companiesOption.id === phones.company.id ? phones.company : companiesOption"
                v-for="companiesOption in companies"
                :key="companiesOption.id"
              >
                {{ companiesOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.phones.citizen')" for="phones-citizen"
              >Citizen</label
            >
            <select class="form-control" id="phones-citizen" data-cy="citizen" name="citizen" v-model="phones.citizen">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="phones.citizen && citizensOption.id === phones.citizen.id ? phones.citizen : citizensOption"
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
            :disabled="$v.phones.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./phones-update.component.ts"></script>
