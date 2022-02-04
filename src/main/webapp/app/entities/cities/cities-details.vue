<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="cities">
        <h2 class="jh-entity-heading" data-cy="citiesDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.cities.detail.title')">Cities</span> {{ cities.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.cities.name')">Name</span>
          </dt>
          <dd>
            <span>{{ cities.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.cities.president')">President</span>
          </dt>
          <dd>
            <span>{{ cities.president }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.cities.presidentsPhone')">Presidents Phone</span>
          </dt>
          <dd>
            <span>{{ cities.presidentsPhone }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.cities.secretary')">Secretary</span>
          </dt>
          <dd>
            <span>{{ cities.secretary }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.cities.secretarysPhone')">Secretarys Phone</span>
          </dt>
          <dd>
            <span>{{ cities.secretarysPhone }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.cities.police')">Police</span>
          </dt>
          <dd>
            <span>{{ cities.police }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.cities.policesPhone')">Polices Phone</span>
          </dt>
          <dd>
            <span>{{ cities.policesPhone }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.cities.doctor')">Doctor</span>
          </dt>
          <dd>
            <span>{{ cities.doctor }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.cities.doctorsPhone')">Doctors Phone</span>
          </dt>
          <dd>
            <span>{{ cities.doctorsPhone }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.cities.teacher')">Teacher</span>
          </dt>
          <dd>
            <span>{{ cities.teacher }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.cities.teachersPhone')">Teachers Phone</span>
          </dt>
          <dd>
            <span>{{ cities.teachersPhone }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.cities.priest')">Priest</span>
          </dt>
          <dd>
            <span>{{ cities.priest }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.cities.priestsPhone')">Priests Phone</span>
          </dt>
          <dd>
            <span>{{ cities.priestsPhone }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.cities.country')">Country</span>
          </dt>
          <dd>
            <div v-if="cities.country">
              <router-link :to="{ name: 'CountriesView', params: { countriesId: cities.country.id } }">{{
                cities.country.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.cities.region')">Region</span>
          </dt>
          <dd>
            <div v-if="cities.region">
              <router-link :to="{ name: 'RegionsView', params: { regionsId: cities.region.id } }">{{ cities.region.name }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="cities.id" :to="{ name: 'CitiesEdit', params: { citiesId: cities.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./cities-details.component.ts"></script>
