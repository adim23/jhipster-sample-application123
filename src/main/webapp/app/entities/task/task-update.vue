<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.task.home.createOrEditLabel"
          data-cy="TaskCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.task.home.createOrEditLabel')"
        >
          Create or edit a Task
        </h2>
        <div>
          <div class="form-group" v-if="task.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="task.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.task.title')" for="task-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="task-title"
              data-cy="title"
              :class="{ valid: !$v.task.title.$invalid, invalid: $v.task.title.$invalid }"
              v-model="$v.task.title.$model"
              required
            />
            <div v-if="$v.task.title.$anyDirty && $v.task.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.task.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.task.description')" for="task-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="task-description"
              data-cy="description"
              :class="{ valid: !$v.task.description.$invalid, invalid: $v.task.description.$invalid }"
              v-model="$v.task.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.task.startDate')" for="task-startDate"
              >Start Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="task-startDate"
                  v-model="$v.task.startDate.$model"
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
                id="task-startDate"
                data-cy="startDate"
                type="text"
                class="form-control"
                name="startDate"
                :class="{ valid: !$v.task.startDate.$invalid, invalid: $v.task.startDate.$invalid }"
                v-model="$v.task.startDate.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.task.startDate.$anyDirty && $v.task.startDate.$invalid">
              <small class="form-text text-danger" v-if="!$v.task.startDate.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.task.endDate')" for="task-endDate"
              >End Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="task-endDate"
                  v-model="$v.task.endDate.$model"
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
                id="task-endDate"
                data-cy="endDate"
                type="text"
                class="form-control"
                name="endDate"
                :class="{ valid: !$v.task.endDate.$invalid, invalid: $v.task.endDate.$invalid }"
                v-model="$v.task.endDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.task.percentCompleted')"
              for="task-percentCompleted"
              >Percent Completed</label
            >
            <input
              type="number"
              class="form-control"
              name="percentCompleted"
              id="task-percentCompleted"
              data-cy="percentCompleted"
              :class="{ valid: !$v.task.percentCompleted.$invalid, invalid: $v.task.percentCompleted.$invalid }"
              v-model.number="$v.task.percentCompleted.$model"
            />
            <div v-if="$v.task.percentCompleted.$anyDirty && $v.task.percentCompleted.$invalid">
              <small class="form-text text-danger" v-if="!$v.task.percentCompleted.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.task.percentCompleted.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.task.percentCompleted.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.task.dependsOn')" for="task-dependsOn"
              >Depends On</label
            >
            <select class="form-control" id="task-dependsOn" data-cy="dependsOn" name="dependsOn" v-model="task.dependsOn">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="task.dependsOn && taskOption.id === task.dependsOn.id ? task.dependsOn : taskOption"
                v-for="taskOption in tasks"
                :key="taskOption.id"
              >
                {{ taskOption.title }}
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
            :disabled="$v.task.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./task-update.component.ts"></script>
