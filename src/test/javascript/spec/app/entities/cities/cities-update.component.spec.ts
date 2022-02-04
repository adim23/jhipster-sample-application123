/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CitiesUpdateComponent from '@/entities/cities/cities-update.vue';
import CitiesClass from '@/entities/cities/cities-update.component';
import CitiesService from '@/entities/cities/cities.service';

import CountriesService from '@/entities/countries/countries.service';

import RegionsService from '@/entities/regions/regions.service';
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
  describe('Cities Management Update Component', () => {
    let wrapper: Wrapper<CitiesClass>;
    let comp: CitiesClass;
    let citiesServiceStub: SinonStubbedInstance<CitiesService>;

    beforeEach(() => {
      citiesServiceStub = sinon.createStubInstance<CitiesService>(CitiesService);

      wrapper = shallowMount<CitiesClass>(CitiesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          citiesService: () => citiesServiceStub,
          alertService: () => new AlertService(),

          countriesService: () =>
            sinon.createStubInstance<CountriesService>(CountriesService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          regionsService: () =>
            sinon.createStubInstance<RegionsService>(RegionsService, {
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
        comp.cities = entity;
        citiesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(citiesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cities = entity;
        citiesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(citiesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCities = { id: 123 };
        citiesServiceStub.find.resolves(foundCities);
        citiesServiceStub.retrieve.resolves([foundCities]);

        // WHEN
        comp.beforeRouteEnter({ params: { citiesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.cities).toBe(foundCities);
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
