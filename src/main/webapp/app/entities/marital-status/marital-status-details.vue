<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="maritalStatus">
        <h2 class="jh-entity-heading" data-cy="maritalStatusDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.maritalStatus.detail.title')">MaritalStatus</span> {{ maritalStatus.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.maritalStatus.name')">Name</span>
          </dt>
          <dd>
            <span>{{ maritalStatus.name }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="maritalStatus.id"
          :to="{ name: 'MaritalStatusEdit', params: { maritalStatusId: maritalStatus.id } }"
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

<script lang="ts" src="./marital-status-details.component.ts"></script>
