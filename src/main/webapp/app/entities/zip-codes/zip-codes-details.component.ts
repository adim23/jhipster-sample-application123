import { Component, Vue, Inject } from 'vue-property-decorator';

import { IZipCodes } from '@/shared/model/zip-codes.model';
import ZipCodesService from './zip-codes.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ZipCodesDetails extends Vue {
  @Inject('zipCodesService') private zipCodesService: () => ZipCodesService;
  @Inject('alertService') private alertService: () => AlertService;

  public zipCodes: IZipCodes = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.zipCodesId) {
        vm.retrieveZipCodes(to.params.zipCodesId);
      }
    });
  }

  public retrieveZipCodes(zipCodesId) {
    this.zipCodesService()
      .find(zipCodesId)
      .then(res => {
        this.zipCodes = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
