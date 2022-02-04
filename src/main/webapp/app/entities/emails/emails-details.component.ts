import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEmails } from '@/shared/model/emails.model';
import EmailsService from './emails.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class EmailsDetails extends Vue {
  @Inject('emailsService') private emailsService: () => EmailsService;
  @Inject('alertService') private alertService: () => AlertService;

  public emails: IEmails = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.emailsId) {
        vm.retrieveEmails(to.params.emailsId);
      }
    });
  }

  public retrieveEmails(emailsId) {
    this.emailsService()
      .find(emailsId)
      .then(res => {
        this.emails = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
