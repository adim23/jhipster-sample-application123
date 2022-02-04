/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CitizensDetailComponent from '@/entities/citizens/citizens-details.vue';
import CitizensClass from '@/entities/citizens/citizens-details.component';
import CitizensService from '@/entities/citizens/citizens.service';
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
  describe('Citizens Management Detail Component', () => {
    let wrapper: Wrapper<CitizensClass>;
    let comp: CitizensClass;
    let citizensServiceStub: SinonStubbedInstance<CitizensService>;

    beforeEach(() => {
      citizensServiceStub = sinon.createStubInstance<CitizensService>(CitizensService);

      wrapper = shallowMount<CitizensClass>(CitizensDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { citizensService: () => citizensServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCitizens = { id: 123 };
        citizensServiceStub.find.resolves(foundCitizens);

        // WHEN
        comp.retrieveCitizens(123);
        await comp.$nextTick();

        // THEN
        expect(comp.citizens).toBe(foundCitizens);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCitizens = { id: 123 };
        citizensServiceStub.find.resolves(foundCitizens);

        // WHEN
        comp.beforeRouteEnter({ params: { citizensId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.citizens).toBe(foundCitizens);
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
