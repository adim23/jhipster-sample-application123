import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRegions } from '@/shared/model/regions.model';
import RegionsService from './regions.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class RegionsDetails extends Vue {
  @Inject('regionsService') private regionsService: () => RegionsService;
  @Inject('alertService') private alertService: () => AlertService;

  public regions: IRegions = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.regionsId) {
        vm.retrieveRegions(to.params.regionsId);
      }
    });
  }

  public retrieveRegions(regionsId) {
    this.regionsService()
      .find(regionsId)
      .then(res => {
        this.regions = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
