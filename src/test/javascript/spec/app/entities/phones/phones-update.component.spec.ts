/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PhonesUpdateComponent from '@/entities/phones/phones-update.vue';
import PhonesClass from '@/entities/phones/phones-update.component';
import PhonesService from '@/entities/phones/phones.service';

import PhoneTypesService from '@/entities/phone-types/phone-types.service';

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
  describe('Phones Management Update Component', () => {
    let wrapper: Wrapper<PhonesClass>;
    let comp: PhonesClass;
    let phonesServiceStub: SinonStubbedInstance<PhonesService>;

    beforeEach(() => {
      phonesServiceStub = sinon.createStubInstance<PhonesService>(PhonesService);

      wrapper = shallowMount<PhonesClass>(PhonesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          phonesService: () => phonesServiceStub,
          alertService: () => new AlertService(),

          phoneTypesService: () =>
            sinon.createStubInstance<PhoneTypesService>(PhoneTypesService, {
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
        comp.phones = entity;
        phonesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(phonesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.phones = entity;
        phonesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(phonesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPhones = { id: 123 };
        phonesServiceStub.find.resolves(foundPhones);
        phonesServiceStub.retrieve.resolves([foundPhones]);

        // WHEN
        comp.beforeRouteEnter({ params: { phonesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.phones).toBe(foundPhones);
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
