<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="zipCodes">
        <h2 class="jh-entity-heading" data-cy="zipCodesDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.zipCodes.detail.title')">ZipCodes</span> {{ zipCodes.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.zipCodes.street')">Street</span>
          </dt>
          <dd>
            <span>{{ zipCodes.street }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.zipCodes.area')">Area</span>
          </dt>
          <dd>
            <span>{{ zipCodes.area }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.zipCodes.fromNumber')">From Number</span>
          </dt>
          <dd>
            <span>{{ zipCodes.fromNumber }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.zipCodes.toNumber')">To Number</span>
          </dt>
          <dd>
            <span>{{ zipCodes.toNumber }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.zipCodes.country')">Country</span>
          </dt>
          <dd>
            <div v-if="zipCodes.country">
              <router-link :to="{ name: 'CountriesView', params: { countriesId: zipCodes.country.id } }">{{
                zipCodes.country.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.zipCodes.region')">Region</span>
          </dt>
          <dd>
            <div v-if="zipCodes.region">
              <router-link :to="{ name: 'RegionsView', params: { regionsId: zipCodes.region.id } }">{{ zipCodes.region.name }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.zipCodes.city')">City</span>
          </dt>
          <dd>
            <div v-if="zipCodes.city">
              <router-link :to="{ name: 'CitiesView', params: { citiesId: zipCodes.city.id } }">{{ zipCodes.city.name }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="zipCodes.id" :to="{ name: 'ZipCodesEdit', params: { zipCodesId: zipCodes.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./zip-codes-details.component.ts"></script>
