/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CompanyKindsDetailComponent from '@/entities/company-kinds/company-kinds-details.vue';
import CompanyKindsClass from '@/entities/company-kinds/company-kinds-details.component';
import CompanyKindsService from '@/entities/company-kinds/company-kinds.service';
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
  describe('CompanyKinds Management Detail Component', () => {
    let wrapper: Wrapper<CompanyKindsClass>;
    let comp: CompanyKindsClass;
    let companyKindsServiceStub: SinonStubbedInstance<CompanyKindsService>;

    beforeEach(() => {
      companyKindsServiceStub = sinon.createStubInstance<CompanyKindsService>(CompanyKindsService);

      wrapper = shallowMount<CompanyKindsClass>(CompanyKindsDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { companyKindsService: () => companyKindsServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompanyKinds = { id: 123 };
        companyKindsServiceStub.find.resolves(foundCompanyKinds);

        // WHEN
        comp.retrieveCompanyKinds(123);
        await comp.$nextTick();

        // THEN
        expect(comp.companyKinds).toBe(foundCompanyKinds);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCompanyKinds = { id: 123 };
        companyKindsServiceStub.find.resolves(foundCompanyKinds);

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
