<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="citizenFolders">
        <h2 class="jh-entity-heading" data-cy="citizenFoldersDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.citizenFolders.detail.title')">CitizenFolders</span> {{ citizenFolders.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizenFolders.name')">Name</span>
          </dt>
          <dd>
            <span>{{ citizenFolders.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizenFolders.description')">Description</span>
          </dt>
          <dd>
            <span>{{ citizenFolders.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizenFolders.special')">Special</span>
          </dt>
          <dd>
            <span>{{ citizenFolders.special }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="citizenFolders.id"
          :to="{ name: 'CitizenFoldersEdit', params: { citizenFoldersId: citizenFolders.id } }"
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

<script lang="ts" src="./citizen-folders-details.component.ts"></script>
