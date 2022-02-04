import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import CountriesService from '@/entities/countries/countries.service';
import { ICountries } from '@/shared/model/countries.model';

import ContactTypesService from '@/entities/contact-types/contact-types.service';
import { IContactTypes } from '@/shared/model/contact-types.model';

import RegionsService from '@/entities/regions/regions.service';
import { IRegions } from '@/shared/model/regions.model';

import CompaniesService from '@/entities/companies/companies.service';
import { ICompanies } from '@/shared/model/companies.model';

import CitizensService from '@/entities/citizens/citizens.service';
import { ICitizens } from '@/shared/model/citizens.model';

import { IAddresses, Addresses } from '@/shared/model/addresses.model';
import AddressesService from './addresses.service';

const validations: any = {
  addresses: {
    address: {},
    addressNo: {},
    zipCode: {},
    prosfLetter: {},
    nameLetter: {},
    letterClose: {},
    firstLabel: {},
    secondLabel: {},
    thirdLabel: {},
    fourthLabel: {},
    fifthLabel: {},
    favourite: {},
  },
};

@Component({
  validations,
})
export default class AddressesUpdate extends Vue {
  @Inject('addressesService') private addressesService: () => AddressesService;
  @Inject('alertService') private alertService: () => AlertService;

  public addresses: IAddresses = new Addresses();

  @Inject('countriesService') private countriesService: () => CountriesService;

  public countries: ICountries[] = [];

  @Inject('contactTypesService') private contactTypesService: () => ContactTypesService;

  public contactTypes: IContactTypes[] = [];

  @Inject('regionsService') private regionsService: () => RegionsService;

  public regions: IRegions[] = [];

  @Inject('companiesService') private companiesService: () => CompaniesService;

  public companies: ICompanies[] = [];

  @Inject('citizensService') private citizensService: () => CitizensService;

  public citizens: ICitizens[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.addressesId) {
        vm.retrieveAddresses(to.params.addressesId);
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
    if (this.addresses.id) {
      this.addressesService()
        .update(this.addresses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.addresses.updated', { param: param.id });
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
      this.addressesService()
        .create(this.addresses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.addresses.created', { param: param.id });
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

  public retrieveAddresses(addressesId): void {
    this.addressesService()
      .find(addressesId)
      .then(res => {
        this.addresses = res;
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
    this.contactTypesService()
      .retrieve()
      .then(res => {
        this.contactTypes = res.data;
      });
    this.regionsService()
      .retrieve()
      .then(res => {
        this.regions = res.data;
      });
    this.companiesService()
      .retrieve()
      .then(res => {
        this.companies = res.data;
      });
    this.citizensService()
      .retrieve()
      .then(res => {
        this.citizens = res.data;
      });
  }
}
