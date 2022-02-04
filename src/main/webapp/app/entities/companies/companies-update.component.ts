import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import CompanyKindsService from '@/entities/company-kinds/company-kinds.service';
import { ICompanyKinds } from '@/shared/model/company-kinds.model';

import PhonesService from '@/entities/phones/phones.service';
import { IPhones } from '@/shared/model/phones.model';

import AddressesService from '@/entities/addresses/addresses.service';
import { IAddresses } from '@/shared/model/addresses.model';

import { ICompanies, Companies } from '@/shared/model/companies.model';
import CompaniesService from './companies.service';

const validations: any = {
  companies: {
    name: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class CompaniesUpdate extends Vue {
  @Inject('companiesService') private companiesService: () => CompaniesService;
  @Inject('alertService') private alertService: () => AlertService;

  public companies: ICompanies = new Companies();

  @Inject('companyKindsService') private companyKindsService: () => CompanyKindsService;

  public companyKinds: ICompanyKinds[] = [];

  @Inject('phonesService') private phonesService: () => PhonesService;

  public phones: IPhones[] = [];

  @Inject('addressesService') private addressesService: () => AddressesService;

  public addresses: IAddresses[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companiesId) {
        vm.retrieveCompanies(to.params.companiesId);
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
    if (this.companies.id) {
      this.companiesService()
        .update(this.companies)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.companies.updated', { param: param.id });
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
      this.companiesService()
        .create(this.companies)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.companies.created', { param: param.id });
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

  public retrieveCompanies(companiesId): void {
    this.companiesService()
      .find(companiesId)
      .then(res => {
        this.companies = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.companyKindsService()
      .retrieve()
      .then(res => {
        this.companyKinds = res.data;
      });
    this.phonesService()
      .retrieve()
      .then(res => {
        this.phones = res.data;
      });
    this.addressesService()
      .retrieve()
      .then(res => {
        this.addresses = res.data;
      });
  }
}
