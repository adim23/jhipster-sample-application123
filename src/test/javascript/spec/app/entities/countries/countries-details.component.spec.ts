/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CountriesDetailComponent from '@/entities/countries/countries-details.vue';
import CountriesClass from '@/entities/countries/countries-details.component';
import CountriesService from '@/entities/countries/countries.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Countries Management Detail Component', () => {
    let wrapper: Wrapper<CountriesClass>;
    let comp: CountriesClass;
    let countriesServiceStub: SinonStubbedInstance<CountriesService>;

    beforeEach(() => {
      countriesServiceStub = sinon.createStubInstance<CountriesService>(CountriesService);

      wrapper = shallowMount<CountriesClass>(CountriesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { countriesService: () => countriesServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCountries = { id: 123 };
        countriesServiceStub.find.resolves(foundCountries);

        // WHEN
        comp.retrieveCountries(123);
        await comp.$nextTick();

        // THEN
        expect(comp.countries).toBe(foundCountries);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCountries = { id: 123 };
        countriesServiceStub.find.resolves(foundCountries);

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
