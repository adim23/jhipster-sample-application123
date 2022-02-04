import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { ICitizenFolders, CitizenFolders } from '@/shared/model/citizen-folders.model';
import CitizenFoldersService from './citizen-folders.service';

const validations: any = {
  citizenFolders: {
    name: {
      required,
    },
    description: {},
    special: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class CitizenFoldersUpdate extends Vue {
  @Inject('citizenFoldersService') private citizenFoldersService: () => CitizenFoldersService;
  @Inject('alertService') private alertService: () => AlertService;

  public citizenFolders: ICitizenFolders = new CitizenFolders();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.citizenFoldersId) {
        vm.retrieveCitizenFolders(to.params.citizenFoldersId);
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
    if (this.citizenFolders.id) {
      this.citizenFoldersService()
        .update(this.citizenFolders)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.citizenFolders.updated', { param: param.id });
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
      this.citizenFoldersService()
        .create(this.citizenFolders)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.citizenFolders.created', { param: param.id });
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

  public retrieveCitizenFolders(citizenFoldersId): void {
    this.citizenFoldersService()
      .find(citizenFoldersId)
      .then(res => {
        this.citizenFolders = res;
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
