/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ZipCodesUpdateComponent from '@/entities/zip-codes/zip-codes-update.vue';
import ZipCodesClass from '@/entities/zip-codes/zip-codes-update.component';
import ZipCodesService from '@/entities/zip-codes/zip-codes.service';

import CountriesService from '@/entities/countries/countries.service';

import RegionsService from '@/entities/regions/regions.service';

import CitiesService from '@/entities/cities/cities.service';
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
  describe('ZipCodes Management Update Component', () => {
    let wrapper: Wrapper<ZipCodesClass>;
    let comp: ZipCodesClass;
    let zipCodesServiceStub: SinonStubbedInstance<ZipCodesService>;

    beforeEach(() => {
      zipCodesServiceStub = sinon.createStubInstance<ZipCodesService>(ZipCodesService);

      wrapper = shallowMount<ZipCodesClass>(ZipCodesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          zipCodesService: () => zipCodesServiceStub,
          alertService: () => new AlertService(),

          countriesService: () =>
            sinon.createStubInstance<CountriesService>(CountriesService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          regionsService: () =>
            sinon.createStubInstance<RegionsService>(RegionsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          citiesService: () =>
            sinon.createStubInstance<CitiesService>(CitiesService, {
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
        comp.zipCodes = entity;
        zipCodesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(zipCodesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.zipCodes = entity;
        zipCodesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(zipCodesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundZipCodes = { id: 123 };
        zipCodesServiceStub.find.resolves(foundZipCodes);
        zipCodesServiceStub.retrieve.resolves([foundZipCodes]);

        // WHEN
        comp.beforeRouteEnter({ params: { zipCodesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.zipCodes).toBe(foundZipCodes);
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
