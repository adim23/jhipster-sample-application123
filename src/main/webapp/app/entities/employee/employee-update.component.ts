import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required, numeric, minValue, maxValue } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import JobService from '@/entities/job/job.service';
import { IJob } from '@/shared/model/job.model';

import DepartmentService from '@/entities/department/department.service';
import { IDepartment } from '@/shared/model/department.model';

import { IEmployee, Employee } from '@/shared/model/employee.model';
import EmployeeService from './employee.service';

const validations: any = {
  employee: {
    firstName: {
      required,
    },
    lastName: {
      required,
    },
    fullName: {
      required,
    },
    email: {
      required,
    },
    phoneNumber: {},
    hireDateTime: {},
    zonedHireDateTime: {},
    hireDate: {},
    salary: {
      numeric,
      min: minValue(0),
    },
    commissionPct: {
      numeric,
      min: minValue(0),
      max: maxValue(100),
    },
    duration: {},
    pict: {},
    comments: {},
    cv: {},
    active: {},
  },
};

@Component({
  validations,
})
export default class EmployeeUpdate extends mixins(JhiDataUtils) {
  @Inject('employeeService') private employeeService: () => EmployeeService;
  @Inject('alertService') private alertService: () => AlertService;

  public employee: IEmployee = new Employee();

  @Inject('jobService') private jobService: () => JobService;

  public jobs: IJob[] = [];

  public employees: IEmployee[] = [];

  @Inject('departmentService') private departmentService: () => DepartmentService;

  public departments: IDepartment[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.employeeId) {
        vm.retrieveEmployee(to.params.employeeId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.employee.id) {
      this.employeeService()
        .update(this.employee)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.employee.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.employeeService()
        .create(this.employee)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.employee.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.employee[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.employee[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.employee[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.employee[field] = null;
    }
  }

  public retrieveEmployee(employeeId): void {
    this.employeeService()
      .find(employeeId)
      .then(res => {
        res.hireDateTime = new Date(res.hireDateTime);
        res.zonedHireDateTime = new Date(res.zonedHireDateTime);
        this.employee = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public clearInputImage(field, fieldContentType, idInput): void {
    if (this.employee && field && fieldContentType) {
      if (Object.prototype.hasOwnProperty.call(this.employee, field)) {
        this.employee[field] = null;
      }
      if (Object.prototype.hasOwnProperty.call(this.employee, fieldContentType)) {
        this.employee[fieldContentType] = null;
      }
      if (idInput) {
        (<any>this).$refs[idInput] = null;
      }
    }
  }

  public initRelationships(): void {
    this.jobService()
      .retrieve()
      .then(res => {
        this.jobs = res.data;
      });
    this.employeeService()
      .retrieve()
      .then(res => {
        this.employees = res.data;
      });
    this.departmentService()
      .retrieve()
      .then(res => {
        this.departments = res.data;
      });
  }
}
