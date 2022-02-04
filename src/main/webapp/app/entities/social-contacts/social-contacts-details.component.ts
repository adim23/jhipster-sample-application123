import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISocialContacts } from '@/shared/model/social-contacts.model';
import SocialContactsService from './social-contacts.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class SocialContactsDetails extends Vue {
  @Inject('socialContactsService') private socialContactsService: () => SocialContactsService;
  @Inject('alertService') private alertService: () => AlertService;

  public socialContacts: ISocialContacts = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.socialContactsId) {
        vm.retrieveSocialContacts(to.params.socialContactsId);
      }
    });
  }

  public retrieveSocialContacts(socialContactsId) {
    this.socialContactsService()
      .find(socialContactsId)
      .then(res => {
        this.socialContacts = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
