import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import CountriesService from '@/entities/countries/countries.service';
import { ICountries } from '@/shared/model/countries.model';

import RegionsService from '@/entities/regions/regions.service';
import { IRegions } from '@/shared/model/regions.model';

import CitiesService from '@/entities/cities/cities.service';
import { ICities } from '@/shared/model/cities.model';

import { IZipCodes, ZipCodes } from '@/shared/model/zip-codes.model';
import ZipCodesService from './zip-codes.service';

const validations: any = {
  zipCodes: {
    street: {},
    area: {},
    fromNumber: {},
    toNumber: {},
  },
};

@Component({
  validations,
})
export default class ZipCodesUpdate extends Vue {
  @Inject('zipCodesService') private zipCodesService: () => ZipCodesService;
  @Inject('alertService') private alertService: () => AlertService;

  public zipCodes: IZipCodes = new ZipCodes();

  @Inject('countriesService') private countriesService: () => CountriesService;

  public countries: ICountries[] = [];

  @Inject('regionsService') private regionsService: () => RegionsService;

  public regions: IRegions[] = [];

  @Inject('citiesService') private citiesService: () => CitiesService;

  public cities: ICities[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.zipCodesId) {
        vm.retrieveZipCodes(to.params.zipCodesId);
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
    if (this.zipCodes.id) {
      this.zipCodesService()
        .update(this.zipCodes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.zipCodes.updated', { param: param.id });
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
      this.zipCodesService()
        .create(this.zipCodes)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.zipCodes.created', { param: param.id });
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

  public retrieveZipCodes(zipCodesId): void {
    this.zipCodesService()
      .find(zipCodesId)
      .then(res => {
        this.zipCodes = res;
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
    this.citiesService()
      .retrieve()
      .then(res => {
        this.cities = res.data;
      });
  }
}
