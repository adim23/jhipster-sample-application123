/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RegionsDetailComponent from '@/entities/regions/regions-details.vue';
import RegionsClass from '@/entities/regions/regions-details.component';
import RegionsService from '@/entities/regions/regions.service';
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
  describe('Regions Management Detail Component', () => {
    let wrapper: Wrapper<RegionsClass>;
    let comp: RegionsClass;
    let regionsServiceStub: SinonStubbedInstance<RegionsService>;

    beforeEach(() => {
      regionsServiceStub = sinon.createStubInstance<RegionsService>(RegionsService);

      wrapper = shallowMount<RegionsClass>(RegionsDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { regionsService: () => regionsServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRegions = { id: 123 };
        regionsServiceStub.find.resolves(foundRegions);

        // WHEN
        comp.retrieveRegions(123);
        await comp.$nextTick();

        // THEN
        expect(comp.regions).toBe(foundRegions);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRegions = { id: 123 };
        regionsServiceStub.find.resolves(foundRegions);

        // WHEN
        comp.beforeRouteEnter({ params: { regionsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.regions).toBe(foundRegions);
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
