<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="citizensMeetings">
        <h2 class="jh-entity-heading" data-cy="citizensMeetingsDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.citizensMeetings.detail.title')">CitizensMeetings</span>
          {{ citizensMeetings.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizensMeetings.meetDate')">Meet Date</span>
          </dt>
          <dd>
            <span>{{ citizensMeetings.meetDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizensMeetings.agenda')">Agenda</span>
          </dt>
          <dd>
            <span>{{ citizensMeetings.agenda }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizensMeetings.comments')">Comments</span>
          </dt>
          <dd>
            <span>{{ citizensMeetings.comments }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizensMeetings.amount')">Amount</span>
          </dt>
          <dd>
            <span>{{ citizensMeetings.amount }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizensMeetings.quantity')">Quantity</span>
          </dt>
          <dd>
            <span>{{ citizensMeetings.quantity }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizensMeetings.status')">Status</span>
          </dt>
          <dd>
            <span>{{ citizensMeetings.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizensMeetings.flag')">Flag</span>
          </dt>
          <dd>
            <span>{{ citizensMeetings.flag }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.citizensMeetings.citizen')">Citizen</span>
          </dt>
          <dd>
            <div v-if="citizensMeetings.citizen">
              <router-link :to="{ name: 'CitizensView', params: { citizensId: citizensMeetings.citizen.id } }">{{
                citizensMeetings.citizen.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="citizensMeetings.id"
          :to="{ name: 'CitizensMeetingsEdit', params: { citizensMeetingsId: citizensMeetings.id } }"
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

<script lang="ts" src="./citizens-meetings-details.component.ts"></script>
