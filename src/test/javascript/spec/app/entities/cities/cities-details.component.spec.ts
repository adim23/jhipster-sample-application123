/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CitiesDetailComponent from '@/entities/cities/cities-details.vue';
import CitiesClass from '@/entities/cities/cities-details.component';
import CitiesService from '@/entities/cities/cities.service';
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
  describe('Cities Management Detail Component', () => {
    let wrapper: Wrapper<CitiesClass>;
    let comp: CitiesClass;
    let citiesServiceStub: SinonStubbedInstance<CitiesService>;

    beforeEach(() => {
      citiesServiceStub = sinon.createStubInstance<CitiesService>(CitiesService);

      wrapper = shallowMount<CitiesClass>(CitiesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { citiesService: () => citiesServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCities = { id: 123 };
        citiesServiceStub.find.resolves(foundCities);

        // WHEN
        comp.retrieveCities(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cities).toBe(foundCities);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCities = { id: 123 };
        citiesServiceStub.find.resolves(foundCities);

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
