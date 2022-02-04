<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="emails">
        <h2 class="jh-entity-heading" data-cy="emailsDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.emails.detail.title')">Emails</span> {{ emails.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.emails.email')">Email</span>
          </dt>
          <dd>
            <span>{{ emails.email }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.emails.description')">Description</span>
          </dt>
          <dd>
            <span>{{ emails.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.emails.favourite')">Favourite</span>
          </dt>
          <dd>
            <span>{{ emails.favourite }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.emails.kind')">Kind</span>
          </dt>
          <dd>
            <div v-if="emails.kind">
              <router-link :to="{ name: 'ContactTypesView', params: { contactTypesId: emails.kind.id } }">{{
                emails.kind.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.emails.citizen')">Citizen</span>
          </dt>
          <dd>
            <div v-if="emails.citizen">
              <router-link :to="{ name: 'CitizensView', params: { citizensId: emails.citizen.id } }">{{ emails.citizen.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="emails.id" :to="{ name: 'EmailsEdit', params: { emailsId: emails.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./emails-details.component.ts"></script>
