import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import CitizensService from '@/entities/citizens/citizens.service';
import { ICitizens } from '@/shared/model/citizens.model';

import { ICitizensRelations, CitizensRelations } from '@/shared/model/citizens-relations.model';
import CitizensRelationsService from './citizens-relations.service';

const validations: any = {
  citizensRelations: {
    name: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class CitizensRelationsUpdate extends Vue {
  @Inject('citizensRelationsService') private citizensRelationsService: () => CitizensRelationsService;
  @Inject('alertService') private alertService: () => AlertService;

  public citizensRelations: ICitizensRelations = new CitizensRelations();

  @Inject('citizensService') private citizensService: () => CitizensService;

  public citizens: ICitizens[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.citizensRelationsId) {
        vm.retrieveCitizensRelations(to.params.citizensRelationsId);
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
    if (this.citizensRelations.id) {
      this.citizensRelationsService()
        .update(this.citizensRelations)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.citizensRelations.updated', { param: param.id });
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
      this.citizensRelationsService()
        .create(this.citizensRelations)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.citizensRelations.created', { param: param.id });
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

  public retrieveCitizensRelations(citizensRelationsId): void {
    this.citizensRelationsService()
      .find(citizensRelationsId)
      .then(res => {
        this.citizensRelations = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.citizensService()
      .retrieve()
      .then(res => {
        this.citizens = res.data;
      });
  }
}
