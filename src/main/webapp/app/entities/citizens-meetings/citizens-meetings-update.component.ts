import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import CitizensService from '@/entities/citizens/citizens.service';
import { ICitizens } from '@/shared/model/citizens.model';

import { ICitizensMeetings, CitizensMeetings } from '@/shared/model/citizens-meetings.model';
import CitizensMeetingsService from './citizens-meetings.service';

const validations: any = {
  citizensMeetings: {
    meetDate: {
      required,
    },
    agenda: {
      required,
    },
    comments: {},
    amount: {},
    quantity: {},
    status: {
      required,
    },
    flag: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class CitizensMeetingsUpdate extends mixins(JhiDataUtils) {
  @Inject('citizensMeetingsService') private citizensMeetingsService: () => CitizensMeetingsService;
  @Inject('alertService') private alertService: () => AlertService;

  public citizensMeetings: ICitizensMeetings = new CitizensMeetings();

  @Inject('citizensService') private citizensService: () => CitizensService;

  public citizens: ICitizens[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.citizensMeetingsId) {
        vm.retrieveCitizensMeetings(to.params.citizensMeetingsId);
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
    if (this.citizensMeetings.id) {
      this.citizensMeetingsService()
        .update(this.citizensMeetings)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.citizensMeetings.updated', { param: param.id });
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
      this.citizensMeetingsService()
        .create(this.citizensMeetings)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.citizensMeetings.created', { param: param.id });
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

  public retrieveCitizensMeetings(citizensMeetingsId): void {
    this.citizensMeetingsService()
      .find(citizensMeetingsId)
      .then(res => {
        this.citizensMeetings = res;
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
