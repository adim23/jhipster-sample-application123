<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhipsterSampleApplication123App.addresses.home.createOrEditLabel"
          data-cy="AddressesCreateUpdateHeading"
          v-text="$t('jhipsterSampleApplication123App.addresses.home.createOrEditLabel')"
        >
          Create or edit a Addresses
        </h2>
        <div>
          <div class="form-group" v-if="addresses.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="addresses.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.addresses.address')" for="addresses-address"
              >Address</label
            >
            <input
              type="text"
              class="form-control"
              name="address"
              id="addresses-address"
              data-cy="address"
              :class="{ valid: !$v.addresses.address.$invalid, invalid: $v.addresses.address.$invalid }"
              v-model="$v.addresses.address.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.addresses.addressNo')" for="addresses-addressNo"
              >Address No</label
            >
            <input
              type="text"
              class="form-control"
              name="addressNo"
              id="addresses-addressNo"
              data-cy="addressNo"
              :class="{ valid: !$v.addresses.addressNo.$invalid, invalid: $v.addresses.addressNo.$invalid }"
              v-model="$v.addresses.addressNo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.addresses.zipCode')" for="addresses-zipCode"
              >Zip Code</label
            >
            <input
              type="text"
              class="form-control"
              name="zipCode"
              id="addresses-zipCode"
              data-cy="zipCode"
              :class="{ valid: !$v.addresses.zipCode.$invalid, invalid: $v.addresses.zipCode.$invalid }"
              v-model="$v.addresses.zipCode.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.addresses.prosfLetter')"
              for="addresses-prosfLetter"
              >Prosf Letter</label
            >
            <input
              type="text"
              class="form-control"
              name="prosfLetter"
              id="addresses-prosfLetter"
              data-cy="prosfLetter"
              :class="{ valid: !$v.addresses.prosfLetter.$invalid, invalid: $v.addresses.prosfLetter.$invalid }"
              v-model="$v.addresses.prosfLetter.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.addresses.nameLetter')" for="addresses-nameLetter"
              >Name Letter</label
            >
            <input
              type="text"
              class="form-control"
              name="nameLetter"
              id="addresses-nameLetter"
              data-cy="nameLetter"
              :class="{ valid: !$v.addresses.nameLetter.$invalid, invalid: $v.addresses.nameLetter.$invalid }"
              v-model="$v.addresses.nameLetter.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.addresses.letterClose')"
              for="addresses-letterClose"
              >Letter Close</label
            >
            <input
              type="text"
              class="form-control"
              name="letterClose"
              id="addresses-letterClose"
              data-cy="letterClose"
              :class="{ valid: !$v.addresses.letterClose.$invalid, invalid: $v.addresses.letterClose.$invalid }"
              v-model="$v.addresses.letterClose.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.addresses.firstLabel')" for="addresses-firstLabel"
              >First Label</label
            >
            <input
              type="text"
              class="form-control"
              name="firstLabel"
              id="addresses-firstLabel"
              data-cy="firstLabel"
              :class="{ valid: !$v.addresses.firstLabel.$invalid, invalid: $v.addresses.firstLabel.$invalid }"
              v-model="$v.addresses.firstLabel.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.addresses.secondLabel')"
              for="addresses-secondLabel"
              >Second Label</label
            >
            <input
              type="text"
              class="form-control"
              name="secondLabel"
              id="addresses-secondLabel"
              data-cy="secondLabel"
              :class="{ valid: !$v.addresses.secondLabel.$invalid, invalid: $v.addresses.secondLabel.$invalid }"
              v-model="$v.addresses.secondLabel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.addresses.thirdLabel')" for="addresses-thirdLabel"
              >Third Label</label
            >
            <input
              type="text"
              class="form-control"
              name="thirdLabel"
              id="addresses-thirdLabel"
              data-cy="thirdLabel"
              :class="{ valid: !$v.addresses.thirdLabel.$invalid, invalid: $v.addresses.thirdLabel.$invalid }"
              v-model="$v.addresses.thirdLabel.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('jhipsterSampleApplication123App.addresses.fourthLabel')"
              for="addresses-fourthLabel"
              >Fourth Label</label
            >
            <input
              type="text"
              class="form-control"
              name="fourthLabel"
              id="addresses-fourthLabel"
              data-cy="fourthLabel"
              :class="{ valid: !$v.addresses.fourthLabel.$invalid, invalid: $v.addresses.fourthLabel.$invalid }"
              v-model="$v.addresses.fourthLabel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.addresses.fifthLabel')" for="addresses-fifthLabel"
              >Fifth Label</label
            >
            <input
              type="text"
              class="form-control"
              name="fifthLabel"
              id="addresses-fifthLabel"
              data-cy="fifthLabel"
              :class="{ valid: !$v.addresses.fifthLabel.$invalid, invalid: $v.addresses.fifthLabel.$invalid }"
              v-model="$v.addresses.fifthLabel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.addresses.favourite')" for="addresses-favourite"
              >Favourite</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="favourite"
              id="addresses-favourite"
              data-cy="favourite"
              :class="{ valid: !$v.addresses.favourite.$invalid, invalid: $v.addresses.favourite.$invalid }"
              v-model="$v.addresses.favourite.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.addresses.country')" for="addresses-country"
              >Country</label
            >
            <select class="form-control" id="addresses-country" data-cy="country" name="country" v-model="addresses.country">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="addresses.country && countriesOption.id === addresses.country.id ? addresses.country : countriesOption"
                v-for="countriesOption in countries"
                :key="countriesOption.id"
              >
                {{ countriesOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.addresses.kind')" for="addresses-kind"
              >Kind</label
            >
            <select class="form-control" id="addresses-kind" data-cy="kind" name="kind" v-model="addresses.kind">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="addresses.kind && contactTypesOption.id === addresses.kind.id ? addresses.kind : contactTypesOption"
                v-for="contactTypesOption in contactTypes"
                :key="contactTypesOption.id"
              >
                {{ contactTypesOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.addresses.region')" for="addresses-region"
              >Region</label
            >
            <select class="form-control" id="addresses-region" data-cy="region" name="region" v-model="addresses.region">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="addresses.region && regionsOption.id === addresses.region.id ? addresses.region : regionsOption"
                v-for="regionsOption in regions"
                :key="regionsOption.id"
              >
                {{ regionsOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.addresses.company')" for="addresses-company"
              >Company</label
            >
            <select class="form-control" id="addresses-company" data-cy="company" name="company" v-model="addresses.company">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="addresses.company && companiesOption.id === addresses.company.id ? addresses.company : companiesOption"
                v-for="companiesOption in companies"
                :key="companiesOption.id"
              >
                {{ companiesOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhipsterSampleApplication123App.addresses.citizen')" for="addresses-citizen"
              >Citizen</label
            >
            <select class="form-control" id="addresses-citizen" data-cy="citizen" name="citizen" v-model="addresses.citizen">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="addresses.citizen && citizensOption.id === addresses.citizen.id ? addresses.citizen : citizensOption"
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
            :disabled="$v.addresses.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./addresses-update.component.ts"></script>
