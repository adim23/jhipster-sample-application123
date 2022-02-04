<template>
  <div>
    <h2 id="page-heading" data-cy="EmployeeHeading">
      <span v-text="$t('jhipsterSampleApplication123App.employee.home.title')" id="employee-heading">Employees</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('jhipsterSampleApplication123App.employee.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'EmployeeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-employee"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('jhipsterSampleApplication123App.employee.home.createLabel')"> Create a new Employee </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && employees && employees.length === 0">
      <span v-text="$t('jhipsterSampleApplication123App.employee.home.notFound')">No employees found</span>
    </div>
    <div class="table-responsive" v-if="employees && employees.length > 0">
      <table class="table table-striped" aria-describedby="employees">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('firstName')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.firstName')">First Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'firstName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastName')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.lastName')">Last Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fullName')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.fullName')">Full Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fullName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('email')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.email')">Email</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'email'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('phoneNumber')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.phoneNumber')">Phone Number</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phoneNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hireDateTime')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.hireDateTime')">Hire Date Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hireDateTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('zonedHireDateTime')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.zonedHireDateTime')">Zoned Hire Date Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'zonedHireDateTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hireDate')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.hireDate')">Hire Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hireDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('salary')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.salary')">Salary</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'salary'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('commissionPct')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.commissionPct')">Commission Pct</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'commissionPct'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('duration')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.duration')">Duration</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'duration'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('pict')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.pict')">Pict</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pict'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('comments')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.comments')">Comments</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'comments'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cv')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.cv')">Cv</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cv'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('active')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.active')">Active</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'active'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('manager.fullName')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.manager')">Manager</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'manager.fullName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('department.departmentName')">
              <span v-text="$t('jhipsterSampleApplication123App.employee.department')">Department</span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'department.departmentName'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="employee in employees" :key="employee.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EmployeeView', params: { employeeId: employee.id } }">{{ employee.id }}</router-link>
            </td>
            <td>{{ employee.firstName }}</td>
            <td>{{ employee.lastName }}</td>
            <td>{{ employee.fullName }}</td>
            <td>{{ employee.email }}</td>
            <td>{{ employee.phoneNumber }}</td>
            <td>{{ employee.hireDateTime ? $d(Date.parse(employee.hireDateTime), 'short') : '' }}</td>
            <td>{{ employee.zonedHireDateTime ? $d(Date.parse(employee.zonedHireDateTime), 'short') : '' }}</td>
            <td>{{ employee.hireDate }}</td>
            <td>{{ employee.salary }}</td>
            <td>{{ employee.commissionPct }}</td>
            <td>{{ employee.duration | duration }}</td>
            <td>
              <a v-if="employee.pict" v-on:click="openFile(employee.pictContentType, employee.pict)">
                <img
                  v-bind:src="'data:' + employee.pictContentType + ';base64,' + employee.pict"
                  style="max-height: 30px"
                  alt="employee image"
                />
              </a>
              <span v-if="employee.pict">{{ employee.pictContentType }}, {{ byteSize(employee.pict) }}</span>
            </td>
            <td>{{ employee.comments }}</td>
            <td>
              <a v-if="employee.cv" v-on:click="openFile(employee.cvContentType, employee.cv)" v-text="$t('entity.action.open')">open</a>
              <span v-if="employee.cv">{{ employee.cvContentType }}, {{ byteSize(employee.cv) }}</span>
            </td>
            <td>{{ employee.active }}</td>
            <td>
              <div v-if="employee.manager">
                <router-link :to="{ name: 'EmployeeView', params: { employeeId: employee.manager.id } }">{{
                  employee.manager.fullName
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="employee.department">
                <router-link :to="{ name: 'DepartmentView', params: { departmentId: employee.department.id } }">{{
                  employee.department.departmentName
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EmployeeView', params: { employeeId: employee.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EmployeeEdit', params: { employeeId: employee.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(employee)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="jhipsterSampleApplication123App.employee.delete.question"
          data-cy="employeeDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-employee-heading" v-text="$t('jhipsterSampleApplication123App.employee.delete.question', { id: removeId })">
          Are you sure you want to delete this Employee?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-employee"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeEmployee()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="employees && employees.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./employee.component.ts"></script>
