import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { ISocialKinds, SocialKinds } from '@/shared/model/social-kinds.model';
import SocialKindsService from './social-kinds.service';

const validations: any = {
  socialKinds: {
    code: {
      required,
    },
    name: {
      required,
    },
    call: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class SocialKindsUpdate extends Vue {
  @Inject('socialKindsService') private socialKindsService: () => SocialKindsService;
  @Inject('alertService') private alertService: () => AlertService;

  public socialKinds: ISocialKinds = new SocialKinds();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.socialKindsId) {
        vm.retrieveSocialKinds(to.params.socialKindsId);
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
    if (this.socialKinds.id) {
      this.socialKindsService()
        .update(this.socialKinds)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.socialKinds.updated', { param: param.id });
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
      this.socialKindsService()
        .create(this.socialKinds)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.socialKinds.created', { param: param.id });
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

  public retrieveSocialKinds(socialKindsId): void {
    this.socialKindsService()
      .find(socialKindsId)
      .then(res => {
        this.socialKinds = res;
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
