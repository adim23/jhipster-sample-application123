<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.cities.home.createOrEditLabel"
          data-cy="CitiesCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.cities.home.createOrEditLabel')"
        >
          Create or edit a Cities
        </h2>
        <div>
          <div class="form-group" v-if="cities.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="cities.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.cities.name')" for="cities-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="cities-name"
              data-cy="name"
              :class="{ valid: !$v.cities.name.$invalid, invalid: $v.cities.name.$invalid }"
              v-model="$v.cities.name.$model"
              required
            />
            <div v-if="$v.cities.name.$anyDirty && $v.cities.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.cities.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.cities.president')" for="cities-president"
              >President</label
            >
            <input
              type="text"
              class="form-control"
              name="president"
              id="cities-president"
              data-cy="president"
              :class="{ valid: !$v.cities.president.$invalid, invalid: $v.cities.president.$invalid }"
              v-model="$v.cities.president.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.cities.presidentsPhone')"
              for="cities-presidentsPhone"
              >Presidents Phone</label
            >
            <input
              type="text"
              class="form-control"
              name="presidentsPhone"
              id="cities-presidentsPhone"
              data-cy="presidentsPhone"
              :class="{ valid: !$v.cities.presidentsPhone.$invalid, invalid: $v.cities.presidentsPhone.$invalid }"
              v-model="$v.cities.presidentsPhone.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.cities.secretary')" for="cities-secretary"
              >Secretary</label
            >
            <input
              type="text"
              class="form-control"
              name="secretary"
              id="cities-secretary"
              data-cy="secretary"
              :class="{ valid: !$v.cities.secretary.$invalid, invalid: $v.cities.secretary.$invalid }"
              v-model="$v.cities.secretary.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.cities.secretarysPhone')"
              for="cities-secretarysPhone"
              >Secretarys Phone</label
            >
            <input
              type="text"
              class="form-control"
              name="secretarysPhone"
              id="cities-secretarysPhone"
              data-cy="secretarysPhone"
              :class="{ valid: !$v.cities.secretarysPhone.$invalid, invalid: $v.cities.secretarysPhone.$invalid }"
              v-model="$v.cities.secretarysPhone.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.cities.police')" for="cities-police"
              >Police</label
            >
            <input
              type="text"
              class="form-control"
              name="police"
              id="cities-police"
              data-cy="police"
              :class="{ valid: !$v.cities.police.$invalid, invalid: $v.cities.police.$invalid }"
              v-model="$v.cities.police.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.cities.policesPhone')" for="cities-policesPhone"
              >Polices Phone</label
            >
            <input
              type="text"
              class="form-control"
              name="policesPhone"
              id="cities-policesPhone"
              data-cy="policesPhone"
              :class="{ valid: !$v.cities.policesPhone.$invalid, invalid: $v.cities.policesPhone.$invalid }"
              v-model="$v.cities.policesPhone.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.cities.doctor')" for="cities-doctor"
              >Doctor</label
            >
            <input
              type="text"
              class="form-control"
              name="doctor"
              id="cities-doctor"
              data-cy="doctor"
              :class="{ valid: !$v.cities.doctor.$invalid, invalid: $v.cities.doctor.$invalid }"
              v-model="$v.cities.doctor.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.cities.doctorsPhone')" for="cities-doctorsPhone"
              >Doctors Phone</label
            >
            <input
              type="text"
              class="form-control"
              name="doctorsPhone"
              id="cities-doctorsPhone"
              data-cy="doctorsPhone"
              :class="{ valid: !$v.cities.doctorsPhone.$invalid, invalid: $v.cities.doctorsPhone.$invalid }"
              v-model="$v.cities.doctorsPhone.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.cities.teacher')" for="cities-teacher"
              >Teacher</label
            >
            <input
              type="text"
              class="form-control"
              name="teacher"
              id="cities-teacher"
              data-cy="teacher"
              :class="{ valid: !$v.cities.teacher.$invalid, invalid: $v.cities.teacher.$invalid }"
              v-model="$v.cities.teacher.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.cities.teachersPhone')" for="cities-teachersPhone"
              >Teachers Phone</label
            >
            <input
              type="text"
              class="form-control"
              name="teachersPhone"
              id="cities-teachersPhone"
              data-cy="teachersPhone"
              :class="{ valid: !$v.cities.teachersPhone.$invalid, invalid: $v.cities.teachersPhone.$invalid }"
              v-model="$v.cities.teachersPhone.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.cities.priest')" for="cities-priest"
              >Priest</label
            >
            <input
              type="text"
              class="form-control"
              name="priest"
              id="cities-priest"
              data-cy="priest"
              :class="{ valid: !$v.cities.priest.$invalid, invalid: $v.cities.priest.$invalid }"
              v-model="$v.cities.priest.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.cities.priestsPhone')" for="cities-priestsPhone"
              >Priests Phone</label
            >
            <input
              type="text"
              class="form-control"
              name="priestsPhone"
              id="cities-priestsPhone"
              data-cy="priestsPhone"
              :class="{ valid: !$v.cities.priestsPhone.$invalid, invalid: $v.cities.priestsPhone.$invalid }"
              v-model="$v.cities.priestsPhone.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.cities.country')" for="cities-country"
              >Country</label
            >
            <select class="form-control" id="cities-country" data-cy="country" name="country" v-model="cities.country" required>
              <option v-if="!cities.country" v-bind:value="null" selected></option>
              <option
                v-bind:value="cities.country && countriesOption.id === cities.country.id ? cities.country : countriesOption"
                v-for="countriesOption in countries"
                :key="countriesOption.id"
              >
                {{ countriesOption.name }}
              </option>
            </select>
          </div>
          <div v-if="$v.cities.country.$anyDirty && $v.cities.country.$invalid">
            <small class="form-text text-danger" v-if="!$v.cities.country.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.cities.region')" for="cities-region"
              >Region</label
            >
            <select class="form-control" id="cities-region" data-cy="region" name="region" v-model="cities.region" required>
              <option v-if="!cities.region" v-bind:value="null" selected></option>
              <option
                v-bind:value="cities.region && regionsOption.id === cities.region.id ? cities.region : regionsOption"
                v-for="regionsOption in regions"
                :key="regionsOption.id"
              >
                {{ regionsOption.name }}
              </option>
            </select>
          </div>
          <div v-if="$v.cities.region.$anyDirty && $v.cities.region.$invalid">
            <small class="form-text text-danger" v-if="!$v.cities.region.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
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
            :disabled="$v.cities.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./cities-update.component.ts"></script>
