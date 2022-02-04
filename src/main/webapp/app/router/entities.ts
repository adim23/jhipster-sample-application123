import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Jobs = () => import('@/entities/jobs/jobs.vue');
// prettier-ignore
const JobsUpdate = () => import('@/entities/jobs/jobs-update.vue');
// prettier-ignore
const JobsDetails = () => import('@/entities/jobs/jobs-details.vue');
// prettier-ignore
const Countries = () => import('@/entities/countries/countries.vue');
// prettier-ignore
const CountriesUpdate = () => import('@/entities/countries/countries-update.vue');
// prettier-ignore
const CountriesDetails = () => import('@/entities/countries/countries-details.vue');
// prettier-ignore
const Regions = () => import('@/entities/regions/regions.vue');
// prettier-ignore
const RegionsUpdate = () => import('@/entities/regions/regions-update.vue');
// prettier-ignore
const RegionsDetails = () => import('@/entities/regions/regions-details.vue');
// prettier-ignore
const Cities = () => import('@/entities/cities/cities.vue');
// prettier-ignore
const CitiesUpdate = () => import('@/entities/cities/cities-update.vue');
// prettier-ignore
const CitiesDetails = () => import('@/entities/cities/cities-details.vue');
// prettier-ignore
const ZipCodes = () => import('@/entities/zip-codes/zip-codes.vue');
// prettier-ignore
const ZipCodesUpdate = () => import('@/entities/zip-codes/zip-codes-update.vue');
// prettier-ignore
const ZipCodesDetails = () => import('@/entities/zip-codes/zip-codes-details.vue');
// prettier-ignore
const Addresses = () => import('@/entities/addresses/addresses.vue');
// prettier-ignore
const AddressesUpdate = () => import('@/entities/addresses/addresses-update.vue');
// prettier-ignore
const AddressesDetails = () => import('@/entities/addresses/addresses-details.vue');
// prettier-ignore
const Phones = () => import('@/entities/phones/phones.vue');
// prettier-ignore
const PhonesUpdate = () => import('@/entities/phones/phones-update.vue');
// prettier-ignore
const PhonesDetails = () => import('@/entities/phones/phones-details.vue');
// prettier-ignore
const Emails = () => import('@/entities/emails/emails.vue');
// prettier-ignore
const EmailsUpdate = () => import('@/entities/emails/emails-update.vue');
// prettier-ignore
const EmailsDetails = () => import('@/entities/emails/emails-details.vue');
// prettier-ignore
const PhoneTypes = () => import('@/entities/phone-types/phone-types.vue');
// prettier-ignore
const PhoneTypesUpdate = () => import('@/entities/phone-types/phone-types-update.vue');
// prettier-ignore
const PhoneTypesDetails = () => import('@/entities/phone-types/phone-types-details.vue');
// prettier-ignore
const ContactTypes = () => import('@/entities/contact-types/contact-types.vue');
// prettier-ignore
const ContactTypesUpdate = () => import('@/entities/contact-types/contact-types-update.vue');
// prettier-ignore
const ContactTypesDetails = () => import('@/entities/contact-types/contact-types-details.vue');
// prettier-ignore
const SocialKinds = () => import('@/entities/social-kinds/social-kinds.vue');
// prettier-ignore
const SocialKindsUpdate = () => import('@/entities/social-kinds/social-kinds-update.vue');
// prettier-ignore
const SocialKindsDetails = () => import('@/entities/social-kinds/social-kinds-details.vue');
// prettier-ignore
const SocialContacts = () => import('@/entities/social-contacts/social-contacts.vue');
// prettier-ignore
const SocialContactsUpdate = () => import('@/entities/social-contacts/social-contacts-update.vue');
// prettier-ignore
const SocialContactsDetails = () => import('@/entities/social-contacts/social-contacts-details.vue');
// prettier-ignore
const CompanyKinds = () => import('@/entities/company-kinds/company-kinds.vue');
// prettier-ignore
const CompanyKindsUpdate = () => import('@/entities/company-kinds/company-kinds-update.vue');
// prettier-ignore
const CompanyKindsDetails = () => import('@/entities/company-kinds/company-kinds-details.vue');
// prettier-ignore
const Companies = () => import('@/entities/companies/companies.vue');
// prettier-ignore
const CompaniesUpdate = () => import('@/entities/companies/companies-update.vue');
// prettier-ignore
const CompaniesDetails = () => import('@/entities/companies/companies-details.vue');
// prettier-ignore
const Codes = () => import('@/entities/codes/codes.vue');
// prettier-ignore
const CodesUpdate = () => import('@/entities/codes/codes-update.vue');
// prettier-ignore
const CodesDetails = () => import('@/entities/codes/codes-details.vue');
// prettier-ignore
const Teams = () => import('@/entities/teams/teams.vue');
// prettier-ignore
const TeamsUpdate = () => import('@/entities/teams/teams-update.vue');
// prettier-ignore
const TeamsDetails = () => import('@/entities/teams/teams-details.vue');
// prettier-ignore
const Origins = () => import('@/entities/origins/origins.vue');
// prettier-ignore
const OriginsUpdate = () => import('@/entities/origins/origins-update.vue');
// prettier-ignore
const OriginsDetails = () => import('@/entities/origins/origins-details.vue');
// prettier-ignore
const CitizenFolders = () => import('@/entities/citizen-folders/citizen-folders.vue');
// prettier-ignore
const CitizenFoldersUpdate = () => import('@/entities/citizen-folders/citizen-folders-update.vue');
// prettier-ignore
const CitizenFoldersDetails = () => import('@/entities/citizen-folders/citizen-folders-details.vue');
// prettier-ignore
const MaritalStatus = () => import('@/entities/marital-status/marital-status.vue');
// prettier-ignore
const MaritalStatusUpdate = () => import('@/entities/marital-status/marital-status-update.vue');
// prettier-ignore
const MaritalStatusDetails = () => import('@/entities/marital-status/marital-status-details.vue');
// prettier-ignore
const Citizens = () => import('@/entities/citizens/citizens.vue');
// prettier-ignore
const CitizensUpdate = () => import('@/entities/citizens/citizens-update.vue');
// prettier-ignore
const CitizensDetails = () => import('@/entities/citizens/citizens-details.vue');
// prettier-ignore
const CitizensRelations = () => import('@/entities/citizens-relations/citizens-relations.vue');
// prettier-ignore
const CitizensRelationsUpdate = () => import('@/entities/citizens-relations/citizens-relations-update.vue');
// prettier-ignore
const CitizensRelationsDetails = () => import('@/entities/citizens-relations/citizens-relations-details.vue');
// prettier-ignore
const CitizensMeetings = () => import('@/entities/citizens-meetings/citizens-meetings.vue');
// prettier-ignore
const CitizensMeetingsUpdate = () => import('@/entities/citizens-meetings/citizens-meetings-update.vue');
// prettier-ignore
const CitizensMeetingsDetails = () => import('@/entities/citizens-meetings/citizens-meetings-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'jobs',
      name: 'Jobs',
      component: Jobs,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'jobs/new',
      name: 'JobsCreate',
      component: JobsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'jobs/:jobsId/edit',
      name: 'JobsEdit',
      component: JobsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'jobs/:jobsId/view',
      name: 'JobsView',
      component: JobsDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'countries',
      name: 'Countries',
      component: Countries,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'countries/new',
      name: 'CountriesCreate',
      component: CountriesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'countries/:countriesId/edit',
      name: 'CountriesEdit',
      component: CountriesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'countries/:countriesId/view',
      name: 'CountriesView',
      component: CountriesDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regions',
      name: 'Regions',
      component: Regions,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regions/new',
      name: 'RegionsCreate',
      component: RegionsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regions/:regionsId/edit',
      name: 'RegionsEdit',
      component: RegionsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regions/:regionsId/view',
      name: 'RegionsView',
      component: RegionsDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cities',
      name: 'Cities',
      component: Cities,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cities/new',
      name: 'CitiesCreate',
      component: CitiesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cities/:citiesId/edit',
      name: 'CitiesEdit',
      component: CitiesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cities/:citiesId/view',
      name: 'CitiesView',
      component: CitiesDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'zip-codes',
      name: 'ZipCodes',
      component: ZipCodes,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'zip-codes/new',
      name: 'ZipCodesCreate',
      component: ZipCodesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'zip-codes/:zipCodesId/edit',
      name: 'ZipCodesEdit',
      component: ZipCodesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'zip-codes/:zipCodesId/view',
      name: 'ZipCodesView',
      component: ZipCodesDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'addresses',
      name: 'Addresses',
      component: Addresses,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'addresses/new',
      name: 'AddressesCreate',
      component: AddressesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'addresses/:addressesId/edit',
      name: 'AddressesEdit',
      component: AddressesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'addresses/:addressesId/view',
      name: 'AddressesView',
      component: AddressesDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'phones',
      name: 'Phones',
      component: Phones,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'phones/new',
      name: 'PhonesCreate',
      component: PhonesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'phones/:phonesId/edit',
      name: 'PhonesEdit',
      component: PhonesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'phones/:phonesId/view',
      name: 'PhonesView',
      component: PhonesDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'emails',
      name: 'Emails',
      component: Emails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'emails/new',
      name: 'EmailsCreate',
      component: EmailsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'emails/:emailsId/edit',
      name: 'EmailsEdit',
      component: EmailsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'emails/:emailsId/view',
      name: 'EmailsView',
      component: EmailsDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'phone-types',
      name: 'PhoneTypes',
      component: PhoneTypes,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'phone-types/new',
      name: 'PhoneTypesCreate',
      component: PhoneTypesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'phone-types/:phoneTypesId/edit',
      name: 'PhoneTypesEdit',
      component: PhoneTypesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'phone-types/:phoneTypesId/view',
      name: 'PhoneTypesView',
      component: PhoneTypesDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'contact-types',
      name: 'ContactTypes',
      component: ContactTypes,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'contact-types/new',
      name: 'ContactTypesCreate',
      component: ContactTypesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'contact-types/:contactTypesId/edit',
      name: 'ContactTypesEdit',
      component: ContactTypesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'contact-types/:contactTypesId/view',
      name: 'ContactTypesView',
      component: ContactTypesDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'social-kinds',
      name: 'SocialKinds',
      component: SocialKinds,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'social-kinds/new',
      name: 'SocialKindsCreate',
      component: SocialKindsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'social-kinds/:socialKindsId/edit',
      name: 'SocialKindsEdit',
      component: SocialKindsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'social-kinds/:socialKindsId/view',
      name: 'SocialKindsView',
      component: SocialKindsDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'social-contacts',
      name: 'SocialContacts',
      component: SocialContacts,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'social-contacts/new',
      name: 'SocialContactsCreate',
      component: SocialContactsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'social-contacts/:socialContactsId/edit',
      name: 'SocialContactsEdit',
      component: SocialContactsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'social-contacts/:socialContactsId/view',
      name: 'SocialContactsView',
      component: SocialContactsDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'company-kinds',
      name: 'CompanyKinds',
      component: CompanyKinds,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'company-kinds/new',
      name: 'CompanyKindsCreate',
      component: CompanyKindsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'company-kinds/:companyKindsId/edit',
      name: 'CompanyKindsEdit',
      component: CompanyKindsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'company-kinds/:companyKindsId/view',
      name: 'CompanyKindsView',
      component: CompanyKindsDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'companies',
      name: 'Companies',
      component: Companies,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'companies/new',
      name: 'CompaniesCreate',
      component: CompaniesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'companies/:companiesId/edit',
      name: 'CompaniesEdit',
      component: CompaniesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'companies/:companiesId/view',
      name: 'CompaniesView',
      component: CompaniesDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'codes',
      name: 'Codes',
      component: Codes,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'codes/new',
      name: 'CodesCreate',
      component: CodesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'codes/:codesId/edit',
      name: 'CodesEdit',
      component: CodesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'codes/:codesId/view',
      name: 'CodesView',
      component: CodesDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'teams',
      name: 'Teams',
      component: Teams,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'teams/new',
      name: 'TeamsCreate',
      component: TeamsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'teams/:teamsId/edit',
      name: 'TeamsEdit',
      component: TeamsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'teams/:teamsId/view',
      name: 'TeamsView',
      component: TeamsDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'origins',
      name: 'Origins',
      component: Origins,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'origins/new',
      name: 'OriginsCreate',
      component: OriginsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'origins/:originsId/edit',
      name: 'OriginsEdit',
      component: OriginsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'origins/:originsId/view',
      name: 'OriginsView',
      component: OriginsDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizen-folders',
      name: 'CitizenFolders',
      component: CitizenFolders,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizen-folders/new',
      name: 'CitizenFoldersCreate',
      component: CitizenFoldersUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizen-folders/:citizenFoldersId/edit',
      name: 'CitizenFoldersEdit',
      component: CitizenFoldersUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizen-folders/:citizenFoldersId/view',
      name: 'CitizenFoldersView',
      component: CitizenFoldersDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'marital-status',
      name: 'MaritalStatus',
      component: MaritalStatus,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'marital-status/new',
      name: 'MaritalStatusCreate',
      component: MaritalStatusUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'marital-status/:maritalStatusId/edit',
      name: 'MaritalStatusEdit',
      component: MaritalStatusUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'marital-status/:maritalStatusId/view',
      name: 'MaritalStatusView',
      component: MaritalStatusDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizens',
      name: 'Citizens',
      component: Citizens,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizens/new',
      name: 'CitizensCreate',
      component: CitizensUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizens/:citizensId/edit',
      name: 'CitizensEdit',
      component: CitizensUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizens/:citizensId/view',
      name: 'CitizensView',
      component: CitizensDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizens-relations',
      name: 'CitizensRelations',
      component: CitizensRelations,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizens-relations/new',
      name: 'CitizensRelationsCreate',
      component: CitizensRelationsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizens-relations/:citizensRelationsId/edit',
      name: 'CitizensRelationsEdit',
      component: CitizensRelationsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizens-relations/:citizensRelationsId/view',
      name: 'CitizensRelationsView',
      component: CitizensRelationsDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizens-meetings',
      name: 'CitizensMeetings',
      component: CitizensMeetings,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizens-meetings/new',
      name: 'CitizensMeetingsCreate',
      component: CitizensMeetingsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizens-meetings/:citizensMeetingsId/edit',
      name: 'CitizensMeetingsEdit',
      component: CitizensMeetingsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'citizens-meetings/:citizensMeetingsId/view',
      name: 'CitizensMeetingsView',
      component: CitizensMeetingsDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
