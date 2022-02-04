/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CompaniesDetailComponent from '@/entities/companies/companies-details.vue';
import CompaniesClass from '@/entities/companies/companies-details.component';
import CompaniesService from '@/entities/companies/companies.service';
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
  describe('Companies Management Detail Component', () => {
    let wrapper: Wrapper<CompaniesClass>;
    let comp: CompaniesClass;
    let companiesServiceStub: SinonStubbedInstance<CompaniesService>;

    beforeEach(() => {
      companiesServiceStub = sinon.createStubInstance<CompaniesService>(CompaniesService);

      wrapper = shallowMount<CompaniesClass>(CompaniesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { companiesService: () => companiesServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompanies = { id: 123 };
        companiesServiceStub.find.resolves(foundCompanies);

        // WHEN
        comp.retrieveCompanies(123);
        await comp.$nextTick();

        // THEN
        expect(comp.companies).toBe(foundCompanies);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCompanies = { id: 123 };
        companiesServiceStub.find.resolves(foundCompanies);

        // WHEN
        comp.beforeRouteEnter({ params: { companiesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.companies).toBe(foundCompanies);
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
