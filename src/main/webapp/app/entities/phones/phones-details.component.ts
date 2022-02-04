import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPhones } from '@/shared/model/phones.model';
import PhonesService from './phones.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PhonesDetails extends Vue {
  @Inject('phonesService') private phonesService: () => PhonesService;
  @Inject('alertService') private alertService: () => AlertService;

  public phones: IPhones = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.phonesId) {
        vm.retrievePhones(to.params.phonesId);
      }
    });
  }

  public retrievePhones(phonesId) {
    this.phonesService()
      .find(phonesId)
      .then(res => {
        this.phones = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
