<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="socialContacts">
        <h2 class="jh-entity-heading" data-cy="socialContactsDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.socialContacts.detail.title')">SocialContacts</span> {{ socialContacts.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.socialContacts.name')">Name</span>
          </dt>
          <dd>
            <span>{{ socialContacts.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.socialContacts.favored')">Favored</span>
          </dt>
          <dd>
            <span>{{ socialContacts.favored }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.socialContacts.social')">Social</span>
          </dt>
          <dd>
            <div v-if="socialContacts.social">
              <router-link :to="{ name: 'SocialKindsView', params: { socialKindsId: socialContacts.social.id } }">{{
                socialContacts.social.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.socialContacts.kind')">Kind</span>
          </dt>
          <dd>
            <div v-if="socialContacts.kind">
              <router-link :to="{ name: 'ContactTypesView', params: { contactTypesId: socialContacts.kind.id } }">{{
                socialContacts.kind.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.socialContacts.citizen')">Citizen</span>
          </dt>
          <dd>
            <div v-if="socialContacts.citizen">
              <router-link :to="{ name: 'CitizensView', params: { citizensId: socialContacts.citizen.id } }">{{
                socialContacts.citizen.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="socialContacts.id"
          :to="{ name: 'SocialContactsEdit', params: { socialContactsId: socialContacts.id } }"
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

<script lang="ts" src="./social-contacts-details.component.ts"></script>
