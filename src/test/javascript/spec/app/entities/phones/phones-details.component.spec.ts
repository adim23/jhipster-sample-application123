/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PhonesDetailComponent from '@/entities/phones/phones-details.vue';
import PhonesClass from '@/entities/phones/phones-details.component';
import PhonesService from '@/entities/phones/phones.service';
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
  describe('Phones Management Detail Component', () => {
    let wrapper: Wrapper<PhonesClass>;
    let comp: PhonesClass;
    let phonesServiceStub: SinonStubbedInstance<PhonesService>;

    beforeEach(() => {
      phonesServiceStub = sinon.createStubInstance<PhonesService>(PhonesService);

      wrapper = shallowMount<PhonesClass>(PhonesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { phonesService: () => phonesServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPhones = { id: 123 };
        phonesServiceStub.find.resolves(foundPhones);

        // WHEN
        comp.retrievePhones(123);
        await comp.$nextTick();

        // THEN
        expect(comp.phones).toBe(foundPhones);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPhones = { id: 123 };
        phonesServiceStub.find.resolves(foundPhones);

        // WHEN
        comp.beforeRouteEnter({ params: { phonesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.phones).toBe(foundPhones);
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
