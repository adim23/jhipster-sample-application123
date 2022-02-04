/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CountriesUpdateComponent from '@/entities/countries/countries-update.vue';
import CountriesClass from '@/entities/countries/countries-update.component';
import CountriesService from '@/entities/countries/countries.service';

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
  describe('Countries Management Update Component', () => {
    let wrapper: Wrapper<CountriesClass>;
    let comp: CountriesClass;
    let countriesServiceStub: SinonStubbedInstance<CountriesService>;

    beforeEach(() => {
      countriesServiceStub = sinon.createStubInstance<CountriesService>(CountriesService);

      wrapper = shallowMount<CountriesClass>(CountriesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          countriesService: () => countriesServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.countries = entity;
        countriesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(countriesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.countries = entity;
        countriesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(countriesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCountries = { id: 123 };
        countriesServiceStub.find.resolves(foundCountries);
        countriesServiceStub.retrieve.resolves([foundCountries]);

        // WHEN
        comp.beforeRouteEnter({ params: { countriesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.countries).toBe(foundCountries);
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
