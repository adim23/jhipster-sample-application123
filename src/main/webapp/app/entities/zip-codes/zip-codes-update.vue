<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.zipCodes.home.createOrEditLabel"
          data-cy="ZipCodesCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.zipCodes.home.createOrEditLabel')"
        >
          Create or edit a ZipCodes
        </h2>
        <div>
          <div class="form-group" v-if="zipCodes.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="zipCodes.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.zipCodes.street')" for="zip-codes-street"
              >Street</label
            >
            <input
              type="text"
              class="form-control"
              name="street"
              id="zip-codes-street"
              data-cy="street"
              :class="{ valid: !$v.zipCodes.street.$invalid, invalid: $v.zipCodes.street.$invalid }"
              v-model="$v.zipCodes.street.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.zipCodes.area')" for="zip-codes-area">Area</label>
            <input
              type="text"
              class="form-control"
              name="area"
              id="zip-codes-area"
              data-cy="area"
              :class="{ valid: !$v.zipCodes.area.$invalid, invalid: $v.zipCodes.area.$invalid }"
              v-model="$v.zipCodes.area.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.zipCodes.fromNumber')" for="zip-codes-fromNumber"
              >From Number</label
            >
            <input
              type="text"
              class="form-control"
              name="fromNumber"
              id="zip-codes-fromNumber"
              data-cy="fromNumber"
              :class="{ valid: !$v.zipCodes.fromNumber.$invalid, invalid: $v.zipCodes.fromNumber.$invalid }"
              v-model="$v.zipCodes.fromNumber.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.zipCodes.toNumber')" for="zip-codes-toNumber"
              >To Number</label
            >
            <input
              type="text"
              class="form-control"
              name="toNumber"
              id="zip-codes-toNumber"
              data-cy="toNumber"
              :class="{ valid: !$v.zipCodes.toNumber.$invalid, invalid: $v.zipCodes.toNumber.$invalid }"
              v-model="$v.zipCodes.toNumber.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.zipCodes.country')" for="zip-codes-country"
              >Country</label
            >
            <select class="form-control" id="zip-codes-country" data-cy="country" name="country" v-model="zipCodes.country">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="zipCodes.country && countriesOption.id === zipCodes.country.id ? zipCodes.country : countriesOption"
                v-for="countriesOption in countries"
                :key="countriesOption.id"
              >
                {{ countriesOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.zipCodes.region')" for="zip-codes-region"
              >Region</label
            >
            <select class="form-control" id="zip-codes-region" data-cy="region" name="region" v-model="zipCodes.region">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="zipCodes.region && regionsOption.id === zipCodes.region.id ? zipCodes.region : regionsOption"
                v-for="regionsOption in regions"
                :key="regionsOption.id"
              >
                {{ regionsOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.zipCodes.city')" for="zip-codes-city">City</label>
            <select class="form-control" id="zip-codes-city" data-cy="city" name="city" v-model="zipCodes.city">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="zipCodes.city && citiesOption.id === zipCodes.city.id ? zipCodes.city : citiesOption"
                v-for="citiesOption in cities"
                :key="citiesOption.id"
              >
                {{ citiesOption.name }}
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
            :disabled="$v.zipCodes.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./zip-codes-update.component.ts"></script>
