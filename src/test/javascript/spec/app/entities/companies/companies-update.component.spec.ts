/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CompaniesUpdateComponent from '@/entities/companies/companies-update.vue';
import CompaniesClass from '@/entities/companies/companies-update.component';
import CompaniesService from '@/entities/companies/companies.service';

import CompanyKindsService from '@/entities/company-kinds/company-kinds.service';

import PhonesService from '@/entities/phones/phones.service';

import AddressesService from '@/entities/addresses/addresses.service';
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
  describe('Companies Management Update Component', () => {
    let wrapper: Wrapper<CompaniesClass>;
    let comp: CompaniesClass;
    let companiesServiceStub: SinonStubbedInstance<CompaniesService>;

    beforeEach(() => {
      companiesServiceStub = sinon.createStubInstance<CompaniesService>(CompaniesService);

      wrapper = shallowMount<CompaniesClass>(CompaniesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          companiesService: () => companiesServiceStub,
          alertService: () => new AlertService(),

          companyKindsService: () =>
            sinon.createStubInstance<CompanyKindsService>(CompanyKindsService, {
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
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.companies = entity;
        companiesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(companiesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.companies = entity;
        companiesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(companiesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCompanies = { id: 123 };
        companiesServiceStub.find.resolves(foundCompanies);
        companiesServiceStub.retrieve.resolves([foundCompanies]);

        // WHEN
        comp.beforeRouteEnter({ params: { companiesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.companies).toBe(foundCompanies);
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
