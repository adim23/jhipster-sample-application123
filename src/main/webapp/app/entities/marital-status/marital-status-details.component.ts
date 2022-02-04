import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMaritalStatus } from '@/shared/model/marital-status.model';
import MaritalStatusService from './marital-status.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class MaritalStatusDetails extends Vue {
  @Inject('maritalStatusService') private maritalStatusService: () => MaritalStatusService;
  @Inject('alertService') private alertService: () => AlertService;

  public maritalStatus: IMaritalStatus = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.maritalStatusId) {
        vm.retrieveMaritalStatus(to.params.maritalStatusId);
      }
    });
  }

  public retrieveMaritalStatus(maritalStatusId) {
    this.maritalStatusService()
      .find(maritalStatusId)
      .then(res => {
        this.maritalStatus = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
