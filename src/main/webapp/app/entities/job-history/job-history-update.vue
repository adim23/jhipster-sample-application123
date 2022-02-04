<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.jobHistory.home.createOrEditLabel"
          data-cy="JobHistoryCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.jobHistory.home.createOrEditLabel')"
        >
          Create or edit a JobHistory
        </h2>
        <div>
          <div class="form-group" v-if="jobHistory.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="jobHistory.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.jobHistory.startDate')"
              for="job-history-startDate"
              >Start Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="job-history-startDate"
                  v-model="$v.jobHistory.startDate.$model"
                  name="startDate"
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
                id="job-history-startDate"
                data-cy="startDate"
                type="text"
                class="form-control"
                name="startDate"
                :class="{ valid: !$v.jobHistory.startDate.$invalid, invalid: $v.jobHistory.startDate.$invalid }"
                v-model="$v.jobHistory.startDate.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.jobHistory.startDate.$anyDirty && $v.jobHistory.startDate.$invalid">
              <small class="form-text text-danger" v-if="!$v.jobHistory.startDate.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.jobHistory.endDate')" for="job-history-endDate"
              >End Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="job-history-endDate"
                  v-model="$v.jobHistory.endDate.$model"
                  name="endDate"
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
                id="job-history-endDate"
                data-cy="endDate"
                type="text"
                class="form-control"
                name="endDate"
                :class="{ valid: !$v.jobHistory.endDate.$invalid, invalid: $v.jobHistory.endDate.$invalid }"
                v-model="$v.jobHistory.endDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.jobHistory.language')" for="job-history-language"
              >Language</label
            >
            <select
              class="form-control"
              name="language"
              :class="{ valid: !$v.jobHistory.language.$invalid, invalid: $v.jobHistory.language.$invalid }"
              v-model="$v.jobHistory.language.$model"
              id="job-history-language"
              data-cy="language"
              required
            >
              <option
                v-for="language in languageValues"
                :key="language"
                v-bind:value="language"
                v-bind:label="$t('jhipsterSampleApplication123App.Language.' + language)"
              >
                {{ language }}
              </option>
            </select>
            <div v-if="$v.jobHistory.language.$anyDirty && $v.jobHistory.language.$invalid">
              <small class="form-text text-danger" v-if="!$v.jobHistory.language.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.jobHistory.job')" for="job-history-job"
              >Job</label
            >
            <select class="form-control" id="job-history-job" data-cy="job" name="job" v-model="jobHistory.job">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="jobHistory.job && jobOption.id === jobHistory.job.id ? jobHistory.job : jobOption"
                v-for="jobOption in jobs"
                :key="jobOption.id"
              >
                {{ jobOption.jobTitle }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.jobHistory.department')"
              for="job-history-department"
              >Department</label
            >
            <select class="form-control" id="job-history-department" data-cy="department" name="department" v-model="jobHistory.department">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  jobHistory.department && departmentOption.id === jobHistory.department.id ? jobHistory.department : departmentOption
                "
                v-for="departmentOption in departments"
                :key="departmentOption.id"
              >
                {{ departmentOption.departmentName }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.jobHistory.employee')" for="job-history-employee"
              >Employee</label
            >
            <select class="form-control" id="job-history-employee" data-cy="employee" name="employee" v-model="jobHistory.employee">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="jobHistory.employee && employeeOption.id === jobHistory.employee.id ? jobHistory.employee : employeeOption"
                v-for="employeeOption in employees"
                :key="employeeOption.id"
              >
                {{ employeeOption.id }}
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
            :disabled="$v.jobHistory.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./job-history-update.component.ts"></script>
