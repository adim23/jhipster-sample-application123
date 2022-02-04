<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.socialKinds.home.createOrEditLabel"
          data-cy="SocialKindsCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.socialKinds.home.createOrEditLabel')"
        >
          Create or edit a SocialKinds
        </h2>
        <div>
          <div class="form-group" v-if="socialKinds.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="socialKinds.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.socialKinds.code')" for="social-kinds-code"
              >Code</label
            >
            <input
              type="text"
              class="form-control"
              name="code"
              id="social-kinds-code"
              data-cy="code"
              :class="{ valid: !$v.socialKinds.code.$invalid, invalid: $v.socialKinds.code.$invalid }"
              v-model="$v.socialKinds.code.$model"
              required
            />
            <div v-if="$v.socialKinds.code.$anyDirty && $v.socialKinds.code.$invalid">
              <small class="form-text text-danger" v-if="!$v.socialKinds.code.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.socialKinds.name')" for="social-kinds-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="social-kinds-name"
              data-cy="name"
              :class="{ valid: !$v.socialKinds.name.$invalid, invalid: $v.socialKinds.name.$invalid }"
              v-model="$v.socialKinds.name.$model"
              required
            />
            <div v-if="$v.socialKinds.name.$anyDirty && $v.socialKinds.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.socialKinds.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.socialKinds.call')" for="social-kinds-call"
              >Call</label
            >
            <input
              type="text"
              class="form-control"
              name="call"
              id="social-kinds-call"
              data-cy="call"
              :class="{ valid: !$v.socialKinds.call.$invalid, invalid: $v.socialKinds.call.$invalid }"
              v-model="$v.socialKinds.call.$model"
              required
            />
            <div v-if="$v.socialKinds.call.$anyDirty && $v.socialKinds.call.$invalid">
              <small class="form-text text-danger" v-if="!$v.socialKinds.call.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.socialKinds.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./social-kinds-update.component.ts"></script>
