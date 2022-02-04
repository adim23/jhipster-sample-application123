import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICodes } from '@/shared/model/codes.model';
import CodesService from './codes.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CodesDetails extends Vue {
  @Inject('codesService') private codesService: () => CodesService;
  @Inject('alertService') private alertService: () => AlertService;

  public codes: ICodes = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.codesId) {
        vm.retrieveCodes(to.params.codesId);
      }
    });
  }

  public retrieveCodes(codesId) {
    this.codesService()
      .find(codesId)
      .then(res => {
        this.codes = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
