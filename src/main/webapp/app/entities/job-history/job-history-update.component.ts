import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import JobService from '@/entities/job/job.service';
import { IJob } from '@/shared/model/job.model';

import DepartmentService from '@/entities/department/department.service';
import { IDepartment } from '@/shared/model/department.model';

import EmployeeService from '@/entities/employee/employee.service';
import { IEmployee } from '@/shared/model/employee.model';

import { IJobHistory, JobHistory } from '@/shared/model/job-history.model';
import JobHistoryService from './job-history.service';
import { Language } from '@/shared/model/enumerations/language.model';

const validations: any = {
  jobHistory: {
    startDate: {
      required,
    },
    endDate: {},
    language: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class JobHistoryUpdate extends Vue {
  @Inject('jobHistoryService') private jobHistoryService: () => JobHistoryService;
  @Inject('alertService') private alertService: () => AlertService;

  public jobHistory: IJobHistory = new JobHistory();

  @Inject('jobService') private jobService: () => JobService;

  public jobs: IJob[] = [];

  @Inject('departmentService') private departmentService: () => DepartmentService;

  public departments: IDepartment[] = [];

  @Inject('employeeService') private employeeService: () => EmployeeService;

  public employees: IEmployee[] = [];
  public languageValues: string[] = Object.keys(Language);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.jobHistoryId) {
        vm.retrieveJobHistory(to.params.jobHistoryId);
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
    if (this.jobHistory.id) {
      this.jobHistoryService()
        .update(this.jobHistory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.jobHistory.updated', { param: param.id });
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
      this.jobHistoryService()
        .create(this.jobHistory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.jobHistory.created', { param: param.id });
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

  public retrieveJobHistory(jobHistoryId): void {
    this.jobHistoryService()
      .find(jobHistoryId)
      .then(res => {
        this.jobHistory = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.jobService()
      .retrieve()
      .then(res => {
        this.jobs = res.data;
      });
    this.departmentService()
      .retrieve()
      .then(res => {
        this.departments = res.data;
      });
    this.employeeService()
      .retrieve()
      .then(res => {
        this.employees = res.data;
      });
  }
}
