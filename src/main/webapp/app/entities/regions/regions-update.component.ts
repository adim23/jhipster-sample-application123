import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import CountriesService from '@/entities/countries/countries.service';
import { ICountries } from '@/shared/model/countries.model';

import { IRegions, Regions } from '@/shared/model/regions.model';
import RegionsService from './regions.service';

const validations: any = {
  regions: {
    name: {
      required,
    },
    country: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class RegionsUpdate extends Vue {
  @Inject('regionsService') private regionsService: () => RegionsService;
  @Inject('alertService') private alertService: () => AlertService;

  public regions: IRegions = new Regions();

  @Inject('countriesService') private countriesService: () => CountriesService;

  public countries: ICountries[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.regionsId) {
        vm.retrieveRegions(to.params.regionsId);
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
    if (this.regions.id) {
      this.regionsService()
        .update(this.regions)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.regions.updated', { param: param.id });
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
      this.regionsService()
        .create(this.regions)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.regions.created', { param: param.id });
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

  public retrieveRegions(regionsId): void {
    this.regionsService()
      .find(regionsId)
      .then(res => {
        this.regions = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.countriesService()
      .retrieve()
      .then(res => {
        this.countries = res.data;
      });
  }
}
