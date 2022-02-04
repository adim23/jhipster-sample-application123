import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOrigins } from '@/shared/model/origins.model';
import OriginsService from './origins.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class OriginsDetails extends Vue {
  @Inject('originsService') private originsService: () => OriginsService;
  @Inject('alertService') private alertService: () => AlertService;

  public origins: IOrigins = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.originsId) {
        vm.retrieveOrigins(to.params.originsId);
      }
    });
  }

  public retrieveOrigins(originsId) {
    this.originsService()
      .find(originsId)
      .then(res => {
        this.origins = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
