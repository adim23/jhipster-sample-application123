/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import OriginsDetailComponent from '@/entities/origins/origins-details.vue';
import OriginsClass from '@/entities/origins/origins-details.component';
import OriginsService from '@/entities/origins/origins.service';
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
  describe('Origins Management Detail Component', () => {
    let wrapper: Wrapper<OriginsClass>;
    let comp: OriginsClass;
    let originsServiceStub: SinonStubbedInstance<OriginsService>;

    beforeEach(() => {
      originsServiceStub = sinon.createStubInstance<OriginsService>(OriginsService);

      wrapper = shallowMount<OriginsClass>(OriginsDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { originsService: () => originsServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOrigins = { id: 123 };
        originsServiceStub.find.resolves(foundOrigins);

        // WHEN
        comp.retrieveOrigins(123);
        await comp.$nextTick();

        // THEN
        expect(comp.origins).toBe(foundOrigins);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOrigins = { id: 123 };
        originsServiceStub.find.resolves(foundOrigins);

        // WHEN
        comp.beforeRouteEnter({ params: { originsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.origins).toBe(foundOrigins);
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
