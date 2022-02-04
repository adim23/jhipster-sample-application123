import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import CountriesService from '@/entities/countries/countries.service';
import { ICountries } from '@/shared/model/countries.model';

import RegionsService from '@/entities/regions/regions.service';
import { IRegions } from '@/shared/model/regions.model';

import { ICities, Cities } from '@/shared/model/cities.model';
import CitiesService from './cities.service';

const validations: any = {
  cities: {
    name: {
      required,
    },
    president: {},
    presidentsPhone: {},
    secretary: {},
    secretarysPhone: {},
    police: {},
    policesPhone: {},
    doctor: {},
    doctorsPhone: {},
    teacher: {},
    teachersPhone: {},
    priest: {},
    priestsPhone: {},
    country: {
      required,
    },
    region: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class CitiesUpdate extends Vue {
  @Inject('citiesService') private citiesService: () => CitiesService;
  @Inject('alertService') private alertService: () => AlertService;

  public cities: ICities = new Cities();

  @Inject('countriesService') private countriesService: () => CountriesService;

  public countries: ICountries[] = [];

  @Inject('regionsService') private regionsService: () => RegionsService;

  public regions: IRegions[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.citiesId) {
        vm.retrieveCities(to.params.citiesId);
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
    if (this.cities.id) {
      this.citiesService()
        .update(this.cities)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.cities.updated', { param: param.id });
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
      this.citiesService()
        .create(this.cities)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.cities.created', { param: param.id });
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

  public retrieveCities(citiesId): void {
    this.citiesService()
      .find(citiesId)
      .then(res => {
        this.cities = res;
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
    this.regionsService()
      .retrieve()
      .then(res => {
        this.regions = res.data;
      });
  }
}
