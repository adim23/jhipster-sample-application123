import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import PhoneTypesService from '@/entities/phone-types/phone-types.service';
import { IPhoneTypes } from '@/shared/model/phone-types.model';

import CompaniesService from '@/entities/companies/companies.service';
import { ICompanies } from '@/shared/model/companies.model';

import CitizensService from '@/entities/citizens/citizens.service';
import { ICitizens } from '@/shared/model/citizens.model';

import { IPhones, Phones } from '@/shared/model/phones.model';
import PhonesService from './phones.service';

const validations: any = {
  phones: {
    phone: {
      required,
    },
    description: {},
    favourite: {},
  },
};

@Component({
  validations,
})
export default class PhonesUpdate extends Vue {
  @Inject('phonesService') private phonesService: () => PhonesService;
  @Inject('alertService') private alertService: () => AlertService;

  public phones: IPhones = new Phones();

  @Inject('phoneTypesService') private phoneTypesService: () => PhoneTypesService;

  public phoneTypes: IPhoneTypes[] = [];

  @Inject('companiesService') private companiesService: () => CompaniesService;

  public companies: ICompanies[] = [];

  @Inject('citizensService') private citizensService: () => CitizensService;

  public citizens: ICitizens[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.phonesId) {
        vm.retrievePhones(to.params.phonesId);
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
    if (this.phones.id) {
      this.phonesService()
        .update(this.phones)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.phones.updated', { param: param.id });
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
      this.phonesService()
        .create(this.phones)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.phones.created', { param: param.id });
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

  public retrievePhones(phonesId): void {
    this.phonesService()
      .find(phonesId)
      .then(res => {
        this.phones = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.phoneTypesService()
      .retrieve()
      .then(res => {
        this.phoneTypes = res.data;
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
