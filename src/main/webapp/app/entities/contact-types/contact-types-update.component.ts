import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IContactTypes, ContactTypes } from '@/shared/model/contact-types.model';
import ContactTypesService from './contact-types.service';

const validations: any = {
  contactTypes: {
    name: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class ContactTypesUpdate extends Vue {
  @Inject('contactTypesService') private contactTypesService: () => ContactTypesService;
  @Inject('alertService') private alertService: () => AlertService;

  public contactTypes: IContactTypes = new ContactTypes();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.contactTypesId) {
        vm.retrieveContactTypes(to.params.contactTypesId);
      }
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
    if (this.contactTypes.id) {
      this.contactTypesService()
        .update(this.contactTypes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.contactTypes.updated', { param: param.id });
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
      this.contactTypesService()
        .create(this.contactTypes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.contactTypes.created', { param: param.id });
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

  public retrieveContactTypes(contactTypesId): void {
    this.contactTypesService()
      .find(contactTypesId)
      .then(res => {
        this.contactTypes = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
