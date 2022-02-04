import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPhoneTypes } from '@/shared/model/phone-types.model';
import PhoneTypesService from './phone-types.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PhoneTypesDetails extends Vue {
  @Inject('phoneTypesService') private phoneTypesService: () => PhoneTypesService;
  @Inject('alertService') private alertService: () => AlertService;

  public phoneTypes: IPhoneTypes = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.phoneTypesId) {
        vm.retrievePhoneTypes(to.params.phoneTypesId);
      }
    });
  }

  public retrievePhoneTypes(phoneTypesId) {
    this.phoneTypesService()
      .find(phoneTypesId)
      .then(res => {
        this.phoneTypes = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
