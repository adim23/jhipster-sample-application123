<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="employee">
        <h2 class="jh-entity-heading" data-cy="employeeDetailsHeading">
          <span v-text="$t('jhipsterSampleApplication123App.employee.detail.title')">Employee</span> {{ employee.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.firstName')">First Name</span>
          </dt>
          <dd>
            <span>{{ employee.firstName }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.lastName')">Last Name</span>
          </dt>
          <dd>
            <span>{{ employee.lastName }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.fullName')">Full Name</span>
          </dt>
          <dd>
            <span>{{ employee.fullName }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.email')">Email</span>
          </dt>
          <dd>
            <span>{{ employee.email }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.phoneNumber')">Phone Number</span>
          </dt>
          <dd>
            <span>{{ employee.phoneNumber }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.hireDateTime')">Hire Date Time</span>
          </dt>
          <dd>
            <span v-if="employee.hireDateTime">{{ $d(Date.parse(employee.hireDateTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.zonedHireDateTime')">Zoned Hire Date Time</span>
          </dt>
          <dd>
            <span v-if="employee.zonedHireDateTime">{{ $d(Date.parse(employee.zonedHireDateTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.hireDate')">Hire Date</span>
          </dt>
          <dd>
            <span>{{ employee.hireDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.salary')">Salary</span>
          </dt>
          <dd>
            <span>{{ employee.salary }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.commissionPct')">Commission Pct</span>
          </dt>
          <dd>
            <span>{{ employee.commissionPct }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.duration')">Duration</span>
          </dt>
          <dd>
            <span>{{ employee.duration | duration }} ({{ employee.duration }})</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.pict')">Pict</span>
          </dt>
          <dd>
            <div v-if="employee.pict">
              <a v-on:click="openFile(employee.pictContentType, employee.pict)">
                <img
                  v-bind:src="'data:' + employee.pictContentType + ';base64,' + employee.pict"
                  style="max-width: 100%"
                  alt="employee image"
                />
              </a>
              {{ employee.pictContentType }}, {{ byteSize(employee.pict) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.comments')">Comments</span>
          </dt>
          <dd>
            <span>{{ employee.comments }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.cv')">Cv</span>
          </dt>
          <dd>
            <div v-if="employee.cv">
              <a v-on:click="openFile(employee.cvContentType, employee.cv)" v-text="$t('entity.action.open')">open</a>
              {{ employee.cvContentType }}, {{ byteSize(employee.cv) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.active')">Active</span>
          </dt>
          <dd>
            <span>{{ employee.active }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.manager')">Manager</span>
          </dt>
          <dd>
            <div v-if="employee.manager">
              <router-link :to="{ name: 'EmployeeView', params: { employeeId: employee.manager.id } }">{{
                employee.manager.fullName
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('jhipsterSampleApplication123App.employee.department')">Department</span>
          </dt>
          <dd>
            <div v-if="employee.department">
              <router-link :to="{ name: 'DepartmentView', params: { departmentId: employee.department.id } }">{{
                employee.department.departmentName
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="employee.id" :to="{ name: 'EmployeeEdit', params: { employeeId: employee.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./employee-details.component.ts"></script>
