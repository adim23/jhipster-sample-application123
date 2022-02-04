import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import ContactTypesService from '@/entities/contact-types/contact-types.service';
import { IContactTypes } from '@/shared/model/contact-types.model';

import CitizensService from '@/entities/citizens/citizens.service';
import { ICitizens } from '@/shared/model/citizens.model';

import { IEmails, Emails } from '@/shared/model/emails.model';
import EmailsService from './emails.service';

const validations: any = {
  emails: {
    email: {
      required,
    },
    description: {},
    favourite: {},
  },
};

@Component({
  validations,
})
export default class EmailsUpdate extends Vue {
  @Inject('emailsService') private emailsService: () => EmailsService;
  @Inject('alertService') private alertService: () => AlertService;

  public emails: IEmails = new Emails();

  @Inject('contactTypesService') private contactTypesService: () => ContactTypesService;

  public contactTypes: IContactTypes[] = [];

  @Inject('citizensService') private citizensService: () => CitizensService;

  public citizens: ICitizens[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.emailsId) {
        vm.retrieveEmails(to.params.emailsId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.emails.id) {
      this.emailsService()
        .update(this.emails)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.emails.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.emailsService()
        .create(this.emails)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.emails.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveEmails(emailsId): void {
    this.emailsService()
      .find(emailsId)
      .then(res => {
        this.emails = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.contactTypesService()
      .retrieve()
      .then(res => {
        this.contactTypes = res.data;
      });
    this.citizensService()
      .retrieve()
      .then(res => {
        this.citizens = res.data;
      });
  }
}
