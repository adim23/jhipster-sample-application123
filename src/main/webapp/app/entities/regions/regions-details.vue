<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="regions">
        <h2 class="jh-entity-heading" data-cy="regionsDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.regions.detail.title')">Regions</span> {{ regions.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.regions.name')">Name</span>
          </dt>
          <dd>
            <span>{{ regions.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.regions.country')">Country</span>
          </dt>
          <dd>
            <div v-if="regions.country">
              <router-link :to="{ name: 'CountriesView', params: { countriesId: regions.country.id } }">{{
                regions.country.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="regions.id" :to="{ name: 'RegionsEdit', params: { regionsId: regions.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./regions-details.component.ts"></script>
