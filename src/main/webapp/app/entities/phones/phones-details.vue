<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="phones">
        <h2 class="jh-entity-heading" data-cy="phonesDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.phones.detail.title')">Phones</span> {{ phones.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.phones.phone')">Phone</span>
          </dt>
          <dd>
            <span>{{ phones.phone }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.phones.description')">Description</span>
          </dt>
          <dd>
            <span>{{ phones.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.phones.favourite')">Favourite</span>
          </dt>
          <dd>
            <span>{{ phones.favourite }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.phones.kind')">Kind</span>
          </dt>
          <dd>
            <div v-if="phones.kind">
              <router-link :to="{ name: 'PhoneTypesView', params: { phoneTypesId: phones.kind.id } }">{{ phones.kind.name }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.phones.company')">Company</span>
          </dt>
          <dd>
            <div v-if="phones.company">
              <router-link :to="{ name: 'CompaniesView', params: { companiesId: phones.company.id } }">{{
                phones.company.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.phones.citizen')">Citizen</span>
          </dt>
          <dd>
            <div v-if="phones.citizen">
              <router-link :to="{ name: 'CitizensView', params: { citizensId: phones.citizen.id } }">{{ phones.citizen.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="phones.id" :to="{ name: 'PhonesEdit', params: { phonesId: phones.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./phones-details.component.ts"></script>
