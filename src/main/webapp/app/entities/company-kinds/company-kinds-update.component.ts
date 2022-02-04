import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { ICompanyKinds, CompanyKinds } from '@/shared/model/company-kinds.model';
import CompanyKindsService from './company-kinds.service';

const validations: any = {
  companyKinds: {
    name: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class CompanyKindsUpdate extends Vue {
  @Inject('companyKindsService') private companyKindsService: () => CompanyKindsService;
  @Inject('alertService') private alertService: () => AlertService;

  public companyKinds: ICompanyKinds = new CompanyKinds();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyKindsId) {
        vm.retrieveCompanyKinds(to.params.companyKindsId);
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
    if (this.companyKinds.id) {
      this.companyKindsService()
        .update(this.companyKinds)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.companyKinds.updated', { param: param.id });
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
      this.companyKindsService()
        .create(this.companyKinds)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.companyKinds.created', { param: param.id });
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

  public retrieveCompanyKinds(companyKindsId): void {
    this.companyKindsService()
      .find(companyKindsId)
      .then(res => {
        this.companyKinds = res;
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
