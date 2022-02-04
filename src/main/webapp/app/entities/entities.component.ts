import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import JobsService from './jobs/jobs.service';
import CountriesService from './countries/countries.service';
import RegionsService from './regions/regions.service';
import CitiesService from './cities/cities.service';
import ZipCodesService from './zip-codes/zip-codes.service';
import AddressesService from './addresses/addresses.service';
import PhonesService from './phones/phones.service';
import EmailsService from './emails/emails.service';
import PhoneTypesService from './phone-types/phone-types.service';
import ContactTypesService from './contact-types/contact-types.service';
import SocialKindsService from './social-kinds/social-kinds.service';
import SocialContactsService from './social-contacts/social-contacts.service';
import CompanyKindsService from './company-kinds/company-kinds.service';
import CompaniesService from './companies/companies.service';
import CodesService from './codes/codes.service';
import TeamsService from './teams/teams.service';
import OriginsService from './origins/origins.service';
import CitizenFoldersService from './citizen-folders/citizen-folders.service';
import MaritalStatusService from './marital-status/marital-status.service';
import CitizensService from './citizens/citizens.service';
import CitizensRelationsService from './citizens-relations/citizens-relations.service';
import CitizensMeetingsService from './citizens-meetings/citizens-meetings.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('jobsService') private jobsService = () => new JobsService();
  @Provide('countriesService') private countriesService = () => new CountriesService();
  @Provide('regionsService') private regionsService = () => new RegionsService();
  @Provide('citiesService') private citiesService = () => new CitiesService();
  @Provide('zipCodesService') private zipCodesService = () => new ZipCodesService();
  @Provide('addressesService') private addressesService = () => new AddressesService();
  @Provide('phonesService') private phonesService = () => new PhonesService();
  @Provide('emailsService') private emailsService = () => new EmailsService();
  @Provide('phoneTypesService') private phoneTypesService = () => new PhoneTypesService();
  @Provide('contactTypesService') private contactTypesService = () => new ContactTypesService();
  @Provide('socialKindsService') private socialKindsService = () => new SocialKindsService();
  @Provide('socialContactsService') private socialContactsService = () => new SocialContactsService();
  @Provide('companyKindsService') private companyKindsService = () => new CompanyKindsService();
  @Provide('companiesService') private companiesService = () => new CompaniesService();
  @Provide('codesService') private codesService = () => new CodesService();
  @Provide('teamsService') private teamsService = () => new TeamsService();
  @Provide('originsService') private originsService = () => new OriginsService();
  @Provide('citizenFoldersService') private citizenFoldersService = () => new CitizenFoldersService();
  @Provide('maritalStatusService') private maritalStatusService = () => new MaritalStatusService();
  @Provide('citizensService') private citizensService = () => new CitizensService();
  @Provide('citizensRelationsService') private citizensRelationsService = () => new CitizensRelationsService();
  @Provide('citizensMeetingsService') private citizensMeetingsService = () => new CitizensMeetingsService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
