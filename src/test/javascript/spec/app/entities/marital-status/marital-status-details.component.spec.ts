/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MaritalStatusDetailComponent from '@/entities/marital-status/marital-status-details.vue';
import MaritalStatusClass from '@/entities/marital-status/marital-status-details.component';
import MaritalStatusService from '@/entities/marital-status/marital-status.service';
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
  describe('MaritalStatus Management Detail Component', () => {
    let wrapper: Wrapper<MaritalStatusClass>;
    let comp: MaritalStatusClass;
    let maritalStatusServiceStub: SinonStubbedInstance<MaritalStatusService>;

    beforeEach(() => {
      maritalStatusServiceStub = sinon.createStubInstance<MaritalStatusService>(MaritalStatusService);

      wrapper = shallowMount<MaritalStatusClass>(MaritalStatusDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { maritalStatusService: () => maritalStatusServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMaritalStatus = { id: 123 };
        maritalStatusServiceStub.find.resolves(foundMaritalStatus);

        // WHEN
        comp.retrieveMaritalStatus(123);
        await comp.$nextTick();

        // THEN
        expect(comp.maritalStatus).toBe(foundMaritalStatus);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMaritalStatus = { id: 123 };
        maritalStatusServiceStub.find.resolves(foundMaritalStatus);

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
