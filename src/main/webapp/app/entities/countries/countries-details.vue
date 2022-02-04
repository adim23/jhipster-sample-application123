<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="countries">
        <h2 class="jh-entity-heading" data-cy="countriesDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.countries.detail.title')">Countries</span> {{ countries.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.countries.name')">Name</span>
          </dt>
          <dd>
            <span>{{ countries.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.countries.iso')">Iso</span>
          </dt>
          <dd>
            <span>{{ countries.iso }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.countries.language')">Language</span>
          </dt>
          <dd>
            <span>{{ countries.language }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.countries.lang')">Lang</span>
          </dt>
          <dd>
            <span>{{ countries.lang }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="countries.id"
          :to="{ name: 'CountriesEdit', params: { countriesId: countries.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./countries-details.component.ts"></script>
