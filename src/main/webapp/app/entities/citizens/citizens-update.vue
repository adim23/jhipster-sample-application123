<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.citizens.home.createOrEditLabel"
          data-cy="CitizensCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.citizens.home.createOrEditLabel')"
        >
          Create or edit a Citizens
        </h2>
        <div>
          <div class="form-group" v-if="citizens.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="citizens.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.title')" for="citizens-title"
              >Title</label
            >
            <input
              type="text"
              class="form-control"
              name="title"
              id="citizens-title"
              data-cy="title"
              :class="{ valid: !$v.citizens.title.$invalid, invalid: $v.citizens.title.$invalid }"
              v-model="$v.citizens.title.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.lastname')" for="citizens-lastname"
              >Lastname</label
            >
            <input
              type="text"
              class="form-control"
              name="lastname"
              id="citizens-lastname"
              data-cy="lastname"
              :class="{ valid: !$v.citizens.lastname.$invalid, invalid: $v.citizens.lastname.$invalid }"
              v-model="$v.citizens.lastname.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.firstname')" for="citizens-firstname"
              >Firstname</label
            >
            <input
              type="text"
              class="form-control"
              name="firstname"
              id="citizens-firstname"
              data-cy="firstname"
              :class="{ valid: !$v.citizens.firstname.$invalid, invalid: $v.citizens.firstname.$invalid }"
              v-model="$v.citizens.firstname.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.fathersName')" for="citizens-fathersName"
              >Fathers Name</label
            >
            <input
              type="text"
              class="form-control"
              name="fathersName"
              id="citizens-fathersName"
              data-cy="fathersName"
              :class="{ valid: !$v.citizens.fathersName.$invalid, invalid: $v.citizens.fathersName.$invalid }"
              v-model="$v.citizens.fathersName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.comments')" for="citizens-comments"
              >Comments</label
            >
            <textarea
              class="form-control"
              name="comments"
              id="citizens-comments"
              data-cy="comments"
              :class="{ valid: !$v.citizens.comments.$invalid, invalid: $v.citizens.comments.$invalid }"
              v-model="$v.citizens.comments.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.birthDate')" for="citizens-birthDate"
              >Birth Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="citizens-birthDate"
                  v-model="$v.citizens.birthDate.$model"
                  name="birthDate"
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
                id="citizens-birthDate"
                data-cy="birthDate"
                type="text"
                class="form-control"
                name="birthDate"
                :class="{ valid: !$v.citizens.birthDate.$invalid, invalid: $v.citizens.birthDate.$invalid }"
                v-model="$v.citizens.birthDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.giortazi')" for="citizens-giortazi"
              >Giortazi</label
            >
            <input
              type="text"
              class="form-control"
              name="giortazi"
              id="citizens-giortazi"
              data-cy="giortazi"
              :class="{ valid: !$v.citizens.giortazi.$invalid, invalid: $v.citizens.giortazi.$invalid }"
              v-model="$v.citizens.giortazi.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.male')" for="citizens-male">Male</label>
            <input
              type="checkbox"
              class="form-check"
              name="male"
              id="citizens-male"
              data-cy="male"
              :class="{ valid: !$v.citizens.male.$invalid, invalid: $v.citizens.male.$invalid }"
              v-model="$v.citizens.male.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.meLetter')" for="citizens-meLetter"
              >Me Letter</label
            >
            <input
              type="number"
              class="form-control"
              name="meLetter"
              id="citizens-meLetter"
              data-cy="meLetter"
              :class="{ valid: !$v.citizens.meLetter.$invalid, invalid: $v.citizens.meLetter.$invalid }"
              v-model.number="$v.citizens.meLetter.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.meLabel')" for="citizens-meLabel"
              >Me Label</label
            >
            <input
              type="number"
              class="form-control"
              name="meLabel"
              id="citizens-meLabel"
              data-cy="meLabel"
              :class="{ valid: !$v.citizens.meLabel.$invalid, invalid: $v.citizens.meLabel.$invalid }"
              v-model.number="$v.citizens.meLabel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.image')" for="citizens-image"
              >Image</label
            >
            <div>
              <img
                v-bind:src="'data:' + citizens.imageContentType + ';base64,' + citizens.image"
                style="max-height: 100px"
                v-if="citizens.image"
                alt="citizens image"
              />
              <div v-if="citizens.image" class="form-text text-danger clearfix">
                <span class="pull-left">{{ citizens.imageContentType }}, {{ byteSize(citizens.image) }}</span>
                <button
                  type="button"
                  v-on:click="clearInputImage('image', 'imageContentType', 'file_image')"
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_image"
                id="file_image"
                data-cy="image"
                v-on:change="setFileData($event, citizens, 'image', true)"
                accept="image/*"
                v-text="$t('entity.action.addimage')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="image"
              id="citizens-image"
              data-cy="image"
              :class="{ valid: !$v.citizens.image.$invalid, invalid: $v.citizens.image.$invalid }"
              v-model="$v.citizens.image.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="imageContentType"
              id="citizens-imageContentType"
              v-model="citizens.imageContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.folder')" for="citizens-folder"
              >Folder</label
            >
            <select class="form-control" id="citizens-folder" data-cy="folder" name="folder" v-model="citizens.folder">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="citizens.folder && citizenFoldersOption.id === citizens.folder.id ? citizens.folder : citizenFoldersOption"
                v-for="citizenFoldersOption in citizenFolders"
                :key="citizenFoldersOption.id"
              >
                {{ citizenFoldersOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.company')" for="citizens-company"
              >Company</label
            >
            <select class="form-control" id="citizens-company" data-cy="company" name="company" v-model="citizens.company">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="citizens.company && companiesOption.id === citizens.company.id ? citizens.company : companiesOption"
                v-for="companiesOption in companies"
                :key="companiesOption.id"
              >
                {{ companiesOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.citizens.maritalStatus')"
              for="citizens-maritalStatus"
              >Marital Status</label
            >
            <select
              class="form-control"
              id="citizens-maritalStatus"
              data-cy="maritalStatus"
              name="maritalStatus"
              v-model="citizens.maritalStatus"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  citizens.maritalStatus && maritalStatusOption.id === citizens.maritalStatus.id
                    ? citizens.maritalStatus
                    : maritalStatusOption
                "
                v-for="maritalStatusOption in maritalStatuses"
                :key="maritalStatusOption.id"
              >
                {{ maritalStatusOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.team')" for="citizens-team">Team</label>
            <select class="form-control" id="citizens-team" data-cy="team" name="team" v-model="citizens.team">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="citizens.team && teamsOption.id === citizens.team.id ? citizens.team : teamsOption"
                v-for="teamsOption in teams"
                :key="teamsOption.id"
              >
                {{ teamsOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.code')" for="citizens-code">Code</label>
            <select class="form-control" id="citizens-code" data-cy="code" name="code" v-model="citizens.code">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="citizens.code && codesOption.id === citizens.code.id ? citizens.code : codesOption"
                v-for="codesOption in codes"
                :key="codesOption.id"
              >
                {{ codesOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.origin')" for="citizens-origin"
              >Origin</label
            >
            <select class="form-control" id="citizens-origin" data-cy="origin" name="origin" v-model="citizens.origin">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="citizens.origin && originsOption.id === citizens.origin.id ? citizens.origin : originsOption"
                v-for="originsOption in origins"
                :key="originsOption.id"
              >
                {{ originsOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.citizens.job')" for="citizens-job">Job</label>
            <select class="form-control" id="citizens-job" data-cy="job" name="job" v-model="citizens.job">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="citizens.job && jobsOption.id === citizens.job.id ? citizens.job : jobsOption"
                v-for="jobsOption in jobs"
                :key="jobsOption.id"
              >
                {{ jobsOption.name }}
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
            :disabled="$v.citizens.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./citizens-update.component.ts"></script>
