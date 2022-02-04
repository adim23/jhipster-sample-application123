import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import SocialKindsService from '@/entities/social-kinds/social-kinds.service';
import { ISocialKinds } from '@/shared/model/social-kinds.model';

import ContactTypesService from '@/entities/contact-types/contact-types.service';
import { IContactTypes } from '@/shared/model/contact-types.model';

import CitizensService from '@/entities/citizens/citizens.service';
import { ICitizens } from '@/shared/model/citizens.model';

import { ISocialContacts, SocialContacts } from '@/shared/model/social-contacts.model';
import SocialContactsService from './social-contacts.service';

const validations: any = {
  socialContacts: {
    name: {
      required,
    },
    favored: {},
  },
};

@Component({
  validations,
})
export default class SocialContactsUpdate extends Vue {
  @Inject('socialContactsService') private socialContactsService: () => SocialContactsService;
  @Inject('alertService') private alertService: () => AlertService;

  public socialContacts: ISocialContacts = new SocialContacts();

  @Inject('socialKindsService') private socialKindsService: () => SocialKindsService;

  public socialKinds: ISocialKinds[] = [];

  @Inject('contactTypesService') private contactTypesService: () => ContactTypesService;

  public contactTypes: IContactTypes[] = [];

  @Inject('citizensService') private citizensService: () => CitizensService;

  public citizens: ICitizens[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.socialContactsId) {
        vm.retrieveSocialContacts(to.params.socialContactsId);
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
    if (this.socialContacts.id) {
      this.socialContactsService()
        .update(this.socialContacts)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.socialContacts.updated', { param: param.id });
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
      this.socialContactsService()
        .create(this.socialContacts)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.socialContacts.created', { param: param.id });
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

  public retrieveSocialContacts(socialContactsId): void {
    this.socialContactsService()
      .find(socialContactsId)
      .then(res => {
        this.socialContacts = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.socialKindsService()
      .retrieve()
      .then(res => {
        this.socialKinds = res.data;
      });
    this.contactTypesService()
      .retrieve()
      .then(res => {
        this.contactTypes = res.data;
      });
    this.citizensService()
      .retrieve()
      .then(res => {
        this.citizens = res.data;
      });
  }
}
