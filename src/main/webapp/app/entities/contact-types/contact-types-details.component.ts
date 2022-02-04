import { Component, Vue, Inject } from 'vue-property-decorator';

import { IContactTypes } from '@/shared/model/contact-types.model';
import ContactTypesService from './contact-types.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ContactTypesDetails extends Vue {
  @Inject('contactTypesService') private contactTypesService: () => ContactTypesService;
  @Inject('alertService') private alertService: () => AlertService;

  public contactTypes: IContactTypes = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.contactTypesId) {
        vm.retrieveContactTypes(to.params.contactTypesId);
      }
    });
  }

  public retrieveContactTypes(contactTypesId) {
    this.contactTypesService()
      .find(contactTypesId)
      .then(res => {
        this.contactTypes = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
