import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { ICitizens } from '@/shared/model/citizens.model';
import CitizensService from './citizens.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CitizensDetails extends mixins(JhiDataUtils) {
  @Inject('citizensService') private citizensService: () => CitizensService;
  @Inject('alertService') private alertService: () => AlertService;

  public citizens: ICitizens = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.citizensId) {
        vm.retrieveCitizens(to.params.citizensId);
      }
    });
  }

  public retrieveCitizens(citizensId) {
    this.citizensService()
      .find(citizensId)
      .then(res => {
        this.citizens = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
