import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { ICitizensMeetings } from '@/shared/model/citizens-meetings.model';
import CitizensMeetingsService from './citizens-meetings.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CitizensMeetingsDetails extends mixins(JhiDataUtils) {
  @Inject('citizensMeetingsService') private citizensMeetingsService: () => CitizensMeetingsService;
  @Inject('alertService') private alertService: () => AlertService;

  public citizensMeetings: ICitizensMeetings = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.citizensMeetingsId) {
        vm.retrieveCitizensMeetings(to.params.citizensMeetingsId);
      }
    });
  }

  public retrieveCitizensMeetings(citizensMeetingsId) {
    this.citizensMeetingsService()
      .find(citizensMeetingsId)
      .then(res => {
        this.citizensMeetings = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
