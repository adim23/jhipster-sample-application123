/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RegionsUpdateComponent from '@/entities/regions/regions-update.vue';
import RegionsClass from '@/entities/regions/regions-update.component';
import RegionsService from '@/entities/regions/regions.service';

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
  describe('Regions Management Update Component', () => {
    let wrapper: Wrapper<RegionsClass>;
    let comp: RegionsClass;
    let regionsServiceStub: SinonStubbedInstance<RegionsService>;

    beforeEach(() => {
      regionsServiceStub = sinon.createStubInstance<RegionsService>(RegionsService);

      wrapper = shallowMount<RegionsClass>(RegionsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          regionsService: () => regionsServiceStub,
          alertService: () => new AlertService(),

          countriesService: () =>
            sinon.createStubInstance<CountriesService>(CountriesService, {
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
        comp.regions = entity;
        regionsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(regionsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.regions = entity;
        regionsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(regionsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRegions = { id: 123 };
        regionsServiceStub.find.resolves(foundRegions);
        regionsServiceStub.retrieve.resolves([foundRegions]);

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
