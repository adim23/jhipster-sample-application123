<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="phoneTypes">
        <h2 class="jh-entity-heading" data-cy="phoneTypesDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.phoneTypes.detail.title')">PhoneTypes</span> {{ phoneTypes.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.phoneTypes.name')">Name</span>
          </dt>
          <dd>
            <span>{{ phoneTypes.name }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="phoneTypes.id"
          :to="{ name: 'PhoneTypesEdit', params: { phoneTypesId: phoneTypes.id } }"
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

<script lang="ts" src="./phone-types-details.component.ts"></script>
