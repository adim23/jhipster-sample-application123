import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IMaritalStatus, MaritalStatus } from '@/shared/model/marital-status.model';
import MaritalStatusService from './marital-status.service';

const validations: any = {
  maritalStatus: {
    name: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class MaritalStatusUpdate extends Vue {
  @Inject('maritalStatusService') private maritalStatusService: () => MaritalStatusService;
  @Inject('alertService') private alertService: () => AlertService;

  public maritalStatus: IMaritalStatus = new MaritalStatus();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.maritalStatusId) {
        vm.retrieveMaritalStatus(to.params.maritalStatusId);
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
    if (this.maritalStatus.id) {
      this.maritalStatusService()
        .update(this.maritalStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.maritalStatus.updated', { param: param.id });
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
      this.maritalStatusService()
        .create(this.maritalStatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.maritalStatus.created', { param: param.id });
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

  public retrieveMaritalStatus(maritalStatusId): void {
    this.maritalStatusService()
      .find(maritalStatusId)
      .then(res => {
        this.maritalStatus = res;
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
