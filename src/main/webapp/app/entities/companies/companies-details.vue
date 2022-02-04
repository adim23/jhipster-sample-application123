<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="companies">
        <h2 class="jh-entity-heading" data-cy="companiesDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.companies.detail.title')">Companies</span> {{ companies.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.companies.name')">Name</span>
          </dt>
          <dd>
            <span>{{ companies.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.companies.kind')">Kind</span>
          </dt>
          <dd>
            <div v-if="companies.kind">
              <router-link :to="{ name: 'CompanyKindsView', params: { companyKindsId: companies.kind.id } }">{{
                companies.kind.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="companies.id"
          :to="{ name: 'CompaniesEdit', params: { companiesId: companies.id } }"
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

<script lang="ts" src="./companies-details.component.ts"></script>
