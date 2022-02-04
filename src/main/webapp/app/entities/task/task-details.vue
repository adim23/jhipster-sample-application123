<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="task">
        <h2 class="jh-entity-heading" data-cy="taskDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.task.detail.title')">Task</span> {{ task.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.task.title')">Title</span>
          </dt>
          <dd>
            <span>{{ task.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.task.description')">Description</span>
          </dt>
          <dd>
            <span>{{ task.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.task.startDate')">Start Date</span>
          </dt>
          <dd>
            <span>{{ task.startDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.task.endDate')">End Date</span>
          </dt>
          <dd>
            <span>{{ task.endDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.task.percentCompleted')">Percent Completed</span>
          </dt>
          <dd>
            <span>{{ task.percentCompleted }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.task.dependsOn')">Depends On</span>
          </dt>
          <dd>
            <div v-if="task.dependsOn">
              <router-link :to="{ name: 'TaskView', params: { taskId: task.dependsOn.id } }">{{ task.dependsOn.title }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="task.id" :to="{ name: 'TaskEdit', params: { taskId: task.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-details.component.ts"></script>
