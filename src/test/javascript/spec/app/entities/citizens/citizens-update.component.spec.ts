/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CitizensUpdateComponent from '@/entities/citizens/citizens-update.vue';
import CitizensClass from '@/entities/citizens/citizens-update.component';
import CitizensService from '@/entities/citizens/citizens.service';

import CitizenFoldersService from '@/entities/citizen-folders/citizen-folders.service';

import CompaniesService from '@/entities/companies/companies.service';

import MaritalStatusService from '@/entities/marital-status/marital-status.service';

import TeamsService from '@/entities/teams/teams.service';

import CodesService from '@/entities/codes/codes.service';

import OriginsService from '@/entities/origins/origins.service';

import JobsService from '@/entities/jobs/jobs.service';

import PhonesService from '@/entities/phones/phones.service';

import AddressesService from '@/entities/addresses/addresses.service';

import SocialContactsService from '@/entities/social-contacts/social-contacts.service';

import EmailsService from '@/entities/emails/emails.service';

import CitizensRelationsService from '@/entities/citizens-relations/citizens-relations.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Citizens Management Update Component', () => {
    let wrapper: Wrapper<CitizensClass>;
    let comp: CitizensClass;
    let citizensServiceStub: SinonStubbedInstance<CitizensService>;

    beforeEach(() => {
      citizensServiceStub = sinon.createStubInstance<CitizensService>(CitizensService);

      wrapper = shallowMount<CitizensClass>(CitizensUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          citizensService: () => citizensServiceStub,
          alertService: () => new AlertService(),

          citizenFoldersService: () =>
            sinon.createStubInstance<CitizenFoldersService>(CitizenFoldersService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          companiesService: () =>
            sinon.createStubInstance<CompaniesService>(CompaniesService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          maritalStatusService: () =>
            sinon.createStubInstance<MaritalStatusService>(MaritalStatusService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          teamsService: () =>
            sinon.createStubInstance<TeamsService>(TeamsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          codesService: () =>
            sinon.createStubInstance<CodesService>(CodesService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          originsService: () =>
            sinon.createStubInstance<OriginsService>(OriginsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          jobsService: () =>
            sinon.createStubInstance<JobsService>(JobsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          phonesService: () =>
            sinon.createStubInstance<PhonesService>(PhonesService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          addressesService: () =>
            sinon.createStubInstance<AddressesService>(AddressesService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          socialContactsService: () =>
            sinon.createStubInstance<SocialContactsService>(SocialContactsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          emailsService: () =>
            sinon.createStubInstance<EmailsService>(EmailsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          citizensRelationsService: () =>
            sinon.createStubInstance<CitizensRelationsService>(CitizensRelationsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.citizens = entity;
        citizensServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(citizensServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.citizens = entity;
        citizensServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(citizensServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCitizens = { id: 123 };
        citizensServiceStub.find.resolves(foundCitizens);
        citizensServiceStub.retrieve.resolves([foundCitizens]);

        // WHEN
        comp.beforeRouteEnter({ params: { citizensId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.citizens).toBe(foundCitizens);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
