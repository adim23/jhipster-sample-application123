<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.employee.home.createOrEditLabel"
          data-cy="EmployeeCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.employee.home.createOrEditLabel')"
        >
          Create or edit a Employee
        </h2>
        <div>
          <div class="form-group" v-if="employee.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="employee.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.employee.firstName')" for="employee-firstName"
              >First Name</label
            >
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="employee-firstName"
              data-cy="firstName"
              :class="{ valid: !$v.employee.firstName.$invalid, invalid: $v.employee.firstName.$invalid }"
              v-model="$v.employee.firstName.$model"
              required
            />
            <div v-if="$v.employee.firstName.$anyDirty && $v.employee.firstName.$invalid">
              <small class="form-text text-danger" v-if="!$v.employee.firstName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.employee.lastName')" for="employee-lastName"
              >Last Name</label
            >
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="employee-lastName"
              data-cy="lastName"
              :class="{ valid: !$v.employee.lastName.$invalid, invalid: $v.employee.lastName.$invalid }"
              v-model="$v.employee.lastName.$model"
              required
            />
            <div v-if="$v.employee.lastName.$anyDirty && $v.employee.lastName.$invalid">
              <small class="form-text text-danger" v-if="!$v.employee.lastName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.employee.fullName')" for="employee-fullName"
              >Full Name</label
            >
            <input
              type="text"
              class="form-control"
              name="fullName"
              id="employee-fullName"
              data-cy="fullName"
              :class="{ valid: !$v.employee.fullName.$invalid, invalid: $v.employee.fullName.$invalid }"
              v-model="$v.employee.fullName.$model"
              required
            />
            <div v-if="$v.employee.fullName.$anyDirty && $v.employee.fullName.$invalid">
              <small class="form-text text-danger" v-if="!$v.employee.fullName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.employee.email')" for="employee-email"
              >Email</label
            >
            <input
              type="text"
              class="form-control"
              name="email"
              id="employee-email"
              data-cy="email"
              :class="{ valid: !$v.employee.email.$invalid, invalid: $v.employee.email.$invalid }"
              v-model="$v.employee.email.$model"
              required
            />
            <div v-if="$v.employee.email.$anyDirty && $v.employee.email.$invalid">
              <small class="form-text text-danger" v-if="!$v.employee.email.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.employee.email.pattern"
                v-text="$t('entity.validation.pattern', { pattern: 'Email' })"
              >
                This field should follow pattern for "Email".
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.employee.phoneNumber')" for="employee-phoneNumber"
              >Phone Number</label
            >
            <input
              type="text"
              class="form-control"
              name="phoneNumber"
              id="employee-phoneNumber"
              data-cy="phoneNumber"
              :class="{ valid: !$v.employee.phoneNumber.$invalid, invalid: $v.employee.phoneNumber.$invalid }"
              v-model="$v.employee.phoneNumber.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.employee.hireDateTime')"
              for="employee-hireDateTime"
              >Hire Date Time</label
            >
            <div class="d-flex">
              <input
                id="employee-hireDateTime"
                data-cy="hireDateTime"
                type="datetime-local"
                class="form-control"
                name="hireDateTime"
                :class="{ valid: !$v.employee.hireDateTime.$invalid, invalid: $v.employee.hireDateTime.$invalid }"
                :value="convertDateTimeFromServer($v.employee.hireDateTime.$model)"
                @change="updateInstantField('hireDateTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.employee.zonedHireDateTime')"
              for="employee-zonedHireDateTime"
              >Zoned Hire Date Time</label
            >
            <div class="d-flex">
              <input
                id="employee-zonedHireDateTime"
                data-cy="zonedHireDateTime"
                type="datetime-local"
                class="form-control"
                name="zonedHireDateTime"
                :class="{ valid: !$v.employee.zonedHireDateTime.$invalid, invalid: $v.employee.zonedHireDateTime.$invalid }"
                :value="convertDateTimeFromServer($v.employee.zonedHireDateTime.$model)"
                @change="updateZonedDateTimeField('zonedHireDateTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.employee.hireDate')" for="employee-hireDate"
              >Hire Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="employee-hireDate"
                  v-model="$v.employee.hireDate.$model"
                  name="hireDate"
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
                id="employee-hireDate"
                data-cy="hireDate"
                type="text"
                class="form-control"
                name="hireDate"
                :class="{ valid: !$v.employee.hireDate.$invalid, invalid: $v.employee.hireDate.$invalid }"
                v-model="$v.employee.hireDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.employee.salary')" for="employee-salary"
              >Salary</label
            >
            <input
              type="number"
              class="form-control"
              name="salary"
              id="employee-salary"
              data-cy="salary"
              :class="{ valid: !$v.employee.salary.$invalid, invalid: $v.employee.salary.$invalid }"
              v-model.number="$v.employee.salary.$model"
            />
            <div v-if="$v.employee.salary.$anyDirty && $v.employee.salary.$invalid">
              <small class="form-text text-danger" v-if="!$v.employee.salary.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.employee.salary.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.employee.commissionPct')"
              for="employee-commissionPct"
              >Commission Pct</label
            >
            <input
              type="number"
              class="form-control"
              name="commissionPct"
              id="employee-commissionPct"
              data-cy="commissionPct"
              :class="{ valid: !$v.employee.commissionPct.$invalid, invalid: $v.employee.commissionPct.$invalid }"
              v-model.number="$v.employee.commissionPct.$model"
            />
            <div v-if="$v.employee.commissionPct.$anyDirty && $v.employee.commissionPct.$invalid">
              <small class="form-text text-danger" v-if="!$v.employee.commissionPct.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.employee.commissionPct.max" v-text="$t('entity.validation.max', { max: 100 })">
                This field cannot be longer than 100 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.employee.commissionPct.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.employee.duration')" for="employee-duration"
              >Duration</label
            >
            <input
              type="text"
              class="form-control"
              name="duration"
              id="employee-duration"
              data-cy="duration"
              :class="{ valid: !$v.employee.duration.$invalid, invalid: $v.employee.duration.$invalid }"
              v-model="$v.employee.duration.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.employee.pict')" for="employee-pict">Pict</label>
            <div>
              <img
                v-bind:src="'data:' + employee.pictContentType + ';base64,' + employee.pict"
                style="max-height: 100px"
                v-if="employee.pict"
                alt="employee image"
              />
              <div v-if="employee.pict" class="form-text text-danger clearfix">
                <span class="pull-left">{{ employee.pictContentType }}, {{ byteSize(employee.pict) }}</span>
                <button
                  type="button"
                  v-on:click="clearInputImage('pict', 'pictContentType', 'file_pict')"
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_pict"
                id="file_pict"
                data-cy="pict"
                v-on:change="setFileData($event, employee, 'pict', true)"
                accept="image/*"
                v-text="$t('entity.action.addimage')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="pict"
              id="employee-pict"
              data-cy="pict"
              :class="{ valid: !$v.employee.pict.$invalid, invalid: $v.employee.pict.$invalid }"
              v-model="$v.employee.pict.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="pictContentType"
              id="employee-pictContentType"
              v-model="employee.pictContentType"
            />
            <div v-if="$v.employee.pict.$anyDirty && $v.employee.pict.$invalid"></div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.employee.comments')" for="employee-comments"
              >Comments</label
            >
            <textarea
              class="form-control"
              name="comments"
              id="employee-comments"
              data-cy="comments"
              :class="{ valid: !$v.employee.comments.$invalid, invalid: $v.employee.comments.$invalid }"
              v-model="$v.employee.comments.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.employee.cv')" for="employee-cv">Cv</label>
            <div>
              <div v-if="employee.cv" class="form-text text-danger clearfix">
                <a class="pull-left" v-on:click="openFile(employee.cvContentType, employee.cv)" v-text="$t('entity.action.open')">open</a
                ><br />
                <span class="pull-left">{{ employee.cvContentType }}, {{ byteSize(employee.cv) }}</span>
                <button
                  type="button"
                  v-on:click="
                    employee.cv = null;
                    employee.cvContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_cv"
                id="file_cv"
                data-cy="cv"
                v-on:change="setFileData($event, employee, 'cv', false)"
                v-text="$t('entity.action.addblob')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="cv"
              id="employee-cv"
              data-cy="cv"
              :class="{ valid: !$v.employee.cv.$invalid, invalid: $v.employee.cv.$invalid }"
              v-model="$v.employee.cv.$model"
            />
            <input type="hidden" class="form-control" name="cvContentType" id="employee-cvContentType" v-model="employee.cvContentType" />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.employee.active')" for="employee-active"
              >Active</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="active"
              id="employee-active"
              data-cy="active"
              :class="{ valid: !$v.employee.active.$invalid, invalid: $v.employee.active.$invalid }"
              v-model="$v.employee.active.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.employee.manager')" for="employee-manager"
              >Manager</label
            >
            <select class="form-control" id="employee-manager" data-cy="manager" name="manager" v-model="employee.manager">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="employee.manager && employeeOption.id === employee.manager.id ? employee.manager : employeeOption"
                v-for="employeeOption in employees"
                :key="employeeOption.id"
              >
                {{ employeeOption.fullName }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.employee.department')" for="employee-department"
              >Department</label
            >
            <select class="form-control" id="employee-department" data-cy="department" name="department" v-model="employee.department">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  employee.department && departmentOption.id === employee.department.id ? employee.department : departmentOption
                "
                v-for="departmentOption in departments"
                :key="departmentOption.id"
              >
                {{ departmentOption.departmentName }}
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
            :disabled="$v.employee.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./employee-update.component.ts"></script>
