<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="socialKinds">
        <h2 class="jh-entity-heading" data-cy="socialKindsDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.socialKinds.detail.title')">SocialKinds</span> {{ socialKinds.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.socialKinds.code')">Code</span>
          </dt>
          <dd>
            <span>{{ socialKinds.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.socialKinds.name')">Name</span>
          </dt>
          <dd>
            <span>{{ socialKinds.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.socialKinds.call')">Call</span>
          </dt>
          <dd>
            <span>{{ socialKinds.call }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="socialKinds.id"
          :to="{ name: 'SocialKindsEdit', params: { socialKindsId: socialKinds.id } }"
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

<script lang="ts" src="./social-kinds-details.component.ts"></script>
