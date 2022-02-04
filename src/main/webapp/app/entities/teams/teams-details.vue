<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="teams">
        <h2 class="jh-entity-heading" data-cy="teamsDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.teams.detail.title')">Teams</span> {{ teams.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.teams.name')">Name</span>
          </dt>
          <dd>
            <span>{{ teams.name }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="teams.id" :to="{ name: 'TeamsEdit', params: { teamsId: teams.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./teams-details.component.ts"></script>
