<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.citizensMeetings.home.createOrEditLabel"
          data-cy="CitizensMeetingsCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.citizensMeetings.home.createOrEditLabel')"
        >
          Create or edit a CitizensMeetings
        </h2>
        <div>
          <div class="form-group" v-if="citizensMeetings.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="citizensMeetings.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.citizensMeetings.meetDate')"
              for="citizens-meetings-meetDate"
              >Meet Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="citizens-meetings-meetDate"
                  v-model="$v.citizensMeetings.meetDate.$model"
                  name="meetDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="citizens-meetings-meetDate"
                data-cy="meetDate"
                type="text"
                class="form-control"
                name="meetDate"
                :class="{ valid: !$v.citizensMeetings.meetDate.$invalid, invalid: $v.citizensMeetings.meetDate.$invalid }"
                v-model="$v.citizensMeetings.meetDate.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.citizensMeetings.meetDate.$anyDirty && $v.citizensMeetings.meetDate.$invalid">
              <small class="form-text text-danger" v-if="!$v.citizensMeetings.meetDate.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.citizensMeetings.agenda')"
              for="citizens-meetings-agenda"
              >Agenda</label
            >
            <input
              type="text"
              class="form-control"
              name="agenda"
              id="citizens-meetings-agenda"
              data-cy="agenda"
              :class="{ valid: !$v.citizensMeetings.agenda.$invalid, invalid: $v.citizensMeetings.agenda.$invalid }"
              v-model="$v.citizensMeetings.agenda.$model"
              required
            />
            <div v-if="$v.citizensMeetings.agenda.$anyDirty && $v.citizensMeetings.agenda.$invalid">
              <small class="form-text text-danger" v-if="!$v.citizensMeetings.agenda.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.citizensMeetings.comments')"
              for="citizens-meetings-comments"
              >Comments</label
            >
            <textarea
              class="form-control"
              name="comments"
              id="citizens-meetings-comments"
              data-cy="comments"
              :class="{ valid: !$v.citizensMeetings.comments.$invalid, invalid: $v.citizensMeetings.comments.$invalid }"
              v-model="$v.citizensMeetings.comments.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.citizensMeetings.amount')"
              for="citizens-meetings-amount"
              >Amount</label
            >
            <input
              type="number"
              class="form-control"
              name="amount"
              id="citizens-meetings-amount"
              data-cy="amount"
              :class="{ valid: !$v.citizensMeetings.amount.$invalid, invalid: $v.citizensMeetings.amount.$invalid }"
              v-model.number="$v.citizensMeetings.amount.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.citizensMeetings.quantity')"
              for="citizens-meetings-quantity"
              >Quantity</label
            >
            <input
              type="number"
              class="form-control"
              name="quantity"
              id="citizens-meetings-quantity"
              data-cy="quantity"
              :class="{ valid: !$v.citizensMeetings.quantity.$invalid, invalid: $v.citizensMeetings.quantity.$invalid }"
              v-model.number="$v.citizensMeetings.quantity.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.citizensMeetings.status')"
              for="citizens-meetings-status"
              >Status</label
            >
            <input
              type="text"
              class="form-control"
              name="status"
              id="citizens-meetings-status"
              data-cy="status"
              :class="{ valid: !$v.citizensMeetings.status.$invalid, invalid: $v.citizensMeetings.status.$invalid }"
              v-model="$v.citizensMeetings.status.$model"
              required
            />
            <div v-if="$v.citizensMeetings.status.$anyDirty && $v.citizensMeetings.status.$invalid">
              <small class="form-text text-danger" v-if="!$v.citizensMeetings.status.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.citizensMeetings.flag')"
              for="citizens-meetings-flag"
              >Flag</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="flag"
              id="citizens-meetings-flag"
              data-cy="flag"
              :class="{ valid: !$v.citizensMeetings.flag.$invalid, invalid: $v.citizensMeetings.flag.$invalid }"
              v-model="$v.citizensMeetings.flag.$model"
              required
            />
            <div v-if="$v.citizensMeetings.flag.$anyDirty && $v.citizensMeetings.flag.$invalid">
              <small class="form-text text-danger" v-if="!$v.citizensMeetings.flag.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.citizensMeetings.citizen')"
              for="citizens-meetings-citizen"
              >Citizen</label
            >
            <select class="form-control" id="citizens-meetings-citizen" data-cy="citizen" name="citizen" v-model="citizensMeetings.citizen">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  citizensMeetings.citizen && citizensOption.id === citizensMeetings.citizen.id ? citizensMeetings.citizen : citizensOption
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
            :disabled="$v.citizensMeetings.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./citizens-meetings-update.component.ts"></script>
