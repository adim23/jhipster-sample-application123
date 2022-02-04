/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AddressesUpdateComponent from '@/entities/addresses/addresses-update.vue';
import AddressesClass from '@/entities/addresses/addresses-update.component';
import AddressesService from '@/entities/addresses/addresses.service';

import CountriesService from '@/entities/countries/countries.service';

import ContactTypesService from '@/entities/contact-types/contact-types.service';

import RegionsService from '@/entities/regions/regions.service';

import CompaniesService from '@/entities/companies/companies.service';

import CitizensService from '@/entities/citizens/citizens.service';
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
  describe('Addresses Management Update Component', () => {
    let wrapper: Wrapper<AddressesClass>;
    let comp: AddressesClass;
    let addressesServiceStub: SinonStubbedInstance<AddressesService>;

    beforeEach(() => {
      addressesServiceStub = sinon.createStubInstance<AddressesService>(AddressesService);

      wrapper = shallowMount<AddressesClass>(AddressesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          addressesService: () => addressesServiceStub,
          alertService: () => new AlertService(),

          countriesService: () =>
            sinon.createStubInstance<CountriesService>(CountriesService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          contactTypesService: () =>
            sinon.createStubInstance<ContactTypesService>(ContactTypesService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          regionsService: () =>
            sinon.createStubInstance<RegionsService>(RegionsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          companiesService: () =>
            sinon.createStubInstance<CompaniesService>(CompaniesService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          citizensService: () =>
            sinon.createStubInstance<CitizensService>(CitizensService, {
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
        comp.addresses = entity;
        addressesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(addressesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.addresses = entity;
        addressesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(addressesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAddresses = { id: 123 };
        addressesServiceStub.find.resolves(foundAddresses);
        addressesServiceStub.retrieve.resolves([foundAddresses]);

        // WHEN
        comp.beforeRouteEnter({ params: { addressesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.addresses).toBe(foundAddresses);
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
