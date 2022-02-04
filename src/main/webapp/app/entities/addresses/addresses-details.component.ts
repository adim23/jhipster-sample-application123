import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAddresses } from '@/shared/model/addresses.model';
import AddressesService from './addresses.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AddressesDetails extends Vue {
  @Inject('addressesService') private addressesService: () => AddressesService;
  @Inject('alertService') private alertService: () => AlertService;

  public addresses: IAddresses = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.addressesId) {
        vm.retrieveAddresses(to.params.addressesId);
      }
    });
  }

  public retrieveAddresses(addressesId) {
    this.addressesService()
      .find(addressesId)
      .then(res => {
        this.addresses = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
