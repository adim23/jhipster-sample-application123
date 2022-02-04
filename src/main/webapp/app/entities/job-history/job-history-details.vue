<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="jobHistory">
        <h2 class="jh-entity-heading" data-cy="jobHistoryDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.jobHistory.detail.title')">JobHistory</span> {{ jobHistory.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.jobHistory.startDate')">Start Date</span>
          </dt>
          <dd>
            <span>{{ jobHistory.startDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.jobHistory.endDate')">End Date</span>
          </dt>
          <dd>
            <span>{{ jobHistory.endDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.jobHistory.language')">Language</span>
          </dt>
          <dd>
            <span v-text="$t('jhipsterSampleApplication123App.Language.' + jobHistory.language)">{{ jobHistory.language }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.jobHistory.job')">Job</span>
          </dt>
          <dd>
            <div v-if="jobHistory.job">
              <router-link :to="{ name: 'JobView', params: { jobId: jobHistory.job.id } }">{{ jobHistory.job.jobTitle }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.jobHistory.department')">Department</span>
          </dt>
          <dd>
            <div v-if="jobHistory.department">
              <router-link :to="{ name: 'DepartmentView', params: { departmentId: jobHistory.department.id } }">{{
                jobHistory.department.departmentName
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.jobHistory.employee')">Employee</span>
          </dt>
          <dd>
            <div v-if="jobHistory.employee">
              <router-link :to="{ name: 'EmployeeView', params: { employeeId: jobHistory.employee.id } }">{{
                jobHistory.employee.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="jobHistory.id"
          :to="{ name: 'JobHistoryEdit', params: { jobHistoryId: jobHistory.id } }"
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

<script lang="ts" src="./job-history-details.component.ts"></script>
