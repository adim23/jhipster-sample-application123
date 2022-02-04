<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="citizensRelations">
        <h2 class="jh-entity-heading" data-cy="citizensRelationsDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.citizensRelations.detail.title')">CitizensRelations</span>
          {{ citizensRelations.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizensRelations.name')">Name</span>
          </dt>
          <dd>
            <span>{{ citizensRelations.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizensRelations.citizen')">Citizen</span>
          </dt>
          <dd>
            <div v-if="citizensRelations.citizen">
              <router-link :to="{ name: 'CitizensView', params: { citizensId: citizensRelations.citizen.id } }">{{
                citizensRelations.citizen.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="citizensRelations.id"
          :to="{ name: 'CitizensRelationsEdit', params: { citizensRelationsId: citizensRelations.id } }"
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

<script lang="ts" src="./citizens-relations-details.component.ts"></script>
