/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CompanyKindsUpdateComponent from '@/entities/company-kinds/company-kinds-update.vue';
import CompanyKindsClass from '@/entities/company-kinds/company-kinds-update.component';
import CompanyKindsService from '@/entities/company-kinds/company-kinds.service';

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
  describe('CompanyKinds Management Update Component', () => {
    let wrapper: Wrapper<CompanyKindsClass>;
    let comp: CompanyKindsClass;
    let companyKindsServiceStub: SinonStubbedInstance<CompanyKindsService>;

    beforeEach(() => {
      companyKindsServiceStub = sinon.createStubInstance<CompanyKindsService>(CompanyKindsService);

      wrapper = shallowMount<CompanyKindsClass>(CompanyKindsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          companyKindsService: () => companyKindsServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.companyKinds = entity;
        companyKindsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(companyKindsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.companyKinds = entity;
        companyKindsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(companyKindsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCompanyKinds = { id: 123 };
        companyKindsServiceStub.find.resolves(foundCompanyKinds);
        companyKindsServiceStub.retrieve.resolves([foundCompanyKinds]);

        // WHEN
        comp.beforeRouteEnter({ params: { companyKindsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.companyKinds).toBe(foundCompanyKinds);
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
