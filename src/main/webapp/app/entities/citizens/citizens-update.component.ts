import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import AlertService from '@/shared/alert/alert.service';

import CitizenFoldersService from '@/entities/citizen-folders/citizen-folders.service';
import { ICitizenFolders } from '@/shared/model/citizen-folders.model';

import CompaniesService from '@/entities/companies/companies.service';
import { ICompanies } from '@/shared/model/companies.model';

import MaritalStatusService from '@/entities/marital-status/marital-status.service';
import { IMaritalStatus } from '@/shared/model/marital-status.model';

import TeamsService from '@/entities/teams/teams.service';
import { ITeams } from '@/shared/model/teams.model';

import CodesService from '@/entities/codes/codes.service';
import { ICodes } from '@/shared/model/codes.model';

import OriginsService from '@/entities/origins/origins.service';
import { IOrigins } from '@/shared/model/origins.model';

import JobsService from '@/entities/jobs/jobs.service';
import { IJobs } from '@/shared/model/jobs.model';

import PhonesService from '@/entities/phones/phones.service';
import { IPhones } from '@/shared/model/phones.model';

import AddressesService from '@/entities/addresses/addresses.service';
import { IAddresses } from '@/shared/model/addresses.model';

import SocialContactsService from '@/entities/social-contacts/social-contacts.service';
import { ISocialContacts } from '@/shared/model/social-contacts.model';

import EmailsService from '@/entities/emails/emails.service';
import { IEmails } from '@/shared/model/emails.model';

import CitizensRelationsService from '@/entities/citizens-relations/citizens-relations.service';
import { ICitizensRelations } from '@/shared/model/citizens-relations.model';

import { ICitizens, Citizens } from '@/shared/model/citizens.model';
import CitizensService from './citizens.service';

const validations: any = {
  citizens: {
    title: {},
    lastname: {},
    firstname: {},
    fathersName: {},
    comments: {},
    birthDate: {},
    giortazi: {},
    male: {},
    meLetter: {},
    meLabel: {},
    image: {},
  },
};

@Component({
  validations,
})
export default class CitizensUpdate extends mixins(JhiDataUtils) {
  @Inject('citizensService') private citizensService: () => CitizensService;
  @Inject('alertService') private alertService: () => AlertService;

  public citizens: ICitizens = new Citizens();

  @Inject('citizenFoldersService') private citizenFoldersService: () => CitizenFoldersService;

  public citizenFolders: ICitizenFolders[] = [];

  @Inject('companiesService') private companiesService: () => CompaniesService;

  public companies: ICompanies[] = [];

  @Inject('maritalStatusService') private maritalStatusService: () => MaritalStatusService;

  public maritalStatuses: IMaritalStatus[] = [];

  @Inject('teamsService') private teamsService: () => TeamsService;

  public teams: ITeams[] = [];

  @Inject('codesService') private codesService: () => CodesService;

  public codes: ICodes[] = [];

  @Inject('originsService') private originsService: () => OriginsService;

  public origins: IOrigins[] = [];

  @Inject('jobsService') private jobsService: () => JobsService;

  public jobs: IJobs[] = [];

  @Inject('phonesService') private phonesService: () => PhonesService;

  public phones: IPhones[] = [];

  @Inject('addressesService') private addressesService: () => AddressesService;

  public addresses: IAddresses[] = [];

  @Inject('socialContactsService') private socialContactsService: () => SocialContactsService;

  public socialContacts: ISocialContacts[] = [];

  @Inject('emailsService') private emailsService: () => EmailsService;

  public emails: IEmails[] = [];

  @Inject('citizensRelationsService') private citizensRelationsService: () => CitizensRelationsService;

  public citizensRelations: ICitizensRelations[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.citizensId) {
        vm.retrieveCitizens(to.params.citizensId);
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
    if (this.citizens.id) {
      this.citizensService()
        .update(this.citizens)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.citizens.updated', { param: param.id });
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
      this.citizensService()
        .create(this.citizens)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhipsterSampleApplication123App.citizens.created', { param: param.id });
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

  public retrieveCitizens(citizensId): void {
    this.citizensService()
      .find(citizensId)
      .then(res => {
        this.citizens = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public clearInputImage(field, fieldContentType, idInput): void {
    if (this.citizens && field && fieldContentType) {
      if (Object.prototype.hasOwnProperty.call(this.citizens, field)) {
        this.citizens[field] = null;
      }
      if (Object.prototype.hasOwnProperty.call(this.citizens, fieldContentType)) {
        this.citizens[fieldContentType] = null;
      }
      if (idInput) {
        (<any>this).$refs[idInput] = null;
      }
    }
  }

  public initRelationships(): void {
    this.citizenFoldersService()
      .retrieve()
      .then(res => {
        this.citizenFolders = res.data;
      });
    this.companiesService()
      .retrieve()
      .then(res => {
        this.companies = res.data;
      });
    this.maritalStatusService()
      .retrieve()
      .then(res => {
        this.maritalStatuses = res.data;
      });
    this.teamsService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
    this.codesService()
      .retrieve()
      .then(res => {
        this.codes = res.data;
      });
    this.originsService()
      .retrieve()
      .then(res => {
        this.origins = res.data;
      });
    this.jobsService()
      .retrieve()
      .then(res => {
        this.jobs = res.data;
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
    this.socialContactsService()
      .retrieve()
      .then(res => {
        this.socialContacts = res.data;
      });
    this.emailsService()
      .retrieve()
      .then(res => {
        this.emails = res.data;
      });
    this.citizensRelationsService()
      .retrieve()
      .then(res => {
        this.citizensRelations = res.data;
      });
  }
}
