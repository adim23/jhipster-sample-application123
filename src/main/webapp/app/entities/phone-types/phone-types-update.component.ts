import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IPhoneTypes, PhoneTypes } from '@/shared/model/phone-types.model';
import PhoneTypesService from './phone-types.service';

const validations: any = {
  phoneTypes: {
    name: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class PhoneTypesUpdate extends Vue {
  @Inject('phoneTypesService') private phoneTypesService: () => PhoneTypesService;
  @Inject('alertService') private alertService: () => AlertService;

  public phoneTypes: IPhoneTypes = new PhoneTypes();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.phoneTypesId) {
        vm.retrievePhoneTypes(to.params.phoneTypesId);
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
    if (this.phoneTypes.id) {
      this.phoneTypesService()
        .update(this.phoneTypes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.phoneTypes.updated', { param: param.id });
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
      this.phoneTypesService()
        .create(this.phoneTypes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.phoneTypes.created', { param: param.id });
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

  public retrievePhoneTypes(phoneTypesId): void {
    this.phoneTypesService()
      .find(phoneTypesId)
      .then(res => {
        this.phoneTypes = res;
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
