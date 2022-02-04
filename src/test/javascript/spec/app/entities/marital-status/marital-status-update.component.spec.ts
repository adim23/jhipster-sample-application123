/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MaritalStatusUpdateComponent from '@/entities/marital-status/marital-status-update.vue';
import MaritalStatusClass from '@/entities/marital-status/marital-status-update.component';
import MaritalStatusService from '@/entities/marital-status/marital-status.service';

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
  describe('MaritalStatus Management Update Component', () => {
    let wrapper: Wrapper<MaritalStatusClass>;
    let comp: MaritalStatusClass;
    let maritalStatusServiceStub: SinonStubbedInstance<MaritalStatusService>;

    beforeEach(() => {
      maritalStatusServiceStub = sinon.createStubInstance<MaritalStatusService>(MaritalStatusService);

      wrapper = shallowMount<MaritalStatusClass>(MaritalStatusUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          maritalStatusService: () => maritalStatusServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.maritalStatus = entity;
        maritalStatusServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(maritalStatusServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.maritalStatus = entity;
        maritalStatusServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(maritalStatusServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMaritalStatus = { id: 123 };
        maritalStatusServiceStub.find.resolves(foundMaritalStatus);
        maritalStatusServiceStub.retrieve.resolves([foundMaritalStatus]);

        // WHEN
        comp.beforeRouteEnter({ params: { maritalStatusId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.maritalStatus).toBe(foundMaritalStatus);
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
