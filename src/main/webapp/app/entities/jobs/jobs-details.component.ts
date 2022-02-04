import { Component, Vue, Inject } from 'vue-property-decorator';

import { IJobs } from '@/shared/model/jobs.model';
import JobsService from './jobs.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class JobsDetails extends Vue {
  @Inject('jobsService') private jobsService: () => JobsService;
  @Inject('alertService') private alertService: () => AlertService;

  public jobs: IJobs = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.jobsId) {
        vm.retrieveJobs(to.params.jobsId);
      }
    });
  }

  public retrieveJobs(jobsId) {
    this.jobsService()
      .find(jobsId)
      .then(res => {
        this.jobs = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
